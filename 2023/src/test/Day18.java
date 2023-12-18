package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18 {

    @Test
    void dig_trench() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day18_part1_example");
        char[][] grid = digTrench(input, 11);

        int actual = countHoles(grid);

        assertEquals(38, actual);
    }

    private char[][] digTrench(List<String> input, int gridSize) {
        char[][] grid = new char[gridSize][gridSize];
        Point curPos = new Point(0, 0);
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

            dumpMap(grid);

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
