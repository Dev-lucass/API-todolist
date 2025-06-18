package com.example.ToDoList_API.api.validation;

import com.example.ToDoList_API.api.exceptions.CategoryException;
import com.example.ToDoList_API.api.exceptions.DuplicateSituationException;
import com.example.ToDoList_API.api.exceptions.SituationNotFoundException;
import com.example.ToDoList_API.api.exceptions.TaskNotFoundException;
import com.example.ToDoList_API.api.model.Category;
import com.example.ToDoList_API.api.model.Situation;
import com.example.ToDoList_API.api.model.Status;
import com.example.ToDoList_API.api.model.Task;
import com.example.ToDoList_API.api.repository.SituationRepository;
import com.example.ToDoList_API.api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SituationValidation {

     private final SituationRepository repository;
     private final TaskRepository taskRepository;


     public Task validateIdSituation(int id) {
          if (!isValidIdSituation(id)) {
               throw new SituationNotFoundException("Id invalid to situation");
          }

          Optional<Situation> byId = repository.findById(id);

          return byId.map(situation -> taskRepository.findById(situation.getIdTask()).orElseThrow()).orElse(null);

     }

     public void validationTaskId(Situation situation) {
          boolean exists = taskRepository.existsById(situation.getIdTask());
          if (!exists) {
               throw new TaskNotFoundException("IdTask not found ...");
          }
     }

     public void validateCategory(Situation situation) {
          Category category = situation.getCategory();
          Status status = situation.getStatus();

          if (!isValidCategory(category) || !isValidStatus(status)) {
               throw new CategoryException("Invalid category.");
          }

     }

     public void validationDuplicated(Situation situation) {
          if (existsPostByTaskId(situation.getIdTask())) {
               throw new DuplicateSituationException("situation already exists, you can update only");
          }
     }

     public void validationDuplicatedUpdate(Situation situation) {
          boolean exists = repository.existsByCommentAndIdTask(situation.getComment(), situation.getIdTask());
          if (exists) {
               throw new DuplicateSituationException("data already registered");
          }
     }

     private boolean isValidIdSituation(int id) {
          return repository.existsById(id);
     }


     private boolean existsPostByTaskId(int idTask) {
          return repository.existsByIdTask(idTask);
     }


     private boolean isValidCategory(Category category) {
          for (Category validCategory : Category.values()) {
               if (validCategory.equals(category)) {
                    return true;
               }
          }
          return false;
     }

     private boolean isValidStatus(Status StatusBody) {
          for (Status validCategory : Status.values()) {
               if (validCategory.equals(StatusBody)) {
                    return true;
               }
          }
          return false;
     }

}

