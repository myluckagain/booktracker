package ru.ea.service.parse;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ea.model.SiteEnum;
import ru.ea.dao.SiteRepository;

import javax.annotation.PostConstruct;

@Service("labirintParserService")
public class LabirintParserService extends WebSiteParserServiceImpl {
    @Autowired
    private SiteRepository siteDao;

    @PostConstruct
    void init(){
        this.bookSiteUrl = this.siteDao.findByName(SiteEnum.LABIRINT.name()).getUrl();
    }


    @Override
    protected String getLinksCssSelector() {
        return "div.carousel-trends a.cover-tooltip";
    }

    @Override
    protected String[] getGenreNames(Document doc) {
        Elements genreElements = doc.select("span.thermo-item span[itemprop=title]");
        return  genreElements.stream().map(el -> el.text()).toArray(String[]::new);
    }

    @Override
    protected String[] getAuthorNames(Document doc) {
        Element authorsDivElement = doc.selectFirst("div.authors");
        String[] authors = {};
        if (authorsDivElement.text().contains("Автор")) {
            Elements a = authorsDivElement.select("a");
            authors =  a.stream().map(al -> al.text()).toArray(String[]::new);
        }
        return authors;
    }

    @Override
    protected String getBookName(Document doc) {
        Element nameElement = doc.selectFirst("div[id=product-title] h1");
        String name = nameElement.text().split(":")[1].trim();
        return name;
    }
}
