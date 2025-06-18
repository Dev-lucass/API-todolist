package com.example.ToDoList_API.api.service;

import com.example.ToDoList_API.api.model.Task;
import com.example.ToDoList_API.api.repository.SituationRepository;
import com.example.ToDoList_API.api.repository.TaskRepository;
import com.example.ToDoList_API.api.validation.TaskValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

     private final TaskRepository repository;
     private final TaskValidation validation;
     private final SituationRepository situationRepository;

     public Task save(Task task) {
          validation.validationDuplicate(task);
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          task.setUserLogin(authentication.getName());
          return this.repository.save(task);
     }

     public List<Task> findAll() {
          return repository.findAll();
     }

     public Task update(Integer id, Task newTask) {
          validation.validationIdExists(id);
          validation.validationDuplicate(newTask);

          Optional<Task> task = repository.findById(id);

          if (task.isPresent()) {
               task.get().setId(id);
               task.get().setTitle(newTask.getTitle());
               task.get().setDescription(newTask.getDescription());
               task.get().setLevelPriority(newTask.getLevelPriority());
               task.get().setLastModifieldDate(LocalDate.now());
               task.get().setSituation(task.get().getSituation());
               return repository.save(task.get());
          }

          return null;
     }

     public void delete(Integer id) {
          Task task = validation.validationIdExists(id);
          int idSituation = task.getSituation().getId();
          situationRepository.deleteById(idSituation);
          repository.deleteById(id);
     }

}
