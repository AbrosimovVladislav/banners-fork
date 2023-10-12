package ru.promopult.aibannersgenerator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.promopult.aibannersgenerator.web.dto.BannersGenerationResponse;
import ru.promopult.aibannersgenerator.web.dto.request.GetBannersByUrlRequest;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testGetBannersByUrl() throws Exception {
    List<TestResult> results = generateRequests().stream()
        .map(e -> {
          try {
            var requestContent = objectMapper.writeValueAsString(e);
            var mvcResult = mockMvc.perform(post("/api/v1/banners/by-url/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestContent))
                .andReturn();
            var response = mvcResult.getResponse().getContentAsString();
            var bannersGenerationDto = objectMapper.readValue(response, BannersGenerationResponse.class);
            return TestResult.builder()
                .responseDto(bannersGenerationDto)
                .status(true)
                .errorMessage(null)
                .build();
          } catch (Exception ex) {
            return TestResult.builder().responseDto(null).status(false)
                .errorMessage(ex.getMessage()).build();
          }
        }).toList();

    TestStatistic statistic = TestStatistic.builder()
        .positiveCases(results.stream().filter(e -> e.status).count())
        .errorCases(results.stream().filter(e -> !e.status).count())
        .build();

    printResultToFile(results, statistic);
  }

  @SneakyThrows
  private void printResultToFile(List<TestResult> results, TestStatistic statistic) {
    StringBuilder report = new StringBuilder();

    report.append("-- Stats\n\n");
    report.append(statistic.toString()).append("\n\n");

    report.append("-- Test results\n\n");
    for (TestResult result : results) {
      report.append("Response: ")
          .append(result.responseDto != null ? result.responseDto.toString() : "null").append("\n");
      report.append("Status: ").append(result.status ? "Success" : "Failed").append("\n");
      if (!result.status) {
        report.append("Error: ").append(result.errorMessage).append("\n");
      }
      report.append("--------------------\n");
    }

    Path directory = Paths.get("test-reports");
    if (!Files.exists(directory)) {
      Files.createDirectories(directory);
    }

    Files.write(Paths.get("test-reports/testReport" + LocalDateTime.now() + ".txt"), report.toString().getBytes());
  }

  @SneakyThrows
  private List<GetBannersByUrlRequest> generateRequests() {
    InputStream inputStream = new ClassPathResource("requests.json").getInputStream();
    List<GetBannersByUrlRequest> requests = objectMapper.readValue(inputStream,
        new TypeReference<>() {
        });
    return requests;
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  static class TestStatistic {

    long positiveCases;
    long errorCases;

    @Override
    public String toString() {
      return "TestStatistic[" +
          "positiveCases=" + positiveCases +
          ", errorCases=" + errorCases +
          ']';
    }
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  static class TestResult {

    BannersGenerationResponse responseDto;
    Boolean status;
    String errorMessage;
  }
}
