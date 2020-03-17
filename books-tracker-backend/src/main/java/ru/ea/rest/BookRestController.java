package ru.ea.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ea.ConfigProperties;
import ru.ea.dao.AuthorRepository;
import ru.ea.dao.BookRepository;
import ru.ea.service.BookService;
import ru.ea.dao.GenreRepository;
import ru.ea.model.Book;

@AllArgsConstructor
@RestController
public class BookRestController {

    private final BookService bookService;
    private final ConfigProperties prop;
    private final AuthorRepository authorDao;
    private final GenreRepository genreDao;
    private final BookRepository bookDao;

    @GetMapping("/api/books")
    public Page<Book> get(@RequestParam(required = false, defaultValue = "0") int page,
                          @RequestParam(required = false, defaultValue = "") String key) {
        return bookDao.findAll(PageRequest.of(page, prop.getItemsPerPage()));
    }


}
