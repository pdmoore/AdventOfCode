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

        assertEquals(99, actual);
    }

    @Test
    void part2_example() {
        char[][] input = PuzzleInput.as2dCharArray("data/day04_myexample.txt");

        int actual = find_X_MAS(input);

        assertEquals(9, actual);
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
                if (wordAt(input, i, j, 0,-1)) result++; //w
                if (wordAt(input, i, j, -1,-1)) result++; //nw
                if (wordAt(input, i, j, -1,0)) result++; //n
                if (wordAt(input, i, j, -1,1)) result++; //ne
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
//
//    private String getWord(char[][] input, int x, int y, int dx, int dy) {
//        // should pass in XMAS length
//        String word = "";
//
//        for (int l = 0; l < 4; l++) {
//            try {
//                word += input[x + (dx * l)][y + (dy * l)];
//            } catch (ArrayIndexOutOfBoundsException e) {
//                // just ignore it
//            }
//        }
//
//        return word;
//    }
}
