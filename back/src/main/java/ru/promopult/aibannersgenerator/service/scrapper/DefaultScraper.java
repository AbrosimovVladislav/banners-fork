package ru.promopult.aibannersgenerator.service.scrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultScraper implements ScraperStrategy {

  @Override
  public boolean isApplicable(String url) {
    return true;
  }

  @Override
  public String scrapContent(StringBuilder content, Document doc) {
    String title = doc.title();
    content.append(title).append(" ");

    Elements paragraphs = doc.select("p");
    for (Element p : paragraphs) {
      content.append(p.text()).append(" ");
    }

    if(content.toString().split(" ").length<20){
      Elements divs = doc.select("div");
      for (Element div : divs) {
        content.append(div.text()).append(" ");
      }
    }

    return content.toString();
  }
}

