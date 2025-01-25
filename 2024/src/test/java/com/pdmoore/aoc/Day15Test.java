package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
    // Maybe a TwoDeeMap class?
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
    void part2_smaller_example() {
        List<List<String>> input = asListOfStringListFrom("data/day15_part2_smaller_example.txt");

        int actual = solvePart2(input);

        fail("Example doesn't give score - visually check results then compute score");
    }

    @Test
    void part2_example() {
        List<List<String>> input = asListOfStringListFrom("data/day15_example.txt");

        int actual = solvePart2(input);

        assertEquals(9021, actual);
    }

    private int solvePart2(List<List<String>> input) {
        List<String> unscaledMapInput = input.get(0);
        List<String> scaledMapInput = scaleUpMapInput(unscaledMapInput);
        char[][] map = as2dCharArray(scaledMapInput);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.get(1).size(); i++) {
            sb.append(input.get(1).get(i));
        }
        String robotMoves = sb.toString();

        Point robotPosition = findCharacter(map, '@');

        for (char move : robotMoves.toCharArray()) {
            robotPosition = switch (move) {
                case '^' -> attemptMove2(map, robotPosition, UP);
                case '>' -> attemptMove2(map, robotPosition, RIGHT);
                case 'v' -> attemptMove2(map, robotPosition, DOWN);
                case '<' -> attemptMove2(map, robotPosition, LEFT);
                default -> throw new RuntimeException("unexpected move character: " + move);
            };
        }

        return sumBoxGPScoordinates(map);
    }

    private List<String> scaleUpMapInput(List<String> unscaledMapInput) {
        List<String> scaledMapInput = new ArrayList<>();

        for (String line : unscaledMapInput) {
            scaledMapInput.add(line
                    .replace("#", "##")
                    .replace("O", "[]")
                    .replace(".", "..")
                    .replace("@", "@."));
        }

        return scaledMapInput;
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

    private Point attemptMove2(char[][] map, Point robotPosition, Point delta) {
        Point nextRobotPosition = new Point(robotPosition.x, robotPosition.y);

        // TODO - left off here

        // < and > basically work the same, just watch for [] instead of O
        // but movement needs to shift characters and not swap the adjacent to the end
        // ^ and v need to recursively check up and down whether character above/below is [ or ]
        // then the move needs to recursively (from the end?) move things up and down
        // can use scaled_final_positions to compare against ongoing moves

        // Need a new canPushBoxes2 that handles < > in straightforward manner,
        // and the ^ or v using the rescursion idea


        char moveTo = safeCharGrab(map, robotPosition.x + delta.x, robotPosition.y + delta.y);
        if (moveTo == EMPTY  || canPushBoxes2(map, robotPosition, delta)) {
            nextRobotPosition.x = robotPosition.x + delta.x;
            nextRobotPosition.y = robotPosition.y + delta.y;
            map[robotPosition.x][robotPosition.y] = EMPTY;
            map[nextRobotPosition.x][nextRobotPosition.y] = '@';
        }

        return nextRobotPosition;
    }

    private boolean canPushBoxes2(char[][] map, Point robotPosition, Point delta) {
        // left and right won't have overlapping box edges, just shift everything if possible
        // TODO - have not verified the RIGHT case....
        if (delta == LEFT || delta == RIGHT) {
            int searchX = robotPosition.x + delta.x;
            int searchY = robotPosition.y + delta.y;
            while ("[]".contains(String.valueOf(safeCharGrab(map, searchX, searchY)))) {
                searchX += delta.x;
                searchY += delta.y;
            }
            char endOfSearch = safeCharGrab(map, searchX, searchY);
            if (endOfSearch == EMPTY) {
                int shiftX = searchX;
                int shiftY = searchY;
                while (shiftX != robotPosition.x + delta.x || shiftY != robotPosition.y + delta.y) {
                    map[shiftX][shiftY] = map[shiftX - delta.x][shiftY - delta.y];
                    shiftX -= delta.x;
                    shiftY -= delta.y;
                }
                map[shiftX][shiftY] = '.';

                return true;
            }
        }

        // TODO - WIP 2025-01-25
        if (delta == UP) {
            // check character above current position
            // when ']' need to check character above and left
            // when '[' need to check character above and right
            // need to recurse on both sides - if true for both sides then
            // shift this box left and right up and return true



        }

        return false;
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