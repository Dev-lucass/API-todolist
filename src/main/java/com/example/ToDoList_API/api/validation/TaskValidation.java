package com.example.ToDoList_API.api.validation;

import com.example.ToDoList_API.api.exceptions.DuplicateException;
import com.example.ToDoList_API.api.exceptions.TaskNotFoundException;
import com.example.ToDoList_API.api.model.Task;
import com.example.ToDoList_API.api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskValidation {

     private final TaskRepository repository;

     public Task validationIdExists(Integer id) {
          if (!validateId(id)) {
               throw new TaskNotFoundException("Invalid id to update task");
          }

          return repository.findById(id).orElseThrow();
     }

     private boolean validateId(Integer id) {
          return repository.existsById(id);
     }

     public void validationDuplicate(Task task) {
          boolean exists = repository.existsByTitleAndDescription(task.getTitle(), task.getDescription());

          if (exists) {
               List<String> errosList = new ArrayList<>(
                       List.of(
                               task.getTitle(),
                               task.getDescription()
                       ));

               throw new DuplicateException("Title and Description duplicated", errosList);
          }
     }

}
