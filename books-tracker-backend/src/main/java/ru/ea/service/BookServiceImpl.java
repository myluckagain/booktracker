package ru.ea.service;

import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ea.dao.*;
import ru.ea.model.Author;
import ru.ea.model.Genre;
import ru.ea.model.Visit;
import ru.ea.ConfigProperties;
import ru.otus.ea.dao.*;
import ru.ea.model.Book;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookDao;
    private final GenreRepository genreDao;
    private final AuthorRepository authorDao;
    private final VisitRepository visitDao;
    private final ConfigProperties prop;
    @Override
    public Optional<Book> insert(String bookName, String[] authorNames, String[] genreNames, String url, Visit visit) {


        List<Book> books = bookDao.findByNameAndSite(bookName, visit.getSite());

        for (int i = 0; i < books.size(); i++) {
            if (authorAreTheSame(authorNames, books.get(i).getAuthors()) &&
                    genresAreTheSame(genreNames, books.get(i).getGenres())) {
                return Optional.empty();
            }
        }

        Set<Author> authors = createAuthors(authorNames);
        Book book = new Book(bookName, visit);
        book.setUrl(url);
        book.setAuthors(authors);
        Set<Genre> genres = createGenres(genreNames);
        book.setGenres(genres);
        return Optional.of(bookDao.save(book));

    }

    private Set<Genre> createGenres(String[] genreNames) {
        Set<Genre> genres = new HashSet<>();
        for (int i = 0; i < genreNames.length; i++) {
            String genreName = genreNames[i];
            Optional<Genre> genre = this.genreDao.findOptionalByName(genreName);
            genre.ifPresentOrElse(g -> genres.add(g)
                    , () -> {
                        Genre newGenre = new Genre(genreName);
                        genres.add(this.genreDao.save(newGenre));
                    });
        }
        return genres;
    }

    private Set<Author> createAuthors(String[] authorNames) {
        Set<Author> authors = new HashSet<>();
        for (int i = 0; i < authorNames.length; i++) {
            String authorName = authorNames[i];
            Optional<Author> author = this.authorDao.findOptionalByName(authorName);
            author.ifPresentOrElse(a -> authors.add(a)
                    , () -> {
                        Author newAuthor = new Author(authorName);
                        authors.add(this.authorDao.save(newAuthor));
                    });
        }
        return authors;
    }

    private boolean genresAreTheSame(String[] genreNames, Set<Genre> genres) {
        Set set1 = Arrays.stream(genreNames).collect(Collectors.toSet());
        Set set2 = genres.stream().map(g -> g.getName()).collect(Collectors.toSet());
        return set1.equals(set2);
    }

    private boolean authorAreTheSame(String[] authorNames, Set<Author> authors) {

        Set set1 = Arrays.stream(authorNames).collect(Collectors.toSet());
        Set set2 = authors.stream().map(a -> a.getName()).collect(Collectors.toSet());
        return set1.equals(set2);
    }

    public Page<Book> findByAuthorKeywords(String[] authorNames, int page) {
        BooleanBuilder predicate=new BooksPredicateBuilder().authorsInLike(authorNames).build();
        return bookDao.findAll(predicate, PageRequest.of(page, prop.getItemsPerPage(), Sort.by(Sort.Direction.ASC, "name")));
    }



    public  Page<Book> findByVisit(UUID id, Pageable pageable) {
        return bookDao.findByVisit_Id(id,  pageable);
    }
}
