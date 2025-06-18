package com.example.ToDoList_API.api.controller;

import com.example.ToDoList_API.api.dto.SituationDTO;
import com.example.ToDoList_API.api.exceptions.TaskNotFoundException;
import com.example.ToDoList_API.api.mapper.SituationMapper;
import com.example.ToDoList_API.api.model.Situation;
import com.example.ToDoList_API.api.model.Task;
import com.example.ToDoList_API.api.repository.TaskRepository;
import com.example.ToDoList_API.api.service.SituationService;
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
import java.util.Optional;

@RestController
@RequestMapping("/v1/situation")
@RequiredArgsConstructor
@Tag(name = "SITUATION")
public class SituationController {

     private final SituationService service;
     private final SituationMapper mapper;
     private final TaskRepository taskRepository;

     @PreAuthorize("hasAnyRole('ADMIN','USER')")
     @PostMapping
     @Operation(summary = "Salvar", description = "Cadastrar nova situation")
     @ApiResponses({
             @ApiResponse(responseCode = "201", description = "cadastrado com sucesso"),
             @ApiResponse(responseCode = "422", description = "erro de validação"),
             @ApiResponse(responseCode = "409", description = "situation ja cadastrada")
     })
     public ResponseEntity<?> save(@RequestBody @Valid SituationDTO dto) {

          Optional<Task> task = taskRepository.findById(dto.idTask());

          if (task.isEmpty()) {
               throw new TaskNotFoundException("IdTask not found ...");
          }


          Situation entity = mapper.toEntity(dto);
          entity.setTask(task.get());
          Situation situation = service.save(entity);

          task.get().setId(situation.getIdTask());
          task.get().setSituation(situation);
          taskRepository.save(task.get());

          SituationDTO mapperDto = mapper.toDto(situation);

          return ResponseEntity.ok(mapperDto);


     }

     @PreAuthorize("hasAnyRole('ADMIN','USER')")
     @GetMapping
     @Operation(summary = "buscar todas situations", description = "Busca todas Situations")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Situations encontradas"),
             @ApiResponse(responseCode = "404", description = "Situations não encontradas"),
     })
     public ResponseEntity<?> getAll() {
          List<Situation> all = service.findAll();

          if (all.isEmpty()) {
               return ResponseEntity.noContent().build();
          }

          List<SituationDTO> reponse = all.stream().map(map -> new SituationDTO(
                  map.getComment(),
                  map.getCategory(),
                  map.getStatus(),
                  map.getIdTask()
          )).toList();

          return ResponseEntity.ok(reponse);
     }

     @PreAuthorize("hasAnyRole('ADMIN','USER')")
     @PutMapping("/{id}")
     @Operation(summary = "atualiza uma situation", description = "atualiza uma situation existente")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Situation atualizada"),
             @ApiResponse(responseCode = "404", description = "Situations não encontradas"),
     })
     public ResponseEntity<?> update(@PathVariable(value = "id") int id, @RequestBody SituationDTO dto) {
          Situation entity = mapper.toEntity(dto);
          Situation update = service.update(id, entity);
          SituationDTO mapperDto = mapper.toDto(update);
          return ResponseEntity.ok(mapperDto);
     }

     @PreAuthorize("hasAnyRole('ADMIN','USER')")
     @DeleteMapping("/{id}")
     @Operation(summary = "deletar", description = "Deleta  uma situation")
     @ApiResponses({
             @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
             @ApiResponse(responseCode = "404", description = "Situation não encontrada"),
     })
     public ResponseEntity<?> delete(@PathVariable(value = "id") int id) {
          service.delete(id);
          return ResponseEntity.ok().build();
     }
}
