package ru.ea.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ea.exceptions.ParsePageException;
import ru.ea.model.Book;


@EnableBatchProcessing
@Configuration
public class JobAndStepConfig {

    private static final Logger logger = LoggerFactory.getLogger(JobAndStepConfig.class);

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private ItemWriter<Book> writer;

    @Autowired
    private ListItemReader<String> reader;


    @Autowired
    @Qualifier("linksToBooksProcessor")
    private ItemProcessor<String, Book> linksToBooksProcessor;

    @Bean
    public Job copyBooksFromSiteToDatabase() {
        return jobBuilderFactory.get("copyBooksFromSiteToDatabase")

                .incrementer(new RunIdIncrementer())
                .start(oneStep())
                .build();
    }

    Step oneStep() {
        return stepBuilderFactory.get("step1")

                .<String, Book>chunk(500)
                .reader(reader)
                .processor(linksToBooksProcessor)
                .writer(writer)
                .faultTolerant()
                .skip(ParsePageException.class)
                .skipLimit(3)
                .build();
    }

}
