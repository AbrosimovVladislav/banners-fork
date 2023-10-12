package ru.promopult.aibannersgenerator.service.imagegen;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.promopult.aibannersgenerator.domain.BannersGenerationDetailsDto;
import ru.promopult.aibannersgenerator.integration.leonardo.client.LeonardoClient;
import ru.promopult.aibannersgenerator.integration.leonardo.model.LeonardoGenerationImagesRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeonardoService implements ImageGenService {

  private final LeonardoClient leonardoClient;

  @Override
  public List<String> generateImages(BannersGenerationDetailsDto detailsDto) {
    prepareDetailsBeforeRequest(detailsDto);

    LeonardoGenerationImagesRequest request =
        new LeonardoGenerationImagesRequest().createDefaultRequest(detailsDto);

    var response = leonardoClient.callGenerationImages(detailsDto.getUrl(), request);
    String generationId = Objects.requireNonNull(response.getBody())
        .getSdGenerationJob()
        .getGenerationId();

    List<String> images = leonardoClient.callGetASingleGeneration(detailsDto.getUrl(),
        generationId);

    log.info("[LEONARDO-SERVICE] Images successfully generated for url:<{}> by model:<{}>",
        detailsDto.getUrl(), detailsDto.getModel());

    return images;
  }

  private void prepareDetailsBeforeRequest(BannersGenerationDetailsDto detailsDto) {

  }
}