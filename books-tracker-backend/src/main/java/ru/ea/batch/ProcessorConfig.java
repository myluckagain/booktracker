package ru.ea.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ea.model.Book;
import ru.ea.model.SiteEnum;
import ru.ea.service.parse.WebSiteParserService;

@Configuration
public class ProcessorConfig {
    private final Logger logger = LoggerFactory.getLogger(ProcessorConfig.class);

    @Autowired
    @Qualifier("fknigaParserService")
    private WebSiteParserService fknigaParserService;

    @Autowired
    @Qualifier("labirintParserService")
    private WebSiteParserService labirintParserService;


    @Bean("linksToBooksProcessor")
    @StepScope
    public ItemProcessor<String, Book> linksToBooksProcessor(
            @Value("#{jobParameters[site]}") String site
    ) {
        return link -> {
            Book book=new Book();
            if (site.equals(SiteEnum.FKNIGA.name())) {
                book = fknigaParserService.getBook(link);
            }
            if (site.equals(SiteEnum.LABIRINT.name())) {
                book = labirintParserService.getBook(link);
            }

            logger.info(String.format("linksToBooksProcessor Downloaded book info: %s", book.toString()));

            return new Book(book.getName(), book.getAuthors(), book.getGenres(), link);

        };
    }



}
