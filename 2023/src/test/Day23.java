package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23 {

    final char  PATH = '.';
    final char FOREST = '#';
    final String slopes = "^>v<";

    @Test
    void something() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day23_part1_example");

        int actual = findLongestHike(input);

        assertEquals(94, actual);
    }

    private int findLongestHike(char[][] input) {
        //94 steps. (The other possible hikes you could have taken were 90, 86, 82, 82, and 74

        Point start = new Point(0, findPoint(input[0], PATH));
        int lastRow = input.length - 1;
        Point end   = new Point(lastRow, findPoint(input[lastRow], PATH));


        return 0;
    }

    private int findPoint(char[] chars, char path) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == path) {
                return i;
            }
        }
        throw new IllegalArgumentException("couldn't find " + path + " in " + chars);
    }
}
