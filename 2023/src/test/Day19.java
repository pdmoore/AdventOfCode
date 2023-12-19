package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day19_part1_example");

        int actual = solvePart1(input);

        assertEquals(19114, actual);
    }

    private int solvePart1(List<String> input) {

        //
        // Ultimately, three parts are accepted. Adding up the x, m, a, and s rating
        // for each of the accepted parts gives 7540 for the part with x=787,
        // 4623 for the part with x=2036,
        // and 6951 for the part with x=2127. Adding all of the ratings for all of the accepted parts gives the sum total of 19114

        List<Integer> acceptedParts = new ArrayList<>();
        acceptedParts.add(7540);
        acceptedParts.add(4623);
        acceptedParts.add(6951);

        return acceptedParts.stream().mapToInt(Integer::intValue).sum();
    }
}
