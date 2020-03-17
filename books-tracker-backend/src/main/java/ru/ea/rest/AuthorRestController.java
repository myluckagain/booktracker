package ru.ea.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ea.ConfigProperties;
import ru.ea.dao.AuthorRepository;
import ru.ea.model.Author;

@AllArgsConstructor
@RestController
public class AuthorRestController {
    private final AuthorRepository authorDao;
    private final ConfigProperties prop;

    @GetMapping("/api/authors")
    public Page<Author> get(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "") String key) {
        return authorDao.findByNameContainingIgnoreCaseOrderByName(key, PageRequest.of(page, prop.getItemsPerPage()));
    }


}
