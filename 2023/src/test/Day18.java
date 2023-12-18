package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18 {

    @Test
    void dig_trench() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day18_part1_example");
        char[][] grid = digTrench(input, 17);
        int actual = countHoles(grid);

        assertEquals(38, actual);
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day18_part1_example");
        char[][] grid = digTrench(input, 20);
        digOutInterior(grid);
        int actual = countHoles(grid);
        assertEquals(62, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day18");
        char[][] grid = digTrench(input, 600);

        digOutInterior(grid);
//        dumpMap(grid);
        int actual = countHoles(grid);
        assertEquals(49061, actual);
    }

    private void digOutInterior(char[][] grid) {

        // Randomly grabbed a poiint in the middle, assuming it was INSIDE
        // works ok for bigger input, failing on example

        int start_x = grid.length / 2;
        if (grid.length < 100) {
            start_x = 14;
        }
        Point start = new Point(start_x, start_x);

        Stack<Point> q = new Stack<>();
        q.add(start);

        while (!q.isEmpty()) {
            Point p = q.pop();
            grid[p.x][p.y] = '#';

            if (grid[p.x][p.y - 1] == '.') q.push(new Point(p.x, p.y-1));
            if (grid[p.x][p.y + 1] == '.') q.push(new Point(p.x, p.y+1));
            if (grid[p.x-1][p.y] == '.') q.push(new Point(p.x-1, p.y));
            if (grid[p.x+1][p.y] == '.') q.push(new Point(p.x+1, p.y));

        }
    }

    private char[][] digTrench(List<String> input, int gridSize) {
        char[][] grid = new char[gridSize][gridSize];
        for (char[] row: grid) {
            Arrays.fill(row, '.');
        }
        Point curPos = new Point(gridSize / 2, gridSize / 2);
        for (String inputLine :
                input) {
            String[] split = inputLine.split(" ");
            String direction = split[0];
            int distance = Integer.parseInt(split[1]);
            //String rgb = split[2];

            switch (direction) {
                case "R" -> curPos = goRight(grid, curPos, distance);
                case "D" -> curPos = goDown(grid, curPos, distance);
                case "L" -> curPos = goLeft(grid, curPos, distance);
                case "U" -> curPos = goUp(grid, curPos, distance);
                default ->  throw new IllegalArgumentException("bad direction " + direction);
            }
        }

        return grid;
    }

    private Point goRight(char[][] grid, Point curPos, int distance) {
        int y = curPos.y;
        for (int i = 1; i <= distance; i++) {
            grid[curPos.x][y+i] = '#';
        }

        return new Point(curPos.x, y + distance);
    }

    private Point goLeft(char[][] grid, Point curPos, int distance) {
        int y = curPos.y;
        for (int i = 1; i <= distance; i++) {
            grid[curPos.x][y-i] = '#';
        }

        return new Point(curPos.x, y - distance);
    }

    private Point goDown(char[][] grid, Point curPos, int distance) {
        int x = curPos.x;
        for (int i = 1; i <= distance; i++) {
            grid[x+i][curPos.y] = '#';
        }

        return new Point(x + distance, curPos.y);
    }

    private Point goUp(char[][] grid, Point curPos, int distance) {
        int x = curPos.x;
        for (int i = 1; i <= distance; i++) {
            grid[x - i][curPos.y] = '#';
        }

        return new Point(x - distance, curPos.y);
    }

    private int countHoles(char[][] grid) {
        int count = 0;
        for (int row = 0; row < grid.length; row++) {
            count += new String(grid[row]).chars().filter(ch -> ch =='#').count();
        }

        return count;
    }




    private void dumpMap(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            System.out.println(map[row]);
        }
    }
}
