package com.example.lightdj.service.impl;

import com.example.lightdj.config.sort.Sort;
import com.example.lightdj.domain.application.Application;
import com.example.lightdj.domain.application.Status;
import com.example.lightdj.domain.exceptions.ApplicationNotFoundException;
import com.example.lightdj.domain.exceptions.DontSuchSortedMethodException;
import com.example.lightdj.domain.exceptions.IllegalArgumentStatusException;
import com.example.lightdj.domain.exceptions.NotFoundDraftApplicationsException;
import com.example.lightdj.domain.operator.Operator;
import com.example.lightdj.domain.user.SimpleUser;
import com.example.lightdj.repository.ApplicationRepository;
import com.example.lightdj.service.ApplicationService;
import com.example.lightdj.service.OperatorService;
import com.example.lightdj.service.SimpleUserService;
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
    private final SimpleUserService simpleUserService;
    private final OperatorService operatorService;

    @Override
    public Application create(Application application, String email) {
        SimpleUser simpleUser = simpleUserService.getByEmail(email);
        application.setUsername(simpleUser.getUsername());
        application.setStatus(Status.DRAFT);
        application.setSimpleUserId(simpleUser);
        application.setDateCreatedApplication(LocalDateTime.now());
        simpleUser.getApplications().add(application);
        simpleUserService.update(simpleUser);
        return applicationRepository.save(application);
    }

    @Override
    public void addApplicationToOperator(String email) {
        SimpleUser simpleUser = simpleUserService.getByEmail(email);
        Operator operator = operatorService.getOperator();
        Application applicationLastDraft = findLastDraftApplication(simpleUser.getApplications());
        applicationLastDraft.setStatus(Status.SEND);
        operator.getOperatorApplications().add(applicationLastDraft);
        applicationRepository.save(applicationLastDraft);
    }

    @Override
    public Application update(String email, Long appId, Application application) {
        SimpleUser simpleUser = simpleUserService.getByEmail(email);
        List<Application> applicationsDraftToUser = findAllDraftApplication(simpleUser.getApplications());
        Application updated;
        if (!applicationsDraftToUser.isEmpty()) {
            updated = applicationsDraftToUser.stream()
                    .filter(app -> app.getId().equals(appId)).findFirst()
                    .orElseThrow(() -> new ApplicationNotFoundException("Обращение не найдено"));
            updated.setUsername(simpleUser.getUsername());
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
        SimpleUser simpleUser = simpleUserService.getByEmail(email);
        List<Application> applications;
        switch (sort) {
            case ASC -> applications = applicationRepository.findAllByUserIdAsc(pageRequest, simpleUser.getId());
            case DESC -> applications = applicationRepository.findAllByUserIdDesc(pageRequest, simpleUser.getId());
            default -> throw new DontSuchSortedMethodException("Не выбран метод сортировки");
        }
        return applications;
    }

    @Override
    public List<Application> getAllSendsApplications(PageRequest pageRequest,
                                                     String email,
                                                     String username,
                                                     Sort sort) {
        Operator operator = operatorService.getByEmail(email);
        List<Application> applications;
        switch (sort) {
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
        Operator operator = operatorService.getByEmail(operatorEmail);
        return applicationRepository.findAllByUsername(pageRequest,
                operator.getId(), username);
    }

    @Override
    public Application getApplicationById(Long id) {
        return applicationRepository.findByIdWithStatusSend(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Заявки с таким id не найдено"));
    }

    @Override
    public Application setStatusApplication(Long id, Status status) {
        Application application = getApplicationById(id);
        switch (status) {
            case ACCEPTED -> {
                application.setStatus(Status.ACCEPTED);
                applicationRepository.save(application);
                return application;
            }
            case REJECTED -> {
                application.setStatus(Status.REJECTED);
                applicationRepository.save(application);
                return application;
            }
            default -> throw new IllegalArgumentStatusException("Неверно указан возможный статус");
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
