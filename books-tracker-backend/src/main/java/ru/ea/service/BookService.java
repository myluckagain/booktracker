package ru.ea.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.ea.model.Visit;
import ru.ea.model.Book;

import java.util.Optional;
import java.util.UUID;

public interface BookService {

    Optional<Book> insert(String bookName, String[] authorNames, String[] genreNames, String url, Visit visit);
    Page<Book> findByAuthorKeywords(String[] authorNames, int page);

    Page<Book> findByVisit(UUID id, Pageable pageable);
}
