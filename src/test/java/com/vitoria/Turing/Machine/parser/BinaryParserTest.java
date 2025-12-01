package com.vitoria.Turing.Machine.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.LinkedList;

public class BinaryParserTest {

    @Test
    void testParseNormalNumber() {
        BigInteger number = new BigInteger("13");
        LinkedList<Character> result = BinaryParser.parse(number);

        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals('1', result.get(0));
        Assertions.assertEquals('1', result.get(1));
        Assertions.assertEquals('0', result.get(2));
        Assertions.assertEquals('1', result.get(3));
    }

    @Test
    void testParseZero() {
        BigInteger number = BigInteger.ZERO;
        LinkedList<Character> result = BinaryParser.parse(number);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals('0', result.get(0));
    }

    @Test
    void testParseLargeNumber() {
        BigInteger number = new BigInteger("12345678901234567890");
        String binary = number.toString(2);

        LinkedList<Character> result = BinaryParser.parse(number);

        Assertions.assertEquals(binary.length(), result.size());

        for (int i = 0; i < binary.length(); i++) {
            Assertions.assertEquals(binary.charAt(i), result.get(i));
        }
    }

    @Test
    void testNullInputThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> BinaryParser.parse((BigInteger) null));
        Assertions.assertThrows(NullPointerException.class, () -> BinaryParser.parse((LinkedList<Character>) null));
        Assertions.assertThrows(NullPointerException.class, () -> BinaryParser.formAsLinkedList(null));
    }

    @Test
    void testParseLinkedListToBigInteger() {
        LinkedList<Character> binary = new LinkedList<>();
        binary.add('1');
        binary.add('0');
        binary.add('1');

        BigInteger result = BinaryParser.parse(binary);
        Assertions.assertEquals(new BigInteger("5"), result);
    }

    @Test
    void testParseLinkedListWithSingleZero() {
        LinkedList<Character> binary = new LinkedList<>();
        binary.add('0');

        BigInteger result = BinaryParser.parse(binary);
        Assertions.assertEquals(BigInteger.ZERO, result);
    }

    @Test
    void testParseLinkedListWithSingleOne() {
        LinkedList<Character> binary = new LinkedList<>();
        binary.add('1');

        BigInteger result = BinaryParser.parse(binary);
        Assertions.assertEquals(BigInteger.ONE, result);
    }

    @Test
    void testFormAsLinkedList() {
        String binaryString = "1101";
        LinkedList<Character> result = BinaryParser.formAsLinkedList(binaryString);

        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals('1', result.get(0));
        Assertions.assertEquals('1', result.get(1));
        Assertions.assertEquals('0', result.get(2));
        Assertions.assertEquals('1', result.get(3));
    }

    @Test
    void testFormAsLinkedListEmptyString() {
        LinkedList<Character> result = BinaryParser.formAsLinkedList("");
        Assertions.assertTrue(result.isEmpty());
    }
}
