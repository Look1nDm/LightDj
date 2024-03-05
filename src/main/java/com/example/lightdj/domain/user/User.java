package com.example.lightdj.domain.user;

import com.example.lightdj.domain.application.Application;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Transient
    private String passwordConfirmation;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    @JoinColumn(name = "user_id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Application> applications;

    @JoinColumn(name = "operator_id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Application> operatorApplications;
}
