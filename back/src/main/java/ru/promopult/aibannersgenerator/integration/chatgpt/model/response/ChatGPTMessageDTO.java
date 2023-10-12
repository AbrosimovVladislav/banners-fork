package ru.promopult.aibannersgenerator.integration.chatgpt.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTMessageDTO {

  private String role;
  private String content;
}
