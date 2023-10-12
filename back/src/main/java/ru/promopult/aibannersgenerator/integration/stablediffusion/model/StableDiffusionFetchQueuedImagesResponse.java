package ru.promopult.aibannersgenerator.integration.stablediffusion.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StableDiffusionFetchQueuedImagesResponse {
  private String status;
  private long id;
  private List<String> output;
}
