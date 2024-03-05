package com.example.lightdj.service;

import com.example.lightdj.domain.application.Application;
import com.example.lightdj.domain.application.Status;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ApplicationService {
    Application create(Application application, String email);

    void addApplicationToOperator(String email);

    Application update(String email, Long appId, Application application);

    List<Application> getAllApplicationsDesc(PageRequest pageRequest, String email);

    List<Application> getAllApplicationsAsc(PageRequest pageRequest, String email);

    List<Application> getAllSendsApplicationsDesc(PageRequest pageRequest, String email, String username);

    List<Application> getAllSendsApplicationsAsc(PageRequest pageRequest, String email, String username);

    List<Application> getAllApplicationsUser(PageRequest pageRequest,String operatorName, String name);

    Application getApplicationById(Long id);

    void setStatusApplication(Long id, Status status);

}
