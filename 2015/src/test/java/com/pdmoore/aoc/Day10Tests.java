package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Tests {

    static final String input = "1321131112";

    @Test
    void examples_SingleTransformations() {
        assertEquals("11", lookAndSay("1"));
        assertEquals("21", lookAndSay("11"));
        assertEquals("1211", lookAndSay("21"));
        assertEquals("111221", lookAndSay("1211"));
        assertEquals("312211", lookAndSay("111221"));
    }

    @Test
    void part1_solution() {
        String actual = lookAndSay(input, 40);
        assertEquals(492982, actual.length());
    }

    private String lookAndSay(String input, int count) {
        String result = input;
        for (int i = 0; i < count; i++) {
            result = lookAndSay(result);
        }

        return result;
    }

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

    private String lookAndSay(String input) {
        Character previousChar = null;
        List<Value> result = new ArrayList<>();
        Value characterAndCount = null;
        for (int i = 0; i < input.length(); i++) {
            Character currentChar = input.charAt(i);

            if (currentChar.equals(previousChar)) {
                characterAndCount.count++;
            } else {
                previousChar = currentChar;
                if (characterAndCount != null) {
                    result.add(characterAndCount);
                }
                characterAndCount = new Value(currentChar, 1);
            }
        }
        result.add(characterAndCount);

        StringBuilder sb = new StringBuilder();
        for (Value v :
                result) {
            sb.append(v.toString());
        }

        return sb.toString();
    }

}
