package ru.promopult.aibannersgenerator.exception;

public class ImageDownloadException extends RuntimeException {

  public ImageDownloadException(String imageUrl) {
    super("Error while trying to get image :<" + imageUrl + ">");
  }

}
