package ru.ea.integration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessageHeaders;
import ru.ea.dao.NotificationRepository;
import ru.ea.model.Book;
import ru.ea.model.Notification;
import ru.ea.service.BooksListToEmailTransformer;
import ru.ea.service.UserProfileService;
import ru.ea.service.UserService;

import java.util.List;


@IntegrationComponentScan
@AllArgsConstructor
@Configuration
public class IntegrationConfig {

    private final UserService userService;
    private final BooksListToEmailTransformer booksListToEmailTransformer;
    private final JavaMailSender mailSender;
    private final NotificationRepository notificationRepository;
    private final UserProfileService userProfileService;

    @Bean
    public IntegrationFlow notifyFlow() {

        return IntegrationFlows.from("notifyInChanel")

                .transform(empty -> userService.findAll())
                .split()
                .enrichHeaders(h ->
                        h.headerExpression("name", "payload.name"))
                .enrichHeaders(h ->
                        h.headerExpression("email", "payload.email"))

                .handle(userProfileService, "selectNewUserBooks")

                .enrichHeaders(h ->
                        h.headerExpression("size", "payload.size()"))
                .<List<Book>>handle(
                        (payload, headers) ->
                        {
                            if (headersCorrect(headers)) {
                                SimpleMailMessage simpleMailMessage = booksListToEmailTransformer.transform(payload, headers.get("email").toString(), headers.get("name").toString());
                                mailSender.send(simpleMailMessage);
                            }
                            return payload;
                        }
                )
                .aggregate()
                .log()
                .handle(m -> notificationRepository.save(new Notification()))
                .get();
    }

    private boolean headersCorrect(MessageHeaders headers) {
        Object bookListSize = headers.get("size");
        Object email = headers.get("email");
        return booksListIsNotEmpty(bookListSize) && emailIsPresent(email);
    }

    private boolean booksListIsNotEmpty(Object bookListSize) {
        return bookListSize != null && Integer.parseInt(bookListSize.toString()) > 0;

    }

    private boolean emailIsPresent(Object email) {
        return email != null && email.toString().length() > 0;
    }
}
