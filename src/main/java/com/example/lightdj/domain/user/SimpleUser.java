package com.example.lightdj.domain.user;

import com.example.lightdj.domain.User;
import com.example.lightdj.domain.application.Application;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("1")
public class SimpleUser extends User {

    @JoinColumn(name = "user_id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Application> applications;
}
