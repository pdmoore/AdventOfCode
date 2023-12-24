package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day23 {

    final char PATH = '.';
    final char FOREST = '#';
    final String slopes = "^>v<";

    boolean _slippery = false;
    int _maxHike = 0;

    @Test
    void part1_example() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day23_part1_example");

        _slippery = true;
        int actual = findLongestHike(input);

        assertEquals(94, actual);
    }

    @Test
    void part2_example() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day23_part1_example");

        _slippery = false;
        int actual = findLongestHike(input);

        assertEquals(154, actual);
    }

    @Test
    void part1_solution() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day23");

        _slippery = true;
        int actual = findLongestHike(input);

        assertEquals(2162, actual);
    }

    @Test
    void part2_solution() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day23");

        _slippery = false;
        int actual = findLongestHike(input);

        assertEquals(99, actual);
    }

    private int findLongestHike(char[][] map) {
        Point start = new Point(0, findPoint(map[0], PATH));
        int lastRow = map.length - 1;
        Point end = new Point(lastRow, findPoint(map[lastRow], PATH));

        List<Point> currentHike = new ArrayList<>();

        takeAHike(map, currentHike, start, end);

        return _maxHike - 1;
    }

    private void takeAHike(char[][] map, List<Point> currentHike, Point thisStep, Point end) {
        currentHike.add(thisStep);
        if (thisStep.equals(end)) {
            if (currentHike.size() > _maxHike) {
                _maxHike = currentHike.size();
            }
            return;
        }

        List<Point> stepsFromHere = new ArrayList<>();
        char tryLeft = map[thisStep.x][thisStep.y - 1];
        if (tryLeft == PATH || tryLeft == '<' || (!_slippery && tryLeft == '>')) {
            Point nextStep = new Point(thisStep.x, thisStep.y - 1);
            if (!currentHike.contains(nextStep)) {
                stepsFromHere.add(nextStep);
            }
        }
        if (thisStep.x > 0) {
            char tryUp = map[thisStep.x - 1][thisStep.y];
            if (tryUp == PATH || tryUp == '^' || (!_slippery && tryUp == 'v')) {
                Point nextStep = new Point(thisStep.x - 1, thisStep.y);
                if (!currentHike.contains(nextStep)) {
                    stepsFromHere.add(nextStep);
                }
            }
        }
        char tryRight = map[thisStep.x][thisStep.y + 1];
        if (tryRight == PATH || tryRight == '>' || (!_slippery && tryRight == '<')) {
            Point nextStep = new Point(thisStep.x, thisStep.y + 1);
            if (!currentHike.contains(nextStep)) {
                stepsFromHere.add(nextStep);
            }
        }
        char tryDown = map[thisStep.x + 1][thisStep.y];
        if (tryDown == PATH || tryDown == 'v' || (!_slippery && tryDown == '^')) {
            Point nextStep = new Point(thisStep.x + 1, thisStep.y);
            if (!currentHike.contains(nextStep)) {
                stepsFromHere.add(nextStep);
            }
        }

//        if (stepsFromHere.size() == 1) {
//            takeAHike(map, currentHike, stepsFromHere.get(0), end);
//        } else {
            for (int i = 0; i < stepsFromHere.size(); i++) {
                List<Point> splitHike = new ArrayList<>(currentHike);
                takeAHike(map, splitHike, stepsFromHere.get(i), end);
            }
//        }
    }

    private int findPoint(char[] chars, char path) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == path) {
                return i;
            }
        }
        throw new IllegalArgumentException("couldn't find " + path + " in " + chars);
    }
}
