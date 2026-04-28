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
    private int sumAllNumbersIn(String s) {
        //naive approach - strip away everything but commas, dashes, and digits
        // the split and sum the numbers

        String stripped = s.replace("[", "").replace("]", "");
        stripped = stripped.replace("{", "").replace("}", "");
        if (stripped.isEmpty()) return 0;
        int sum1 = Arrays.stream(stripped.split(",")).mapToInt(Integer::parseInt).sum();
        return sum1;
    }
}
