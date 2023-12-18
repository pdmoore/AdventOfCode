package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18 {

    @Test
    void dig_trench() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day18_part1_example");
        int actual = digTrench(input, 6);
        assertEquals(38, actual);
    }

    private int digTrench(List<String> input, int gridSize) {

        int result = 0;
        for (String inputLine :
                input) {
            String[] split = inputLine.split(" ");
            result += Integer.parseInt(split[1]);
        }


        return result;
    }
}
