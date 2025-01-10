package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {

    public static final char BOX = 'O';
    public static final char EMPTY = '.';

    // TODO - new candidate for PuzzleInput
    // When puzzle input consists of two groups of input
    // typically a map and instructions separated by a blank line
    private List<List<String>> asListOfStringListFrom(String filename) {
        List<List<String>> result = new ArrayList<>();

        List<String> inputAsStrings = PuzzleInput.asStringListFrom(filename);
        boolean firstHalf = true;
        List<String> topHalf = new ArrayList<>();
        List<String> bottomHalf = new ArrayList<>();
        for (String inputLine : inputAsStrings) {
            if (inputLine.isEmpty()) {
                firstHalf = false;
                continue;
            }
            if (firstHalf) {
                topHalf.add(inputLine);
            } else {
                bottomHalf.add(inputLine);
            }
        }

        result.add(topHalf);
        result.add(bottomHalf);
        return result;
    }

    // TODO add this to PuzzleInput also,
    private char[][] as2dCharArray(List<String> inputAsStrings) {
        int rowCount = inputAsStrings.size();
        int colCount = ((String) inputAsStrings.get(0)).length();
        char[][] map = new char[rowCount][colCount];
        int i = 0;

        for (String line : inputAsStrings) {
            map[i] = line.toCharArray();
            ++i;
        }

        return map;
    }

    //TODO - this is candidate for re-use, but not as puzzleInput
    private Point findCharacter(char[][] map, char target) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y] == target) {
                    return new Point(x, y);
                }
            }
        }

        throw new IllegalArgumentException("could not find " + target);
    }


    @Test
    void part1_smaller_example() {
        List<List<String>> input = asListOfStringListFrom("data/day15_smaller_example.txt");

        int actual = solvePart1(input);

        assertEquals(2028, actual);
    }

    @Test
    void part1_example() {
        List<List<String>> input = asListOfStringListFrom("data/day15_example.txt");

        int actual = solvePart1(input);

        assertEquals(10092, actual);
    }

    @Test
    void part1_smaller_example_computeGPSscore() {
        char[][] map = PuzzleInput.as2dCharArray("data/day15_smaller_example_final_positions.txt");
        int actual = sumBoxGPScoordinates(map);

        assertEquals(2028, actual);
    }

    @Test
    void part1_example_computeGPSscore() {
        char[][] map = PuzzleInput.as2dCharArray("data/day15_example_final_positions.txt");
        int actual = sumBoxGPScoordinates(map);

        assertEquals(10092, actual);
    }

    private int solvePart1(List<List<String>> input) {
        char[][] map = as2dCharArray(input.get(0));
        String robotMoves = input.get(1).get(0);
        Point robotPosition = findCharacter(map, '@');

        for (char move : robotMoves.toCharArray()) {
            switch (move) {
                case '^':
                    robotPosition = moveUp(map, robotPosition);
                    break;
                case '>':
                    robotPosition = moveRight(map, robotPosition);
                    break;
                case 'v':
                    robotPosition = moveDown(map, robotPosition);
                    break;
                case '<':
                    robotPosition = moveLeft(map, robotPosition);
                    break;
                default:
                    throw new RuntimeException("unexpected move chaarcter: " + move);
            }
        }

        return sumBoxGPScoordinates(map);
    }

    private char safeCharGrab(char[][] map, int row, int col) {
        char c = '#';
        try {
            c = map[row][col];
        } catch (IndexOutOfBoundsException x) {
            //ignore
        }
        return c;
    }

    private Point moveUp(char[][] map, Point robotPosition) {
        Point nextRobotPosition = new Point(robotPosition.x, robotPosition.y);

        char moveTo = safeCharGrab(map, robotPosition.x - 1, robotPosition.y);
        if (moveTo == EMPTY || canPushBoxesUp(map, robotPosition)) {
            nextRobotPosition.x = robotPosition.x - 1;
            map[robotPosition.x][robotPosition.y] = EMPTY;
            map[nextRobotPosition.x][nextRobotPosition.y] = '@';
        }

        return nextRobotPosition;
    }

    private Point moveDown(char[][] map, Point robotPosition) {
        Point nextRobotPosition = new Point(robotPosition.x, robotPosition.y);
        char moveTo = safeCharGrab(map, robotPosition.x + 1, robotPosition.y);
        if (moveTo == EMPTY || canPushBoxesDown(map, robotPosition)) {
            nextRobotPosition.x = robotPosition.x + 1;
            map[robotPosition.x][robotPosition.y] = EMPTY;
            map[nextRobotPosition.x][nextRobotPosition.y] = '@';
        }

        return nextRobotPosition;
    }

    private boolean canPushBoxesUp(char[][] map, Point robotPosition) {
        int searchUp = robotPosition.x - 1;
        while (safeCharGrab(map, searchUp, robotPosition.y) == BOX) {
            searchUp = searchUp - 1;
        }
        char endOfSearch = safeCharGrab(map, searchUp, robotPosition.y);
        if (endOfSearch == EMPTY) {
            map[robotPosition.x - 1][robotPosition.y] = EMPTY;
            map[searchUp][robotPosition.y] = BOX;
            return true;
        }

        return false;
    }

    private boolean canPushBoxesDown(char[][] map, Point robotPosition) {
        int searchDown = robotPosition.x + 1;
        while (safeCharGrab(map, searchDown, robotPosition.y) == BOX) {
            searchDown += 1;
        }
        char endOfSearch = safeCharGrab(map, searchDown, robotPosition.y);
        if (endOfSearch == EMPTY) {
            map[robotPosition.x + 1][robotPosition.y] = EMPTY;
            map[searchDown][robotPosition.y] = BOX;
            return true;
        }

        return false;
    }

    private Point moveRight(char[][] map, Point robotPosition) {
        Point nextRobotPosition = new Point(robotPosition.x, robotPosition.y);
        char moveTo = safeCharGrab(map, robotPosition.x, robotPosition.y + 1);
        if (moveTo == EMPTY || canPushBoxesRight(map, robotPosition)) {
            nextRobotPosition.y = robotPosition.y + 1;
            map[robotPosition.x][robotPosition.y] = EMPTY;
            map[nextRobotPosition.x][nextRobotPosition.y] = '@';
        }

        return nextRobotPosition;
    }

    private Point moveLeft(char[][] map, Point robotPosition) {
        Point nextRobotPosition = new Point(robotPosition.x, robotPosition.y);
        char moveTo = safeCharGrab(map, robotPosition.x, robotPosition.y - 1);
        if (moveTo == EMPTY || canPushBoxesLeft(map, robotPosition)) {
            nextRobotPosition.y = robotPosition.y - 1;
            map[robotPosition.x][robotPosition.y] = EMPTY;
            map[nextRobotPosition.x][nextRobotPosition.y] = '@';
        }

        return nextRobotPosition;
    }

    private boolean canPushBoxesRight(char[][] map, Point robotPosition) {
        int searchRight = robotPosition.y + 1;
        while (safeCharGrab(map, robotPosition.x, searchRight) == BOX) {
            searchRight += 1;
        }
        char endOfSearch = safeCharGrab(map, robotPosition.x, searchRight);
        if (endOfSearch == EMPTY) {
            map[robotPosition.x][robotPosition.y + 1] = EMPTY;
            map[robotPosition.x][searchRight] = BOX;
            return true;
        }

        return false;
    }

    private boolean canPushBoxesLeft(char[][] map, Point robotPosition) {
        int searchLeft = robotPosition.y - 1;
        while (safeCharGrab(map, robotPosition.x, searchLeft) == BOX) {
            searchLeft -= 1;
        }
        char endOfSearch = safeCharGrab(map, robotPosition.x, searchLeft);
        if (endOfSearch == EMPTY) {
            map[robotPosition.x][robotPosition.y + 1] = EMPTY;
            map[robotPosition.x][searchLeft] = BOX;
            return true;
        }

        return false;
    }

    private int sumBoxGPScoordinates(char[][] map) {
        int result = 0;
        for (int fromTop = 0; fromTop < map.length; fromTop++) {
            for (int fromLeft = 0; fromLeft < map[fromTop].length; fromLeft++) {
                if (map[fromTop][fromLeft] == BOX) {
                    int gps = 100 * fromTop + fromLeft;
                    result += gps;
                }
            }
        }

        return result;
    }
}