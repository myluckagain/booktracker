package ru.ea.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ea.ConfigProperties;
import ru.ea.model.Genre;
import ru.ea.dao.GenreRepository;

@AllArgsConstructor
@RestController

public class GenreRestController {
    private final GenreRepository genreDao;
    private final ConfigProperties prop;
    @GetMapping("/api/genres")
    public Page<Genre> get(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "") String key ) {
        return genreDao.findByNameContainingIgnoreCaseOrderByName(key, PageRequest.of(page, prop.getItemsPerPage()));
    }


}
