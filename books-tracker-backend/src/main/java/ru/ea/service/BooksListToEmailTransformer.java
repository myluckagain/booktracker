package ru.ea.service;

import org.springframework.mail.SimpleMailMessage;
import ru.ea.model.Book;

import java.util.List;

public interface BooksListToEmailTransformer {
    SimpleMailMessage transform(List<Book> books, String email, String name);
}
