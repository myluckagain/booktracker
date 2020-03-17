package ru.ea.service.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ea.model.Author;
import ru.ea.model.Genre;
import ru.ea.exceptions.ParsePageException;
import ru.ea.model.Book;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class WebSiteParserServiceImpl implements WebSiteParserService {

    String bookSiteUrl;
    private static final Logger logger = LoggerFactory.getLogger(WebSiteParserServiceImpl.class);
    protected abstract String getLinksCssSelector();

    protected abstract String[] getGenreNames(Document doc);

    protected abstract String[] getAuthorNames(Document doc);

    protected abstract String getBookName(Document doc);

    public List<String> getLinks() throws IOException {
        System.out.println("GET-LINKS");

        Document doc = Jsoup.connect(this.bookSiteUrl).get();
        System.out.println(doc.title());

        Elements links = doc.select(getLinksCssSelector());
        return links.stream().map(link -> link.attr("abs:href"))

                .collect(Collectors.toList());
    }


    public Book getBook(String link) {
        System.out.println("CONNECT AND GET-BOOK " + link);
        Book book = null;
        Document doc;
        try {

            doc = Jsoup.connect(link).userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com").get();
            String[] genreNames = getGenreNames(doc);
            Set<Genre> genres= Arrays.stream(genreNames).map(name->new Genre(name)).collect(Collectors.toSet());

            String[] authorNames = getAuthorNames(doc);
            Set<Author> authors= Arrays.stream(authorNames).map(name->new Author(name)).collect(Collectors.toSet());


            String name = getBookName(doc);

            book = new Book(name, authors, genres);
            return book;

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new ParsePageException("Ошибка при загрузке страницы книги");

        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new ParsePageException("Ошибка при парсинге страницы книги");
        }


    }

}


