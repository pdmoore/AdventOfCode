package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Tests {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13_example.txt");

        int actual = solvePart1(input);

        assertEquals(480, actual);
    }

    private int solvePart1(List<String> input) {
        // need to split input by blank lines

        // for each set of three,
        // button a stats
        // button b stats
        // prize target
        //3 tokens for A button, 1 token for B button

        // solving a single set
        // would it be x mod a plus x mod b equal to 0
        // and y mod a plus y mod b equal to 0 for some pair of a and b

        // a or b can be pressed up to 100
        // so it could be 1 to 100 for each and whether both x and y are met
        // it is possible there is not solution



        // solve those for each set of inputs


        return 0;
    }
}
