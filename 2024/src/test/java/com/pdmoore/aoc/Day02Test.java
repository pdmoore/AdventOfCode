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
        return (int) input
                .stream()
                .filter(this::isSafe)
                .count();
    }

    private int countOfTolerantSafeReports(List<String> input) {
        return (int) input
                .stream()
                .filter(this::isTolerantSafe)
                .count();
    }

    private boolean isSafe(String line) {
        String[] levels = line.split(" ");
        boolean decreasing = Integer.parseInt(levels[0]) > Integer.parseInt(levels[1]);
        for (int i = 1; i < levels.length; i++) {
            int next = Integer.parseInt(levels[i]);
            int delta;
            if (decreasing) {
                delta = Integer.parseInt(levels[i - 1]) - next;
            } else {
                delta = next - Integer.parseInt(levels[i - 1]);
            }
            if (delta <= 0 || delta > 3) {
                return false;
            }
        }

        return true;
    }

    private boolean isTolerantSafe(String line) {
        // for each line, slice out one level and test if the resulting report is safe or not
        String[] levels = line.split(" ");

        for (int i = 0; i < levels.length; i++) {
            StringBuilder reportWithOneSkippedLevel = new StringBuilder();
            for (int j = 0; j < levels.length; j++) {
                if (i != j) {
                    reportWithOneSkippedLevel.append(levels[j]).append(" ");
                }
            }
            if (isSafe(reportWithOneSkippedLevel.toString())) {
                return true;
            }
        }

        return false;
    }
}
