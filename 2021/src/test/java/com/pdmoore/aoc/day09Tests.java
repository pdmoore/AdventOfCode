package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day09Tests {

    @Test
    void day09_part1_example() {
        List<String> input = PuzzleInput.asListOfStringsFrom("data/day09_example");

        int actual = sumRiskLevel(input);

        assertEquals(15, actual);
    }

    @Test
    void day09_part1_solution() {
        List<String> input = PuzzleInput.asListOfStringsFrom("data/day09");

        int actual = sumRiskLevel(input);

        assertEquals(516, actual);
    }

    @Test
    void day09_part2_example() {
        List<String> input = PuzzleInput.asListOfStringsFrom("data/day09_example");

        LavaTube sut = new LavaTube(input);
        int actual = sut.threeLargestBasinsMultiplied();

        assertEquals(1134, actual);
    }

    @Test
    void day09_part2_solution() {
        List<String> input = PuzzleInput.asListOfStringsFrom("data/day09");

        LavaTube sut = new LavaTube(input);
        int actual = sut.threeLargestBasinsMultiplied();

        assertEquals(1023660, actual);
    }

    private int sumRiskLevel(List<String> input) {
        int[][] locations = as2dIntArray(input);

        int riskLevel = identifyLowPointsAndScore(locations);

        return riskLevel;
    }

    private int identifyLowPointsAndScore(int[][] locations) {
        int riskLevel = 0;

        for (int x = 0; x < locations.length; x++) {
            for (int y = 0; y < locations[0].length; y++) {
                if (isLowPoint(locations, x, y)) {
                    riskLevel += locations[x][y] + 1;
                }
            }
        }

        return riskLevel;
    }

    private boolean isLowPoint(int[][] locations, int x, int y) {
        int checkValue = locations[x][y];

        // check Left
        if (y > 0 && locations[x][y - 1] <= checkValue) {
            return false;
        }
        // check Right
        if (y < locations[0].length - 1 && locations[x][y + 1] <= checkValue) {
            return false;
        }
        // check Below
        if (x > 0 && locations[x - 1][y] <= checkValue) {
            return false;
        }
        // check Above
        if (x < locations.length - 1 && locations[x + 1][y] <= checkValue) {
            return false;
        }

        return true;
    }

    //TODO - combine with caller to make a PuzzleInput method that just takes the file name
    private int[][] as2dIntArray(List<String> input) {
        int rowCount = input.size();
        int colCount = input.get(0).length();
        int[][] locations = new int[rowCount][colCount];
        for (int x = 0; x < input.size(); x++) {
            String thisLine = input.get(x);
            for (int y = 0; y < input.get(x).length(); y++) {
                char c = thisLine.charAt(y);
                locations[x][y] = Integer.parseInt(String.valueOf(c));
            }
        }
        return locations;
    }

    private class LavaTube {
        private final int[][] _locations;

        public LavaTube(List<String> input) {
            _locations = as2dIntArray(input);
        }

        public int threeLargestBasinsMultiplied() {
            int firstLargestBasin = 0;
            int secondLargestBasin = 0;
            int thirdLargestBasin = 0;

            for (int x = 0; x < _locations.length; x++) {
                for (int y = 0; y < _locations[0].length; y++) {
                    if (isLowPoint(_locations, x, y)) {

                        Point starting = new Point(x, y);
                        List<Point> visited = new ArrayList<>();
                        int size = calcBasinSize(x, y, 0, visited);

                        if (size >= firstLargestBasin) {
                            thirdLargestBasin = secondLargestBasin;
                            secondLargestBasin = firstLargestBasin;
                            firstLargestBasin = size;
                        } else if (size >= secondLargestBasin) {
                            thirdLargestBasin = secondLargestBasin;
                            secondLargestBasin = size;
                        } else if (size > thirdLargestBasin) {
                            thirdLargestBasin = size;
                        }
                    }
                }
            }

            return firstLargestBasin * secondLargestBasin * thirdLargestBasin;
        }

        private int calcBasinSize(int x, int y, int size, List<Point> visited) {
            Point here = new Point(x, y);
            if (visited.contains(here)) {
                return 0;
            }
            if (_locations[x][y] == 9) {
                return 0;
            }

            visited.add(here);
            size += 1;

            if (y > 0) {
                size += calcBasinSize(x, y-1, 0, visited);
            }
            if (y < _locations[0].length - 1) {
                size += calcBasinSize(x, y+1, 0, visited);
            }
            if (x > 0) {
                size += calcBasinSize(x - 1, y, 0, visited);
            }
            if (x < _locations.length - 1) {
                size += calcBasinSize(x + 1, y, 0, visited);
            }
            return size;
        }
    }
}