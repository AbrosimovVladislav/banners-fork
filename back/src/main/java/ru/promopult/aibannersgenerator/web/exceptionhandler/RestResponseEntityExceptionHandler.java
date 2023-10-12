package ru.promopult.aibannersgenerator.web.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.promopult.aibannersgenerator.exception.GetHtmlPageException;
import ru.promopult.aibannersgenerator.exception.GetPromptException;
import ru.promopult.aibannersgenerator.exception.ImageDownloadException;
import ru.promopult.aibannersgenerator.exception.LeonardoException;
import ru.promopult.aibannersgenerator.exception.NoImagesGeneratedException;
import ru.promopult.aibannersgenerator.exception.StableDiffusionException;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String GET_HTML_PAGE_EXCEPTION_CODE = "GET_HTML_PAGE_EXCEPTION";
  private static final String NO_IMAGES_GENERATED_EXCEPTION_CODE = "NO_IMAGES_GENERATED_EXCEPTION";
  private static final String STABLE_DIFFUSION_GENERAL_ERROR = "STABLE_DIFFUSION_GENERAL_ERROR";
  private static final String LEONARDO_GENERAL_ERROR = "LEONARDO_GENERAL_ERROR";
  private static final String GET_PROMPT_EXCEPTION_CODE = "GET_PROMPT_EXCEPTION";
  private static final String IMAGE_DOWNLOAD_EXCEPTION_CODE = "IMAGE_DOWNLOAD_EXCEPTION";

  @ExceptionHandler(value
      = {GetHtmlPageException.class})
  protected ResponseEntity<Object> handleGetHtmlPageException(
      RuntimeException ex, WebRequest request) {
    log.error(ex.getMessage());
    return handleExceptionInternal(ex, GET_HTML_PAGE_EXCEPTION_CODE,
        new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value
      = {NoImagesGeneratedException.class})
  protected ResponseEntity<Object> handleNoImagesGeneratedException(
      RuntimeException ex, WebRequest request) {
    log.error(ex.getMessage());
    return handleExceptionInternal(ex, NO_IMAGES_GENERATED_EXCEPTION_CODE,
        new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
  }

  @ExceptionHandler(value
      = {GetPromptException.class})
  protected ResponseEntity<Object> handleGetPromptException(
      RuntimeException ex, WebRequest request) {
    log.error(ex.getMessage());
    return handleExceptionInternal(ex, GET_PROMPT_EXCEPTION_CODE,
        new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(value
      = {ImageDownloadException.class})
  protected ResponseEntity<Object> handleImageDownloadException(
      RuntimeException ex, WebRequest request) {
    log.error(ex.getMessage());
    return handleExceptionInternal(ex, IMAGE_DOWNLOAD_EXCEPTION_CODE,
        new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(value
      = {StableDiffusionException.class})
  protected ResponseEntity<Object> handleStableDiffusionException(
      RuntimeException ex, WebRequest request) {
    log.error(ex.getMessage());
    return handleExceptionInternal(ex, STABLE_DIFFUSION_GENERAL_ERROR,
        new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
  }

  @ExceptionHandler(value
      = {LeonardoException.class})
  protected ResponseEntity<Object> handleLeonardoException(
      RuntimeException ex, WebRequest request) {
    log.error(ex.getMessage());
    return handleExceptionInternal(ex, LEONARDO_GENERAL_ERROR,
        new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE, request);
  }

}
