package ru.promopult.aibannersgenerator.integration.chatgpt.model.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTRequest {

  private String model;
  private Integer max_tokens;
  private Integer top_p;
  private Integer frequency_penalty;
  private Integer presence_penalty;
  private Double temperature;
  private List<ChatGPTRequestMessageDTO> messages;

  public ChatGPTRequest createDefaultRequest(String requestContent) {
    return new ChatGPTRequest(
        "gpt-4",
        256,
        1,
        0,
        0,
        1.0,
        List.of(new ChatGPTRequestMessageDTO(
            "user",
            requestContent
        ))
    );
  }
}
