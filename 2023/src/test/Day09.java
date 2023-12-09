package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class Day09 {

    private List<String> _exampleInput  = Arrays.asList("0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45");

    @Test
    void part1_example() {
        int actual = actuallySolvePart1(_exampleInput);

        assertEquals(114, actual);
    }

    @Test
    void part2_example() {
        int actual = actuallySolvePart2(_exampleInput);

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

    @ParameterizedTest
    @MethodSource("exampleInput")
    void part1_example_input_each_line(String inputLine, int expected, int ignored) {
        int actual = part1_solveSingleLine(inputLine);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("exampleInput")
    void part2_example_input_each_line(String inputLine, int ignored, int expected) {
        String input = "0 3 6 9 12 15";

        int actual = part2_solveSingleLine(inputLine);

        assertEquals(expected, actual);
    }

    private static Stream<Arguments> exampleInput() {
        // single line of input, part 1 expected answer, part 2 expected answer
        return Stream.of(
            arguments("0 3 6 9 12 15", 18, -3),
            arguments("1   3   6  10  15  21", 28, 0),
            arguments("10 13 16 21 30 45", 68, 5)
        );
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
