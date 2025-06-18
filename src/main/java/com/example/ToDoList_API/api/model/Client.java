package com.example.ToDoList_API.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_client")
public class Client {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private int id;

     @Column(name = "clientId")
     private String clientId;

     @Column(name = "clientSecret")
     private String clientSecret;

     @Column(name = "redirectUri")
     private String redirectUri;

     @Column(name = "scope")
     private String scope;
}
