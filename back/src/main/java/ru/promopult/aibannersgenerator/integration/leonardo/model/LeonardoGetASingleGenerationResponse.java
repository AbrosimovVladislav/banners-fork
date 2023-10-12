package ru.promopult.aibannersgenerator.integration.leonardo.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeonardoGetASingleGenerationResponse {

  private Generation generations_by_pk;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Generation {
    private List<GeneratedImage> generated_images;
    private String modelId;
    private String prompt;
    private String negativePrompt;
    private Integer imageHeight;
    private Integer imageWidth;
    private Integer inferenceSteps;
    private Integer seed;
    private Boolean publicFlag;
    private String scheduler;
    private String sdVersion;
    private String status;
    private String presetStyle;
    private Double initStrength;
    private Integer guidanceScale;
    private String id;
    private String createdAt;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class GeneratedImage {
    private String url;
    private Boolean nsfw;
    private String id;
    private Integer likeCount;
    private List<String> generated_image_variation_generics;
  }
}
