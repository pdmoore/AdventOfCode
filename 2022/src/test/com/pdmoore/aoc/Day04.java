package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04 {

    @Test
    public void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04_example");

        int actual = part1(input);

        assertEquals(2, actual);
    }
    @Test
    public void part1_slution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04");

        int actual = part1(input);

        assertEquals(462, actual);
    }

    private int part1(List<String> input) {
        int result = 0;

        for (String inputLine :
                input) {

            if (fullyContains(inputLine)) {
                result++;
            }
        }

        return result;
    }

    private boolean fullyContains(String inputLine) {
        String[] split = inputLine.split(",");
        String lhs = split[0];
        String rhs = split[1];

        String[] lhsplit = lhs.split("-");
        String[] rhsplit = rhs.split("-");

        int l_lower = Integer.parseInt(lhsplit[0]);
        int l_upper = Integer.parseInt(lhsplit[1]);

        int r_lower = Integer.parseInt(rhsplit[0]);
        int r_upper = Integer.parseInt(rhsplit[1]);

        if (l_lower <= r_lower && l_upper >= r_upper)
            return true;

        if (r_lower <= l_lower && r_upper >= l_upper)
            return true;

        return false;
    }

}
