package ru.promopult.aibannersgenerator.integration.chatgpt.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptUsageDTO {

  private int prompt_tokens;
  private int completion_tokens;
  private int total_tokens;
}
