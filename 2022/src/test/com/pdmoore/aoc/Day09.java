package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day09 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09_example");

        int actual = part1(input);

        Assertions.assertEquals(13, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09");

        int actual = part1(input);

        Assertions.assertEquals(6271, actual);
    }

    private int part1(List<String> input) {
        Set<Point> tailVisits = new HashSet<>();

        Point headPosition = new Point(0, 0);
        Point tailPosition = new Point(0, 0);
        tailVisits.add(tailPosition);


        // for each input
        for (String inputLine :
                input) {
            String[] split = inputLine.split(" ");
            String direction = split[0];
            int count = Integer.parseInt(split[1]);

            for (int i = 0; i < count; i++) {
                Point oldHeadPosition = headPosition;
                headPosition = headPosition.move(direction);

                if (!tailPosition.adjacentTo(headPosition)) {
                    tailPosition = oldHeadPosition;
                    tailVisits.add(tailPosition);
                }
            }
        }

        return tailVisits.size();
    }

    private class Point {
        final int x;
        final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point move(String direction) {
            switch (direction) {
                case "R":  return new Point(x+1, y);
                case "L":  return new Point(x-1, y);
                case "U":  return new Point(x, y+1);
                case "D":  return new Point(x, y-1);
            }
            return null;
        }

        public boolean adjacentTo(Point other) {
            int deltaX = Math.abs(x - other.x);
            int deltaY = Math.abs(y - other.y);

            return ((deltaX <= 1) && (deltaY <= 1));
        }

        @Override
        public String toString() {
            return "[" + x + ", " + y + "]";
        }

        @Override
        public boolean equals(Object obj) {
            Point other = ((Point) obj);
            return x == other.x && y == other.y;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + x;
            result = 31 * result + y;
            return result;
        }
    }
}
