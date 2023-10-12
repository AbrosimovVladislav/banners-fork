package ru.promopult.aibannersgenerator.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.promopult.aibannersgenerator.service.ImageDownloadService;

@Slf4j
@RestController
@RequestMapping("api/v1/image")
@RequiredArgsConstructor
public class ImageDownloadApi {

  private final ImageDownloadService imageDownloadService;

  @CrossOrigin
  @GetMapping("/zip")
  public ResponseEntity<byte[]> downloadImages(@RequestParam List<String> imageUrls) {

    byte[] zipArchive = imageDownloadService.downloadImagesAsZip(imageUrls);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDisposition(
        ContentDisposition.builder("attachment").filename("images.zip").build());

    return new ResponseEntity<>(zipArchive, headers, HttpStatus.OK);
  }

  @CrossOrigin
  @GetMapping("")
  public ResponseEntity<byte[]> downloadImage(@RequestParam String imageUrl) {

    byte[] image = imageDownloadService.downloadImage(imageUrl);

    String imageName = imageUrl.substring(imageUrl.indexOf("generations/"));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);
    headers.setContentDisposition(
        ContentDisposition.builder("attachment").filename(imageName).build());

    return new ResponseEntity<>(image, headers, HttpStatus.OK);
  }

}
