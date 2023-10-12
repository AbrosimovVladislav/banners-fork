package ru.promopult.aibannersgenerator.integration.leonardo.client;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.promopult.aibannersgenerator.exception.LeonardoException;
import ru.promopult.aibannersgenerator.exception.NoImagesGeneratedException;
import ru.promopult.aibannersgenerator.exception.StableDiffusionException;
import ru.promopult.aibannersgenerator.integration.leonardo.model.LeonardoGenerationImagesRequest;
import ru.promopult.aibannersgenerator.integration.leonardo.model.LeonardoGenerationImagesResponse;
import ru.promopult.aibannersgenerator.integration.leonardo.model.LeonardoGetASingleGenerationResponse;
import ru.promopult.aibannersgenerator.integration.leonardo.model.LeonardoGetASingleGenerationResponse.GeneratedImage;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeonardoClient {

  @Value("${leonardo.api-key}")
  private String LEONARDO_API_KEY;

  @Value("${leonardo.generation-images-url}")
  private String LEONARDO_GENERATION_IMAGES_URL;

  private final RestTemplate restTemplate;

  @Retryable(retryFor = StableDiffusionException.class, maxAttempts = 2, backoff = @Backoff(delay = 1000))
  public ResponseEntity<LeonardoGenerationImagesResponse> callGenerationImages(String landingUrl,
      LeonardoGenerationImagesRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(LEONARDO_API_KEY);

    HttpEntity<LeonardoGenerationImagesRequest> requestEntity = new HttpEntity<>(request,
        headers);
    ResponseEntity<LeonardoGenerationImagesResponse> response;
    try {
      response = restTemplate.exchange(
          LEONARDO_GENERATION_IMAGES_URL,
          HttpMethod.POST,
          requestEntity,
          LeonardoGenerationImagesResponse.class);
    } catch (Exception e) {
      throw new LeonardoException(e.getMessage());
    }

    log.info("[LEONARDO-CLIENT] Leonardo response received for url:<{}>",
        landingUrl);

    return response;
  }

  @Retryable(retryFor = NoImagesGeneratedException.class, maxAttempts = 60, backoff = @Backoff(delay = 5000))
  public List<String> callGetASingleGeneration(String landingUrl, String generationId) {
    log.info(
        "[LEONARDO-CLIENT] Get A Single Generation procedure started for url:<{}> and generationId:<{}>",
        landingUrl, generationId);

    String getASingleGenerationUrl = LEONARDO_GENERATION_IMAGES_URL + "/" + generationId;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(LEONARDO_API_KEY);

    HttpEntity requestEntity = new HttpEntity<>(headers);

    var response = restTemplate.exchange(
        getASingleGenerationUrl,
        HttpMethod.GET,
        requestEntity,
        LeonardoGetASingleGenerationResponse.class
    );

    String status = Objects.requireNonNull(response.getBody()).getGenerations_by_pk().getStatus();

    switch (status) {
      case "PENDING" -> {
        log.info("[LEONARDO-CLIENT] No Images after fetching. Leonardo message is: <{}>",
            status);
        throw new NoImagesGeneratedException(landingUrl);
      }
      case "FAILED" -> {
        throw new LeonardoException("General Leonardo Exception: " + status);
      }
    }

    List<String> images = response.getBody().getGenerations_by_pk().getGenerated_images().stream()
        .map(GeneratedImage::getUrl)
        .toList();

    log.info(
        "[LEONARDO-CLIENT] Get A Single Generation response received for url:<{}> and generationId:<{}>",
        landingUrl, generationId);

    return images;
  }

  @Recover
  public ResponseEntity<LeonardoGenerationImagesResponse> handleLeonardoException(
      LeonardoException ex, String landingUrl, LeonardoGenerationImagesRequest request){
    log.error(
        "[LEONARDO-CLIENT] Failed to retrieve images from Leonardo after 2 attempts for url: {}."
            + " Leonardo Exception",
        landingUrl);
    throw ex;
  }

  @Recover
  public List<String> handleLeonardoException(
      LeonardoException ex, String landingUrl, String generationId) {
    log.error(
        "[LEONARDO-CLIENT] Failed to retrieve images from Leonardo for url: {}."
            + " Leonardo Exception",
        landingUrl);
    throw ex;
  }

  @Recover
  public List<String> handleNoImagesGeneratedException(
      NoImagesGeneratedException ex, String landingUrl, String generationId) {
    log.error(
        "[LEONARDO-CLIENT] Failed to retrieve images from Leonardo after 5 attempts for url: {}."
            + " No Images",
        landingUrl);
    throw ex;
  }
}
