package com.example.ToDoList_API.api.repository;

import com.example.ToDoList_API.api.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UserLogin,Integer> {
     UserLogin findByUsername(String username);
     UserLogin findByEmail(String email);
     boolean existsByUsernameAndEmail(String username, String email);
     UserLogin findByPassword(String password);
}
