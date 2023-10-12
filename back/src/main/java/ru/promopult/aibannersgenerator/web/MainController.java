package ru.promopult.aibannersgenerator.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class MainController {

  @CrossOrigin
  @GetMapping("/")
  public ResponseEntity<String> healthCheck() {
    log.info("[HEALTH-CHECK] Welcome to Ai Banners Generator Service");
    return ResponseEntity.ok("Welcome to Ai Banners Generator Service");
  }

}
