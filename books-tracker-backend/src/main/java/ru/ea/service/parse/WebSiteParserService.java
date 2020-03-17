package ru.ea.service.parse;

import ru.ea.model.Book;

import java.io.IOException;
import java.util.List;

public interface WebSiteParserService {

    List<String> getLinks() throws IOException;

    Book getBook(String link) ;

}
