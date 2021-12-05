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

    private class Day05DataStructure {
        Map<Point, Integer> pointCount = new HashMap<Point, Integer>();

        public Day05DataStructure(List<String> input) {
            for (String inputLine :
                    input) {
                String[] tokens = inputLine.split(" -> ");
                Point startAt = createPointFrom(tokens[0]);
                Point endAt   = createPointFrom(tokens[1]);

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
