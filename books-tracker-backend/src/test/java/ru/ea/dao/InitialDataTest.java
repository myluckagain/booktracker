package ru.ea.dao;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ea.model.Book;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InitialDataTest {
    @Autowired
    private GenreRepository genreDao;
    @Autowired
    private AuthorRepository authorDao;
    @Autowired
    private BookRepository bookDao;
    @Autowired
    private VisitRepository visitDao;
    @Autowired
    private SiteRepository siteDao;

    @Test
    public void whenCountSites_thenCorrect() {
        assertEquals(2, siteDao.count());
    }

    @Test
    public void whenCountAuthors_thenCorrect() {
        assertEquals(12, authorDao.count());
    }

    @Test
    public void whenCountGenres_thenCorrect() {
        assertEquals(18, genreDao.count());
    }

    @Test
    public void whenCountVisit_thenCorrect() {
        assertEquals(2, visitDao.count());
    }

    @Test
    public void whenCountBooks_thenCorrect() {
        assertEquals(10, bookDao.count());
    }

    @Test
    public void givenAuthor_whenCheckBooks_thenCorrect() {

        Book b1 = bookDao.findOptionalByName("b1").get();
        Set<String> expectedAuthors = new HashSet<String>(Arrays.asList("a1", "a2"));
        Set<String> actualAuthors = b1.getAuthors().stream().map(a -> a.getName()).collect(Collectors.toSet());

        assertEquals(expectedAuthors, actualAuthors);


        Book b2 = bookDao.findOptionalByName("b2").get();
        Set<String> expectedAuthors2 = new HashSet<String>(Arrays.asList("a2", "a3"));
        Set<String> actualAuthors2 = b2.getAuthors().stream().map(a -> a.getName()).collect(Collectors.toSet());

        assertEquals(expectedAuthors, actualAuthors);

    }

    @Test
    public void givenGenres_whenCheckBook_thenCorrect() {

        Book b1 = bookDao.findOptionalByName("b1").get();
        Set<String> expectedGenres = new HashSet<String>(Arrays.asList("g1", "g2"));
        Set<String> actualGenres = b1.getGenres().stream().map(a -> a.getName()).collect(Collectors.toSet());

        assertEquals(expectedGenres, actualGenres);

    }
}
