package ru.ea.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ea.model.Site;
import ru.ea.model.Visit;
import ru.ea.dao.SiteRepository;
import ru.ea.dao.VisitRepository;
import ru.ea.model.Book;
import ru.ea.service.BookService;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Component
public class BooksWriter implements ItemWriter<Book> {
    private static final Logger logger = LoggerFactory.getLogger(BooksWriter.class);
    @Autowired
    private VisitRepository visitDao;
    @Autowired
    private BookService bookService;
    @Autowired
    private SiteRepository siteDao;

    private StepExecution stepExecution;


    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(List<? extends Book> books) throws Exception {
        JobParameters jobParameters = this.stepExecution.getJobParameters();

        Site site = siteDao.findByName(jobParameters.getString("site"));
        Visit visit = new Visit(new Date(), site);
        visit.setBooksAdded(0);
        visit.setSuccess(false);
        visit.setSite(site);
        visit = visitDao.save(visit);
        int booksAdded = 0;

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            String bookName = book.getName();
            String url=book.getUrl();
            String[] authors = book.getAuthors().stream().map(a -> a.getName()).toArray(String[]::new);
            String[] genres = book.getGenres().stream().map(g -> g.getName()).toArray(String[]::new);
            Optional<Book> bookAdded = bookService.insert(bookName, authors, genres, url, visit);
            if (!bookAdded.isEmpty()) booksAdded++;
        }
        visit.setBooksAdded(booksAdded);
        visit.setSuccess(true);
        visitDao.save(visit);

        logger.info(String.format("WRITER: ДОБАВЛЕНО %d", booksAdded));
    }
}

