package com.vitoria.Turing.Machine.subroutines;

import com.vitoria.Turing.Machine.model.Alphabet;

import java.util.LinkedList;

public class IncrementSubroutine {
    public static LinkedList<Alphabet> increment(LinkedList<Alphabet> tape) {
        if (tape.isEmpty()) {
            tape.add(Alphabet.ONE);
            return tape;
        }

        int carry = 1;
        for (int i = tape.size() - 1; i >= 0; i--) {
            if (tape.get(i) == Alphabet.ONE) {
                tape.set(i, Alphabet.ZERO);
            } else if (tape.get(i) == Alphabet.ZERO) {
                tape.set(i, Alphabet.ONE);
                carry = 0;
                break;
            } else {
                tape.set(i, Alphabet.ONE);
                carry = 0;
                break;
            }
        }

        if (carry == 1) tape.addFirst(Alphabet.ONE); // overflow

        while (tape.size() > 1 && tape.getFirst() == Alphabet.ZERO) {
            tape.removeFirst();
        }

        return tape;
    }

}
