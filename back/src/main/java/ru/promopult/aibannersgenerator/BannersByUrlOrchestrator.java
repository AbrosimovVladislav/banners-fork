package ru.promopult.aibannersgenerator;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.promopult.aibannersgenerator.domain.BannersGenerationDetailsDto;
import ru.promopult.aibannersgenerator.domain.RequestAndPrompt;
import ru.promopult.aibannersgenerator.service.ConceptService;
import ru.promopult.aibannersgenerator.service.imagegen.ImageAIService;
import ru.promopult.aibannersgenerator.service.PromptService;
import ru.promopult.aibannersgenerator.web.dto.BannersGenerationResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class BannersByUrlOrchestrator {

  private final ConceptService conceptService;
  private final PromptService promptService;
  private final ImageAIService imageAIService;

  public BannersGenerationResponse getImageByLink(BannersGenerationDetailsDto detailsDto) {
    log.info("[ORCHESTRATOR] Procedure getImageByLink for url:<{}> started", detailsDto.getUrl());

    String conceptRequest = conceptService.getConceptRequestByUrl(detailsDto.getUrl());
    RequestAndPrompt requestAndPrompt = promptService.getPromptForImageAI(detailsDto.getUrl(),
        conceptRequest, detailsDto.getStyleDescription());
    detailsDto.setPrompt(requestAndPrompt.getPrompt());
    List<String> images = imageAIService.getImagesByPrompt(detailsDto);

    var imageGenerationDto = BannersGenerationResponse.builder()
        .concept(conceptRequest)
        .prompt(requestAndPrompt.getPrompt())
        .promptRequest(requestAndPrompt.getPromptRequest())
        .images(images)
        .build();

    log.info("[ORCHESTRATOR] Procedure getImageByLink for url:<{}> finished successfully",
        detailsDto.getUrl());
    return imageGenerationDto;
  }

  public BannersGenerationResponse getImagesByPrompt(BannersGenerationDetailsDto detailsDto) {
    log.info("[ORCHESTRATOR] Procedure getImagesByPrompt for prompt:<{}> started",
        detailsDto.getPrompt());

    List<String> images = imageAIService.getImagesByPrompt(detailsDto);

    var imageGenerationDto = BannersGenerationResponse.builder()
        .prompt(detailsDto.getPrompt())
        .images(images).build();

    log.info("[ORCHESTRATOR] Procedure getImagesByPrompt for prompt:<{}> finished successfully",
        detailsDto.getPrompt());
    return imageGenerationDto;
  }
}
