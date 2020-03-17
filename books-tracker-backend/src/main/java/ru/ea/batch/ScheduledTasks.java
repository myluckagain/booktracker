package ru.ea.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.ea.model.SiteEnum;
import ru.ea.integration.NotificationGateway;


@EnableScheduling
@Configuration
public class ScheduledTasks {
    @Autowired
    private NotificationGateway notificationGateway;

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;


    //@Scheduled(cron = "0 11 9 * * ?", zone = "GMT+5:00")
   // @Scheduled(initialDelay = 30000, fixedRate = 120000)


    //@Scheduled(initialDelay = 30000, fixedRate = 120000)
    public void loadBooksFromLabirintWebsite() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobInstanceAlreadyExistsException, NoSuchJobException {

        jobLauncher.run(job, new JobParametersBuilder()
                .addLong("launchTime", System.currentTimeMillis())
                .addString("site", SiteEnum.LABIRINT.name())
                .toJobParameters());
    }


    //@Scheduled(initialDelay = 3000, fixedRate = 120000)
    public void loadBooksFromFKnigaWebsite() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobInstanceAlreadyExistsException, NoSuchJobException {

        jobLauncher.run(job, new JobParametersBuilder()
                .addLong("launchTime", System.currentTimeMillis())
                .addString("site", SiteEnum.FKNIGA.name())
                .toJobParameters());
    }

    //@Scheduled(initialDelay = 10000, fixedRate = 300000)
    public void notifyUsersAboutNewBooks() {
        notificationGateway.notifyUsers("");
    }

}