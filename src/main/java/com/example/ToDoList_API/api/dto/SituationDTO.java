package com.example.ToDoList_API.api.dto;

import com.example.ToDoList_API.api.model.Category;
import com.example.ToDoList_API.api.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@Schema(name = "SITUATION")
public record SituationDTO(

        @NotBlank(message = "required field")
        @Size(max = 200, message = "size exceeded")
        @Size(min = 1, message = "invalid size")
        String comment,

        @NotNull(message = "field category not be empty")
        Category category,

        @NotNull(message = "field status not be empty")
        @Enumerated(EnumType.STRING)
        Status status,

        @NotNull(message = "required field")
        @Min(value = 0, message = "min value 0")
        Integer idTask

) {
}
