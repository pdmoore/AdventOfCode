package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10 {

    enum directions { up, right, down, left }
    directions _heading;

    @Test
    void find_starting_point() {
        char[][] map = PuzzleInput.as2dCharArray("./data/day10_part1_example1");
        Point actual = locateStartingPoint(map);
        assertEquals(new Point(1, 1), actual);

        map = PuzzleInput.as2dCharArray("./data/day10_part1_example2");
        actual = locateStartingPoint(map);
        assertEquals(new Point(2, 0), actual);
    }

    @Test
    void distance_clockwise_example1() {
        char[][] map = PuzzleInput.as2dCharArray("./data/day10_part1_example1");
        int actual = getClockwiseDistance(map);

        assertEquals(8, actual);
    }

    @Test
    void distance_clockwise_example2() {
        char[][] map = PuzzleInput.as2dCharArray("./data/day10_part1_example2");
        int actual = getClockwiseDistance(map);

        assertEquals(16, actual);
    }

    @Test
    void distance_clockwise_solution() {
        char[][] map = PuzzleInput.as2dCharArray("./data/day10");
        int actual = getClockwiseDistance(map);
        assertEquals(6909, actual / 2);
    }

    @Test
    void part2_example1() {
        char[][] map = PuzzleInput.as2dCharArray("./data/day10_part2_example1");
        getClockwiseDistance(map);

        int actual = countEnclosedSpaces(map);
        assertEquals(4, actual);
    }

    @Test
    void part2_eaxmple2() {
        char[][] map = PuzzleInput.as2dCharArray("./data/day10_part2_example2");
        getClockwiseDistance(map);
        dumpMap(map);

        int actual = countEnclosedSpaces(map);
        assertEquals(8, actual);
    }

    @Test
    void part2_solution() {
        char[][] map = PuzzleInput.as2dCharArray("./data/day10");
        getClockwiseDistance(map);

        int actual = countEnclosedSpaces(map);

        assertEquals(-99, actual);
    }

    private int countEnclosedSpaces(char[][] map) {
        int result = 0;
        for (int row = 0; row < map.length; row++) {
            boolean inside = false;
            for (int col = 1; col < map[0].length; col++) {
                if (map[row][col] == '*' && !inside && map[row][col - 1] != '*') {
                    inside = true;
                } else if (map[row][col] == '.' && inside) {
                    result++;
                } else {
                    inside = false;
                }
            }
        }

        return  result;
    }

    private int getClockwiseDistance(char[][] map) {
        Point startingPoint = locateStartingPoint(map);

        Point currentLocation = moveFrom(map, startingPoint);
        int distance = 1;
        while (!currentLocation.equals(startingPoint)) {
            distance++;
            currentLocation = moveFrom(map, currentLocation);
        }

        return distance;
    }

    private Point moveFrom(char[][] map, Point currentLocation) {
        // we can read the character at the current location, except S
        char currentChar = map[currentLocation.x][currentLocation.y];
        map[currentLocation.x][currentLocation.y] = '*';

        if ('S' == currentChar) {
            char charAbove = map[currentLocation.x - 1][currentLocation.y];
            if (charAbove == '7' || charAbove == 'F' || charAbove == '|') {
                _heading = directions.up;
                return new Point(currentLocation.x - 1, currentLocation.y);
            }
            if (map[currentLocation.x][currentLocation.y + 1] != '.') {
                _heading = directions.right;
                return new Point(currentLocation.x, currentLocation.y + 1);
            }
        }

        if ('-' == currentChar) {
            switch (_heading) {
                case right -> { return new Point(currentLocation.x, currentLocation.y + 1); }
                case left  -> { return new Point(currentLocation.x, currentLocation.y - 1); }
            }
        }

        if ('7' == currentChar) {
            switch (_heading) {
                case right -> { _heading = directions.down; return new Point(currentLocation.x + 1, currentLocation.y); }
                case up  ->   { _heading = directions.left; return new Point(currentLocation.x, currentLocation.y - 1); }
            }
        }

        if ('|' == currentChar) {
            switch (_heading) {
                case down -> { return new Point(currentLocation.x + 1, currentLocation.y); }
                case up  -> { return new Point(currentLocation.x - 1, currentLocation.y); }
            }
        }

        if ('J' == currentChar) {
            switch (_heading) {
                case down -> { _heading = directions.left; return new Point(currentLocation.x, currentLocation.y - 1); }
                case right  -> { _heading = directions.up; return new Point(currentLocation.x - 1, currentLocation.y);}
            }

        }

        if ('L' == currentChar) {
            switch (_heading) {
                case left -> { _heading = directions.up;    return new Point(currentLocation.x - 1, currentLocation.y); }
                case down -> { _heading = directions.right; return new Point(currentLocation.x, currentLocation.y + 1);  }
            }
        }

        if ('F' == currentChar) {
            switch (_heading) {
                case up ->   { _heading = directions.right; return new Point(currentLocation.x, currentLocation.y + 1); }
                case left -> { _heading = directions.down;  return new Point(currentLocation.x + 1, currentLocation.y); }
            }
        }

        return null;
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



    private void dumpMap(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            System.out.println(map[row]);
        }
    }

}
