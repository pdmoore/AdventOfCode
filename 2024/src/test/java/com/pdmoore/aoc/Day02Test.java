package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Test {


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

        assertEquals(99, actual);
    }



    private int countOfSafeReports(List<String> input) {
        int result = 0;

        for (String line: input) {
            if (isSafe(line)) result++;
        }

        return result;
    }

    private boolean isSafe(String line) {
        //7 6 4 2 1
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
            return true;
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
            return true;
        }
    }
}
