package ru.promopult.aibannersgenerator.exception;

public class GetHtmlPageException extends RuntimeException {

  public GetHtmlPageException(String url) {
    super("Error while trying to get HTML page :<" + url + ">");
  }

}
