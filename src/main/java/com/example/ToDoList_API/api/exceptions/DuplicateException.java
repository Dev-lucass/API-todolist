package com.example.ToDoList_API.api.exceptions;


import lombok.Getter;

import java.util.List;


@Getter
public class DuplicateException extends RuntimeException {

     private List<String> errors;

     public DuplicateException(String message, List<String> errorsField) {
          super(message);
          this.errors = errorsField;
     }
}
