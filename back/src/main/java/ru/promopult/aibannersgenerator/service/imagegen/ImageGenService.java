package ru.promopult.aibannersgenerator.service.imagegen;

import java.util.List;
import ru.promopult.aibannersgenerator.domain.BannersGenerationDetailsDto;

public interface ImageGenService {

  List<String> generateImages(BannersGenerationDetailsDto detailsDto);

}
