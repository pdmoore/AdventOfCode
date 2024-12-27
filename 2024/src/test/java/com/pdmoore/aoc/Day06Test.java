package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day06Test {

    @Test
    void part1_example() {
        char[][] map = PuzzleInput.as2dCharArray("data/day06_example.txt");

        int actual = solvePart1(map);

        assertEquals(41, actual);
    }

    @Test
    void part1() {
        char[][] map = PuzzleInput.as2dCharArray("data/day06.txt");

        int actual = solvePart1(map);

        assertEquals(5453, actual);
    }

    // The example input includes the actual guard paths but this doesn't affect
    // the detection algo as it only looks for '#' obstructions
    @Test
    void part2_detect_a_loop_exists() {
        char[][] map = PuzzleInput.as2dCharArray("data/day06_example_loop1.txt");
        DirectionalPoint guardPosition = findGuard(map);

        boolean actual = isThereALoop(map, guardPosition);
        assertTrue(actual, "expect there to be a loop in the loop1 example map");

        map = PuzzleInput.as2dCharArray("data/day06_example_loop2.txt");
        actual = isThereALoop(map, guardPosition);
        assertTrue(actual, "expect there to be a loop in the loop1 example map");

        map = PuzzleInput.as2dCharArray("data/day06_example_loop6.txt");
        actual = isThereALoop(map, guardPosition);
        assertTrue(actual, "expect there to be a loop in the loop1 example map");
    }

    @Test
    void part2_detect_no_loop_exists() {
        char[][] map = PuzzleInput.as2dCharArray("data/day06_example.txt");
        DirectionalPoint guardPosition = findGuard(map);

        boolean actual = isThereALoop(map, guardPosition);
        assertFalse(actual, "initial example has not loop");
    }

    @Test
    void part2_example() {
        char[][] map = PuzzleInput.as2dCharArray("data/day06_example.txt");
        DirectionalPoint guardPosition = findGuard(map);

        int actual = solvePart2(map, guardPosition);

        assertEquals(6, actual);
    }

    @Test
    void part2() {
        char[][] map = PuzzleInput.as2dCharArray("data/day06.txt");
        DirectionalPoint guardPosition = findGuard(map);

        int actual = solvePart2(map, guardPosition);

        assertEquals(2188, actual);
    }

    // collect visited positions
    // if the same position, with direction, is found, there is a loop
    // if guard leaves mapped area there is no loop
    private boolean isThereALoop(char[][] map, DirectionalPoint guardPosition) {
        Set<DirectionalPoint> visited = new HashSet<>();
        visited.add(guardPosition);

        while (true) {
            switch (guardPosition.direction) {
                case UP:
                    if (guardPosition.x == 0) {
                        return false;
                    } else {
                        if (map[guardPosition.x - 1][guardPosition.y] == '#') {
                            guardPosition = new DirectionalPoint(guardPosition.x, guardPosition.y, Direction.RIGHT);
                        } else {
                            guardPosition = new DirectionalPoint(guardPosition.x - 1, guardPosition.y, Direction.UP);
                        }
                    }
                    break;
                case RIGHT:
                    if (guardPosition.y == map.length - 1) {
                        return false;
                    } else {
                        if (map[guardPosition.x][guardPosition.y + 1] == '#') {
                            guardPosition = new DirectionalPoint(guardPosition.x, guardPosition.y, Direction.DOWN);
                        } else {
                            guardPosition = new DirectionalPoint(guardPosition.x, guardPosition.y + 1, Direction.RIGHT);
                        }
                    }
                    break;
                case DOWN:
                    if (guardPosition.x == map[0].length - 1) {
                        return false;
                    } else {
                        if (map[guardPosition.x + 1][guardPosition.y] == '#') {
                            guardPosition = new DirectionalPoint(guardPosition.x, guardPosition.y, Direction.LEFT);
                        } else {
                            guardPosition = new DirectionalPoint(guardPosition.x + 1, guardPosition.y, Direction.DOWN);
                        }
                    }
                    break;
                case LEFT:
                    if (guardPosition.y == 0) {
                        return false;
                    } else {
                        if (map[guardPosition.x][guardPosition.y - 1] == '#') {
                            guardPosition = new DirectionalPoint(guardPosition.x, guardPosition.y, Direction.UP);
                        } else {
                            guardPosition = new DirectionalPoint(guardPosition.x, guardPosition.y - 1, Direction.LEFT);
                        }
                    }
                    break;
            }
            if (visited.contains(guardPosition)) {
                return true;
            }
            visited.add(guardPosition);
        }
    }

    private int solvePart2(char[][] map, DirectionalPoint guardPosition) {

        // Brute Force, for every row/col, add a '#' if there isn't one and try all combos....
        Set<Point> obstructionsCausingLoop = new HashSet<>();

        for (int i = 0; i < map.length; i++) {
            System.out.println("processing row " + i);
            for (int j = 0; j < map[i].length; j++) {

                if (map[i][j] != '#') {
                    char originalChar = map[i][j];
                    map[i][j] = '#';

                    if (isThereALoop(map, guardPosition)) {
                        obstructionsCausingLoop.add(new Point(i, j));
                    }

                    map[i][j] = originalChar;
                }
            }
        }

        // Thought:
        // Place a block '#' in front of the current position
        // and then try to see if the new map has a loop or not



        // track points visited in order and look for repetition
        // if stuck in a loop, add the point to a list
        // remove the block '#', advance the guard one position, and try again
        // return the size of the list

        // given the guard position and direction
        // if guard is at edge and moving off board, don't bother
        // copy the map and place a '#' in the up/down/left/right of guardPosition to block it
        // call isThereALoop
        // if returns true, collect the position of the '#' as a point in a set
        // there can be dupes of the blocking points since they can be approached from 4 sides


        return obstructionsCausingLoop.size();
    }

    private int solvePart1(char[][] map) {
        Set<DirectionalPoint> visited = new HashSet<>();

        DirectionalPoint current = findGuard(map);
        visited.add(current);

        boolean done = false;
        while (!done) {
            switch (current.direction) {
                case UP:
                    if (current.x == 0) {
                        done = true;
                    } else {
                        if (map[current.x - 1][current.y] == '#') {
                            current = new DirectionalPoint(current.x, current.y, Direction.RIGHT);
                        } else {
                            current = new DirectionalPoint(current.x - 1, current.y, Direction.UP);
                        }
                    }
                    break;
                case RIGHT:
                    if (current.y == map.length - 1) {
                        done = true;
                    } else {
                        if (map[current.x][current.y + 1] == '#') {
                            current = new DirectionalPoint(current.x, current.y, Direction.DOWN);
                        } else {
                            current = new DirectionalPoint(current.x, current.y + 1, Direction.RIGHT);
                        }
                    }
                    break;
                case DOWN:
                    if (current.x == map[0].length - 1) {
                        done = true;
                    } else {
                        if (map[current.x + 1][current.y] == '#') {
                            current = new DirectionalPoint(current.x, current.y, Direction.LEFT);
                        } else {
                            current = new DirectionalPoint(current.x + 1, current.y, Direction.DOWN);
                        }
                    }
                    break;
                case LEFT:
                    if (current.y == 0) {
                        done = true;
                    } else {
                        if (map[current.x][current.y - 1] == '#') {
                            current = new DirectionalPoint(current.x, current.y, Direction.UP);
                        } else {
                            current = new DirectionalPoint(current.x, current.y - 1, Direction.LEFT);
                        }
                    }
                    break;
            }
            visited.add(current);
        }

        // visited may have duplicate positions where the direction differs,
        // only return the count of unique positions visited
        return visited
                .stream()
                .map(dp -> new Point(dp.x, dp.y))
                .collect(Collectors.toSet())
                .size();
    }

    private DirectionalPoint findGuard(char[][] map) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y] == '^') {
                    return new DirectionalPoint(x, y, Direction.UP);
                }
            }
        }

        throw new IllegalArgumentException("could not find ^");
    }

    enum Direction {UP, RIGHT, DOWN, LEFT}

    static class DirectionalPoint extends Point {

        private final Direction direction;

        public DirectionalPoint(int x, int y, Direction direction) {
            super(x, y);
            this.direction = direction;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            DirectionalPoint that = (DirectionalPoint) o;
            return direction == that.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), direction);
        }
    }
}
