package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test {

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

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
    enum Direction {up, right, down, left}

    private int solvePart1(char[][] map) {
        Set<Point> visited = new HashSet<>();

        Point current = findGuard(map);
        visited.add(current);

        Direction facing = Direction.up;

        boolean done = false;
        while (!done) {
            switch (facing) {
                case up:
                    if (current.x == 0) {
                        done = true;
                    } else {
                        if (map[current.x - 1][current.y] == '#') {
                            facing = Direction.right;
                        } else {
                            current = new Point(current.x - 1, current.y);
                            visited.add(current);
                        }
                    }
                    break;
                case right:
                    if (current.y == map.length - 1) {
                        done = true;
                    } else {
                        if (map[current.x][current.y + 1] == '#') {
                           facing = Direction.down;
                        } else {
                            current = new Point(current.x, current.y + 1);
                            visited.add(current);
                        }
                    }
                    break;
                case down:
                    if (current.x == map[0].length - 1) {
                        done = true;
                    } else {
                        if (map[current.x + 1][current.y] == '#') {
                            facing = Direction.left;
                        } else {
                            current = new Point(current.x + 1, current.y);
                            visited.add(current);
                        }
                    }
                    break;
                case left:
                    if (current.y == 0) {
                        done = true;
                    } else {
                        if (map[current.x][current.y - 1] == '#') {
                            facing = Direction.up;
                        } else {
                            current = new Point(current.x, current.y - 1);
                            visited.add(current);
                        }
                    }
                    break;
            }
        }

        return visited.size();
    }

    private Point findGuard(char[][] map) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y] == '^') {
                    return new Point(x, y);
                }
            }
        }

        throw new IllegalArgumentException("could not find ^");
    }
}
