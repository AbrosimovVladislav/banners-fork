package ru.promopult.aibannersgenerator.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBannersByUrlRequest {

  private String url;
  private String aiSystem;
  private String sdModel;
  private String styleDescription;
  private String aspectRatio;
  private String numInferenceSteps;
  private String loraModel;
  private String loraModelStrength;
  private String embeddingsModel;
  private String seed;
  private String scheduler;
  private String clipSkip;
  private String negativePrompt;

}
