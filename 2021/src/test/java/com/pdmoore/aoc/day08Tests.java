package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day08Tests {

    @Test
    void day08_part1_example() {
        List<String> input = PuzzleInput.asListOfStringsFrom("data/day08_example");

        int actual = countUniqueSegments(input);

        assertEquals(26, actual);
    }

    @Test
    void day08_part1_solution() {
        List<String> input = PuzzleInput.asListOfStringsFrom("data/day08");

        int actual = countUniqueSegments(input);

        assertEquals(504, actual);
    }

    private int countUniqueSegments(List<String> input) {
        int uniqueSegmentCount = 0;

        for (String line :
                input) {
            String rightHandSide = line.split(Pattern.quote("|"))[1];
            String[] segments = rightHandSide.split(" ");
            for (String segment :
                    segments) {
                int segmentLength = segment.length();
                if (segmentLength == 2 || segmentLength == 3 || segmentLength == 4 || segmentLength == 7) {
                    uniqueSegmentCount++;
                }
            }
        }

        return uniqueSegmentCount;
    }
}
