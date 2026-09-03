package com.codingshuttle.youtube.hospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String specialization;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @ManyToMany(mappedBy = "doctors")
    @Builder.Default
    private Set<Department> departments = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @Builder.Default
    private Set<Appointment> appointments = new HashSet<>();
}