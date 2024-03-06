package com.example.lightdj.service.impl;

import com.example.lightdj.config.sort.Sort;
import com.example.lightdj.domain.application.Application;
import com.example.lightdj.domain.application.Status;
import com.example.lightdj.domain.exceptions.ApplicationNotFoundException;
import com.example.lightdj.domain.exceptions.DontSuchSortedMethodException;
import com.example.lightdj.domain.exceptions.NotFoundDraftApplicationsException;
import com.example.lightdj.domain.user.Role;
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
        application.setUsername(user.getUsername());
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
        operator.getOperatorApplications().add(applicationLastDraft);
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
            updated.setUsername(user.getUsername());
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
    public List<Application> getAllApplications(PageRequest pageRequest, String email, Sort sort) {
        User user = userService.getUserByEmail(email);
        List<Application> applications;
        switch (sort){
            case ASC ->
                applications = applicationRepository.findAllByUserIdAsc(pageRequest, user.getId());
            case DESC ->
                applications = applicationRepository.findAllByUserIdDesc(pageRequest, user.getId());
            default -> throw new DontSuchSortedMethodException("Не выбран метод сортировки");
        }
        return applications;
    }

    @Override
    public List<Application> getAllSendsApplications(PageRequest pageRequest,
                                                         String email,
                                                         String username,
                                                         Sort sort) {
        User operator = userService.getUserByEmail(email);
        List<Application> applications;
        switch (sort){
            case DESC -> applications = applicationRepository
                    .findAllSendsByOperatorIdDesc(pageRequest, operator.getId(), username);
            case ASC -> applications = applicationRepository
                    .findAllSendsByOperatorIdAsc(pageRequest, operator.getId(), username);
            default -> throw new DontSuchSortedMethodException("Не выбран метод сортировки");
        }
        return findAllSendsApplication(applications);
    }

    @Override
    public List<Application> getAllApplicationsUser(PageRequest pageRequest,
                                                    String operatorEmail,
                                                    String username) {
        User user = userService.getUserByEmail(operatorEmail);
        if (!user.getRoles().contains(Role.ADMIN)){
            return applicationRepository.findAllByUsername(pageRequest,
                    user.getId(), username);
        } else {
            return applicationRepository.findAllApplications(pageRequest, username);
        }
    }

    @Override
    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(()-> new ApplicationNotFoundException("Заявки с таким id не найдено"));
    }

    @Override
    public void setStatusApplication(Long id, Status status) {
        Application application = getApplicationById(id);
        switch (status){
            case ACCEPTED -> {
                application.setStatus(Status.ACCEPTED);
                applicationRepository.save(application);
            }
            case REJECTED -> {
                application.setStatus(Status.REJECTED);
                applicationRepository.save(application);
            }
        }
    }

    private static List<Application> findAllSendsApplication(List<Application> applications) {
        return applications.stream().filter(app -> app.getStatus().equals(Status.SEND))
                .collect(Collectors.toList());
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
