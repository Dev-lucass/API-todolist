package com.example.ToDoList_API.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "CLIENT")
public record ClientDTO(

        @NotBlank(message = "required field")
        @Size(max = 50, message = "size exceeded")
        @Size(min = 1, message = "invalid size")
        String clientId,

        @NotBlank(message = "required field")
        @Size(max = 50, message = "size exceeded")
        @Size(min = 1, message = "invalid size")
        String clientSecret,

        @NotBlank(message = "required field")
        @Size(max = 50, message = "size exceeded")
        @Size(min = 1, message = "invalid size")
        String redirectUri,

        @NotBlank(message = "required field")
        @Size(max = 50, message = "size exceeded")
        @Size(min = 1, message = "invalid size")
        String scope
) {
}
