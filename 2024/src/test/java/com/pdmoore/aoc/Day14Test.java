package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day14Test {
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

    static class Robot {
        Point location;
        Point velocity;

        public Robot(String input) {
            // p=0,4 v=3,-3
            String[] split = input.split(" ");
            String[] position = split[0].substring(2).split(",");
            location = new Point(Integer.parseInt(position[0]), Integer.parseInt(position[1]));

            String[] speed = split[1].substring(2).split(",");
            velocity = new Point(Integer.parseInt(speed[0]), Integer.parseInt(speed[1]));
        }
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example.txt");

        int actual = solvePart1(input);

        assertEquals(12, actual);
    }

    @Test
    void canParseRobotInput() {
        String input = "p=0,4 v=3,-3";

        Robot actual = parseInputLine(input);

        assertEquals(new Point(0, 4), actual.location);
        assertEquals(new Point(3, -3), actual.velocity);
    }

    private Robot parseInputLine(String input) {
        return new Robot(input);
    }

    private int solvePart1(List<String> input) {
        // parse single line for robot with position and velocity


        // List of Robots that have Point and Velocity

        // clock tick to move
        // wrap around the edges correctly

        // specify board width/height and then tally points in each quadrant




        return 0;
    }
}
