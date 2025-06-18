package com.example.ToDoList_API.api.mapper;

import com.example.ToDoList_API.api.dto.ClientDTO;
import com.example.ToDoList_API.api.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

     @Mapping(source = "clientId" , target =  "clientId")
     @Mapping(source = "clientSecret" , target =  "clientSecret")
     @Mapping(source = "redirectUri" , target =  "redirectUri")
     @Mapping(source = "scope" , target =  "scope")
     Client toEntity(ClientDTO dto);

     @Mapping(source = "clientId" , target =  "clientId")
     @Mapping(source = "clientSecret" , target =  "clientSecret")
     @Mapping(source = "redirectUri" , target =  "redirectUri")
     @Mapping(source = "scope" , target =  "scope")
     ClientDTO  toDto(Client client);
}
