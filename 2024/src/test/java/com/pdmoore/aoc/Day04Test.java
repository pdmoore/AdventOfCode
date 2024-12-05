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
        for (int x = 0; x <= input.length; x++) {
            for (int y = 0; y <= input[0].length; y++) {

                // check [x,y] as the start of the word, then try all directions
                // radiating from that point outwards seeing if word is found
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (wordAt(input, x, y, dx, dy)) result++;
                    }
                }
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

                return (upperleft == 'M' && upperright == 'M' && lowerleft == 'S' && lowerright == 'S' ||
                    upperleft == 'M' && lowerleft == 'M' && upperright == 'S' && lowerright == 'S' ||
                    upperleft == 'S' && upperright == 'S' && lowerleft == 'M' && lowerright == 'M' ||
                    upperleft == 'S' && lowerleft == 'S' && upperright == 'M' && lowerright == 'M');
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
