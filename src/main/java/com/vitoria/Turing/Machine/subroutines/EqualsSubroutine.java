package com.vitoria.Turing.Machine.subroutines;

import com.vitoria.Turing.Machine.model.Alphabet;

import java.util.LinkedList;

public class EqualsSubroutine {
    public static boolean equals(LinkedList<Alphabet> a, LinkedList<Alphabet> b) {
        if (a == null || b == null)
            throw new IllegalArgumentException("Tapes cannot be null");

        LinkedList<Alphabet> aCopy = normalize(a);
        LinkedList<Alphabet> bCopy = normalize(b);

        if (aCopy.size() != bCopy.size()) return false;

        for (int i = 0; i < aCopy.size(); i++)
            if (aCopy.get(i) != bCopy.get(i)) return false;

        return true;
    }

    private static LinkedList<Alphabet> normalize(LinkedList<Alphabet> tape) {
        LinkedList<Alphabet> result = new LinkedList<>();

        for(Alphabet alpha : tape) {
            if(alpha != Alphabet.BLANK) result.add(alpha);
        }

        while(result.size() > 1 && result.getFirst() == Alphabet.ZERO) {
            result.removeFirst();
        }

        if(result.isEmpty()) result.add(Alphabet.ZERO);

        return result;
    }
}
