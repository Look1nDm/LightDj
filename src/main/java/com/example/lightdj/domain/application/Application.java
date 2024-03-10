package com.example.lightdj.domain.application;

import com.example.lightdj.domain.operator.Operator;
import com.example.lightdj.domain.user.SimpleUser;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "applications")
public class Application implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "text")
    private String textApplication;
    @Column(name = "username")
    private String username;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "date_created_application")
    private LocalDateTime dateCreatedApplication;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private SimpleUser simpleUserId;
    @JoinColumn(name = "operator_id")
    @ManyToOne
    private Operator operatorId;
}
