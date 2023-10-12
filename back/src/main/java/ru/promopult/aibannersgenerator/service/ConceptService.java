package ru.promopult.aibannersgenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.promopult.aibannersgenerator.service.scrapper.ScrapperService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConceptService {

  private static final String CONCEPT_SCHEMA = """
      "I gathered information about the company I'm interested in from its website.
      The information is extracted in raw form, meaning everything is in sequence.
            
        {chaoticInfo}

      Please analyze this information, and create a concept.
      Concept is short information about main service of this company in 20 words in English.
      Please exclude any info about city or region"
      """;

  private final ScrapperService scrapperService;

  public String getConceptRequestByUrl(String landingUrl) {
    log.info("[CONCEPT-SERVICE] Concept receiving for url:<{}> started", landingUrl);
    String chaoticWebsiteInfo = scrapperService.extractChaoticInfoFromUrl(landingUrl);
    chaoticWebsiteInfo = chaoticWebsiteInfo.length() <= 1000
        ? chaoticWebsiteInfo
        : chaoticWebsiteInfo.substring(0, 1000);
    String concept = prepareConcept(chaoticWebsiteInfo);
    log.info("[CONCEPT-SERVICE] Concept for url:<{}> successfully received:<{}>", landingUrl,
        chaoticWebsiteInfo);
    return concept;
  }

  private String prepareConcept(String chaoticInfo) {
    return CONCEPT_SCHEMA.replace("{chaoticInfo}", chaoticInfo);
  }
}
