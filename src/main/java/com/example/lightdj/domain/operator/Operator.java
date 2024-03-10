package com.example.lightdj.domain.operator;

import com.example.lightdj.domain.User;
import com.example.lightdj.domain.application.Application;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("2")
public class Operator extends User {

    @JoinColumn(name = "operator_id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Application> operatorApplications;
}
