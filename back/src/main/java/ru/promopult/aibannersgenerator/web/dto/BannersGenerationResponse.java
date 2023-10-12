package ru.promopult.aibannersgenerator.web.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BannersGenerationResponse {

  String concept;
  String prompt;
  String promptRequest;
  List<String> images;
}
