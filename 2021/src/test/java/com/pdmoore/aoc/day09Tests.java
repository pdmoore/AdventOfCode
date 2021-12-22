package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day09Tests {

    @Test
    void day09_part1_example() {
        List<String> input = PuzzleInput.asListOfStringsFrom("data/day09_example");

        int actual = sumRiskLevel(input);

        assertEquals(15, actual);
    }

    private int sumRiskLevel(List<String> input) {
        return 0;
    }
}
