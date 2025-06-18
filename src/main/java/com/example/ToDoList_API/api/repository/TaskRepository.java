package com.example.ToDoList_API.api.repository;

import com.example.ToDoList_API.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
     boolean existsByTitleAndDescription(String title, String description);
}
