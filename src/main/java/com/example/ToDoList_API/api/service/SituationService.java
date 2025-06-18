package com.example.ToDoList_API.api.service;

import com.example.ToDoList_API.api.model.Situation;
import com.example.ToDoList_API.api.model.Task;
import com.example.ToDoList_API.api.repository.SituationRepository;
import com.example.ToDoList_API.api.repository.TaskRepository;
import com.example.ToDoList_API.api.validation.SituationValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SituationService {

     private final SituationValidation validation;
     private final SituationRepository repository;
     private final TaskRepository taskRepository;

     public Situation save(Situation situation) {
          validation.validateCategory(situation);
          validation.validationDuplicated(situation);
          validation.validationTaskId(situation);
          return repository.save(situation);
     }


     public List<Situation> findAll() {
          return repository.findAll();
     }


     public Situation update(int id, Situation situation) {

          validation.validateIdSituation(id);
          validation.validationTaskId(situation);
          validation.validationDuplicatedUpdate(situation);

          Optional<Situation> optional = repository.findById(id);

          if (optional.isPresent()) {
               optional.get().setId(id);
               optional.get().setComment(situation.getComment());
               optional.get().setCategory(situation.getCategory());
               optional.get().setStatus(situation.getStatus());
               optional.get().setIdTask(situation.getIdTask());
               return repository.save(optional.get());
          }

          return null;
     }


     public void delete(int id) {
          Task task = validation.validateIdSituation(id);
          task.setSituation(null);
          repository.deleteById(id);
     }

}



