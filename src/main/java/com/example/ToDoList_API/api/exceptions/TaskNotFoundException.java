package com.example.ToDoList_API.api.exceptions;

public class TaskNotFoundException extends RuntimeException {
     public TaskNotFoundException(String message) {
          super(message);
     }
}
