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
