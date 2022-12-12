package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day09 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09_example");

        int actual = part1(input, 2);

        Assertions.assertEquals(13, actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09_example");

        int actual = part1(input, 10);

        Assertions.assertEquals(1, actual);
    }

    @Test
    void part2_example_2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09_example_part2");

        //TODO - left off here - the tail logic for the example isn't clear
        // see the part 2 description for the second move (U) where the head to tail
        // has the diagonal but then knots 5 and 4 are adjacent horizontally
        int actual = part1(input, 10);

        Assertions.assertEquals(36, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09");

        int actual = part1(input, 2);

        Assertions.assertEquals(6271, actual);
    }

    @Test
    @Disabled
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09");

        int actual = part2(input);

        Assertions.assertEquals(99, actual);
    }


    private int part2(List<String> input) {
        return 0;
    }

    private int part1(List<String> input, int numKnots) {

        Set<Point> tailVisits = new HashSet<>();

        List<Point> positions = new ArrayList<>();
        for (int i = 0; i < numKnots; i++) {
            positions.add(new Point(0, 0));
        }

        tailVisits.add(positions.get(numKnots - 1));

        for (String inputLine :
                input) {
            String[] split = inputLine.split(" ");
            String direction = split[0];
            int repeatMoveCount = Integer.parseInt(split[1]);

            for (int i = 0; i < repeatMoveCount; i++) {

                List<Point> nextPositions = new ArrayList<>();

                //always move head, knotNum 0, then iterate over the rest based on prev position
                // Move the head
                Point oldHeadPosition = positions.get(0);
                Point newHeadPosition = oldHeadPosition.move(direction);
                nextPositions.add(newHeadPosition);

                for (int knotNum = 1; knotNum < numKnots; knotNum++) {

                    Point prevKnot = positions.get(knotNum);

                    // rather than move the knot to the prev knot's exact position,
                    // drag it to within one of new position - this may be a position
                    // the head hasn't visited

                    if (!prevKnot.adjacentTo(nextPositions.get(knotNum-1))) {

                        Point next = prevKnot.move(direction);

                        nextPositions.add(next);
//                        nextPositions.add(positions.get(knotNum-1));


                        if (knotNum == numKnots - 1) {
                            tailVisits.add(oldHeadPosition);
                        }
                    } else {
                        nextPositions.add(prevKnot);
                    }
                }

                positions = nextPositions;
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
