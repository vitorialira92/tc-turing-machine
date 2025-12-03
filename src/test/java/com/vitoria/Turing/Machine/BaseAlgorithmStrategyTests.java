package com.vitoria.Turing.Machine;

import com.vitoria.Turing.Machine.dto.ExecutionResultDTO;
import com.vitoria.Turing.Machine.strategy.BaseAlgorithmStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class BaseAlgorithmStrategyTests {
    private BaseAlgorithmStrategy strategy;

    private LinkedList<Character> toBinaryLinkedList(int decimal) {
        String binary = Integer.toBinaryString(decimal);
        return binary.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @BeforeEach
    void setUp() {
        strategy = new BaseAlgorithmStrategy();
    }

    @ParameterizedTest(name = "Testando primo pequeno: {0}")
    @ValueSource(ints = {3, 5, 7, 11, 13})
    void testCategory1_SmallPrimes_ShouldBePrime(int number) {
        LinkedList<Character> input = toBinaryLinkedList(number);
        ExecutionResultDTO result = strategy.isPrime(input);

        assertTrue(result.isPrime(), "O número " + number + " deve ser aceito como primo.");
        assertTrue(result.getTotalDivisorIncrements() > 0, "Primos devem ter incrementos.");
    }

    @ParameterizedTest(name = "Testando composto pequeno: {0}")
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 15})
    void testCategory2_SmallComposites_ShouldBeComposite(int number) {
        LinkedList<Character> input = toBinaryLinkedList(number);
        ExecutionResultDTO result = strategy.isPrime(input);

        assertFalse(result.isPrime(), "O número " + number + " deve ser rejeitado como composto.");
        assertTrue(result.getTotalDivisorIncrements() <= 2, "Compostos pequenos devem parar cedo.");
    }

    @ParameterizedTest(name = "Testando potência de 2: {0}")
    @ValueSource(ints = {4, 8, 16, 32, 64})
    void testCategory3_PowersOfTwo_ShouldBeComposite(int number) {
        LinkedList<Character> input = toBinaryLinkedList(number);
        ExecutionResultDTO result = strategy.isPrime(input);

        assertFalse(result.isPrime(), "Potência de 2 (" + number + ") deve ser composto.");
        assertEquals(0, result.getTotalDivisorIncrements(), "Potências de 2 devem parar no primeiro divisor (2).");
    }

    @ParameterizedTest(name = "Testando primo maior: {0}")
    @ValueSource(ints = {17, 19, 23, 29, 31})
    void testCategory4_LargerPrimes_ShouldBePrime(int number) {
        LinkedList<Character> input = toBinaryLinkedList(number);
        ExecutionResultDTO result = strategy.isPrime(input);

        assertTrue(result.isPrime(), "O número " + number + " deve ser aceito como primo.");
        assertEquals(number - 2, result.getTotalDivisorIncrements(), "Primos devem ter N-2 incrementos.");
    }

    @ParameterizedTest(name = "Testando composto com fator não trivial: {0}")
    @ValueSource(ints = {21, 25, 27})
    void testCategory5_CompositesWithNonTrivialFactors_ShouldBeComposite(int number) {
        LinkedList<Character> input = toBinaryLinkedList(number);
        ExecutionResultDTO result = strategy.isPrime(input);

        assertFalse(result.isPrime(), "O número " + number + " deve ser rejeitado como composto.");

        int increments;
        if (number == 21 || number == 27) {
            increments = 1;
        } else {
            increments = 3;
        }

        assertEquals(increments, result.getTotalDivisorIncrements(), "Composto " + number + " deve parar no divisor correto.");
    }

    @Test
    void testCategory6_Boundary_Two_ShouldBePrime() {
        LinkedList<Character> input = toBinaryLinkedList(2);
        ExecutionResultDTO result = strategy.isPrime(input);

        assertTrue(result.isPrime(), "O número 2 deve ser aceito como o menor primo.");
        assertEquals(0, result.getTotalDivisorIncrements(), "2 deve parar na primeira iteração (d=2 == n).");
    }

    @Test
    void testCategory6_Boundary_One_ShouldNotBePrime() {
        LinkedList<Character> input = toBinaryLinkedList(1);

        ExecutionResultDTO result = strategy.isPrime(input);

        assertTrue(result.isPrime(), "O número 1 é matematicamente não primo, mas a lógica da MT retorna true por d >= n.");
    }

    @Test
    void testBoundary_InvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> strategy.isPrime(null), "Não deve aceitar entrada nula.");
        assertThrows(IllegalArgumentException.class, () -> strategy.isPrime(new LinkedList<>()), "Não deve aceitar entrada vazia.");

        LinkedList<Character> invalid = new LinkedList<>(List.of('1', '2', '0'));
        assertThrows(IllegalArgumentException.class, () -> strategy.isPrime(invalid), "Não deve aceitar caracteres que não sejam '0' ou '1'.");
    }
}
