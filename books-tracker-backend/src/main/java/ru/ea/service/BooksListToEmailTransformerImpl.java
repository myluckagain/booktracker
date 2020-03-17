package ru.ea.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.ea.model.Book;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class BooksListToEmailTransformerImpl implements BooksListToEmailTransformer {


    @Value("${app.server-email}")
    private String serverEmail;

    @Override
    public SimpleMailMessage transform(List<Book> books, String email, String name) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom(this.serverEmail);
        mailMessage.setSubject("Новые книги для "+name);
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        books.forEach(book -> {
            joiner.add(
                    book.getVisit().getDate().toString() + " "
                            + book.getName() + "; "
                            + book.getAuthors().stream().map(a-> a.getName()).collect( Collectors.joining( "," ) )
                            + "; "
                            + book.getGenres().stream().map(g-> g.getName()).collect( Collectors.joining( "," ) ));
        });
        mailMessage.setText(joiner.toString());

        return mailMessage;
    }
}
