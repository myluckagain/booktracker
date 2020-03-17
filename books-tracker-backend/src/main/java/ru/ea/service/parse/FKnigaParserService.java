package ru.ea.service.parse;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ea.model.SiteEnum;
import ru.ea.dao.SiteRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service("fknigaParserService")
public class FKnigaParserService extends WebSiteParserServiceImpl {
    @Autowired
    private SiteRepository siteDao;

    @PostConstruct
    void init(){
        this.bookSiteUrl = this.siteDao.findByName(SiteEnum.FKNIGA.name()).getUrl();
    }

    @Override
    protected String getLinksCssSelector() {
        return "div.item > a[href]:eq(0)";
    }

    @Override
    protected String[] getGenreNames(Document doc) {
        Elements genreElements = doc.select("div.nav.noCat a");
        return genreElements.stream().skip(3).map(al -> al.text()).toArray(String[]::new);
    }

    @Override
    protected String[] getAuthorNames(Document doc) {
        Elements authorElements = doc.select("td.descData span.hChildrenLink a");
        if (authorElements.size() == 0) {
            Elements authorElementsWithoutLink = doc.select("td.descData span.hChildrenLink");
            if (authorElementsWithoutLink.text().trim() != "") {
                String[] dirtyArray = authorElementsWithoutLink.text().trim().split(",");
                String[] trimmedArray = Arrays.stream(dirtyArray).map(el -> el.trim()).toArray(String[]::new);
                return trimmedArray;
            }
        }
        return authorElements.stream().map(al -> al.text()).toArray(String[]::new);
    }

    @Override
    protected String getBookName(Document doc) {

        Element nameElement = doc.selectFirst("td.descData h1");
        return nameElement.text();
    }
}
