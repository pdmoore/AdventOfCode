package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03 {

    static public char[][] as2dCharArray(String filename) {
        List<String> input = PuzzleInput.asStringListFrom(filename);

        int rowCount = input.size();
        int colCount = input.get(0).length();
        char[][] locations = new char[rowCount][colCount];
        for (int x = 0; x < input.size(); x++) {
            String thisLine = input.get(x);
            for (int y = 0; y < input.get(x).length(); y++) {
                locations[x][y] = thisLine.charAt(y);
            }
        }
        return locations;
    }

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Test
    void something() {
        List<Integer> expected = Arrays.asList(467, 114, 35, 633, 617, 58, 592, 755, 664, 598);
        Collections.sort(expected);

        Map<Point, Integer> actual = getAllTheNumbers("./data/day03_part1_example");

        Collection<Integer> values = actual.values();
        List<Integer> xxx = new ArrayList<>();
        xxx.addAll(values);
        Collections.sort(xxx);

        assertEquals(expected, xxx);
    }

    private Map<Point, Integer> getAllTheNumbers(String filename) {
        char[][] input = as2dCharArray(filename);

        Map<Point, Integer> result = new HashMap<>();

        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < (input[x]).length; y++) {
                if (input[x][y] >= '0' && input [x][y] <= '9') {
                    Point startOfNumber = new Point(x, y);
                    String number = String.valueOf(input[x][y]);

                    // keep adding char until not a char or end of line
                    y++;
                    while (y < input[x].length &&
                           input[x][y] >= '0' && input[x][y] <= '9') {
                        number = number.concat(String.valueOf(input[x][y]));
                        y++;
                    }


                    result.put(startOfNumber, Integer.parseInt(number));
                }
            }
        }

        return result;

    }
}
