package com.example.lightdj.service;

import com.example.lightdj.domain.operator.Operator;

public interface OperatorService {

    Operator getOperator();

    Operator getByEmail(String email);

    Operator createOperator(Operator operator);
}
