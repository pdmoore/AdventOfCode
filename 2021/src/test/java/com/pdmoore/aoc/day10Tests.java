package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class day10Tests {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day10_example");

        int actual = calcSyntaxErrorScore(input);

        Assertions.assertEquals(26397, actual);
    }

    private int calcSyntaxErrorScore(List<String> input) {
        return 0;
    }
}
