package com.vitoria.Turing.Machine.service;

import com.vitoria.Turing.Machine.dto.ExecutionResultDTO;
import com.vitoria.Turing.Machine.model.ExecutionStrategy;
import com.vitoria.Turing.Machine.model.NumberFormat;
import com.vitoria.Turing.Machine.strategy.BaseAlgorithmStrategy;
import com.vitoria.Turing.Machine.strategy.OptimizedAlgorithmStrategy;
import com.vitoria.Turing.Machine.strategy.PrimalityStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Service
public class PrimalityCheckService {

    private PrimalityStrategy strategy;

    @Autowired
    private StatisticsService statisticsService;

    public ExecutionResultDTO check(LinkedList<Character> binaryNumber,
                                    BigInteger decimalNumber,
                                    NumberFormat originalNumberFormat,
                                    ExecutionStrategy executionStrategy) {
        validateInput(binaryNumber, decimalNumber);

        this.strategy = selectStrategy(executionStrategy);

        long startTime = System.currentTimeMillis();
        ExecutionResultDTO executionResult = strategy.isPrime(binaryNumber);
        try {
            executionResult.setStrategy(executionStrategy);
            executionResult.setOriginalNumberBinary(
                    binaryNumber.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining())
            );
            executionResult.setOriginalNumberDecimal(decimalNumber);
            executionResult.setOriginalNumberFormat(originalNumberFormat);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar verificação de primalidade: " + e.getMessage(), e);
        } finally {
            long endTime = System.currentTimeMillis();
            executionResult.setExecutionTimeMillis(endTime - startTime);
        }
        statisticsService.recordExecution(executionResult);
        return executionResult;
    }

    private void validateInput(LinkedList<Character> binaryNumber, BigInteger decimalNumber) {
        if (binaryNumber == null || binaryNumber.isEmpty()) {
            throw new IllegalArgumentException("Número binário não pode ser nulo ou vazio");
        }

        if (decimalNumber == null) {
            throw new IllegalArgumentException("Número decimal não pode ser nulo");
        }

        if (decimalNumber.compareTo(BigInteger.ONE) <= 0) {
            throw new IllegalArgumentException("Número deve ser maior que 1");
        }

        for (Character c : binaryNumber) {
            if (c != '0' && c != '1') {
                throw new IllegalArgumentException("Representação binária deve conter apenas '0' e '1'");
            }
        }
    }

    private PrimalityStrategy selectStrategy(ExecutionStrategy executionStrategy) {
        switch (executionStrategy) {
            case BASE:
                return new BaseAlgorithmStrategy();
            case OPTIMIZED:
                return new OptimizedAlgorithmStrategy();
            default:
                throw new IllegalArgumentException("Estratégia de execução inválida: " + executionStrategy);
        }
    }
}