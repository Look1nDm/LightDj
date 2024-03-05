package com.example.lightdj.web.controller;

import com.example.lightdj.domain.application.Application;
import com.example.lightdj.domain.application.Status;
import com.example.lightdj.service.ApplicationService;
import com.example.lightdj.web.dto.ApplicationDto;
import com.example.lightdj.web.mappers.ApplicationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
@Tag(name = "Application controller", description = "Application API")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;

    @Operation(summary = "Создание обращения пользователем",
            description = "Создаем черновик нашего обращения")
    @PostMapping("/create")
    public ApplicationDto create(@RequestBody ApplicationDto dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Application application = applicationMapper.toEntity(dto);
        return applicationMapper.toDto(applicationService.create(application, userDetails.getUsername()));
    }

    @Operation(summary = "Оправка заявки пользователем оператору",
            description = "Оправка заявки со статусом черновик оператору")
    @PutMapping("/send")
    public HttpStatus sendApplication() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        applicationService.addApplicationToOperator(userDetails.getUsername());
        return HttpStatus.OK;
    }

    @Operation(summary = "Радактирование заявки пользователем",
            description = "Радактирование заявки со статусом DRAFT")
    @PutMapping("/update/{appId}")
    public ApplicationDto updateApplicationDraft(@PathVariable @Parameter(description = "Id заявки") Long appId,
                                                 @RequestBody ApplicationDto applicationDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Application application = applicationMapper.toEntity(applicationDto);
        return applicationMapper.toDto(applicationService.update(userDetails.getUsername(), appId, application));
    }

    @Operation(summary = "Отображение заявок пользователя",
            description = "Выводим все заявки у пользователя начиная с самой поздей по дате")
    @GetMapping("/allDesc")
    public List<ApplicationDto> getAllApplicationToUserDesc(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return applicationMapper.toDto(applicationService.getAllApplicationsDesc(PageRequest.of(page, size)
                , userDetails.getUsername()));
    }

    @Operation(summary = "Отображение заявок пользователя",
            description = "Выводим все заявки у пользователя начания с самой ранней по дате")
    @GetMapping("/allAsc")
    public List<ApplicationDto> getAllApplicationToUserAsc(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return applicationMapper.toDto(applicationService.getAllApplicationsAsc(PageRequest.of(page, size)
                , userDetails.getUsername()));
    }

    @Operation(summary = "Смотрим все заявки на рассмотрении",
            description = "Выводим все заявки, направленные оператору на рассмотрение начиная с самой поздней")
    @GetMapping("/allSendsDesc/{username}")
    public List<ApplicationDto> getAllSendsApplicationsDesc(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size,
                                                            @PathVariable String username) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return applicationMapper.toDto(applicationService.getAllSendsApplicationsDesc(PageRequest.of(page, size)
                , userDetails.getUsername(), username));
    }
    @Operation(summary = "Смотрим все заявки на рассмотрении",
            description = "Выводим все заявки, направленные оператору на рассмотрение начиная с самой ранней")
    @GetMapping("/allSendsAsc/{username}")
    public List<ApplicationDto> getAllSendsApplicationsAsc(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size,
                                                           @PathVariable String username) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return applicationMapper.toDto(applicationService.getAllSendsApplicationsAsc(PageRequest.of(page, size)
                , userDetails.getUsername(), username));
    }
    @Operation(summary = "Просмотр заявок по имени пользователя",
            description = "Выводим все заявки отправленные пользователем конкретно этому оператору")
    @GetMapping("/allAppUser/{username}")
    public List<ApplicationDto> getAllApplicationsToUser(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size,
                                                        @PathVariable String username){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return applicationMapper.toDto(applicationService.getAllApplicationsUser(PageRequest.of(page, size)
                , userDetails.getUsername(), username));
    }
    @Operation(summary = "Просмотр заявки по id",
            description = "Выводим заявку по id")
    @GetMapping("/get/{id}")
    public ApplicationDto getApplicationById(@PathVariable Long id){
        return applicationMapper.toDto(applicationService.getApplicationById(id));
    }
    @Operation(summary = "Принимаем заявку",
            description = "Устанавливаем статус заявке ACCEPTED")
    @PutMapping("/accepted/{id},{status}")
    public HttpStatus acceptedApplication(@PathVariable Long id,@PathVariable Status status){
        applicationService.setStatusApplication(id, status);
        return HttpStatus.OK;
    }
}
