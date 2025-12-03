package com.vitoria.Turing.Machine.strategy;

import com.vitoria.Turing.Machine.dto.ExecutionResultDTO;

import java.util.LinkedList;

public interface PrimalityStrategy {
    ExecutionResultDTO isPrime(LinkedList<Character> binaryNumber);
}
