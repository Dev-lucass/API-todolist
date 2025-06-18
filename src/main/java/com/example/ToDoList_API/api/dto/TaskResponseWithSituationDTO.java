package com.example.ToDoList_API.api.dto;

public record TaskResponseWithSituationDTO(

        int id,
        String title,
        String description,
        int levelPriority,
        SituationDTO situation
) {
}
