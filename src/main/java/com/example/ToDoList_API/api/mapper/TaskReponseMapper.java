package com.example.ToDoList_API.api.mapper;

import com.example.ToDoList_API.api.dto.ResponseTaskDTO;
import com.example.ToDoList_API.api.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskReponseMapper {

     @Mapping(source = "title", target = "title")
     @Mapping(source = "description", target = "description")
     @Mapping(source = "createAt", target = "createAt")
     @Mapping(source = "createIn", target = "createIn")
     @Mapping(source = "lastModifieldDate", target = "lastModifieldDate")
     @Mapping(source = "levelPriority", target = "levelPriority")
     ResponseTaskDTO toDto(Task task);

}
