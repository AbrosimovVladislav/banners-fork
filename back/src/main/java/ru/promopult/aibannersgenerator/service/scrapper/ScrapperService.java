package ru.promopult.aibannersgenerator.service.scrapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.promopult.aibannersgenerator.exception.GetHtmlPageException;

@Service
@RequiredArgsConstructor
public class ScrapperService {

  private final List<ScraperStrategy> scraperStrategies;

  public String extractChaoticInfoFromUrl(String url) {
    StringBuilder chaoticContent = new StringBuilder();
    try {
      url = prepareUrl(url);
      Document doc = Jsoup.connect(url)
          .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
          .get();

      String finalUrl = url;
      ScraperStrategy applicableStrategy = scraperStrategies.stream()
          .filter(strategy -> strategy.isApplicable(finalUrl))
          .findFirst()
          .orElseThrow(
              () -> new IllegalArgumentException(
                  "No applicable strategy found for URL: " + finalUrl));

      chaoticContent.append(applicableStrategy.scrapContent(chaoticContent, doc));

    } catch (Exception e) {
      throw new GetHtmlPageException(url);
    }

    return chaoticContent.toString();
  }

  private String prepareUrl(String url) {
    if (!url.contains("http://") && !url.contains("https://")) {
      url = "http://" + url;
    }

    url = url.replaceAll(" ", "");

    return url;
  }
}
