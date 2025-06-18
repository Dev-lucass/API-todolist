package com.example.ToDoList_API.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ResponseTaskDTO (

        int id,
        String title,
        String description,
        LocalTime createAt,
        LocalDate createIn,
        LocalDate lastModifieldDate,
        int levelPriority

){
}
