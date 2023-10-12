package ru.promopult.aibannersgenerator.service.scrapper;

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
public class ScrapperServiceTest {

  @Autowired
  private ScrapperService scrapperService;

  @SneakyThrows
  @Test
  void extractChaoticInfoFromUrlTest() {
    List<String> urls = List.of(
        "radar-online.ru", "xn----8sbkbd2atcigsd3l.xn--p1ai", "ambitiouscourse.fun"
    );

    var results = urls.stream()
        .map(url -> {
          try {
            String content = scrapperService.extractChaoticInfoFromUrl(url);
            var contentArr = List.of(content.split(" "));
            return ScrapperServiceTestResult.builder()
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
    for (ScrapperServiceTestResult result : results) {
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
        new File("test-reports/scrapper/testResults" + LocalDateTime.now() + ".json"), testResults);
  }

  @Data
  @Builder
  @ToString
  static class ScrapperServiceTestResult {

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
    private List<ScrapperServiceTestResult> failed = new ArrayList<>();
    private List<ScrapperServiceTestResult> succsed = new ArrayList<>();
  }

}
