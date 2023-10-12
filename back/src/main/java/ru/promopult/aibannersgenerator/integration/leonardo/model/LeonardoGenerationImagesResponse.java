package ru.promopult.aibannersgenerator.integration.leonardo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeonardoGenerationImagesResponse {

  private SdGenerationJob sdGenerationJob;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SdGenerationJob {
    private String generationId;
  }
}
