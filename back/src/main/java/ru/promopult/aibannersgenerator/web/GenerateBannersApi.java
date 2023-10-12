package ru.promopult.aibannersgenerator.web;

import ru.promopult.aibannersgenerator.BannersByUrlOrchestrator;
import ru.promopult.aibannersgenerator.domain.BannersGenerationDetailsDto;
import ru.promopult.aibannersgenerator.mapper.BannerGenerationMapper;
import ru.promopult.aibannersgenerator.web.dto.BannersGenerationResponse;
import ru.promopult.aibannersgenerator.web.dto.request.GetBannersByPromptRequest;
import ru.promopult.aibannersgenerator.web.dto.request.GetBannersByUrlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/banners")
@RequiredArgsConstructor
public class GenerateBannersApi {

  private final BannersByUrlOrchestrator orchestrator;
  private final BannerGenerationMapper bannerGenerationMapper;

  @CrossOrigin
  @PostMapping("/by-url/")
  public ResponseEntity<BannersGenerationResponse> getBannersByUrl(
      @RequestBody GetBannersByUrlRequest request) {
    BannersGenerationDetailsDto detailsDto = bannerGenerationMapper.toDto(request);
    BannersGenerationResponse response = orchestrator.getImageByLink(detailsDto);
    return ResponseEntity.ok(response);
  }

  @CrossOrigin
  @PostMapping("/by-prompt/")
  public ResponseEntity<BannersGenerationResponse> getBannersByPrompt(
      @RequestBody GetBannersByPromptRequest request) {
    BannersGenerationDetailsDto detailsDto = bannerGenerationMapper.toDto(request);
    BannersGenerationResponse response = orchestrator.getImagesByPrompt(detailsDto);
      return ResponseEntity.ok(response);
  }

}
