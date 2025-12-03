package com.vitoria.Turing.Machine.subroutines;

import com.vitoria.Turing.Machine.model.Alphabet;

import java.util.LinkedList;

public class SubtractionSubroutine {
    public static LinkedList<Alphabet> subtract(LinkedList<Alphabet> aInput, LinkedList<Alphabet> bInput) {
        LinkedList<Alphabet> result = new LinkedList<>(aInput);
        LinkedList<Alphabet> b = new LinkedList<>(bInput);

        trimBlanksRight(result);
        trimBlanksRight(b);

        while (b.size() < result.size())
            b.addFirst(Alphabet.ZERO);

        int borrow = 0;
        for (int i = result.size() - 1; i >= 0; i--) {
            int bitA = (result.get(i) == Alphabet.ONE ? 1 : 0);
            int bitB = (i < b.size() && b.get(i) == Alphabet.ONE ? 1 : 0);

            if(b.size() == result.size()) {
                bitB = (b.get(i) == Alphabet.ONE ? 1 : 0);
            }

            int diff = bitA - bitB - borrow;
            if (diff == 0) {
                result.set(i, Alphabet.ZERO);
                borrow = 0;
            } else if (diff == 1) {
                result.set(i, Alphabet.ONE);
                borrow = 0;
            } else if (diff == -1) { // 0 - 1
                result.set(i, Alphabet.ONE);
                borrow = 1;
            } else { // 0 - 1 - 1 = -2
                result.set(i, Alphabet.ZERO);
                borrow = 1;
            }
        }

        while (result.size() > 1 && result.getFirst() == Alphabet.ZERO)
            result.removeFirst();

        return result;
    }

    private static void trimBlanksRight(LinkedList<Alphabet> tape) {
        while (!tape.isEmpty() && tape.getLast() == Alphabet.BLANK) {
            tape.removeLast();
        }
    }
}