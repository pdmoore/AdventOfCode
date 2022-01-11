package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day15Tests {

    @Test
    void day15_part_example() {
        int[][] input = PuzzleInput.as2dIntArray("data/day15_example");

        int actual = findLowestRiskPath(input);

        assertEquals(40, actual);
    }

    private int findLowestRiskPath(int[][] cavernMap) {
        return 0;
    }
}
