package com.example.ToDoList_API.api.controller;

import com.example.ToDoList_API.api.dto.UserLoginDTO;
import com.example.ToDoList_API.api.mapper.UserLoginMapper;
import com.example.ToDoList_API.api.model.UserLogin;
import com.example.ToDoList_API.api.service.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/userLogin")
@Tag(name = "USER-LOGIN")
public class UserLoginController {

     private final UserLoginService service;
     private final UserLoginMapper mapper;

     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     @Operation(summary = "Salvar", description = "Salva um novo user-login")
     @ApiResponses({
             @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
             @ApiResponse(responseCode = "409", description = "user-login ja cadastrado"),
             @ApiResponse(responseCode = "422", description = "erro de validação")
     })
     public ResponseEntity<Void> save(@RequestBody @Valid UserLoginDTO dto) {
          UserLogin entity = mapper.toEntity(dto);
          service.save(entity);
          return ResponseEntity.ok().build();
     }

}
