package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

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
}