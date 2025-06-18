package com.example.ToDoList_API.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_user")
public class UserLogin {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private int id;

     @Column(name = "username", nullable = false)
     private String username;

     @Column(name = "password", nullable = false)
     private String password;

     @Column(name = "email", nullable = false)
     private String email;

     @Column(name = "role", nullable = false)
     @Enumerated(EnumType.STRING)
     private Role role;

}
