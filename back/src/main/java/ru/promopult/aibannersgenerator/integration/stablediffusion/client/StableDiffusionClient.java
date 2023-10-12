package ru.promopult.aibannersgenerator.integration.stablediffusion.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import ru.promopult.aibannersgenerator.exception.NoImagesGeneratedException;
import ru.promopult.aibannersgenerator.exception.StableDiffusionException;
import ru.promopult.aibannersgenerator.integration.stablediffusion.model.StableDiffusionFetchQueuedImagesRequest;
import ru.promopult.aibannersgenerator.integration.stablediffusion.model.StableDiffusionFetchQueuedImagesResponse;
import ru.promopult.aibannersgenerator.integration.stablediffusion.model.StableDiffusionTextToImageRequest;
import ru.promopult.aibannersgenerator.integration.stablediffusion.model.StableDiffusionTextToImageResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class StableDiffusionClient {

  @Value("${stable-diffusion.text-2-img-url}")
  private String SD_TEXT_TO_IMAGE_URL;

  @Value("${stable-diffusion.fetch-queued-images-url}")
  private String SD_FETCH_QUEUED_IMAGES_URL;

  @Value("${stable-diffusion.api-key}")
  private String STABLE_DIFFUSION_API_KEY;

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Retryable(retryFor = StableDiffusionException.class, maxAttempts = 2, backoff = @Backoff(delay = 1000))
  public ResponseEntity<StableDiffusionTextToImageResponse> callTextToImage(String landingUrl,
      StableDiffusionTextToImageRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    request.setKey(STABLE_DIFFUSION_API_KEY);

    HttpEntity<StableDiffusionTextToImageRequest> requestEntity = new HttpEntity<>(request,
        headers);
    ResponseEntity<StableDiffusionTextToImageResponse> response;
    try {
      response = restTemplate.exchange(
          SD_TEXT_TO_IMAGE_URL,
          HttpMethod.POST,
          requestEntity,
          StableDiffusionTextToImageResponse.class);
    } catch (Exception e) {
      throw new StableDiffusionException(e.getMessage());
    }

    log.info("[STABLE-DIFFUSION-CLIENT] Stable Diffusion response received for url:<{}>",
        landingUrl);

    return response;
  }

  @SneakyThrows
  @Retryable(retryFor = NoImagesGeneratedException.class, maxAttempts = 60, backoff = @Backoff(delay = 5000))
  public List<String> callFetchQueuedImages(String landingUrl, long id) {
    log.info(
        "[STABLE-DIFFUSION-CLIENT] Fetch Queued Images procedure started for url:<{}> and id:<{}>",
        landingUrl, id);
    var request = StableDiffusionFetchQueuedImagesRequest.builder()
        .key(STABLE_DIFFUSION_API_KEY)
        .request_id(String.valueOf(id))
        .build();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<StableDiffusionFetchQueuedImagesRequest> requestEntity = new HttpEntity<>(request,
        headers);

    StableDiffusionFetchQueuedImagesResponse response;
    var rawResponse = restTemplate.exchange(
        SD_FETCH_QUEUED_IMAGES_URL,
        HttpMethod.POST,
        requestEntity,
        String.class);

    if (rawResponse.getBody().contains("processing")) {
      log.info("[STABLE-DIFFUSION-CLIENT] No Images after fetching. SD message is: <{}>",
          rawResponse.getBody());
      throw new NoImagesGeneratedException(landingUrl);
    } else if (rawResponse.getBody().contains("failed")) {
      throw new StableDiffusionException("General SD Exception: " + rawResponse.getBody());
    } else {
      response = objectMapper.readValue(rawResponse.getBody(),
          StableDiffusionFetchQueuedImagesResponse.class);
    }

    List<String> fetchedImages = response.getOutput();

    log.info(
        "[STABLE-DIFFUSION-CLIENT] Fetch Queued Images response received for url:<{}> and id:<{}>",
        landingUrl, id);

    return fetchedImages;
  }

  @Recover
  public List<String> handleStableDiffusionError(
      StableDiffusionException ex, String landingUrl, long id) {
    log.error(
        "[STABLE-DIFFUSION-CLIENT] Failed to retrieve images from SD after 2 attempts for url: {}."
            + " Stable Diffusion Error",
        landingUrl);
    throw ex;
  }

  @Recover
  public List<String> handleNoImagesGeneratedException(
      NoImagesGeneratedException ex, String landingUrl, long id) {
    log.error(
        "[STABLE-DIFFUSION-CLIENT] Failed to retrieve images from SD for url: {}."
            + " No Images",
        landingUrl);
    throw ex;
  }

}
