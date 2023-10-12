package ru.promopult.aibannersgenerator.service.imagegen;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.promopult.aibannersgenerator.domain.BannersGenerationDetailsDto;
import ru.promopult.aibannersgenerator.integration.stablediffusion.client.StableDiffusionClient;
import ru.promopult.aibannersgenerator.integration.stablediffusion.model.StableDiffusionTextToImageRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class StableDiffusionService implements ImageGenService {

  @Value("${stable-diffusion.clip-skip}")
  private String CLIP_SKIP_DEFAULT;
  @Value("${stable-diffusion.num-inference-steps}")
  private String NUM_INFERENCE_STEPS_DEFAULT;
  @Value("${default-negative-prompt}")
  private String NEGATIVE_PROMPT_DEFAULT;
  @Value("${stable-diffusion.scheduler}")
  private String SD_SCHEDULER;

  private final StableDiffusionClient stableDiffusionClient;

  @Override
  @SneakyThrows
  public List<String> generateImages(BannersGenerationDetailsDto detailsDto) {
    prepareGenerationDetailsBeforeRequest(detailsDto);

    StableDiffusionTextToImageRequest request =
        new StableDiffusionTextToImageRequest().createDefaultRequest(detailsDto);

    var response = stableDiffusionClient.callTextToImage(detailsDto.getUrl(), request);
    List<String> images = response.getBody().getOutput();

    if (CollectionUtils.isEmpty(images)) {
      Thread.sleep(5000);
      images = stableDiffusionClient.callFetchQueuedImages(detailsDto.getUrl(),
          response.getBody().getId());
    }

    log.info("[STABLE-DIFFUSION-SERVICE] Images successfully generated for url:<{}> by model:<{}>",
        detailsDto.getUrl(), detailsDto.getModel());

    return images;
  }

  /**
   * Check some fields(fields which have default values and can't be null) inside details -> they
   * have value or null?
   * <p>
   * If they have value, then set it If not, then set default value List of fields:
   * [negativePrompt,scheduler,clipSkip,numInferenceSteps]
   */
  private void prepareGenerationDetailsBeforeRequest(BannersGenerationDetailsDto detailsDto) {
    detailsDto.setNegativePrompt(
        detailsDto.getNegativePrompt() != null ? detailsDto.getNegativePrompt()
            : NEGATIVE_PROMPT_DEFAULT
    );

    detailsDto.setScheduler(
        detailsDto.getScheduler() != null ? detailsDto.getScheduler() : SD_SCHEDULER
    );

    detailsDto.setClipSkip(
        detailsDto.getClipSkip() != null ? detailsDto.getClipSkip() : CLIP_SKIP_DEFAULT
    );

    detailsDto.setNumInferenceSteps(
        detailsDto.getNumInferenceSteps() != null ? detailsDto.getNumInferenceSteps()
            : NUM_INFERENCE_STEPS_DEFAULT
    );
  }

}
