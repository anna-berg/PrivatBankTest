package com.example.privatBank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
@Entity
@Table(name = "dj")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Dj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dj_id")
    private Integer djId;

    @Column(name = "name")
    private String name;

    @Column(name = "experience")
    private String experience;

    @Column(name = "resume")
    private String resume;

}
