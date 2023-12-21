package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21 {
    @Test
    void part1_example() {
        char[][] map = PuzzleInput.as2dCharArray("./data/day21_part1_example");

        assertEquals(2, solvePart1(map, 1).size());

//        Collection<Point> p = solvePart1(map, 6);
//        assertEquals(16, p.size());
    }

    private Set<Point> solvePart1(char[][] map, int targetStepCount) {
        Set<Point> visited = new HashSet<>();

        // find start
        Point start = locateStartingPoint(map);
        // add start to current
        List<Point> toProcess = new ArrayList<>();
        toProcess.add(start);


        int currentStep = 1;
        while (currentStep <= targetStepCount) {
            List<Point> nextProcess = new ArrayList<>();
            visited = new HashSet<>();
            for (Point thisP: toProcess) {
                List<Point> stepsFrom = validStepsFrom(map, thisP);
                nextProcess.addAll(stepsFrom);
                visited.addAll(stepsFrom);
            }
            toProcess = nextProcess;
            currentStep++;
        }


        return visited;
    }

    private List<Point> validStepsFrom(char[][] map, Point p) {
        List<Point> result = new ArrayList<>();

        if (p.x-1 >= 0 && map[p.x-1][p.y] == '.') {
            result.add(new Point(p.x-1, p.y));
        }

        if (p.x+1 < map.length && map[p.x+1][p.y] == '.') {
            result.add(new Point(p.x+1, p.y));
        }

        if (p.y-1 >= 0 && map[p.x][p.y-1] == '.') {
            result.add(new Point(p.x, p.y-1));
        }

        if (p.y+1 < map.length && map[p.x][p.y+1] == '.') {
            result.add(new Point(p.x, p.y+1));
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
