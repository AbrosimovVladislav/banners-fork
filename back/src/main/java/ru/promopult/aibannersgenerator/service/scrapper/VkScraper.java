package ru.promopult.aibannersgenerator.service.scrapper;

import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class VkScraper implements ScraperStrategy {

  @Override
  public boolean isApplicable(String url) {
    return url.contains("vk.com");
  }

  @Override
  public String scrapContent(StringBuilder content, Document doc) {
    String title = doc.title();
    if (StringUtils.hasLength(title)) {
      content.append(title);
    }

    var descriptionEl = doc.getElementsByClass("line_value");
    if(descriptionEl.size()>0){
      String description = descriptionEl.get(0).text();
      if (StringUtils.hasLength(description)) {
        content.append(description);
      }
    }

    var postTextEl = doc.getElementsByClass("wall_post_text");
    if(postTextEl.size()>0){
      String postTexts = postTextEl.stream()
          .map(Element::text)
          .collect(Collectors.joining("|||"));
      postTexts = postTexts.substring(0, postTexts.length() >= 1000 ? 1000 : postTexts.length() - 1);
      if (StringUtils.hasLength(postTexts)) {
        content.append(postTexts);
      }
    }

    var paragraphs = doc.select("p");
    for (Element p : paragraphs) {
      content.append(p.text());
    }

    return content.toString();
  }
}
