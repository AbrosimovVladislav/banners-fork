package ru.promopult.aibannersgenerator.web.dto.request;

import lombok.Data;

@Data
public class GetBannersByPromptRequest {

  private String prompt;
  private String aiSystem;
  private String sdModel;
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
