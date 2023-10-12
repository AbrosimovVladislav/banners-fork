package ru.promopult.aibannersgenerator.exception;

public class GetPromptException extends RuntimeException {

  public GetPromptException(String responseId, String content) {
    super("Exception when getting prompt and concept from response body "
        + "gptResponseId:<" + responseId + ">, responseContent:<" + content + ">");
  }

}
