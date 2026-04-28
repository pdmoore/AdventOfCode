package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12_take2_Tests {

    @Test
    void emptyElements_SumToZero() {
        assertEquals(0, sumAllNumbersIn("[]"));
        assertEquals(0, sumAllNumbersIn("{}"));
    }

    @Test
    void sumPositiveNumbers_CommaDelimited() {
        String input = "[1,2,3]";

        int actual = sumAllNumbersIn(input);

        assertEquals(6, actual);
    }

    @Test
    void sumPositiveNumber_nestedBrackets() {
        String input = "[[[3]]]";
        int actual = sumAllNumbersIn(input);
        assertEquals(3, actual);
    }

    @Test
    void sumPositiveNumbers_CharactersMixedIn() {
        String input = "{\"a\":2,\"b\":4}";

        int actual = sumAllNumbersIn(input);

        assertEquals(6, actual);
    }

    @Test
    void sumNegativeNumber() {
        String input = "{\"a\":{\"b\":4},\"c\":-1}";
        int actual = sumAllNumbersIn(input);
        assertEquals(3, actual);

        input = "{\"a\":[-1,1]}";
        actual = sumAllNumbersIn(input);
        assertEquals(0, actual);

        input = "[-1,{\"a\":1}]";
        actual = sumAllNumbersIn(input);
        assertEquals(0, actual);
    }

    @Test
    void longerExample() {
        String input = "[\"violet\",{\"e\":\"blue\",\"a\":187,\"d\":115,\"j\":193,\"c\":119,\"h\":\"yellow\",\"b\":\"yellow\",\"g\":\"red\",\"f\":74,\"i\":25},\"orange\",0,-17,\"yellow\",-23]";
        int actual = sumAllNumbersIn(input);
        int expected = 187+115+193+119+74+25+0-17-23;
        assertEquals(expected, actual);
    }



    private int sumAllNumbersIn(String input) {
        //naive approach - strip away everything but commas, dashes, and digits
        // the split and sum the numbers

//        String stripped = s.replace("[", "").replace("]", "");
//        stripped = stripped.replace("{", "").replace("}", "");
        String stripped = input.replaceAll("[a-z\"\\:\\[\\]\\{\\}]", "");
        if (stripped.isEmpty()) return 0;
        int sum1 = Arrays.stream(stripped.split(","))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt).sum();
        return sum1;
    }
}
