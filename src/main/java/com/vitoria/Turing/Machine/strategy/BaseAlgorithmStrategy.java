package com.vitoria.Turing.Machine.strategy;

import com.vitoria.Turing.Machine.dto.ExecutionResultDTO;
import com.vitoria.Turing.Machine.model.Alphabet;
import com.vitoria.Turing.Machine.subroutines.*;

import java.util.LinkedList;
import java.util.List;

public class BaseAlgorithmStrategy implements PrimalityStrategy {

    public static final LinkedList<Alphabet> ZERO = new LinkedList<>(List.of(Alphabet.ZERO));
    public static final LinkedList<Alphabet> TWO = new LinkedList<>(List.of(Alphabet.ONE, Alphabet.ZERO));

    private LinkedList<Alphabet> tape0;
    private LinkedList<Alphabet> tape1;
    private LinkedList<Alphabet> tape2;

    private long totalSteps;
    private long totalSubtractions;
    private long totalComparisons;
    private long totalDivisorIncrements;

    @Override
    public ExecutionResultDTO isPrime(LinkedList<Character> binaryNumber) {
        if (binaryNumber == null || binaryNumber.isEmpty()) {
            throw new IllegalArgumentException("Entrada não pode ser nula ou vazia");
        }

        LinkedList<Alphabet> alphabetInput = convertToAlphabet(binaryNumber);

        initialize(alphabetInput);

        ExecutionResultDTO dto = new ExecutionResultDTO();
        dto.setPrime(execute());
        dto.setTotalComparisons(totalComparisons);
        dto.setTotalSubtractions(totalSubtractions);
        dto.setTotalDivisorIncrements(totalDivisorIncrements);
        dto.setTotalSteps(totalSteps + totalSubtractions + totalDivisorIncrements + totalComparisons);
        return dto;
    }

    private LinkedList<Alphabet> convertToAlphabet(LinkedList<Character> input) {
        LinkedList<Alphabet> result = new LinkedList<>();

        for (Character c : input) {
            if (c == '0') {
                result.add(Alphabet.ZERO);
            } else if (c == '1') {
                result.add(Alphabet.ONE);
            } else {
                throw new IllegalArgumentException("Entrada deve conter apenas '0' e '1'");
            }
        }

        return result;
    }

    private void initialize(LinkedList<Alphabet> input) {
        tape0 = input;
        tape1 = new LinkedList<Alphabet>();
        tape2 = new LinkedList<Alphabet>();

        CopySubroutine.copy(tape1, TWO);
        totalSteps++;

        tape2 = CopySubroutine.copy(new LinkedList<Alphabet>(), tape0);
        totalSteps++;
    }

    private boolean execute() {
        while(true) {
            CopySubroutine.copy(tape2, tape0);
            totalSteps++;

            while(GreaterOrEqualsSubroutine.greaterOrEquals(tape2, tape1)) {
                totalComparisons++;
                tape2 = SubtractionSubroutine.subtract(tape2, tape1);
                totalSubtractions++;
            }

            if(EqualsSubroutine.equals(tape2, ZERO)) {
                totalComparisons++;
                if(!EqualsSubroutine.equals(tape1, tape0)) {
                    totalComparisons++;
                    return false;
                } else {
                    return true;
                }
            }

            IncrementSubroutine.increment(tape1);
            totalDivisorIncrements++;
            if(GreaterOrEqualsSubroutine.greaterOrEquals(tape1, tape0)) {
                totalComparisons++;
                return true;
            }
        }
    }
}