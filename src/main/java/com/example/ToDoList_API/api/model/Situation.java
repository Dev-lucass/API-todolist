package com.example.ToDoList_API.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Situation {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private int id;

     @Column(name = "comment", nullable = false)
     private String comment;

     @Column(name = "category", nullable = false)
     @Enumerated(EnumType.STRING)
     private Category category;

     @Column(name = "status", nullable = false)
     @Enumerated(EnumType.STRING)
     private Status status;

     @Column(name = "task_id", nullable = false)
     private Integer idTask;

     @JoinColumn(name = "task", nullable = true)
     @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     private Task task;

}
