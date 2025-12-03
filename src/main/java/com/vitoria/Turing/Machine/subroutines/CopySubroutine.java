package com.vitoria.Turing.Machine.subroutines;

import com.vitoria.Turing.Machine.model.Alphabet;

import java.util.LinkedList;

public class CopySubroutine {
    public static LinkedList<Alphabet> copy(LinkedList<Alphabet> toBeCopiedTo, LinkedList<Alphabet> toBeCopied) {
        toBeCopiedTo.clear();

        if (toBeCopied == null || toBeCopied.isEmpty()) {
            toBeCopiedTo.add(Alphabet.BLANK);
            return toBeCopiedTo;
        }

        toBeCopiedTo.addAll(toBeCopied);

        return toBeCopiedTo;
    }
}
