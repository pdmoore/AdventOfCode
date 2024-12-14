package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        public static int _wide;
        public static int _tall;
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

        public void move() {
            int newX = location.x + velocity.x;
            int newY = location.y + velocity.y;

            // newX less than 0?
            // DONE newY less than 0?
            // newX > _wide
            // newY > _yall

            if (newX < 0) newX += _wide;
            if (newY < 0) newY += _tall;

            if (newX > _wide) newX -= _wide;
            if (newY > _tall) newY -= _tall;

            location = new Point(newX, newY);
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

    @Test
    void canMoveAndWarpRobot() {
        String input = "p=2,4 v=2,-3";
        Robot actual = parseInputLine(input);
        actual._wide = 11;
        actual._tall = 7;

        actual.move();
        assertEquals(new Point(4, 1), actual.location);

        actual.move();
        assertEquals(new Point(6, 5), actual.location);

        actual.move();
        assertEquals(new Point(8, 2), actual.location);

        actual.move();
        assertEquals(new Point(10, 6), actual.location);

        actual.move();
        assertEquals(new Point(1, 3), actual.location);
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
