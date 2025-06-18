package com.example.ToDoList_API.api.exceptions;

public class DuplicateSituationException extends RuntimeException {
     public DuplicateSituationException(String message) {
          super(message);
     }
}
