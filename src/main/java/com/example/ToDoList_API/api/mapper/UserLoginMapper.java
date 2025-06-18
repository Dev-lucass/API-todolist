package com.example.ToDoList_API.api.mapper;

import com.example.ToDoList_API.api.dto.UserLoginDTO;
import com.example.ToDoList_API.api.model.UserLogin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserLoginMapper {

     @Mapping(source = "username" , target = "username")
     @Mapping(source = "password" , target = "password")
     @Mapping(source = "email" , target = "email")
     @Mapping(source = "role" , target = "role")
     UserLogin toEntity(UserLoginDTO dto);

     @Mapping(source = "username" , target = "username")
     @Mapping(source = "password" , target = "password")
     @Mapping(source = "email" , target = "email")
     @Mapping(source = "role" , target = "role")
     UserLoginDTO toDto(UserLogin  userLogin);
}
