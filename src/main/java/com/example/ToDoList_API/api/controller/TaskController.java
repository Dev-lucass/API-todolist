package com.example.ToDoList_API.api.controller;

import com.example.ToDoList_API.api.dto.ResponseTaskDTO;
import com.example.ToDoList_API.api.dto.SituationDTO;
import com.example.ToDoList_API.api.dto.TaskDTO;
import com.example.ToDoList_API.api.dto.TaskResponseWithSituationDTO;
import com.example.ToDoList_API.api.mapper.TaskMapper;
import com.example.ToDoList_API.api.mapper.TaskReponseMapper;
import com.example.ToDoList_API.api.model.Task;
import com.example.ToDoList_API.api.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/task")
@RequiredArgsConstructor
@Tag(name = "TASK")
public class TaskController {

     private final TaskService service;
     private final TaskMapper mapper;
     private final TaskReponseMapper reponseMapper;

     @PreAuthorize("hasAnyRole('ADMIN','USER')")
     @PostMapping
     @Operation(summary = "Salvar", description = "Cadastrar nova task")
     @ApiResponses({
             @ApiResponse(responseCode = "201", description = "cadastrado com sucesso"),
             @ApiResponse(responseCode = "422", description = "erro de validação"),
             @ApiResponse(responseCode = "409", description = "task ja cadastrada")
     })
     public ResponseEntity<?> save(@RequestBody @Valid TaskDTO dto) {
          Task entity = mapper.toEntity(dto);
          Task save = service.save(entity);
          ResponseTaskDTO reponseMapperDto = reponseMapper.toDto(save);
          return ResponseEntity.ok(reponseMapperDto);
     }

     @PreAuthorize("hasAnyRole('ADMIN','USER')")
     @GetMapping
     @Operation(summary = "buscar todas tasks", description = "Busca todas tasks")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Tasks encontradas"),
             @ApiResponse(responseCode = "404", description = "Tasks não encontradas"),
     })
     public ResponseEntity<?> findAll() {

          List<Record> response = service.findAll().stream().map(map -> {
               if (map.getSituation() != null) {
                    return new TaskResponseWithSituationDTO(
                            map.getId(),
                            map.getTitle(),
                            map.getDescription(),
                            map.getLevelPriority(),

                            new SituationDTO(
                                    map.getSituation().getComment(),
                                    map.getSituation().getCategory(),
                                    map.getSituation().getStatus(),
                                    map.getSituation().getIdTask()
                            )
                    );
               }

               return new ResponseTaskDTO(
                       map.getId(),
                       map.getTitle(),
                       map.getDescription(),
                       map.getCreateAt(),
                       map.getCreateIn(),
                       map.getLastModifieldDate(),
                       map.getLevelPriority()
               );

          }).toList();

          if (response.isEmpty()) {
               return ResponseEntity.noContent().build();
          }

          return ResponseEntity.ok(response);
     }

     @PreAuthorize("hasAnyRole('ADMIN','USER')")
     @PutMapping("/{id}")
     @Operation(summary = "atualiza uma task", description = "atualiza uma task existente")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Tasks atualizada"),
             @ApiResponse(responseCode = "404", description = "Tasks não encontradas"),
     })
     public ResponseEntity<?> update(@PathVariable(value = "id") Integer id, @RequestBody @Valid TaskDTO dto) {
          Task entity = mapper.toEntity(dto);
          Task update = service.update(id, entity);

          if (update == null) {
               return ResponseEntity.internalServerError().build();
          }

          if (update.getSituation() == null) {
               return ResponseEntity.ok(update);
          }


          TaskResponseWithSituationDTO response = new TaskResponseWithSituationDTO(
                  update.getId(),
                  update.getTitle(),
                  update.getDescription(),
                  update.getLevelPriority(),

                  new SituationDTO(
                          update.getSituation().getComment(),
                          update.getSituation().getCategory(),
                          update.getSituation().getStatus(),
                          update.getSituation().getIdTask()
                  )
          );


          return ResponseEntity.ok(response);

     }

     @PreAuthorize("hasAnyRole('ADMIN','USER')")
     @DeleteMapping("/{id}")
     @Operation(summary = "deletar", description = "Deleta  uma task")
     @ApiResponses({
             @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
             @ApiResponse(responseCode = "404", description = "Task não encontrada"),
             @ApiResponse(responseCode = "422", description = "erro de validação"),
             @ApiResponse(responseCode = "409", description = "task ja atualizada")
     })
     public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
          service.delete(id);
          return ResponseEntity.ok().build();
     }

}
