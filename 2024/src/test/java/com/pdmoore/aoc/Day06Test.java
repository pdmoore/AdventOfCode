package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @Disabled("Have not started to solve")
    void part2_example() {
        char[][] map = PuzzleInput.as2dCharArray("data/day06_example.txt");

        int actual = solvePart2(map);

        assertEquals(6, actual);
    }

    private int solvePart2(char[][] map) {

        // Thought:
        // Place a block '#' in front of the current position
        // and then try to see if the new map can be escaped or not
        // challenge is to figure out whether it gets stuck in a loop
        // run simulation for 10_000 rounds and if no escape, assume it's a loop
        // track points visited in order and look for repetition
        // if stuck in a loop, add the point to a list
        // remove the block '#', advance the guard one position, and try again
        // return the size of the list


        return 0;
    }

    private int solvePart1(char[][] map) {
        Set<DirectionalPoint> visited = new HashSet<>();

        DirectionalPoint current = findGuard(map);
        visited.add(current);

        Direction facing = Direction.UP;

        boolean done = false;
        while (!done) {
            switch (facing) {
                case UP:
                    if (current.x == 0) {
                        done = true;
                    } else {
                        if (map[current.x - 1][current.y] == '#') {
                            facing = Direction.RIGHT;
                        } else {
                            current = new DirectionalPoint(current.x - 1, current.y, Direction.UP);
                            visited.add(current);
                        }
                    }
                    break;
                case RIGHT:
                    if (current.y == map.length - 1) {
                        done = true;
                    } else {
                        if (map[current.x][current.y + 1] == '#') {
                            facing = Direction.DOWN;
                        } else {
                            current = new DirectionalPoint(current.x, current.y + 1, Direction.RIGHT);
                            visited.add(current);
                        }
                    }
                    break;
                case DOWN:
                    if (current.x == map[0].length - 1) {
                        done = true;
                    } else {
                        if (map[current.x + 1][current.y] == '#') {
                            facing = Direction.LEFT;
                        } else {
                            current = new DirectionalPoint(current.x + 1, current.y, Direction.DOWN);
                            visited.add(current);
                        }
                    }
                    break;
                case LEFT:
                    if (current.y == 0) {
                        done = true;
                    } else {
                        if (map[current.x][current.y - 1] == '#') {
                            facing = Direction.UP;
                        } else {
                            current = new DirectionalPoint(current.x, current.y - 1, Direction.LEFT);
                            visited.add(current);
                        }
                    }
                    break;
            }
        }

        // visited may have duplicate positions where the direction differs
        Set<Point> distinctPositions = new HashSet<>();
        for (DirectionalPoint dp: visited) {
            distinctPositions.add(new Point(dp.x, dp.y));
        }


        return distinctPositions.size();
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

    class DirectionalPoint extends Point {

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
