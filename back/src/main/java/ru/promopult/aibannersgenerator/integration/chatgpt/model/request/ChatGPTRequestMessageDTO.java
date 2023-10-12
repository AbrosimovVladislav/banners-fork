package ru.promopult.aibannersgenerator.integration.chatgpt.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTRequestMessageDTO {

  private String role;
  private String content;
}
