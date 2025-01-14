package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {

    public static final char EMPTY = '.';
    public static final char BOX = 'O';
    public static final char BOX_LEFT = '[';
    public static final char BOX_RIGHT = ']';

    public static Point UP    = new Point(-1, 0);
    public static Point DOWN  = new Point(1, 0);
    public static Point LEFT  = new Point(0, -1);
    public static Point RIGHT = new Point(0, 1);

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
        int colCount = inputAsStrings.getFirst().length();
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
    void part1() {
        List<List<String>> input = asListOfStringListFrom("data/day15.txt");
        int actual = solvePart1(input);
        assertEquals(1430439, actual);
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

    @Test
    void part2_example_computeGPSscore() {
        char[][] map = PuzzleInput.as2dCharArray("data/day15_example_scaled_final_positions.txt");
        int actual = sumBoxGPScoordinates(map);
        assertEquals(9021, actual);
    }

    @Test
    void part2_example() {
        List<List<String>> input = asListOfStringListFrom("data/day15_example.txt");

        int actual = solvePart2(input);

        assertEquals(9021, actual);
    }

    private int solvePart2(List<List<String>> input) {
        // split input into map and moves
        // scale map up into []
        // handle moves in a loop
        // < and > basically work the same, just watch for [] instead of O
        // but movement needs to shift characters and not swap the adjacent to the end
        // ^ and v need to recursively check up and down whether character above/below is [ or ]
        // then the move needs to recursively (from the end?) move things up and down
        // can use scaled_final_positions to compare against ongoing moves

        // calculate score, can call the same routine

        return 0;
    }


    private int solvePart1(List<List<String>> input) {
        char[][] map = as2dCharArray(input.get(0));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.get(1).size(); i++) {
            sb.append(input.get(1).get(i));
        }

        String robotMoves = sb.toString();
        Point robotPosition = findCharacter(map, '@');

        for (char move : robotMoves.toCharArray()) {
            robotPosition = switch (move) {
                case '^' -> attemptMove(map, robotPosition, UP);
                case '>' -> attemptMove(map, robotPosition, RIGHT);
                case 'v' -> attemptMove(map, robotPosition, DOWN);
                case '<' -> attemptMove(map, robotPosition, LEFT);
                default -> throw new RuntimeException("unexpected move character: " + move);
            };
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

    private Point attemptMove(char[][] map, Point robotPosition, Point delta) {
        Point nextRobotPosition = new Point(robotPosition.x, robotPosition.y);

        char moveTo = safeCharGrab(map, robotPosition.x + delta.x, robotPosition.y + delta.y);
        if (moveTo == EMPTY || canPushBoxes(map, robotPosition, delta)) {
            nextRobotPosition.x = robotPosition.x + delta.x;
            nextRobotPosition.y = robotPosition.y + delta.y;
            map[robotPosition.x][robotPosition.y] = EMPTY;
            map[nextRobotPosition.x][nextRobotPosition.y] = '@';
        }

        return nextRobotPosition;
    }

    private boolean canPushBoxes(char[][] map, Point robotPosition, Point delta) {
        int searchX = robotPosition.x + delta.x;
        int searchY = robotPosition.y + delta.y;
        while (safeCharGrab(map, searchX, searchY) == BOX) {
            searchX += delta.x;
            searchY += delta.y;
        }
        char endOfSearch = safeCharGrab(map, searchX, searchY);
        if (endOfSearch == EMPTY) {
            map[robotPosition.x + delta.x][robotPosition.y + delta.y] = EMPTY;
            map[searchX][searchY] = BOX;
            return true;
        }

        return false;
    }

    private int sumBoxGPScoordinates(char[][] map) {
        int result = 0;
        for (int fromTop = 0; fromTop < map.length; fromTop++) {
            for (int fromLeft = 0; fromLeft < map[fromTop].length; fromLeft++) {
                if (map[fromTop][fromLeft] == BOX || map[fromTop][fromLeft] == BOX_LEFT) {
                    int gps = 100 * fromTop + fromLeft;
                    result += gps;
                }
            }
        }

        return result;
    }
}