package ru.ea.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ea.model.SiteEnum;
import ru.ea.service.parse.WebSiteParserService;

import java.io.IOException;
import java.util.List;

@Configuration
public class ReaderConfig {

    private static final Logger logger = LoggerFactory.getLogger(ReaderConfig.class);
    @Autowired
    @Qualifier("fknigaParserService")
    private WebSiteParserService fknigaParserService;

    @Autowired
    @Qualifier("labirintParserService")
    private WebSiteParserService labirintParserService;

    @Bean
    @StepScope
    public ListItemReader<String> reader(
            @Value("#{jobParameters[site]}") String site
    ) throws IOException {
        List<String> items = fknigaParserService.getLinks();

        if (site.equals(SiteEnum.LABIRINT.name())) {
            items = labirintParserService.getLinks();
        }
        System.out.println("Hello from ListItemReader!!! " + site);

        return new ListItemReader<>(items);
    }


}
