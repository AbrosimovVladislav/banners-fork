package ru.promopult.aibannersgenerator.service.scrapper;

import org.jsoup.nodes.Document;

public interface ScraperStrategy {

  boolean isApplicable(String url);

  String scrapContent(StringBuilder content, Document doc);
}
