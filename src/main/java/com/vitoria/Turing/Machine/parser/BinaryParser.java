package com.vitoria.Turing.Machine.parser;

import java.math.BigInteger;
import java.util.LinkedList;

public class BinaryParser {

    public static LinkedList<Character> parse(BigInteger number) {
        String binaryString = number.toString(2);

        return formAsLinkedList(binaryString);
    }

    public static BigInteger parse(LinkedList<Character> binaryNumber) {
        BigInteger result = BigInteger.ZERO;

        for (char c : binaryNumber) {
            result = result.shiftLeft(1);
            if (c == '1') {
                result = result.add(BigInteger.ONE);
            }
        }

        return result;
    }

    public static LinkedList<Character> formAsLinkedList(String binaryNumber) {
        LinkedList<Character> tape = new LinkedList<>();

        for (char c : binaryNumber.toCharArray())
            tape.add(c);

        return tape;
    }
}
