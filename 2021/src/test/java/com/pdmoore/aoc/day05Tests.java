package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;
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

    private class Day05DataStructure {

        Map<Point, Integer> pointCount = new HashMap<Point, Integer>();


        public Day05DataStructure(List<String> input) {
            for (String inputLine :
                    input) {
                // if point is in map of <Point, int> add <Point, 0>
                String[] tokens = inputLine.split(" -> ");
                Point startAt = createPointFrom(tokens[0]);
                Point endAt   = createPointFrom(tokens[1]);

                if (startAt.y == endAt.y) {
                    if (startAt.x <= endAt.x) {
                        for (int i = startAt.x; i <= endAt.x; i++) {
                            Point thisPoint = new Point(i, startAt.y);
                            if (!pointCount.containsKey(thisPoint)) {
                                pointCount.put(thisPoint, 1);
                            } else {
                                int currentCount = pointCount.get(thisPoint);
                                pointCount.put(thisPoint, currentCount + 1);
                            }
                        }
                    } else {
                        for (int i = endAt.x; i <= startAt.x; i++) {
                            Point thisPoint = new Point(i, startAt.y);
                            if (!pointCount.containsKey(thisPoint)) {
                                pointCount.put(thisPoint, 1);
                            } else {
                                int currentCount = pointCount.get(thisPoint);
                                pointCount.put(thisPoint, currentCount + 1);
                            }
                        }
                    }
                } else if (startAt.x == endAt.x) {
                    if (startAt.y < endAt.y) {
                        for (int i = startAt.y; i <= endAt.y; i++) {
                            Point thisPoint = new Point(startAt.x, i);
                            if (!pointCount.containsKey(thisPoint)) {
                                pointCount.put(thisPoint, 1);
                            } else {
                                int currentCount = pointCount.get(thisPoint);
                                pointCount.put(thisPoint, currentCount + 1);
                            }
                        }
                    } else {
                        for (int i = endAt.y; i <= startAt.y; i++) {
                            Point thisPoint = new Point(startAt.x, i);
                            if (!pointCount.containsKey(thisPoint)) {
                                pointCount.put(thisPoint, 1);
                            } else {
                                int currentCount = pointCount.get(thisPoint);
                                pointCount.put(thisPoint, currentCount + 1);
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
