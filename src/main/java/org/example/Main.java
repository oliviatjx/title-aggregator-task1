package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "https://www.wired.com/";
        List<String> categoryLinks = new ArrayList<>();

        try {
            //Connect to the main Wired page
            Document doc = Jsoup.connect(url).get();

            //Extract category links
            Elements categoryElements = doc.select("a[href]");

            for (Element categoryElement : categoryElements) {
                String link = categoryElement.attr("href");
                if (link.contains("/category/")) {
                    if (!link.startsWith("http")) {
                        link = "https://www.wired.com" + link;
                    }
                    categoryLinks.add(link);
                }
            }

            //For debugging
            System.out.println("Extracted category links:");
            categoryLinks.forEach(System.out::println);

            //Generate HTML with category links
            generateHTML(categoryLinks);

        } catch (IOException e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
    }

    private static void generateHTML(List<String> categoryLinks) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<!DOCTYPE html>")
                .append("<html lang='en'>")
                .append("<head>")
                .append("<meta charset='UTF-8'>")
                .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>")
                .append("<title>Category Links</title>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; background: white; color: black; margin: 0; padding: 20px; }")
                .append("h1 { text-align: center; }")
                .append("a { color: black; text-decoration: none; }")
                .append("a:hover { text-decoration: underline; }")
                .append("ul { list-style: none; padding: 0; }")
                .append("li { margin: 10px 0; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<h1>Category Links</h1>")
                .append("<ul>");

        for (String link : categoryLinks) {
            htmlContent.append("<li><a href='").append(link).append("' target='_blank'>")
                    .append(link)
                    .append("</a></li>");
        }

        htmlContent.append("</ul>")
                .append("</body>")
                .append("</html>");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("category_links.html"))) {
            writer.write(htmlContent.toString());
            System.out.println("HTML file generated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing HTML file: " + e.getMessage());
        }
    }
}
