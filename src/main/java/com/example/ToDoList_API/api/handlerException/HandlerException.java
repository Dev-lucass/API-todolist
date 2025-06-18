package com.example.ToDoList_API.api.handlerException;

import com.example.ToDoList_API.api.dto.ErrorFieldDTO;
import com.example.ToDoList_API.api.dto.ErrorResponseDTO;
import com.example.ToDoList_API.api.dto.ErrorResponseNoListDTO;
import com.example.ToDoList_API.api.exceptions.DuplicateException;
import com.example.ToDoList_API.api.exceptions.DuplicateSituationException;
import com.example.ToDoList_API.api.exceptions.SituationNotFoundException;
import com.example.ToDoList_API.api.exceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class HandlerException {

     @ExceptionHandler(MethodArgumentNotValidException.class)
     @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
     public ErrorResponseDTO argumentNotValidException(MethodArgumentNotValidException e) {
          List<FieldError> fieldErrors = e.getFieldErrors();

          List<ErrorFieldDTO> list = fieldErrors.stream().map(fieldError -> new ErrorFieldDTO(
                  fieldError.getDefaultMessage(),
                  fieldError.getField()
          )).toList();

          return new ErrorResponseDTO(
                  HttpStatus.UNPROCESSABLE_ENTITY.value(),
                  "Invalid fields",
                  list
          );
     }

     @ExceptionHandler(DuplicateException.class)
     @ResponseStatus(HttpStatus.CONFLICT)
     public ErrorResponseDTO duplicate(DuplicateException e) {

          List<String> errors = e.getErrors();

          List<ErrorFieldDTO> list = errors.stream().map(map -> new ErrorFieldDTO(
                  "DUPLICATED",
                  map
          )).toList();

          return new ErrorResponseDTO(
                  HttpStatus.CONFLICT.value(),
                  e.getMessage(),
                  list
          );
     }

     @ExceptionHandler(HttpMessageNotReadableException.class)
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     public ErrorResponseNoListDTO handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
          String message = "Invalid request format: " + extractEnumErrorMessage(e.getMessage());
          return new ErrorResponseNoListDTO(
                  HttpStatus.BAD_REQUEST.value(),
                  message
          );
     }

     private String extractEnumErrorMessage(String message) {
          if (message.contains("from String")) {
               int start = message.indexOf("from String");
               int end = message.indexOf("\r\n", start);
               return message.substring(start, end != -1 ? end : message.length());
          }
          return "Malformed JSON or invalid enum value.";
     }


     @ExceptionHandler(DuplicateSituationException.class)
     @ResponseStatus(HttpStatus.CONFLICT)
     public ErrorResponseNoListDTO duplicateSituation(DuplicateSituationException e) {
          return new ErrorResponseNoListDTO(
                  HttpStatus.CONFLICT.value(),
                  e.getMessage()
          );
     }

     @ExceptionHandler(TaskNotFoundException.class)
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     public ErrorResponseNoListDTO idTaskNotValid(TaskNotFoundException e) {
          return new ErrorResponseNoListDTO(
                  HttpStatus.BAD_REQUEST.value(),
                  e.getMessage()
          );
     }

     @ExceptionHandler(SituationNotFoundException.class)
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     public ErrorResponseNoListDTO idSituationNotValid(SituationNotFoundException e) {
          return new ErrorResponseNoListDTO(
                  HttpStatus.BAD_REQUEST.value(),
                  e.getMessage()
          );
     }


}
