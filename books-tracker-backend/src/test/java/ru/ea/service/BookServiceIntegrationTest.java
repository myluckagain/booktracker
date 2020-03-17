package ru.ea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.ea.dao.*;
import ru.ea.model.Book;
import ru.ea.model.Site;
import ru.ea.model.SiteEnum;
import ru.ea.model.Visit;
import ru.otus.ea.dao.*;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional //to rollback

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService;
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

    @Autowired
    private EntityManager em;


    @Test
    public void whenInsertTheSameBook_thenReturnEmpty() {
        Site labirintSite = siteDao.findByName(SiteEnum.LABIRINT.name());
        Visit visit = new Visit(new Date(), labirintSite);
        String[] authorNames = {"a1", "a2"};
        String[] genreNames = {"g1", "g2"};
        visitDao.save(visit);
        Optional<Book> optBook = bookService.insert("b1", authorNames, genreNames, "",visit);
        assertTrue(optBook.isEmpty());
    }

    @Test
    public void givenNewAuthor_whenInsertANewBook_thenReturnBook() {
        Site labirintSite = siteDao.findByName(SiteEnum.LABIRINT.name());
        Visit visit = new Visit(new Date(), labirintSite);
        String[] authorNames = {"a1", "a4"};
        String[] genreNames = {"g1", "g2"};
        visitDao.save(visit);
        Optional<Book> optBook = bookService.insert("b1", authorNames, genreNames,"url", visit);
        assertEquals("b1", optBook.get().getName());
        assertEquals(2, bookDao.findByNameAndSite("b1", labirintSite).size());
        assertFalse(authorDao.findOptionalByName("a4").isEmpty());
    }

    @Test
    public void givenOldAuthor_whenInsertANewBook_thenReturnBook() {
        Site labirintSite = siteDao.findByName(SiteEnum.LABIRINT.name());
        Visit visit = new Visit(new Date(), labirintSite);
        String[] authorNames = {"a1", "a3"};
        String[] genreNames = {"g1", "g2"};
        em.persist(visit);
        Optional<Book> optBook = bookService.insert("b1", authorNames,  genreNames, "url",visit);
        assertEquals("b1", optBook.get().getName());
        assertEquals(2, bookDao.findByNameAndSite("b1", labirintSite).size());

    }

    @Test
    public void givenAuthorKeywords_whenFindBooks_thenCorrect(){
        String[] authorNames= {"a1", "a2"};
        assertEquals(2,  bookService.findByAuthorKeywords(authorNames, 0).getContent().size());

        String[] authorKeyword= {"a"};
        assertEquals(2,  bookService.findByAuthorKeywords(authorKeyword, 0).getContent().size());

        String[] authorAnotherKeyword= {"a1"};
        assertEquals(1,  bookService.findByAuthorKeywords(authorAnotherKeyword, 0).getContent().size());
    }
}
