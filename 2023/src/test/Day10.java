package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10 {

    enum directions { up, right, down, left };
    directions _heading;

    @Test
    void find_starting_point() {
        char[][] map = as2dCharArray("./data/day10_part1_example1");
        Point actual = locateStartingPoint(map);
        assertEquals(new Point(1, 1), actual);

        map = as2dCharArray("./data/day10_part1_example2");
        actual = locateStartingPoint(map);
        assertEquals(new Point(2, 0), actual);
    }

    @Test
    void distance_clockwise_example1() {
        char[][] map = as2dCharArray("./data/day10_part1_example1");
        int actual = getClockwiseDistance(map);

        assertEquals(8, actual);
    }

    @Test
    void distance_clockwise_example2() {
        char[][] map = as2dCharArray("./data/day10_part1_example2");
        int actual = getClockwiseDistance(map);

        assertEquals(16, actual);
    }

    @Test
    void distance_clockwise_solution() {
        char[][] map = as2dCharArray("./data/day10");
        int actual = getClockwiseDistance(map);
        assertEquals(6909, actual / 2);
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
//        System.out.print(currentChar);

        if ('S' == currentChar) {
            if (map[currentLocation.x - 1][currentLocation.y] != '.') {
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



// TODO - move to PuzzleInput
    public static char[][] as2dCharArray(String filename) {
        List<String> inputAsStrings = PuzzleInput.asStringListFrom(filename);
        int rowCount = inputAsStrings.size();
        int colCount = inputAsStrings.get(0).length();
        char[][] map = new char[rowCount][colCount];
        int i = 0;
        for (String line :
                inputAsStrings) {
            map[i] = line.toCharArray();
            i++;
        }
        return map;
    }
}
