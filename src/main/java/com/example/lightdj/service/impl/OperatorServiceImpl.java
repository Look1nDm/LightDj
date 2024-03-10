package com.example.lightdj.service.impl;

import com.example.lightdj.domain.exceptions.UserNotFoundException;
import com.example.lightdj.domain.operator.Operator;
import com.example.lightdj.repository.OperatorRepository;
import com.example.lightdj.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final OperatorRepository operatorRepository;

    @Override
    public Operator getOperator() {
        List<Operator> operators = operatorRepository.findAll();
        return findFreeOperator(operators);
    }

    @Override
    public Operator getByEmail(String email) {
        return operatorRepository.findOperatorByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("Оператор не найден"));
    }

    @Override
    public Operator createOperator(Operator operator) {
        return operatorRepository.save(operator);
    }

    private static Operator findFreeOperator(List<Operator> operators){
        return operators.stream()
                .min(Comparator.comparing(u -> u.getOperatorApplications().size()))
                .orElseThrow();
    }
}
