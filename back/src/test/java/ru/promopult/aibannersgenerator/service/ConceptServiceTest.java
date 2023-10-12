package ru.promopult.aibannersgenerator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@Disabled
@SpringBootTest
public class ConceptServiceTest {

  @Autowired
  private ConceptService conceptService;

  @Test
  @SneakyThrows
  void test() {
    List<String> urls = List.of(
        "juristi-yar.ru",
        "ritmovoz.ru",
        "sferar.ru",
        "cleanhunter-sochi.ru",
        "cliningtop-spb.ru",
        "uberu24.ru",
        "alfaremelectro24.ru",
        "specstroyservice-tomsk.ru",
        "folan.ru",
        "vent39.ru",
        "cmproject.ru",
        "tochkatepla.ru",
        "alfamebel76.ru",
        "solar-e.ru",
        "gadget-opt.ru",
        "sercop.ru",
        "massstaff.ru",
        "wasma.ru",
        "xn----jtbjedlgibqeme0dq.xn--p1ai",
        "xn----7sbnbcbmicvxh7ak9fvgk.xn--p1ai",
        "victoria.ru",
        "promistiku.ru",
        "investclub.money",
        "kncapital.ru",
        "immoalanya.ru",
        "gk-grad.ru",
        "ckmo.ru",
        "doctor-inshakov.ru",
        "roomers.ru",
        "china-office.ru",
        "vaser-lipo.ru",
        "mesocleansing.ru",
        "pmu-vanessa.ru",
        "permanentkrsk.ru",
        "more-on.ru",
        "usluga.me",
        "proxy6.net",
        "artcompany-nsk.ru",
        "guskamaz.ru",
        "xn----7sbbaqdeapjdegp7bzazefj4a4r.xn--p1ai",
        "skorostinet.ru",
        "i2-prom.ru",
        "sredajob.ru",
        "arsempire.ru",
        "poamt.ru",
        "cadillacwestline.ru",
        "127fz-czdrzn.ru",
        "help-smolensk24.ru",
        "fantorg.ru",
        "1kirpich.com",
        "mosgka.ru",
        "go1mash.ru",
        "inveys-krim.ru",
        "delonghi-repair.ru.com");

    var results = urls.stream()
        .map(url -> {
          try {
            String content = conceptService.getConceptRequestByUrl(url);
            var contentArr = List.of(content.split(" "));
            return ConceptServiceTestResult.builder()
                .url(url)
                .content(content)
                .amountOfWords(contentArr.size())
                .success(contentArr.size() >= 20).build();
          } catch (Exception e) {
            return null;
          }
        })
        .filter(Objects::nonNull)
        .toList();

    TestResults testResults = new TestResults();
    for (ConceptServiceTestResult result : results) {
      if (result.isSuccess()) {
        testResults.getSuccsed().add(result);
      } else {
        testResults.getFailed().add(result);
        testResults.getFailedUrls().add(result.getUrl());
      }
    }
    testResults.setAmountOfSuccess(testResults.getSuccsed().size());
    testResults.setAmountOfFailed(testResults.getFailed().size());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(
        new File("test-reports/concept/testResults" + LocalDateTime.now() + ".json"), testResults);
  }

  @Data
  @Builder
  @ToString
  static class ConceptServiceTestResult {

    private String url;
    private String content;
    private int amountOfWords;
    private boolean success;
  }

  @Data
  static class TestResults {

    private int amountOfSuccess;
    private int amountOfFailed;
    private List<String> failedUrls = new ArrayList<>();
    private List<ConceptServiceTestResult> failed = new ArrayList<>();
    private List<ConceptServiceTestResult> succsed = new ArrayList<>();
  }

}
