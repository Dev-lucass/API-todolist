package com.example.ToDoList_API.api.repository;

import com.example.ToDoList_API.api.model.Situation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SituationRepository extends JpaRepository<Situation, Integer> {
     boolean existsByIdTask(Integer idTask);
     Situation findByIdTask(Integer idTask);
     boolean existsByCommentAndIdTask(String comment, Integer idTask);
}
