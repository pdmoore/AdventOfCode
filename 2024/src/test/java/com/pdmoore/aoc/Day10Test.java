package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {

    static Collection<Point> peaksReached = new HashSet<>();
    static int rating = 0;

    @Test
    void part1_example() {
        int[][] input = PuzzleInput.as2dIntArray("data/day10_example.txt");

        int actual = solvePart1(input);

        assertEquals(2, actual);
    }

    @Test
    void part1_multiplePathsFromOneZero() {
        int[][] input = PuzzleInput.as2dIntArray("data/day10_exampleScore4.txt");
        int actual = solvePart1(input);
        assertEquals(4, actual);
    }

    @Test
    void part1_largerExample() {
        int[][] input = PuzzleInput.as2dIntArray("data/day10_largerExample.txt");
        int actual = solvePart1(input);
        assertEquals(36, actual);
    }

    @Test
    void part1() {
        int[][] input = PuzzleInput.as2dIntArray("data/day10.txt");
        int actual = solvePart1(input);
        assertEquals(709, actual);
    }

    @Test
    void part2_largerExample() {
        int[][] input = PuzzleInput.as2dIntArray("data/day10_largerExample.txt");
        solvePart1(input);
        assertEquals(81, rating);
    }

    @Test
    void part2() {
        int[][] input = PuzzleInput.as2dIntArray("data/day10.txt");
        solvePart1(input);
        assertEquals(1326, rating);
    }

    private int solvePart1(int[][] input) {
        List<Point> zeros = locateZeros(input);

        rating = 0;
        int result = 0;
        for (Point zero : zeros) {
            peaksReached = new HashSet<>();
            move(input, zero);
            result += peaksReached.size();
        }

        return result;
    }

    private void move(int[][] input, Point p) {
        if (input[p.x][p.y] == 9) {
            peaksReached.add(p);
            rating++;
        }

        // from current point
        int targetHeight = input[p.x][p.y] + 1;

        // up
        try {
            if (input[p.x - 1][p.y] == targetHeight) {
                move(input, new Point(p.x - 1, p.y));
            }
        } catch (IndexOutOfBoundsException e) {
            // just ignore when we go outside the map
        }

        // right
        try {
            if (input[p.x][p.y + 1] == targetHeight) {
                move(input, new Point(p.x, p.y + 1));
            }
        } catch ( IndexOutOfBoundsException e) {
            // just ignore when we go outside the map
        }

        // down
        try {
            if (input[p.x + 1][p.y] == targetHeight) {
                move(input, new Point(p.x + 1, p.y));
            }
        } catch ( IndexOutOfBoundsException e) {
            // just ignore when we go outside the map
        }

        // left
        try {
            if (input[p.x][p.y - 1] == targetHeight) {
                move(input, new Point(p.x, p.y - 1));
            }
        } catch ( IndexOutOfBoundsException e) {
            // just ignore when we go outside the map
        }
    }

    private List<Point> locateZeros(int[][] map) {
        List<Point> zeros = new ArrayList<>();

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y] == 0) {
                    zeros.add(new Point(x, y));
                }
            }
        }

        return zeros;
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
