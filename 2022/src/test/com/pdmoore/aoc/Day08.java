package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08 {

    @Test
    void part1_example() {
        int[][] input = PuzzleInput.as2dIntArray("data/day08_example");

        int actual = part1(input);

        assertEquals(21, actual);
    }

    @Test
    void part2_example() {
        int[][] input = PuzzleInput.as2dIntArray("data/day08_example");

        int actual = part2(input);

        assertEquals(8, actual);
    }

    @Test
    void part1_solution() {
        int[][] input = PuzzleInput.as2dIntArray("data/day08");

        int actual = part1(input);

        assertEquals(1801, actual);
    }

    @Test
    void part2_solution() {
        int[][] input = PuzzleInput.as2dIntArray("data/day08");

        int actual = part2(input);

        assertEquals(209880, actual);
    }

    private int part1(int[][] input) {
        int visibleCount = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {

                if (isVisible(input, i, j)) {
                    visibleCount++;
                }

            }
        }
        return visibleCount;
    }

    private boolean isVisible(int[][] input, int i, int j) {
        if ((i == 0) || (j == 0) || (i == input.length - 1) || (j == input[0].length - 1)) {
            return true;
        }

        int treeHeight = input[i][j];

        boolean canBeSeen = true;
        for (int left = j - 1; left >= 0 ; left--) {
            int leftTree = input[i][left];
            if (leftTree >= treeHeight) {
                canBeSeen = false;
                break;
            }
        }
        if (canBeSeen) return true;

        canBeSeen = true;
        for (int right = j + 1; right < input[0].length ; right++) {
            int rightTree = input[i][right];
            if (rightTree >= treeHeight) {
                canBeSeen = false;
                break;
            }
        }
        if (canBeSeen) return true;

        canBeSeen = true;
        for (int top = i - 1; top >= 0; top--) {
            int aboveTree = input[top][j];
            if (aboveTree >= treeHeight) {
                canBeSeen = false;
                break;
            }
        }
        if (canBeSeen) return true;

        canBeSeen = true;
        for (int bottom = i + 1; bottom < input.length; bottom++) {
            int belowTree = input[bottom][j];
            if (belowTree >= treeHeight) {
                canBeSeen = false;
                break;
            }
        }
        if (canBeSeen) return true;

        return false;
    }


    private int part2(int[][] input) {
        int bestScenicScore = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                int scenicScore = computeScenicScore(input, i, j);

                if (scenicScore > bestScenicScore) bestScenicScore = scenicScore;
            }
        }

        return bestScenicScore;
    }

    private int computeScenicScore(int[][] input, int i, int j) {
        if ((i == 0) || (j == 0) || (i == input.length - 1) || (j == input[0].length - 1)) {
            return 0;
        }

        int treeHeight = input[i][j];

        int upScore = 0;
        for (int top = i - 1; top >= 0; top--) {
            upScore++;
            int aboveTree = input[top][j];
            if (aboveTree >= treeHeight) {
                break;
            }
        }

        int leftScore = 0;
        for (int left = j - 1; left >= 0 ; left--) {
            leftScore++;
            int leftTree = input[i][left];
            if (leftTree >= treeHeight) {
                break;
            }
        }

        int rightScore = 0;
        for (int right = j + 1; right < input[0].length ; right++) {
            rightScore++;
            int rightTree = input[i][right];
            if (rightTree >= treeHeight) {
                break;
            }
        }

        int downScore = 0;
        for (int bottom = i + 1; bottom < input.length; bottom++) {
            downScore++;
            int belowTree = input[bottom][j];
            if (belowTree >= treeHeight) {
                break;
            }
        }

        return upScore * leftScore * rightScore * downScore;
    }

}
