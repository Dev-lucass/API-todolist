package com.example.ToDoList_API.api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Task {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private Integer id;

     @Column(name = "title", nullable = false)
     private String title;

     @Column(name = "description", nullable = false)
     private String description;

     @Column(name = "creatAt", nullable = false)
     @CreatedDate
     private LocalTime createAt;

     @Column(name = "createIn", nullable = false)
     @CreatedDate
     private LocalDate createIn;

     @Column(name = "lastModidieldDate", nullable = false)
     @LastModifiedDate
     private LocalDate lastModifieldDate;

     @Column(name = "levelPriority", nullable = false)
     private int levelPriority;

     @JoinColumn(name = "situation", nullable = true)
     @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private Situation situation;

     @Column(name = "userLogin")
     private String userLogin;

}
