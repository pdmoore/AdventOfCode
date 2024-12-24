package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {

    @Test
    void part1Example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day19_example.txt");

        int actual = solvePart1(input).size();

        assertEquals(6, actual);
    }

    @Test
    void part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day19.txt");

        int actual = solvePart1(input).size();

        assertEquals(355, actual);
    }

    @Test
    void part2Example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day19_example.txt");

        int actual = solvePart2(input);

        assertEquals(16, actual);
    }

    @Test
    void part2_examples_one_at_time() {
        String towelPatterns = "r, wr, b, g, bwu, rb, gb, br";
        List<String> input = List.of(towelPatterns, "", "brwrr");
//        int actual = solvePart2(input);
//        assertEquals(2, actual);

//        assertEquals(1, solvePart2(List.of(towelPatterns, "", "bggr")));
        assertEquals(4, solvePart2(List.of(towelPatterns, "", "gbbr")));
    }

    @Test
    void part2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day19.txt");

        int actual = solvePart2(input);

        assertEquals(-1, actual);
    }

    int allDesignPermutations = 0;
    Map<String, Integer> cachedPatterns = new HashMap<>();
    private int solvePart2(List<String> input) {
        List<String> patterns = grabPatternsFrom(input.get(0));

        List<String> validDesigns = solvePart1(input);
        allDesignPermutations = 0;
        cachedPatterns = new HashMap<>();
        for (String pattern : patterns) {
            int count = tryPermutations(patterns, pattern);
            cachedPatterns.put(pattern, count);
        }
        for (String design : validDesigns) {
            allDesignPermutations += tryPermutations(patterns, design);
        }

        return allDesignPermutations;
    }

    // TODO - this is not a brute force solution. Think about caching solved sub-problems
    // along the way.
    // Could it be building permutations from the patterns list first and then looking for sub-strings?
    private int tryPermutations(List<String> patterns, String design) {

        // Maybe determine cached patterns by length of pattern:
        // r, b, g
        // wr, rb, gb, br,
        // bwu
        // then do the same for the designs?


        // how to show br should be 'br' and 'b, r'? need to update the cached permutations
        // once and not each time it's encountered
        // rather than copy all patterns in once at the beginning, build it before solving designs?
        if (design.isEmpty()) {
            return 0;
        }

//        if (cachedPatterns.containsKey(design)) {
//            return cachedPatterns.get(design);
//        }
//
//        if (patterns.contains(design)) {
//            return 1;
//        }
//
//        for (String pattern : patterns) {
//            if (design.startsWith(pattern)) {
//                int tryPermutations = tryPermutations(patterns, design.substring(pattern.length()));
//                if (tryPermutations > 0) {
//                    cachedPatterns.put(pattern, tryPermutations);
//                } else {
//                    cachedPatterns.put(pattern, 1);
//                }
//            }
//        }

        return 0;
    }

    private List<String> solvePart1(List<String> input) {
        List<String> patterns = grabPatternsFrom(input.get(0));

        List<String> validDesigns = new ArrayList<>();
        for (int i = 2; i < input.size(); i++) {

            try {
                isValidDesign(patterns, input.get(i));
            } catch (IllegalArgumentException x) {
                validDesigns.add(input.get(i));
            }

        }

        return validDesigns;
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
