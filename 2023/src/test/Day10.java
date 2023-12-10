package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10 {

    @Test
    void find_starting_point() {
        char[][] map = as2dCharArray("./data/day10_part1_example1");
        Point actual = locateStartingPoint(map);
        assertEquals(new Point(1, 1), actual);

        map = as2dCharArray("./data/day10_part1_example2");
        actual = locateStartingPoint(map);
        assertEquals(new Point(0, 2), actual);


    }

    private Point locateStartingPoint(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 'S') {
                    return new Point(col, row);
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
