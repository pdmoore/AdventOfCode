package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11 {

    @Test
    void expand_space() {
        char[][] input = as2dCharArray("./data/day11_part1_example");
        char[][] expected = as2dCharArray("./data/day11_part1_example_expanded");

        char[][] actual = expand(input);
        dumpMap(actual);

        assertEquals(expected.length, actual.length);
        assertEquals(expected[0].length, actual[0].length);

    }

    private char[][] expand(char[][] input) {
        List<Integer> emptyRows = new ArrayList<>();
        for (int row = 0; row < input.length; row++) {
            boolean emptySpace = true;
            for (int col = 0; col < input[row].length; col++) {
                if (input[row][col] != '.') {
                    emptySpace = false;
                }
            }
            if (emptySpace) {
                emptyRows.add(row);
            }
        }

        List<Integer> emptyCols = new ArrayList<>();
        for (int col = 0; col < input[0].length; col++) {
            boolean emptySpace = true;
            for (int row = 0; row < input.length; row++) {
                if (input[row][col] != '.') {
                    emptySpace = false;
                }
            }
            if (emptySpace) {
                emptyCols.add(col);
            }
        }

        char[][] expanded = new char[input.length + emptyRows.size()][input[0].length + emptyCols.size()];

        int copyFromRow = 0;
        int copyToRow   = 0;
        while (copyToRow < input.length + emptyRows.size()) {
            if (emptyRows.contains(copyFromRow)) {
                for (int col = 0; col < input[0].length + emptyCols.size(); col++) {
                    expanded[copyToRow][col] = '.';
                }
                copyToRow++;
            }

            for (int col = 0; col < input[0].length; col++) {
                expanded[copyToRow][col] = input[copyFromRow][col];
            }
            for (int col = input[0].length; col < input[0].length + emptyCols.size(); col++) {
                expanded[copyToRow][col] = '.';
            }
            copyFromRow++;
            copyToRow++;
        }

        return expanded;
    }

    // TODO - move to PuzzleInput
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


    private void dumpMap(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            System.out.println(map[row]);
        }
    }

}
