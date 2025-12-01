package com.vitoria.Turing.Machine.dto;

import com.vitoria.Turing.Machine.model.ExecutionStrategy;
import com.vitoria.Turing.Machine.model.NumberFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class ExecutionResultDTO {
    private NumberFormat originalNumberFormat;
    private BigInteger originalNumberDecimal;
    private String originalNumberBinary;
    private boolean isPrime;
    private long executionTimeMillis;
    private ExecutionStrategy strategy;

    private long totalSteps;
    private long totalSubtractions;
    private long totalComparisons;
    private long totalDivisorIncrements;


    private String completeStepsDescription;
}
