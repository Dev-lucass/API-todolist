package com.example.ToDoList_API.api.service;

import com.example.ToDoList_API.api.config.PasswordEncoderConfiguration;
import com.example.ToDoList_API.api.model.UserLogin;
import com.example.ToDoList_API.api.repository.UserLoginRepository;
import com.example.ToDoList_API.api.validation.UserLoginValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

     private final UserLoginRepository repository;
     private final PasswordEncoderConfiguration encoder;
     private final UserLoginValidation validation;

     public void save(UserLogin userLogin) {
          validation.validationDupliacate(userLogin);
          validation.validationRoleEnum(userLogin);
          String password = userLogin.getPassword();
          userLogin.setPassword(encoder.passwordEncoder().encode(password));
          repository.save(userLogin);
     }

     public UserLogin findUserByUsername(String username) {
          return repository.findByUsername(username);
     }

     public UserLogin findUserByEmail(String email) {
          return repository.findByEmail(email);
     }


}
