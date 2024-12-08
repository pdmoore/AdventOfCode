package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08Test {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day08_example.txt");

        int actual = solvePart1(input);

        assertEquals(1, actual);
    }

    private int solvePart1(List<String> input) {
        return 0;
    }


}
