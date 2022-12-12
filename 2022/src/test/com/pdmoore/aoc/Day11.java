package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day11_example");

        int actual = part1(input);

        assertEquals(101 * 105, actual);

    }

    @Test
    @Disabled
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day11");

        int actual = part1(input);

        assertEquals(99, actual);

    }

    private int part1(List<String> input) {
        return 0;
    }
}
