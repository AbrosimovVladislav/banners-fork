package ru.promopult.aibannersgenerator.integration.stablediffusion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StableDiffusionFetchQueuedImagesRequest {
  private String key;
  private String request_id;
}
