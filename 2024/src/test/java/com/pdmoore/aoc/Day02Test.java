package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day02_example.txt");

        int actual = countOfSafeReports(input);

        assertEquals(2, actual);
    }

    @Test
    void part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day02.txt");

        int actual = countOfSafeReports(input);

        assertEquals(369, actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day02_example.txt");

        int actual = countOfTolerantSafeReports(input);

        assertEquals(4, actual);
    }

    @Test
    void part2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day02.txt");

        int actual = countOfTolerantSafeReports(input);

        assertEquals(428, actual);
    }

    private int countOfSafeReports(List<String> input) {
        int result = 0;

        for (String line: input) {
            if (isSafe(line)) result++;
        }

        return result;
    }

    private boolean isSafe(String line) {
        String[] levels = line.split(" ");
        int current = Integer.parseInt(levels[0]);
        int second = Integer.parseInt(levels[1]);
        if (current > second) {
            // decreasing
            for (int i = 1; i < levels.length; i++) {
                int next = Integer.parseInt(levels[i]);
                int delta = current - next;
                if (delta > 0 && delta <= 3) {
                    current = next;
                } else {
                    return false;
                }
            }
        } else {
            // increasing
            for (int i = 1; i < levels.length; i++) {
                int next = Integer.parseInt(levels[i]);
                int delta = next  - current;
                if (delta > 0 && delta <= 3) {
                    current = next;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    private int countOfTolerantSafeReports(List<String> input) {
        int result = 0;

        for (String line: input) {
            if (isTolerantSafe(line)) result++;
        }

        return result;
    }

    private boolean isTolerantSafe(String line) {
        if (isSafe(line)) {
            return true;
        }

        // for each line, slice out one number and test if it's good or not
        String[] levels = line.split(" ");

        for (int i = 0; i < levels.length; i++) {

            StringBuilder levelSkip = new StringBuilder();
            for (int j = 0; j < levels.length; j++) {
                 if (i != j) {
                     levelSkip.append(levels[j]).append(" ");
                 }
            }
            if (isSafe(levelSkip.toString())) {
                return true;
            }
        }

        return false;
    }
}
