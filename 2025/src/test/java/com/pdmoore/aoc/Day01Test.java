package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day01Test {

    @Test
    void solvePart1Example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day01_example.txt");

        int actual = solve(input);

        Assertions.assertEquals(3, actual);
    }

    @Test
    void solvePart1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day01.txt");

        int actual = solve(input);

        Assertions.assertEquals(1055, actual);
    }

    @Test
    void solvePart2Example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day01_example.txt");

        int actual = solve_2(input);

        Assertions.assertEquals(6, actual);
    }

    @Test
    void part2_leftTurn_stopOnZero() {
        List<String> input = List.of("L45", "L5");
        int actual = solve_2(input);
        Assertions.assertEquals(1, actual);
    }

    @Test
    void part2_leftTurn_manyTimesPastZero() {
        List<String> input = List.of("L1000");
        int actual = solve_2(input);
        Assertions.assertEquals(10, actual);
    }

    @Test
    void part2_rightTurn_countOnZero() {
        List<String> input = List.of("R45", "R5");
        int actual = solve_2(input);
        Assertions.assertEquals(2, actual);
    }

    @Test
    void part2_rightTurn_manyTimesPastZero() {
        List<String> input = List.of("R200");
        int actual = solve_2(input);
        Assertions.assertEquals(2, actual);
    }

    @Test
    void part2_partialList() {
        List<String> input = List.of("L44", "R35", "R4", "L40", "L13", "L7",
                "L33", "L8", "L44", "L39");
        int actual = solve_2(input);
        Assertions.assertEquals(3, actual);
    }

    @Test
    void solvePart2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day01.txt");

        int actual = solve_2(input);

        Assertions.assertEquals(-99, actual);
    }

    private int solve_2(List<String> input) {
        int currentPosition = 50;
        int dialPassesZeroCount = 0;

        for (String line : input) {
            int value = Integer.parseInt(line.substring(1));
            if (line.charAt(0) == 'L') {
                currentPosition -= value;
                while (currentPosition < 0) {
                    dialPassesZeroCount++;
                    currentPosition += 100;
                }
                if (currentPosition == 0) {
                    dialPassesZeroCount++;
                }
            } else if (line.charAt(0) == 'R') {
                currentPosition += value;
                while (currentPosition >= 100) {
                    dialPassesZeroCount++;
                    currentPosition -= 100;
                }
            } else {
                throw new IllegalArgumentException("Invalid input" + line);
            }

            System.out.println(String.format("The dial is rotated %s to point at %d", line, currentPosition));
        }
        return dialPassesZeroCount;
    }

    private int solve(List<String> input) {
        int currentPosition = 50;
        int dialAtZeroCount = 0;

        for (String line : input) {
            int value = Integer.parseInt(line.substring(1));
            if (line.charAt(0) == 'L') {
                currentPosition -= value;
                if (currentPosition < 0) { currentPosition = currentPosition % 100; }
            } else if (line.charAt(0) == 'R') {
                currentPosition += value;
                if (currentPosition >= 100) { currentPosition = Math.abs(currentPosition % 100); }
            } else {
                throw new IllegalArgumentException("Invalid input" + line);
            }

            if (currentPosition == 0) dialAtZeroCount++;
            System.out.println(String.format("The dial is rotated %s to point at %d", line, currentPosition));
        }


        return dialAtZeroCount;
    }
}
