package com.example.ToDoList_API.api.dto;

import com.example.ToDoList_API.api.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "USER-LOGIN")
public record UserLoginDTO(

        @NotBlank(message = "required field")
        @Size(max = 50, message = "size exceeded")
        @Size(min = 3, message = "invalid size")
        String username,

        @NotBlank(message = "required field")
        @Size(max = 50, message = "size exceeded")
        @Size(min = 3, message = "invalid size")
        String password,

        @NotBlank(message = "required field")
        @Size(max = 50, message = "size exceeded")
        @Size(min = 10, message = "invalid size")
        @Email(message = "E-mail invalid")
        String email,

        @NotNull(message = "field status not be empty")
        @Enumerated(EnumType.STRING)
        Role role


) {
}
