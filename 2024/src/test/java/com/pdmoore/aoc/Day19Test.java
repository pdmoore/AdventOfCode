package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {

    @Test
    void part1Example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day19_example.txt");

        int actual = solvePart1(input);

        assertEquals(6, actual);

    }

    private int solvePart1(List<String> input) {
        return 0;
    }
}
