package com.sparta.schedule.exceptionhandler;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class ErrorResponse {

  private final LocalDateTime timestamp = LocalDateTime.now();
  private String message;
  private int status;
  public Map<String, String> errors;
  private String code;

  @Getter
  @Setter
  @NoArgsConstructor
  public static class FieldError {
    private String field;
    private String message;
  }

  public ErrorResponse(ErrorCode errorCode) {
    this.message = errorCode.getMessage();
    this.status = errorCode.getStatus();
    this.code = errorCode.getCode();
  }

  public ErrorResponse(ErrorCode errorCode, Map<String, String> errors) {
      this.message = errorCode.getMessage();
      this.status = errorCode.getStatus();
      this.code = errorCode.getCode();
      this.errors = errors;
  }

}
