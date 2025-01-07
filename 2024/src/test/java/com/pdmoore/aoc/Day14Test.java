package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test {
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
        Robot._wide = 101;
        Robot._tall = 103;

        int actual = solvePart1(input);

        assertEquals(220971520, actual);
    }

    @Test
    void part2_assumeRobotsAreClusteredInOneQuadrant() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14.txt");
        Robot._wide = 101;
        Robot._tall = 103;

//        displayRobotsForPart2(input);
        int roundWithLargestSafeArea = findSafestQuadrant(input, 10000);

        assertEquals(6355, roundWithLargestSafeArea);
    }

    @Test
    @Disabled("Need to check whether points are linear in row with lots of robots")
    void part2_lookForHorizontalLineOfRobots() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14.txt");
        Robot._wide = 101;
        Robot._tall = 103;

        int roundWithLineOfRobots = findHorizontalLineOfRobots(input, 10000);

        assertEquals(6355, roundWithLineOfRobots);
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
        Robot._wide = 11;
        Robot._tall = 7;

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
        Robot._wide = 11;
        Robot._tall = 7;

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
        List<Robot> robots = input
                .stream()
                .map(Robot::new)
                .collect(Collectors.toList());

        for (int seconds = 1; seconds <= 100; seconds++) {
            robots.forEach(Robot::move);
        }

        return countRobotsByQuadrant(robots)
                .values()
                .stream()
                .mapToInt(quadrantCount -> quadrantCount).reduce(1, (a, b) -> a * b);
    }

    private void displayRobotsForPart2(List<String> input) {
        List<Robot> robots = input
                .stream()
                .map(Robot::new)
                .collect(Collectors.toList());

        for (int seconds = 1; seconds <= 10000; seconds++) {
            robots.forEach(Robot::move);

            Map<Integer, Integer> robotCountByRow = new HashMap<>();
            for (Robot robot : robots) {
                robotCountByRow.put(robot.location.x, robotCountByRow.getOrDefault(robot.location.x, 0) + 1);
            }

            if (seconds == 6355) {
                System.out.printf("Second: %d\n", seconds);
                displayRobotPositions(robots);
            }
        }
    }

    private void displayRobotPositions(List<Robot> robots) {
        char[][] map = new char[Robot._wide][Robot._tall];

        for (int x = 0; x < Robot._wide; x++) {
            for (int y = 0; y < Robot._tall; y++) {
                map[x][y] = '.';
            }
        }

        for (Robot robot : robots) {
            if (map[robot.location.x][robot.location.y] == '.') {
                map[robot.location.x][robot.location.y] = '1';
            } else {
                map[robot.location.x][robot.location.y] = (char) (map[robot.location.x][robot.location.y] + 1);
            }

        }

        for (int x = 0; x < Robot._tall; x++) {
            for (int y = 0; y < Robot._wide; y++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }

    private int findSafestQuadrant(List<String> input, int totalSeconds) {
        List<Robot> robots = input
                .stream()
                .map(Robot::new)
                .collect(Collectors.toList());

        int safetyCount = 0;
        int roundSafestQuadrantFound = 0;
        for (int seconds = 1; seconds <= totalSeconds; seconds++) {
            robots.forEach(Robot::move);

            Map<Integer, Integer> integerIntegerMap = countRobotsByQuadrant(robots);
            for (Integer quadrant : integerIntegerMap.keySet()) {
                if (integerIntegerMap.get(quadrant) > safetyCount) {
                    safetyCount = integerIntegerMap.get(quadrant);
                    roundSafestQuadrantFound = seconds;
                }
            }
        }

        return roundSafestQuadrantFound;
    }

    private int findHorizontalLineOfRobots(List<String> input, int totalSeconds) {
        List<Robot> robots = input
                .stream()
                .map(Robot::new)
                .toList();

        int roundSafestQuadrantFound = 0;
        for (int seconds = 1; seconds <= totalSeconds; seconds++) {
            robots.forEach(Robot::move);

            // need to iterate through robots, create Map of row x position
            // then look for a list > 10 and the positions are in a line
            Map<Integer, List<Point>> lookingForLines = new HashMap<>();
            for (Robot robot : robots) {
                int row = robot.location.y;
                if (lookingForLines.containsKey(row)) {
                    lookingForLines.get(row).add(robot.location);
                } else {
                    List<Point> points = new ArrayList<>();
                    points.add(robot.location);
                    lookingForLines.put(row, points);
                }
            }
            lookingForLines.forEach((row, points) -> {
                if (points.size() > 10) {
                    int breakpoint = 0;
                }
            });
        }

        return roundSafestQuadrantFound;
    }

    private static Map<Integer, Integer> countRobotsByQuadrant(List<Robot> robots) {
        int mid_x = Robot._wide / 2;
        int mid_y = Robot._tall / 2;

        Map<Integer, Integer> quadrantCount = new HashMap<>();
        for (Robot robot : robots) {
            int x = robot.location.x;
            int y = robot.location.y;
            if ((0 <= x && x <= mid_x - 1) && (0 <= y && y <= mid_y - 1)) {
                quadrantCount.put(1, quadrantCount.getOrDefault(1, 0) + 1);
            }
            if ((mid_x + 1 <= x && x <= Robot._wide - 1) && (0 <= y && y <= mid_y - 1)) {
                quadrantCount.put(2, quadrantCount.getOrDefault(2, 0) + 1);
            }
            if ((0 <= x && x <= mid_x - 1) && (mid_y + 1 <= y && y <= Robot._tall - 1)) {
                quadrantCount.put(3, quadrantCount.getOrDefault(3, 0) + 1);
            }
            if ((mid_x + 1 <= x && x <= Robot._wide - 1) && (mid_y + 1 <= y && y <= Robot._tall - 1)) {
                quadrantCount.put(4, quadrantCount.getOrDefault(4, 0) + 1);
            }
        }
        return quadrantCount;
    }

    public void assertPointEquals(Point x, Point o) {
        assertAll(
                () -> assertEquals(x.x, o.x, "x value mismatch"),
                () -> assertEquals(x.y, o.y, "y value mismatch")
        );
    }
}
