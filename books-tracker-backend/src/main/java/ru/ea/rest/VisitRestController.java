package ru.ea.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ea.ConfigProperties;
import ru.ea.dao.VisitRepository;
import ru.ea.model.Visit;
import ru.ea.service.BookService;
import ru.ea.model.Book;

import java.util.UUID;

@AllArgsConstructor
@RestController

public class VisitRestController {
    private final BookService bookService;
    private final VisitRepository visitDao;
    private final ConfigProperties prop;

    @GetMapping("/api/visits")
    @ResponseStatus(HttpStatus.OK)
    public Page<Visit> getAll(@RequestParam(required = false, defaultValue = "0") int page) {
        return visitDao.findAll(PageRequest.of(page, prop.getItemsPerPage()));
    }


    @GetMapping("/api/visit/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    public Page<Book> getBooks(@PathVariable("id") UUID id, @RequestParam(required = false, defaultValue = "0") int page)
    {
        return bookService.findByVisit(id, PageRequest.of(page, prop.getItemsPerPage()));
    }

}

