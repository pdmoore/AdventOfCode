package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day12Tests {

    @Test
    void day1_smallExample() {
        List<String> input = PuzzleInput.asStringListFrom("data/day12_smallExample");

        int actual = findDistinctPaths(input);

        assertEquals(10, actual);
    }

    @Test
    @Disabled
    void day1_slightlyLargerExample() {
        List<String> input = PuzzleInput.asStringListFrom("data/day12_slightlyLargerExample");

        int actual = findDistinctPaths(input);

        assertEquals(19, actual);
    }

    @Test
    @Disabled
    void day1_lastExample() {
        List<String> input = PuzzleInput.asStringListFrom("data/day12_lastExample");

        int actual = findDistinctPaths(input);

        assertEquals(226, actual);
    }

    @Test
    @Disabled
    void day1_part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day12");

        int actual = findDistinctPaths(input);

        assertEquals(-1, actual);
    }

    private int findDistinctPaths(List<String> input) {
        return 0;
    }
}
