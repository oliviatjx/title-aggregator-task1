package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WiredScraper {

    public static class Article {
        private String title;
        private String link;
        private LocalDate date;

        public Article(String title, String link, LocalDate date) {
            this.title = title;
            this.link = link;
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public LocalDate getDate() {
            return date;
        }
    }

    public List<Article> getAllArticles() throws IOException {
        String baseUrl = "https://www.wired.com";
        List<Article> articleList = new ArrayList<>();
        String currentUrl = baseUrl;

        while (currentUrl != null) {
            Document doc = Jsoup.connect(currentUrl).get();
            Elements articles = doc.select("article");

            for (Element article : articles) {
                Element titleElement = article.selectFirst("h2");
                Element linkElement = article.selectFirst("a");

                if (titleElement != null && linkElement != null) {
                    String title = titleElement.text();
                    String link = linkElement.absUrl("href");
                    LocalDate publishedDate = fetchDateFromLink(link);

                    if (publishedDate.isAfter(LocalDate.of(2021, 12, 31))) {
                        articleList.add(new Article(title, link, publishedDate));
                    }
                }
            }

            // Find the "next page" link
            Element nextPage = doc.selectFirst(".pagination__next a"); // Adjust selector for Wired
            currentUrl = (nextPage != null) ? nextPage.absUrl("href") : null;
        }

        // Sort articles by date, latest first
        articleList.sort(Comparator.comparing(Article::getDate).reversed());
        return articleList;
    }

    private LocalDate fetchDateFromLink(String link) {
        // Parse the date from the article URL or metadata
        return LocalDate.now(); // Placeholder: Update to parse actual date
    }
}
