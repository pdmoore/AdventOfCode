package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07 {

    @Test
    void part1_example() {
        List<String> input = Arrays.asList("32T3K 765", "T55J5 684",
                "KK677 28", "KTJJT 220", "QQQJA 483");

        int actual = part1SolveFor(input);

        assertEquals(6440, actual);
    }

    private int part1SolveFor(List<String> input) {
        int result = 765 * 1 + 220 * 2 + 28 * 3 + 684 * 4 + 483 * 5;
        return result;
    }
}
