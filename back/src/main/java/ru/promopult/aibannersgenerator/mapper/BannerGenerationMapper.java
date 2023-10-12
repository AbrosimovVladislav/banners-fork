package ru.promopult.aibannersgenerator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.promopult.aibannersgenerator.domain.BannersGenerationDetailsDto;
import ru.promopult.aibannersgenerator.web.dto.request.GetBannersByPromptRequest;
import ru.promopult.aibannersgenerator.web.dto.request.GetBannersByUrlRequest;

@Mapper(componentModel = "spring")
public interface BannerGenerationMapper {

  @Mapping(target = "url", source = "url")
  @Mapping(target = "aiSystem", source = "aiSystem")
  @Mapping(target = "model", source = "sdModel")
  @Mapping(target = "styleDescription", source = "styleDescription")
  @Mapping(target = "aspectRatio", source = "aspectRatio")
  @Mapping(target = "numInferenceSteps", source = "numInferenceSteps")
  @Mapping(target = "loraModel", source = "loraModel")
  @Mapping(target = "loraModelStrength", source = "loraModelStrength")
  @Mapping(target = "embeddingsModel", source = "embeddingsModel")
  @Mapping(target = "seed", source = "seed")
  @Mapping(target = "scheduler", source = "scheduler")
  @Mapping(target = "clipSkip", source = "clipSkip")
  @Mapping(target = "negativePrompt", source = "negativePrompt")
  BannersGenerationDetailsDto toDto(GetBannersByUrlRequest source);

  @Mapping(target = "url", constant = "ByPrompt")
  @Mapping(target = "aiSystem", source = "aiSystem")
  @Mapping(target = "prompt", source = "prompt")
  @Mapping(target = "model", source = "sdModel")
  @Mapping(target = "aspectRatio", source = "aspectRatio")
  @Mapping(target = "numInferenceSteps", source = "numInferenceSteps")
  @Mapping(target = "loraModel", source = "loraModel")
  @Mapping(target = "loraModelStrength", source = "loraModelStrength")
  @Mapping(target = "embeddingsModel", source = "embeddingsModel")
  @Mapping(target = "seed", source = "seed")
  @Mapping(target = "scheduler", source = "scheduler")
  @Mapping(target = "clipSkip", source = "clipSkip")
  @Mapping(target = "negativePrompt", source = "negativePrompt")
  BannersGenerationDetailsDto toDto(GetBannersByPromptRequest source);


}
