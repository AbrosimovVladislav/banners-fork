package ru.promopult.aibannersgenerator.service.imagegen;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.promopult.aibannersgenerator.domain.BannersGenerationDetailsDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageAIService {

  private final ImageGenServiceFactory serviceFactory;

  public List<String> getImagesByPrompt(BannersGenerationDetailsDto detailsDto) {
    log.info("[IMAGE-AI-SERVICE] Images receiving for url:<{}> by model:<{}> started",
        detailsDto.getUrl(), detailsDto.getModel());

    List<String> images = createService(detailsDto.getAiSystem()).generateImages(detailsDto);

    log.info("[IMAGE-AI-SERVICE] Images for url:<{}>, successfully received: <{}>",
        detailsDto.getUrl(), images);
    return images;
  }

  private ImageGenService createService(AISystem aiSystem){
    return serviceFactory.createService(aiSystem);
  }

}
