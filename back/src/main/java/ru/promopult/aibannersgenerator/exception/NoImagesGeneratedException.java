package ru.promopult.aibannersgenerator.exception;

public class NoImagesGeneratedException extends RuntimeException {

  public NoImagesGeneratedException(String url) {
    super("There is no images for url:<" + url + ">");
  }

}
