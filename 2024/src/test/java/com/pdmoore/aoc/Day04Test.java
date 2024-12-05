package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test {


    @Test
    void part1_example() {
        char[][] input = PuzzleInput.as2dCharArray("data/day04_example.txt");
        assertEquals(10, input[0].length);
        assertEquals(10, input.length);

        int actual = findWord(input, "XMAS");

        assertEquals(18, actual);
    }

    @Test
    void part1_myExample() {
        char[][] input = PuzzleInput.as2dCharArray("data/day04_myexample.txt");

        int actual = findWord(input, "XMAS");

        assertEquals(8, actual);
    }

    @Test
    void part1_do_not_double_count() {
        char[][] input = {"..XMAS..".toCharArray()};

        int actual = findWord(input, "XMAS");

        assertEquals(1, actual);
    }

    @Test
    void part1() {
        char[][] input = PuzzleInput.as2dCharArray("data/day04.txt");
        assertEquals(140, input[0].length);
        assertEquals(140, input.length);

        int actual = findWord(input, "XMAS");

        assertEquals(2549, actual);
    }

    @Test
    void part2() {
        char[][] input = PuzzleInput.as2dCharArray("data/day04.txt");

        int actual = find_X_MAS(input);

        assertEquals(2003, actual);
    }

    @Test
    void part2_example() {
        char[][] input = PuzzleInput.as2dCharArray("data/day04_example.txt");

        int actual = find_X_MAS(input);

        assertEquals(9, actual);
    }

    @Test
    void part2_crossExample() {
        char[][] input = PuzzleInput.as2dCharArray("data/day04_crossexample.txt");

        int actual = find_X_MAS(input);

        assertEquals(4, actual);
    }

    private int findWord(char[][] input, String word) {

        int result = 0;

        List<String> words = new ArrayList<String>();
        for (int i = 0; i <= input.length; i++) {
            for (int j = 0; j <= input[0].length; j++) {
                if (wordAt(input, i, j, 0, 1)) result++; //s
                if (wordAt(input, i, j, 1, 1)) result++; //se
                if (wordAt(input, i, j, 1, 0)) result++; //s
                if (wordAt(input, i, j, 1, -1)) result++; //sw
                if (wordAt(input, i, j, 0, -1)) result++; //w
                if (wordAt(input, i, j, -1, -1)) result++; //nw
                if (wordAt(input, i, j, -1, 0)) result++; //n
                if (wordAt(input, i, j, -1, 1)) result++; //ne
            }
        }

        return result;
    }

    private boolean wordAt(char[][] input, int x, int y, int dx, int dy) {
        String target = "XMAS";
        for (int l = 0; l < 4; l++) {
            try {
                if (input[x + (dx * l)][y + (dy * l)] != target.charAt(l)) return false;
            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }

        return true;
    }

    private int find_X_MAS(char[][] input) {
        int result = 0;

        List<String> words = new ArrayList<String>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (centeredCheck(input, i, j)) result++;
            }
        }

        return result;
    }

    private boolean centeredCheck(char[][] input, int x, int y) {
        char center = input[x][y];
        try {
            if (center == 'A') {

                char upperleft = input[x - 1][y - 1];
                char upperright = input[x - 1][y + 1];
                char lowerleft = input[x + 1][y - 1];
                char lowerright = input[x + 1][y + 1];

                if (upperleft == 'M' && upperright == 'M' && lowerleft == 'S' && lowerright == 'S') return true;
                if (upperleft == 'M' && lowerleft == 'M' && upperright == 'S' && lowerright == 'S') return true;
                if (upperleft == 'S' && upperright == 'S' && lowerleft == 'M' && lowerright == 'M') return true;
                if (upperleft == 'S' && lowerleft == 'S' && upperright == 'M' && lowerright == 'M') return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
