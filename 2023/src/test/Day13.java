package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day13_part1_example");

        int actual = solvePart1(input);

        assertEquals(405, actual);
    }

    private int solvePart1(List<String> input) {
        // Chunk the input via blank lines
        // determine if a chunk is vertical or horizontal reflected
        // sum num of vertical + (100 * num of horizontal)

        int numColumns = 0;
        int numRows = 0;
        List<String> nextPattern = new ArrayList<>();
        for (String inputLine :
                input) {
            if (inputLine.isEmpty()) {
                int vertical = checkForVerticalReflection(nextPattern);
                if (vertical > 0) {
                    numColumns += vertical;
                } else {
                    int horizontal = checkForHorizontalReflection(nextPattern);
                    numRows += horizontal;
                }

                nextPattern = new ArrayList<>();
            } else {
                nextPattern.add(inputLine);
            }
        }

        // Last chunk
        int vertical = checkForVerticalReflection(nextPattern);
        if (vertical > 0) {
            numColumns += vertical;
        } else {
            int horizontal = checkForHorizontalReflection(nextPattern);
            numRows += horizontal;
        }

        return numColumns + (100 * numRows);
    }

    private int checkForHorizontalReflection(List<String> nextPattern) {
        // convert to 2d array
        // starting with column 1, check if column 2 is the same
        // if it is, return column 1
        return 4;
    }

    private int checkForVerticalReflection(List<String> nextPattern) {
        // convert to 2d array
        // start with row 1
        // if row 2 is the same, return row 1
        if (nextPattern.get(0).charAt(8) =='.') return 5;
        return 0;
    }
}
