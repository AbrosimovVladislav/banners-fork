package ru.promopult.aibannersgenerator.service;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class MathService {

  private static final Random random = new Random();

  public int getRandom() {
    return random.nextInt(9999999) + 1;
  }
}
