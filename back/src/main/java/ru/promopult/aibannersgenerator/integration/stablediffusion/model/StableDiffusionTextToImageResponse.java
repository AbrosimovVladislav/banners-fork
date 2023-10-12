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
public class StableDiffusionTextToImageResponse {

  private String status;
  private double generationTime;
  private long id;
  private List<String> output;
  private MetaData meta;

  public static class MetaData {

    private int H;
    private int W;
    private String enable_attention_slicing;
    private String file_prefix;
    private double guidance_scale;
    private String model;
    private int n_samples;
    private String negative_prompt;
    private String outdir;
    private String prompt;
    private String revision;
    private String safetychecker;
    private long seed;
    private int steps;
    private String vae;
  }

}
