package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {

    @Test
    void part1Example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day19_example.txt");

        int actual = solvePart1(input);

        assertEquals(6, actual);

    }

    private int solvePart1(List<String> input) {
        List<String> patterns = grabPatternsFrom(input.get(0));

        List<String> validDesigns = new ArrayList<>();
        for (int i = 2; i < input.size(); i++) {

            try {
                isValidDesign(patterns, input.get(i));
            } catch (IllegalArgumentException x) {
                validDesigns.add(input.get(i));
            }

        }

        return validDesigns.size();
    }

    private boolean isValidDesign(List<String> patterns, String design) {
        if (design.isEmpty()) throw new IllegalArgumentException("design is valid");

        for (String pattern : patterns) {
            if (design.startsWith(pattern)) {
                if (!isValidDesign(patterns, design.substring(pattern.length()))) {
                    continue;
                }
            }
        }

        return false;
    }

    private List<String> grabPatternsFrom(String inputLine) {
        String[] split = inputLine.split(",");


        List<String> patterns = new ArrayList<>();
        for (String pattern : split) {
            patterns.add(pattern.trim());
        }

        return patterns;
    }
}
