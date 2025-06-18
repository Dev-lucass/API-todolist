package com.example.ToDoList_API.api.controller;

import com.example.ToDoList_API.api.dto.ClientDTO;
import com.example.ToDoList_API.api.mapper.ClientMapper;
import com.example.ToDoList_API.api.model.Client;
import com.example.ToDoList_API.api.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/client")
@Tag(name = "CLIENT")
public class ClientController {

     private final ClientService service;
     private final ClientMapper mapper;

     @PreAuthorize("hasAnyRole('ADMIN','USER')")
     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     @Operation(summary = "Salvar", description = "Salva um novo client")
     @ApiResponses({
             @ApiResponse(responseCode = "201", description = "cadastrado com sucesso"),
             @ApiResponse(responseCode = "422", description = "erro de validação"),
             @ApiResponse(responseCode = "409", description = "client ja cadastrado")
     })
     public void save(@RequestBody @Valid ClientDTO dto) {
          Client entity = mapper.toEntity(dto);
          service.save(entity);
     }
}
