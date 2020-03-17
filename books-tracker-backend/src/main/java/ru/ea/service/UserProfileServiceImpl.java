package ru.ea.service;

import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ea.model.Notification;
import ru.ea.model.User;
import ru.ea.ConfigProperties;
import ru.ea.dao.BookRepository;
import ru.ea.dao.BooksPredicateBuilder;
import ru.ea.dao.NotificationRepository;
import ru.ea.dao.UserRepository;
import ru.ea.dto.UserProfileDto;
import ru.ea.model.Book;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private NotificationRepository notificationRepository;
    final private BookRepository bookDao;
    final private ConfigProperties prop;


    @Override
    public List<String> saveAuthors(User user, List<String> authors) {
        user.setAuthors(authors);
        userRepository.save(user);
        return user.getAuthors();
    }

    @Override
    public List<String> saveGenres(User user, List<String> genres) {
        user.setGenres(genres);
        userRepository.save(user);
        return user.getGenres();
    }

    @Override
    public List<String> saveBooks(User user, List<String> books) {
        user.setBooks(books);
        userRepository.save(user);
        return user.getBooks();
    }

    @Override
    public List<Book> selectNewUserBooks(User user) {
        System.out.println("selectNewUserBooks");
        Date x = user.getCreateDate();
        Notification lastNotification = notificationRepository.findTopByOrderByCreateDateDesc();
        if (lastNotification != null) {
            Date lastNotificationDate = lastNotification.getCreateDate();
            if (lastNotificationDate.compareTo(user.getCreateDate()) > 0) {
                x = lastNotificationDate;
            }
        }
        System.out.println("Ищем книги после даты " + x + "для юзера " + user.getName());
        BooleanBuilder predicate = keysAndDatePredicate(user, x);
        return (List<Book>) bookDao.findAll(predicate);
    }

    public Page<Book> selectUserBooks(User user, int page) {

        BooleanBuilder predicate = keysPredicate(user);
        return bookDao.findAll(predicate, PageRequest.of(page, prop.getItemsPerPage(), Sort.by(Sort.Direction.ASC, "name")));
    }

    private BooleanBuilder keysPredicate(User user) {
        return keysPredicateBuilder(user)
                .build();
    }

    private BooleanBuilder keysAndDatePredicate(User user, Date date) {

        return keysPredicateBuilder(user)
                .createDateIsGreaterThan(date)
                .build();

    }

    BooksPredicateBuilder keysPredicateBuilder(User user) {
        String[] authorNames = user.getAuthors().toArray(new String[0]);
        String[] genreNames = user.getGenres().toArray(new String[0]);
        String[] bookNames = user.getBooks().toArray(new String[0]);

        BooksPredicateBuilder builder = new BooksPredicateBuilder()
                .authorsInLike(authorNames)
                .genresInLike(genreNames)
                .nameInLike(bookNames);
        return builder;
    }

    @Override
    public UserProfileDto updateProfile(User user, UserProfileDto userProfileDto) {

        if (userProfileDto.getEmail() != null && !userProfileDto.getEmail().isEmpty()) {
            user.setEmail(userProfileDto.getEmail());
        }
        if (userProfileDto.getPassword() != null && !userProfileDto.getEmail().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userProfileDto.getPassword());
            user.setPassword(encodedPassword);
        }
        userRepository.save(user);
        return new UserProfileDto(user.getEmail());

    }


}
