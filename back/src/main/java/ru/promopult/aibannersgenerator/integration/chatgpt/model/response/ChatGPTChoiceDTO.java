package ru.promopult.aibannersgenerator.integration.chatgpt.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTChoiceDTO {

  private int index;
  private ChatGPTMessageDTO message;
  private String finish_reason;
}
