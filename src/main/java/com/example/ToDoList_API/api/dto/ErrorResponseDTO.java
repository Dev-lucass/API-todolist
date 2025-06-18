package com.example.ToDoList_API.api.dto;

import java.util.List;

public record ErrorResponseDTO(int status, String message, List<?> errorFields) {
}
