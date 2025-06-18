package com.example.ToDoList_API.api.dto;

import com.example.ToDoList_API.api.model.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(name = "TASK")
public record TaskDTO(
        @NotBlank(message = "required field")
        @Size(max = 50, message = "size exceeded")
        @Size(min = 1, message = "invalid size")
        @Schema(name = "title")
        String title,

        @NotBlank(message = "required field")
        @Size(max = 50, message = "size exceeded")
        @Size(min = 1, message = "invalid size")
        @Schema(name = "description")
        String description,

        @NotNull(message = "required field")
        @Min(value = 1, message = "min value 1")
        @Max(value = 10, message = "max value 10")
        @Schema(name = "levelPriority", minimum = "1", maximum = "10")
        int levelPriority) {

     public Task addMoreDetails(LocalDate date, LocalTime time) {
          Task task = new Task();
          task.setTitle(this.title);
          task.setDescription(this.description);
          task.setLevelPriority(this.levelPriority);
          task.setCreateIn(date);
          task.setCreateAt(time);
          return task;
     }
}
