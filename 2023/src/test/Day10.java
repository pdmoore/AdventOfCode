package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10 {

    @Test
    void find_starting_point() {
        /*
.....
.S-7.
.|.|.
.L-J.
.....
         */

        char[][] map = as2dCharArray("./data/day10_part1_example1");
        Point actual = locateStartingPoint(map);

        assertEquals(new Point(1, 1), actual);
    }

    private Point locateStartingPoint(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[x][y] == 'S') {
                    return new Point(x, y);
                }
            }
        }

        return null;
    }


    public static char[][] as2dCharArray(String filename) {
        List<String> inputAsStrings = PuzzleInput.asStringListFrom(filename);
        int rowCount = inputAsStrings.size();
        int colCount = inputAsStrings.get(0).length();
        char[][] map = new char[rowCount][colCount];
        int i = 0;
        for (String line :
                inputAsStrings) {
            map[i] = line.toCharArray();
            i++;
        }
        return map;
    }
}
