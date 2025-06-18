package com.example.ToDoList_API.api.validation;

import com.example.ToDoList_API.api.exceptions.DuplicateException;
import com.example.ToDoList_API.api.model.Client;
import com.example.ToDoList_API.api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientValidation {

     private final ClientRepository repository;

     public void validationDuplicate(Client client){
          boolean exists = repository.existsByClientIdAndClientSecretAndRedirectUri(client.getClientId(), client.getClientSecret(), client.getRedirectUri());

          if(exists){
               List<String> duplicatedFields = new ArrayList<>(List.of(
                       client.getClientId(),
                       client.getClientSecret(),
                       client.getRedirectUri()
               ));

               throw  new DuplicateException("Duplicated", duplicatedFields);
          }
     }
}
