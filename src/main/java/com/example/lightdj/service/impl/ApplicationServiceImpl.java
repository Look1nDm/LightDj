package com.example.lightdj.service.impl;

import com.example.lightdj.domain.application.Application;
import com.example.lightdj.domain.application.Status;
import com.example.lightdj.domain.exceptions.ApplicationNotFoundException;
import com.example.lightdj.domain.exceptions.NotFoundDraftApplicationsException;
import com.example.lightdj.domain.user.User;
import com.example.lightdj.repository.ApplicationRepository;
import com.example.lightdj.service.ApplicationService;
import com.example.lightdj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;

    @Override
    public Application create(Application application, String email) {
        User user = userService.getUserByEmail(email);
        application.setStatus(Status.DRAFT);
        application.setUserId(user.getId());
        application.setDateCreatedApplication(LocalDateTime.now());
        user.getApplications().add(application);
        userService.update(user);
        return applicationRepository.save(application);
    }

    @Override
    public void addApplicationToOperator(String email) {
        User user = userService.getUserByEmail(email);
        Application applicationLastDraft = findLastDraftApplication(user.getApplications());
        User operator = userService.findOperator();
        applicationLastDraft.setStatus(Status.SEND);
        operator.getApplications().add(applicationLastDraft);
        applicationRepository.save(applicationLastDraft);
    }

    @Override
    public Application update(String email, Long appId, Application application) {
        User user = userService.getUserByEmail(email);
        List<Application> applicationsDraftToUser = findAllDraftApplication(user.getApplications());
        Application updated;
        if (!applicationsDraftToUser.isEmpty()) {
            updated = applicationsDraftToUser.stream()
                    .filter(app -> app.getId().equals(appId)).findFirst()
                    .orElseThrow(() -> new ApplicationNotFoundException("Обращение не найдено"));
            updated.setTextApplication(application.getTextApplication());
            updated.setPhoneNumber(application.getPhoneNumber());
            updated.setUsername(application.getUsername());
            updated.setDateCreatedApplication(LocalDateTime.now());
            applicationRepository.save(updated);
        } else {
            throw new NotFoundDraftApplicationsException("У Вас нет доступный обращений для изменения");
        }
        return updated;
    }
    @Override
    public List<Application> getAllApplicationsDesc(PageRequest pageRequest, String email) {
        User user = userService.getUserByEmail(email);
        applicationRepository.findAllByUserIdDesc(pageRequest, user.getId());
        return user.getApplications();
    }

    @Override
    public List<Application> getAllApplicationsAsc(PageRequest pageRequest, String email) {
        User user = userService.getUserByEmail(email);
        applicationRepository.findAllByUserIdAsc(pageRequest, user.getId());
        return user.getApplications();
    }

    private static Application findLastDraftApplication(List<Application> list) {
        return list.stream()
                .filter(e -> e.getStatus().equals(Status.DRAFT))
                .reduce((i, j) -> j).orElseThrow();
    }

    private static List<Application> findAllDraftApplication(List<Application> applications) {
        return applications.stream().filter(app -> app.getStatus().equals(Status.DRAFT))
                .collect(Collectors.toList());
    }
}
