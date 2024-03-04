package com.example.lightdj.service;

import com.example.lightdj.domain.application.Application;
import com.example.lightdj.web.dto.ApplicationDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    Application create(Application application, String email);

    void addApplicationToOperator(String email);

    Application update(String email, Long appId, Application application);

    List<Application> getAllApplicationsDesc (PageRequest pageRequest, String email);

    List<Application> getAllApplicationsAsc(PageRequest pageRequest, String email);
}
