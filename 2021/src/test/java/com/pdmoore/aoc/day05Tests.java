package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day05Tests {

    @Test
    void day05_part1_example() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day05_example");

        Day05DataStructure dataStructure = new Day05DataStructure(input);

        int actual = dataStructure.overlapsOfTwoOrMore();

        assertEquals(5, actual);
    }

    @Test
    void day05_part1_solution() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day05");

        Day05DataStructure dataStructure = new Day05DataStructure(input);

        int actual = dataStructure.overlapsOfTwoOrMore();

        assertEquals(4873, actual);
    }

    @Test
    void day05_part2_example() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day05_example");
        Day05DataStructure dataStructure = new Day05DataStructure(input, true);

        int actual = dataStructure.overlapsOfTwoOrMore();

        assertEquals(12, actual);
    }

    @Test
    void day05_part2_solution() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day05");

        Day05DataStructure dataStructure = new Day05DataStructure(input, true);

        int actual = dataStructure.overlapsOfTwoOrMore();

        assertEquals(19472, actual);
    }



    private class Day05DataStructure {
        Map<Point, Integer> pointCount = new HashMap<Point, Integer>();
        boolean handleDiagonals = false;

        public Day05DataStructure(List<String> input) {
            processInput(input);
        }

        public Day05DataStructure(List<String> input, boolean ignored) {
            this.handleDiagonals = true;
            processInput(input);
        }

        private void processInput(List<String> input) {
            for (String inputLine :
                    input) {
                String[] tokens = inputLine.split(" -> ");
                Point startAt = createPointFrom(tokens[0]);
                Point endAt = createPointFrom(tokens[1]);

                if (startAt.y == endAt.y) {
                    int lower = Math.min(startAt.x, endAt.x);
                    int upper = Math.max(startAt.x, endAt.x);

                    for (int i = lower; i <= upper; i++) {
                        Point thisPoint = new Point(i, startAt.y);
                        if (!pointCount.containsKey(thisPoint)) {
                            pointCount.put(thisPoint, 1);
                        } else {
                            int currentCount = pointCount.get(thisPoint);
                            pointCount.put(thisPoint, currentCount + 1);
                        }
                    }
                } else if (startAt.x == endAt.x) {
                    int lower = Math.min(startAt.y, endAt.y);
                    int upper = Math.max(startAt.y, endAt.y);

                    for (int i = lower; i <= upper; i++) {
                        Point thisPoint = new Point(startAt.x, i);
                        if (!pointCount.containsKey(thisPoint)) {
                            pointCount.put(thisPoint, 1);
                        } else {
                            int currentCount = pointCount.get(thisPoint);
                            pointCount.put(thisPoint, currentCount + 1);
                        }
                    }
                } else if (handleDiagonals) {
                    System.out.println("Diagonal from " + startAt + " to " + endAt);

                    if (startAt.x < endAt.x) {

                        if (startAt.y < endAt.y) {
                            // 498, 588 -- 503, 593
                            System.out.println("1a Diagonal");
                            int currentY = startAt.y;

                            for (int i = startAt.x; i <= endAt.x; i++) {
                                Point thisPoint = new Point(i, currentY);
                                if (!pointCount.containsKey(thisPoint)) {
                                    pointCount.put(thisPoint, 1);
                                } else {
                                    int currentCount = pointCount.get(thisPoint);
                                    pointCount.put(thisPoint, currentCount + 1);
                                }
                                currentY++;
                            }
                        } else {
                            // 627, 659 -- 763, 523
                            System.out.println("Diagonal 1b");
                            int currentY = startAt.y;
                            for (int i = startAt.x; i <= endAt.x; i++) {
                                Point thisPoint = new Point(i, currentY);
                                if (!pointCount.containsKey(thisPoint)) {
                                    pointCount.put(thisPoint, 1);
                                } else {
                                    int currentCount = pointCount.get(thisPoint);
                                    pointCount.put(thisPoint, currentCount + 1);
                                }
                                currentY--;
                            }
                        }
                    } else {

                        // guaranteed that start.x > end.x
                        if (startAt.y < endAt.y) {
                            // 861, 137 -- 102, 896
                            System.out.println("Diagonal 2a");
                            int currentY = startAt.y;
                            for (int i = startAt.x; i >= endAt.x; i--) {
                                Point thisPoint = new Point(i, currentY);
                                if (!pointCount.containsKey(thisPoint)) {
                                    pointCount.put(thisPoint, 1);
                                } else {
                                    int currentCount = pointCount.get(thisPoint);
                                    pointCount.put(thisPoint, currentCount + 1);
                                }
                                currentY++;
                            }
                        } else {
                            //629, 581   to 123, 75
                            System.out.println("Diagonal 2b");

                            int currentY = startAt.y;

                            for (int i = startAt.x; i >= endAt.x; i--) {
                                Point thisPoint = new Point(i, currentY);
                                if (!pointCount.containsKey(thisPoint)) {
                                    pointCount.put(thisPoint, 1);
                                } else {
                                    int currentCount = pointCount.get(thisPoint);
                                    pointCount.put(thisPoint, currentCount + 1);
                                }
                                currentY--;
                            }
                        }

                    }

                }
            }
        }

        private Point createPointFrom(String pointAsString) {
            String[] xy = pointAsString.split(",");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            return new Point(x, y);
        }

        public int overlapsOfTwoOrMore() {
            int overlapsCount = 0;
            for (Point p :
                    pointCount.keySet()) {
                if (pointCount.get(p) >= 2) {
                    overlapsCount++;
                }
            }

            return overlapsCount;
        }
    }
}
