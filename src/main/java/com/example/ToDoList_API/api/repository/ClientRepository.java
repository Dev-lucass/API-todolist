package com.example.ToDoList_API.api.repository;

import com.example.ToDoList_API.api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
     boolean existsByClientIdAndClientSecretAndRedirectUri(String clientId, String clientSecret, String redirectUri);
     Client findByClientId(String clientId);
}
