package com.sparta.schedule.exceptionhandler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // Controller에서 @Valid 유효성 검증 실패시 해당 exception 발생
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      log.error("name: {} , message: {}", fieldError.getField(), fieldError.getDefaultMessage());
      FieldError error = (FieldError) fieldError;
      String fieldName = error.getField();
      String message = error.getDefaultMessage();
      errors.put(fieldName, message);
    }
    ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  // CustomException
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    log.error("handleCustomException: {} ", e.getErrorCode());
    ErrorResponse response = new ErrorResponse(e.getErrorCode());
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  /**
   * enum type 일치하지 않아 binding 못할 경우 발생 주로 @RequestParam enum으로 binding 못했을 경우 발생
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST);
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  // Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생
  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
    ErrorResponse response = new ErrorResponse(ErrorCode.ACCESS_DENIED);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  // HttpStatus 405 Exception 지원하지 않는 메서드 호출 시 발생
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException e) {
    log.error("handleHttpRequestMethodNotSupported: {} ", e.getMessage());
    ErrorResponse response = new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  // HttpStatus 500 Exception
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("handleException : {} ", e.getMessage());
    ErrorResponse response = new ErrorResponse(ErrorCode.SERVER_ERROR);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

}
