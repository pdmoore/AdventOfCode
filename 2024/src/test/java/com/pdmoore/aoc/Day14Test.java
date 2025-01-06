package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
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
            String[] split = input.split(" ");
            String[] position = split[0].substring(2).split(",");
            location = new Point(Integer.parseInt(position[0]), Integer.parseInt(position[1]));

            String[] speed = split[1].substring(2).split(",");
            velocity = new Point(Integer.parseInt(speed[0]), Integer.parseInt(speed[1]));
        }

        public void move() {
            int newX = location.x + velocity.x;
            int newY = location.y + velocity.y;

            if (newX < 0) newX += _wide;
            if (newY < 0) newY += _tall;
            if (newX >= _wide) newX -= _wide;
            if (newY >= _tall) newY -= _tall;

            if (newX < 0) throw new RuntimeException("x went negative");
            if (newY < 0) throw new RuntimeException("y went negative");

            location = new Point(newX, newY);
        }
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example.txt");
        Robot._wide = 11;
        Robot._tall = 7;

        int actual = solvePart1(input);

        assertEquals(12, actual);
    }

    @Test
    void part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14.txt");
        Robot r = new Robot("p=0,0 v=0,0");
        r._wide = 101;
        r._tall = 103;

        int actual = solvePart1(input);

        assertEquals(99, actual);
    }

    @Test
    void canParseRobotInput() {
        String input = "p=0,4 v=3,-3";

        Robot actual = parseInputLine(input);

        assertPointEquals(new Point(0, 4), actual.location);
        assertPointEquals(new Point(3, -3), actual.velocity);
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

    @Test
    void canMoveRobotsOffEdges() {
        String input = "p=0,0 v=0,8";
        Robot actual = parseInputLine(input);
        actual._wide = 11;
        actual._tall = 7;

        actual.move();
        assertPointEquals(new Point(0, 1), actual.location);

        actual = parseInputLine("p=0,0 v=12,0");
        actual.move();
        assertPointEquals(new Point(1, 0), actual.location);

        actual = parseInputLine("p=0,0 v=-1,0");
        actual.move();
        assertPointEquals(new Point(10, 0), actual.location);
    }


    private Robot parseInputLine(String input) {
        return new Robot(input);
    }

    private int solvePart1(List<String> input) {
        List<Robot> robots = new ArrayList<>();
        for (String line : input) {
            robots.add(new Robot(line));
        }

        for (int seconds = 1; seconds <= 100; seconds++) {
            for (Robot robot : robots) {
                robot.move();
            }
        }

        int q1Count = 0;
        int q2Count = 0;
        int q3Count = 0;
        int q4Count = 0;
        // q1 = 0,0 to 2,4
        // q2 = 0,6, to 2,10
        // q3 = 4,0 to 6,4
        // q4 = 4,6 to 6,10

        // TODO movement seems correct at edges
        // but after 100 seconds the counts by quadrant are wrong
        // currently seeing 3/1/3/1 for q1-4
        //throw new RuntimeException("Left off here, read comment");

        // TODO need to derive edges via the static values _wide and _tall

        int mid_x = Robot._wide / 2;
        int mid_y = Robot._tall / 2;

        for (Robot robot : robots) {
            int x = robot.location.x;
            int y = robot.location.y;
            if ((0 <= x && x <= mid_x - 1) && (0 <= y && y <= mid_y - 1)) q1Count++;
            if ((mid_x + 1 <= x && x <= Robot._wide - 1) && (0 <= y && y <= mid_y - 1)) q2Count++;
            if ((0 <= x && x <= mid_x - 1) && (mid_y + 1 <= y && y <= Robot._tall - 1)) q3Count++;
            if ((mid_x + 1 <= x && x <= Robot._wide - 1) && (mid_y + 1 <= y && y <= Robot._tall - 1)) q4Count++;
        }

        return q1Count * q2Count * q3Count * q4Count;
    }

    public void assertPointEquals(Point x, Point o) {
        assertAll(
                () -> assertEquals(x.x, o.x, "x value mismatch"),
                () -> assertEquals(x.y, o.y, "y value mismatch")
        );
    }
}
