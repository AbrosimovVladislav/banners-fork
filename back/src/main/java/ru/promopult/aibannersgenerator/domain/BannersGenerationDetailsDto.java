package ru.promopult.aibannersgenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.promopult.aibannersgenerator.service.imagegen.AISystem;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannersGenerationDetailsDto {

  private String url;
  private String prompt;
  private AISystem aiSystem;
  private String model;
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
