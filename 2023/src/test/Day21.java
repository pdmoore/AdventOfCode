package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21 {
    @Test
    void part1_example() {
        char[][] map = PuzzleInput.as2dCharArray("./data/day21_part1_example");

        assertEquals(2, solvePart1(map, 1).size());
        assertEquals(4, solvePart1(map, 2).size());

        Collection<Point> p = solvePart1(map, 6);
        assertEquals(16, p.size());
    }

    @Test
    void part1_solution() {
        char[][] map = PuzzleInput.as2dCharArray("./data/day21");

        assertEquals(3816, solvePart1(map, 64).size());
    }

    private Set<Point> solvePart1(char[][] map, int targetStepCount) {
        Set<Point> visited = new HashSet<>();

        // find start
        Point start = locateStartingPoint(map);
        map[start.x][start.y] = '.';
        // add start to current
        Set<Point> toProcess = new HashSet<>();
        toProcess.add(start);


        int currentStep = 1;
        while (currentStep <= targetStepCount) {
            Set<Point> nextProcess = new HashSet<>();
            visited = new HashSet<>();
            for (Point thisP: toProcess) {
                List<Point> stepsFrom = validStepsFrom(map, thisP);
                nextProcess.addAll(stepsFrom);
                visited.addAll(stepsFrom);
            }
            toProcess = nextProcess;
            currentStep++;
        }

        map[start.x][start.y] = 'S';

        return visited;
    }

    private List<Point> validStepsFrom(char[][] map, Point p) {
        List<Point> result = new ArrayList<>();

        if (p.x - 1 >= 0) {
            char up = map[p.x - 1][p.y];
            if (up == '.') {
                result.add(new Point(p.x - 1, p.y));
            }
        }

        if (p.x + 1 < map.length) {
            char down = map[p.x + 1][p.y];
            if (down == '.') {
                result.add(new Point(p.x + 1, p.y));
            }
        }

        if (p.y - 1 >= 0) {
            char left = map[p.x][p.y - 1];
            if (left == '.') {
                result.add(new Point(p.x, p.y - 1));
            }
        }

        if (p.y + 1 < map.length) {
            char right = map[p.x][p.y + 1];
            if (right == '.') {
                result.add(new Point(p.x, p.y + 1));
            }
        }


        return result;
    }

    private Point locateStartingPoint(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 'S') {
                    return new Point(row, col);
                }
            }
        }

        throw new IllegalArgumentException("Could not locate starting point in map");
    }

}
