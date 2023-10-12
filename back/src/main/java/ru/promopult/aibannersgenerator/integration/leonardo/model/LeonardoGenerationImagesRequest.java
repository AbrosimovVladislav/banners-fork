package ru.promopult.aibannersgenerator.integration.leonardo.model;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.promopult.aibannersgenerator.domain.BannersGenerationDetailsDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeonardoGenerationImagesRequest {

  private String modelId;
  private String prompt;
  private int width;
  private int height;
  private boolean alchemy;
  private boolean photoReal;
  private String presetStyle;

  public LeonardoGenerationImagesRequest createDefaultRequest(
      BannersGenerationDetailsDto detailsDto) {
    var widthAndHeight =
        Arrays.stream(detailsDto.getAspectRatio().split(":")).toList();

    var request = LeonardoGenerationImagesRequest.builder()
        .prompt(detailsDto.getPrompt())
        .width(Integer.parseInt(widthAndHeight.get(0)))
        .height(Integer.parseInt(widthAndHeight.get(1)))
        .build();

    if (detailsDto.getModel().equals("photoreal")) {
      request.setPhotoReal(true);
      request.setAlchemy(true);
      request.setPresetStyle("CINEMATIC");
    } else {
      request.setModelId(detailsDto.getModel());
    }

    return request;
  }

}