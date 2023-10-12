package ru.promopult.aibannersgenerator.integration.stablediffusion.model;

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
public class StableDiffusionTextToImageRequest {

  private String key;
  private String model_id;
  private String prompt;
  private String negative_prompt;
  private String width;
  private String height;
  private String samples;
  private String num_inference_steps;
  private String safety_checker;
  private String enhance_prompt;
  private String seed;
  private double guidance_scale;
  private String multi_lingual;
  private String panorama;
  private String self_attention;
  private String upscale;
  private String embeddings_model;
  private String lora_model;
  private String lora_strength;
  private String scheduler;
  private String webhook;
  private String track_id;
  private String clip_skip;
  private String base64;
  private String temp;

  public StableDiffusionTextToImageRequest createDefaultRequest(
      BannersGenerationDetailsDto detailsDto) {
    var widthAndHeight =
        Arrays.stream(detailsDto.getAspectRatio().split(":")).toList();

    return StableDiffusionTextToImageRequest.builder()
        .model_id(detailsDto.getModel())
        .prompt(detailsDto.getPrompt())
        .negative_prompt(detailsDto.getNegativePrompt())
        .width(widthAndHeight.get(0))
        .height(widthAndHeight.get(1))
        .scheduler(detailsDto.getScheduler())
        .clip_skip(detailsDto.getClipSkip())
        .num_inference_steps(detailsDto.getNumInferenceSteps())
        .lora_model(detailsDto.getLoraModel())
        .lora_strength(detailsDto.getLoraModelStrength())
        .embeddings_model(detailsDto.getEmbeddingsModel())
        .seed(detailsDto.getSeed())
        .samples("4")
        .safety_checker("no")
        .enhance_prompt("no")
        .guidance_scale(7.5)
        .multi_lingual("no")
        .panorama("no")
        .self_attention("yes")
        .upscale("no")
        .base64("no")
        .temp("yes")
        .build();
  }

}
