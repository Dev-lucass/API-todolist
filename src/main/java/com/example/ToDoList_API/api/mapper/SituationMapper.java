package com.example.ToDoList_API.api.mapper;

import com.example.ToDoList_API.api.dto.SituationDTO;
import com.example.ToDoList_API.api.model.Situation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SituationMapper {

     Situation toEntity(SituationDTO dto);
     SituationDTO toDto(Situation situation);
}
