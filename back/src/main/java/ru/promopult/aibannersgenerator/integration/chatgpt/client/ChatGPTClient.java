package ru.promopult.aibannersgenerator.integration.chatgpt.client;

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
import ru.promopult.aibannersgenerator.integration.chatgpt.model.request.ChatGPTRequest;
import ru.promopult.aibannersgenerator.integration.chatgpt.model.response.ChatGPTResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatGPTClient {

  @Value("${open-ai.chat-gpt-url}")
  private String CHAT_GPT_URL;

  @Value("${open-ai.bearer-key}")
  private String OPEN_AI_BEARER_KEY;

  private final RestTemplate restTemplate;

  @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1500))
  public ResponseEntity<ChatGPTResponse> call(String landingUrl, ChatGPTRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(OPEN_AI_BEARER_KEY);

    HttpEntity<ChatGPTRequest> requestEntity = new HttpEntity<>(request, headers);
    ResponseEntity<ChatGPTResponse> response = restTemplate.exchange(
        CHAT_GPT_URL,
        HttpMethod.POST,
        requestEntity,
        ChatGPTResponse.class);
    log.info("[CHAT_GPT-CLIENT] ChatGPT response received for url:<{}>", landingUrl);
    return response;
  }

  @Recover
  public ResponseEntity<ChatGPTResponse> recoverCall(
      RuntimeException ex, String landingUrl, ChatGPTRequest request) {
    log.error(
        "[CHAT_GPT-CLIENT] Failed to retrieve prompt ChatGPT after 3 attempts for url: {}",
        landingUrl);
    throw ex;
  }

}
