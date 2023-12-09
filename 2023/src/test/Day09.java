package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09 {

    @Test
    void part1_example() {
        List<String> input = Arrays.asList("0 3 6 9 12 15",
                "1 3 6 10 15 21",
                "10 13 16 21 30 45");

        int actual = actuallySolvePart1(input);

        assertEquals(114, actual);
    }

    @Test
    void part2_example() {
        List<String> input = Arrays.asList("0 3 6 9 12 15",
                "1 3 6 10 15 21",
                "10 13 16 21 30 45");

        int actual = actuallySolvePart2(input);

        assertEquals(2, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day09");
        int actual = actuallySolvePart1(input);
        assertEquals(1798691765, actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day09");
        int actual = actuallySolvePart2(input);
        assertEquals(1104, actual);
    }


    @Test
    void part1_example_line_1() {
        String input = "0 3 6 9 12 15";

        int actual = part1_solveSingleLine(input);

        assertEquals(18, actual);
    }

    @Test
    void part1_example_line_2() {
        String input = "1   3   6  10  15  21";

        int actual = part1_solveSingleLine(input);

        assertEquals(28, actual);
    }

    @Test
    void part1_example_line_3() {
        String input = "10 13 16 21 30 45";

        int actual = part1_solveSingleLine(input);

        assertEquals(68, actual);
    }

    @Test
    void part2_example_line_1() {
        String input = "0 3 6 9 12 15";

        int actual = part2_solveSingleLine(input);

        assertEquals(-3, actual);
    }

    @Test
    void part2_example_line_2() {
        String input = "1   3   6  10  15  21";

        int actual = part2_solveSingleLine(input);

        assertEquals(0, actual);
    }

    @Test
    void part2_example_line_3() {
        String input = "10 13 16 21 30 45";

        int actual = part2_solveSingleLine(input);

        assertEquals(5, actual);
    }

    // ----------------------------------------
    private int part2_solveSingleLine(String input) {
        List<Integer> firstRow = convertStringsToIntgers(input);
        Collections.reverse(firstRow);
        return solvePart1(firstRow);
    }

    private int part1_solveSingleLine(String input) {
        List<Integer> firstRow = convertStringsToIntgers(input);
        return solvePart1(firstRow);
    }

    private static Integer solvePart1(List<Integer> firstRow) {
        List<List<Integer>> histories = new ArrayList<>();
        histories.add(firstRow);

        boolean allZeros = false;
        while (!allZeros) {
            List<Integer> nextHistory = new ArrayList<>();
            List<Integer> lastHistory = histories.get(histories.size() - 1);
            allZeros = true;
            for (int i = 1; i < lastHistory.size(); i++) {
                int difference = lastHistory.get(i) - lastHistory.get(i - 1);
                nextHistory.add(difference);
                if (difference != 0) {
                    allZeros = false;
                }
            }
            histories.add(nextHistory);
        }

        // Now go backwards and add up to the top
        for (int i = histories.size() - 1; i > 0; i--) {
            List<Integer> bottomHistory = histories.get(i);
            List<Integer> historyOneAbove = histories.get(i - 1);
            int newEnd = bottomHistory.get(bottomHistory.size() - 1) + historyOneAbove.get(historyOneAbove.size() - 1);
            historyOneAbove.add(newEnd);
        }

        return histories.get(0).get(histories.get(0).size() - 1);
    }

    private int actuallySolvePart1(List<String> input) {
        return input.stream().mapToInt(this::part1_solveSingleLine).sum();
    }

    private int actuallySolvePart2(List<String> input) {
        return input.stream().mapToInt(this::part2_solveSingleLine).sum();
    }



    private static List<Integer> convertStringsToIntgers(String input) {
        String[] s = input.split(" ");

        List<Integer> firstRow = new ArrayList<>();
        for (String number :
                s) {
            if (!number.isEmpty()) {
                firstRow.add(Integer.valueOf(number));
            }
        }
        return firstRow;
    }
}
