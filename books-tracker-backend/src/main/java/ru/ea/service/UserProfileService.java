package ru.ea.service;

import org.springframework.data.domain.Page;
import ru.ea.model.User;
import ru.ea.dto.UserProfileDto;
import ru.ea.model.Book;

import java.util.List;

public interface UserProfileService {

    List<String> saveAuthors(User user, List<String> authors);
    List<String> saveGenres(User user,  List<String> genres);
    List<String> saveBooks(User user,  List<String> books);

    Page<Book> selectUserBooks(User user, int page);
    List<Book> selectNewUserBooks(User user);
    UserProfileDto updateProfile(User user, UserProfileDto userProfileDto);
}
