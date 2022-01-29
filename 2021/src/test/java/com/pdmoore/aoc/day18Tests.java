package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day18Tests {

    @Test
    void part1_magnitude_examples() {
        //[[1,2],[[3,4],5]] becomes 143
        assertEquals(29, magnitudeOf("[9,1]"));
        assertEquals(129, magnitudeOf("[[9,1],[1,9]]"));
//        assertEquals(143, magnitudeOf("[[1,2],[[3,4],5]]"));
    }

    private int magnitudeOf(String input) {
        String removeOuterBrackets = input.substring(1, input.length() - 1);
        if (removeOuterBrackets.charAt(0) != '[') {
            String[] split = removeOuterBrackets.split(",");
            int magnitude = (3 * Integer.parseInt(split[0])) + (2 * Integer.parseInt(split[1]));
            return magnitude;
        } else {
            int splitIx = removeOuterBrackets.indexOf("],[");
            String lhs = removeOuterBrackets.substring(0, splitIx + 1);
            String rhs = removeOuterBrackets.substring(splitIx + 2);
            return (3 * magnitudeOf(lhs)) + (2 * magnitudeOf(rhs));
        }
    }
}
