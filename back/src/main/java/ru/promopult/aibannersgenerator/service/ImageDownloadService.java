package ru.promopult.aibannersgenerator.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.promopult.aibannersgenerator.exception.ImageDownloadException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageDownloadService {

  private final RestTemplate restTemplate;
  private final MathService mathService;

  public byte[] downloadImage(String imageUrl) {
    try {
      log.info("[IMAGE_DOWNLOAD-SERVICE] Downloading of image started: <{}>", imageUrl);
      byte[] image = restTemplate.getForObject(imageUrl, byte[].class);
      log.info("[IMAGE_DOWNLOAD-SERVICE] Downloading of image successfully finished: <{}>",
          imageUrl);
      return image;
    } catch (Exception ex) {
      log.error("[IMAGE_DOWNLOAD-SERVICE] Downloading of image <{}> failed: <{}>",
          imageUrl, ex.getMessage());
      throw new ImageDownloadException(imageUrl);
    }
  }

  public byte[] downloadImagesAsZip(List<String> imageUrls) {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos)) {
      log.info("[IMAGE_DOWNLOAD-SERVICE] Downloading of images as zip started: <{}>", imageUrls);

      for (String imageUrl : imageUrls) {
        byte[] image = downloadImage(imageUrl);
        String imageName = imageUrl.contains("temp/")
            ? imageUrl.substring(imageUrl.indexOf("temp/"))
            : "img-" + mathService.getRandom() + ".png";
        ZipEntry entry = new ZipEntry(imageName);
        zos.putNextEntry(entry);
        zos.write(image);
        zos.closeEntry();
      }

      zos.finish();
      byte[] zipArchive = baos.toByteArray();
      log.info("[IMAGE_DOWNLOAD-SERVICE] Downloading of images as zip successfully finished: <{}>",
          imageUrls);
      return zipArchive;

    } catch (Exception ex) {
      log.error("[IMAGE_DOWNLOAD-SERVICE] Downloading of zip with images <{}> failed: <{}>",
          imageUrls, ex.getMessage());
      throw new ImageDownloadException(imageUrls.toString());
    }
  }
}
