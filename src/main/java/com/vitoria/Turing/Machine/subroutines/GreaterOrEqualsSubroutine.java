package com.vitoria.Turing.Machine.subroutines;

import com.vitoria.Turing.Machine.model.Alphabet;

import java.util.LinkedList;

public class GreaterOrEqualsSubroutine {
    public static boolean greaterOrEquals(LinkedList<Alphabet> aOriginal, LinkedList<Alphabet> bOriginal) {
        LinkedList<Alphabet> a = new LinkedList<>(aOriginal);
        LinkedList<Alphabet> b = new LinkedList<>(bOriginal);

        trimBlanksRight(a);
        trimBlanksRight(b);

        while (a.size() < b.size())
            a.addFirst(Alphabet.ZERO);

        while (b.size() < a.size())
            b.addFirst(Alphabet.ZERO);

        for (int i = 0; i < a.size(); i++) {
            int bitA = (a.get(i) == Alphabet.ONE ? 1 : 0);
            int bitB = (b.get(i) == Alphabet.ONE ? 1 : 0);
            if (bitA > bitB) return true;
            if (bitA < bitB) return false;
        }

        return true;
    }

    private static void trimBlanksRight(LinkedList<Alphabet> tape) {
        while (!tape.isEmpty() && tape.getLast() == Alphabet.BLANK) {
            tape.removeLast();
        }
    }
}
