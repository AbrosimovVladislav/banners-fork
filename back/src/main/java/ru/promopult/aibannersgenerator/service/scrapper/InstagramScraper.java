package ru.promopult.aibannersgenerator.service.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InstagramScraper implements ScraperStrategy {

  @Override
  public boolean isApplicable(String url) {
    return url.contains("instagram.com");
  }

  @Override
  public String scrapContent(StringBuilder content, Document doc) {
    String title = doc.title();
    content.append(title);

    Elements paragraphs = doc.select("p");
    for (Element p : paragraphs) {
      content.append(p.text());
    }

    return content.toString();
  }
}
