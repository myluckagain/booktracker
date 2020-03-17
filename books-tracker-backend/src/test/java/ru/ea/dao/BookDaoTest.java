package ru.ea.dao;


import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ea.model.*;
import ru.otus.ea.model.*;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoTest {
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

    @BeforeEach
    public void addOneMoreB1Book() {
        Site labirintSite = siteDao.findByName(SiteEnum.LABIRINT.name());
        Visit visit = new Visit(new Date(), labirintSite);
        Book anotherBook = new Book("b1", null, null, visit);
        em.persist(visit);
        em.persist(anotherBook);
    }


    @Test
    public void when_findBookByNameAndSite_thenCorrect() {
        Site labirintSite = siteDao.findByName(SiteEnum.LABIRINT.name());

        List<Book> allB1Books = bookDao.findByName("b1");
        assertEquals(2, allB1Books.size());

        List<Book> anotherBooks = bookDao.findByNameAndSite("b1", labirintSite);
        assertEquals(2, anotherBooks.size());
    }

    @Test
    public void when_FindBook_thenCorrect() {
       String[] authorNames={"a1"};
        BooleanBuilder builder = new BooleanBuilder();

        for (int i = 0; i < authorNames.length; i++) {
            builder.or(QBook.book.authors.any().name.contains(authorNames[i]));
        }
        List books=(List<Book>)   bookDao.findAll(builder);

        assertEquals(1, books.size());


    }


 }
