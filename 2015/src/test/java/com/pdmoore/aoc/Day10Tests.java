package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Tests {

    static final String input = "1321131112";

    @Test
    void examples_SingleTransformations() {
        Assertions.assertAll(() -> {
            assertEquals("11", lookAndSay("1"));
            assertEquals("21", lookAndSay("11"));
            assertEquals("1211", lookAndSay("21"));
            assertEquals("111221", lookAndSay("1211"));
            assertEquals("312211", lookAndSay("111221"));
        });
    }

    @Test
    void part1_solution() {
        int actual = lookAndSay(input, 40);
        assertEquals(492982, actual);
    }

    @Test
    void part2_solution() {
        // Kinda disappointed this was so easy
        // Was expecting the result o exceed the max length of String/StringBuilder
        int actual = lookAndSay(input, 50);
        assertEquals(6989950, actual);
    }

    private int lookAndSay(String input, int count) {
        String result = input;
        for (int i = 0; i < count; i++) {
            result = lookAndSay(result);
        }

        return result.length();
    }

    class NextStep {
        Integer numberOfDigits;
        Character digitItself;

        public NextStep(Character d, int numberOfDigits) {
            this.digitItself = d;
            this.numberOfDigits = numberOfDigits;
        }

        @Override
        public String toString() {
            return new StringBuilder().append(numberOfDigits).append(digitItself).toString();
        }
    }

    private String lookAndSay(String input) {
        List<NextStep> result = new ArrayList<>();
        NextStep characterAndCount = null;
        Character previousChar = null;
        for (int i = 0; i < input.length(); i++) {
            Character currentChar = input.charAt(i);

            if (currentChar.equals(previousChar)) {
                characterAndCount.numberOfDigits++;
            } else {
                previousChar = currentChar;
                if (characterAndCount != null) {
                    result.add(characterAndCount);
                }
                characterAndCount = new NextStep(currentChar, 1);
            }
        }
        result.add(characterAndCount);

        return result.stream()
                .map(n -> n.toString())
                .collect(Collectors.joining());
    }

}
