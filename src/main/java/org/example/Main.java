package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WiredScraper scraper = new WiredScraper();
        try {
            List<WiredScraper.Article> articles = scraper.getAllArticles();

            // Generate HTML
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<!DOCTYPE html>");
            htmlContent.append("<html>");
            htmlContent.append("<head><title>Wired Articles</title></head>");
            htmlContent.append("<body>");
            htmlContent.append("<h1>All Wired Articles</h1>");
            htmlContent.append("<ul>");

            for (WiredScraper.Article article : articles) {
                htmlContent.append("<li><a href=\"")
                        .append(article.getLink())
                        .append("\" target=\"_blank\">")
                        .append(article.getTitle())
                        .append("</a></li>");
            }

            htmlContent.append("</ul>");
            htmlContent.append("</body>");
            htmlContent.append("</html>");

            // Save to an HTML file
            try (FileWriter writer = new FileWriter("all_articles.html")) {
                writer.write(htmlContent.toString());
                System.out.println("HTML file generated: all_articles.html");
            }

        } catch (IOException e) {
            System.err.println("Error fetching articles: " + e.getMessage());
        }
    }
}
