package ru.ea.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ea.model.Site;
import ru.ea.model.SiteEnum;
import ru.ea.model.Visit;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VisitDaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private VisitRepository visitDao;
    @Autowired
    private SiteRepository siteDao;

   @BeforeEach
    public void init() {
        Site site = siteDao.findByName(SiteEnum.LABIRINT.name());
        Visit v1 = new Visit(new Date(), site);
        visitDao.save(v1);

    }

    @Test
    public void whenFindByEntity_thenOk(){
        Site site = siteDao.findByName(SiteEnum.LABIRINT.name());
        Visit v1 = new Visit(new Date(), site);
        visitDao.save(v1);

        Visit visit=visitDao.findBySite(site);

        assertEquals(v1, visit);
    }

    @Test
    public void whenFindByPropertyOfNestedEntity_thenOk(){
        Site site = siteDao.findByName(SiteEnum.LABIRINT.name());
        Visit v1 = new Visit(new Date(), site);
        visitDao.save(v1);

        Visit visit=visitDao.findBySiteName(SiteEnum.LABIRINT.name());

        assertEquals(v1, visit);
    }
}
