package ru.promopult.aibannersgenerator.service.imagegen;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageGenServiceFactory {

  private final StableDiffusionService stableDiffusionService;
  private final LeonardoService leonardoService;

  public ImageGenService createService(AISystem aiSystem) {
    return switch (aiSystem) {
      case STABLE_DIFFUSION -> stableDiffusionService;
      case LEONARDO -> leonardoService;
    };
  }
}
