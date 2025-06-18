package com.example.ToDoList_API.api.service;

import com.example.ToDoList_API.api.config.PasswordEncoderConfiguration;
import com.example.ToDoList_API.api.model.Client;
import com.example.ToDoList_API.api.repository.ClientRepository;
import com.example.ToDoList_API.api.validation.ClientValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

     private final ClientRepository repository;
     private final ClientValidation validation;
     private final PasswordEncoderConfiguration encoder;

     public void save(Client client) {
          String clientSecret = client.getClientSecret();
          client.setClientSecret(encoder.passwordEncoder().encode(clientSecret));
          validation.validationDuplicate(client);
          repository.save(client);
     }

     public Client findByClientId(String clientId) {
          return repository.findByClientId(clientId);
     }

}
