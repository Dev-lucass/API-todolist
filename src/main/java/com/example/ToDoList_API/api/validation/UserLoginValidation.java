package com.example.ToDoList_API.api.validation;

import com.example.ToDoList_API.api.exceptions.CategoryException;
import com.example.ToDoList_API.api.exceptions.DuplicateException;
import com.example.ToDoList_API.api.model.Role;
import com.example.ToDoList_API.api.model.UserLogin;
import com.example.ToDoList_API.api.repository.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserLoginValidation {

     private final UserLoginRepository repository;
     private final PasswordEncoder encoder;

     public void validationDupliacate(UserLogin userLogin) {

          UserLogin userLoginFound = repository.findByUsername(userLogin.getUsername());

          if (userLoginFound != null) {

               boolean matches = encoder.matches(userLogin.getPassword(), userLoginFound.getPassword());

               if (matches && userLoginFound.getEmail().equalsIgnoreCase(userLogin.getEmail())) {
                    List<String> duplicateFields = new ArrayList<>(
                            List.of(
                                    userLoginFound.getEmail(),
                                    userLoginFound.getPassword()
                            )
                    );
                    throw new DuplicateException("E-mail and Password duplicate", duplicateFields);
               }

               if (matches) {
                    List<String> duplicateFields = new ArrayList<>(
                            List.of(
                                    userLoginFound.getPassword()
                            )
                    );
                    throw new DuplicateException("Password duplicate", duplicateFields);
               }

               if (userLoginFound.getEmail().equalsIgnoreCase(userLogin.getEmail())) {
                    List<String> duplicateFields = new ArrayList<>(
                            List.of(
                                    userLoginFound.getEmail()
                            )
                    );
                    throw new DuplicateException("E-mail duplicate", duplicateFields);
               }
          }
     }

     public void validationRoleEnum(UserLogin userLogin) {
          if (!isValidRoleEnum(userLogin.getRole())) {
               throw new CategoryException("Invalid role.");
          }
     }

     private boolean isValidRoleEnum(Role role) {
          for (Role validRole : Role.values()) {
               if (validRole.equals(role)) {
                    return true;
               }
          }
          return false;
     }


}
