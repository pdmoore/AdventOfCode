package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Day10Tests {

    /*
    1 becomes 11 (1 copy of digit 1).
11 becomes 21 (2 copies of digit 1).
21 becomes 1211 (one 2 followed by one 1).
1211 becomes 111221 (one 1, one 2, and two 1s).
111221 becomes 312211 (three 1s, two 2s, and one 1).
     */

    static final String input = "1321131112";

    @Test
    void examples_SingleTransformations() {
        Assertions.assertEquals("11", lookAndSay("1"));
    }

    private String lookAndSay(String input) {

        class Value {
            Character value;
            Integer count;

            public Value(Character v, int i) {
                value = v;
                count = i;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(count);
                sb.append(value);
                return sb.toString();
            }
        }

        // foreach element of input
        // if it is same as previous char, increment count
        // if it is different, add new value to list and start a new value
        Character previousChar = null;
        List<Value> result = new ArrayList<>();
        Value v = null;
        int currentCharCount = 0;
        for (int i = 0; i < input.length(); i++) {
            Character currentChar = input.charAt(i);

            if (currentChar.equals(previousChar)) {
                currentCharCount++;
            } else {
                if (v != null) {
                    v.count = currentCharCount;
                    result.add(v);
                }
                v = new Value(currentChar, 1);
                currentCharCount = 1;
            }
        }





        return v.toString();
    }

}
