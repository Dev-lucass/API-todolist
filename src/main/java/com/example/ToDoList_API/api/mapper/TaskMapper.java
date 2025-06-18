package com.example.ToDoList_API.api.mapper;

import com.example.ToDoList_API.api.dto.TaskDTO;
import com.example.ToDoList_API.api.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

     @Mapping(source = "title", target = "title")
     @Mapping(source = "description", target = "description")
     @Mapping(source = "levelPriority", target = "levelPriority")
     Task toEntity(TaskDTO dto);

     @Mapping(source = "title", target = "title")
     @Mapping(source = "description", target = "description")
     @Mapping(source = "levelPriority", target = "levelPriority")
     TaskDTO toDto(Task task);

}
