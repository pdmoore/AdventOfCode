package com.pdmoore.aoc;

import java.awt.*;
import java.util.*;
import java.util.List;

public class day6 {
    public static int solution1(List<String> input) {
        Board board = parseInput(input);

        board.fillClosestCoordinates();

//        board.printGrid();

        return board.countOfLargestArea();
    }

    public static int solution2(List<String> input, int maxDistance) {
        // TODO - might need to expand left/right/top/bottom edges
        Board board = parseInput(input);

        board.fillSafestRegion(maxDistance);

//        board.printGrid();

        return board.countOfRegion();
    }

    private static Board parseInput(List<String> input) {
        List<Point> points = new ArrayList<>();

        for (String inputLine :
                input) {
            String[] tokens = inputLine.split(",");
            int row = Integer.parseInt(tokens[0].trim());
            int col = Integer.parseInt(tokens[1].trim());

            points.add(new Point(col, row));
        }

        return new Board(points);
    }

    public static int manhattanDistanceBetween(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    private static class Board {

        private final List<Point> initialCoordinates;
        int[][] grid;
        private int colMinimum;
        private int colMaximum;
        private int rowMinimum;
        private int rowMaximum;

        public Board(List<Point> points) {

            initialCoordinates = points;

            int maxRow = locateMaxRow(points);
            int maxCol = locateMaxCol(points);

            grid = new int[maxRow + 2][maxCol + 2];

            fillStartingGrid(grid, points);

            locateGridBoundaries(points);
        }

        private void fillStartingGrid(int[][] grid, List<Point> points) {
            int  i = 1;
            for (Point point :
                    points) {
                grid[point.x][point.y] = i++;
            }
        }

        private void locateGridBoundaries(List<Point> points) {
            colMinimum = Integer.MAX_VALUE;
            colMaximum = Integer.MIN_VALUE;
            rowMinimum = Integer.MAX_VALUE;
            rowMaximum = Integer.MIN_VALUE;

            for (Point point :
                    points) {

                if (point.y < colMinimum) colMinimum = point.y;
                if (point.y > colMaximum) colMaximum = point.y;

                if (point.x < rowMinimum) rowMinimum = point.x;
                if (point.x > rowMaximum) rowMaximum = point.x;
            }
        }

        public void fillClosestCoordinates() {
            fillClosestCoordinates(grid, initialCoordinates);
        }

        void fillClosestCoordinates(int[][] grid, List<Point> points) {

            //Brute force (ok for small grid and few points)
            for (int row = 0; row < rowCount(grid); row++) {
                for (int col = 0; col < columnCount(grid); col++) {

                    // if this cell is a coordinate, skip it
                    if (points.contains(new Point(row, col))) continue;

                    int gridValue = 0;

                    int shortestDistance = Integer.MAX_VALUE;
                    int index = 0;
                    for (Point coordinate :
                            points) {
                        Point target = new Point(row, col);
                        index++;

                        if (points.contains(target)) continue;

                        int distance = manhattanDistanceBetween(coordinate, target);
                        if (distance < shortestDistance) {
                            shortestDistance = distance;
                            gridValue = index;
                        } else if (distance == shortestDistance) {
                            gridValue = 0;
                        }
                    }

                    grid[row][col] = gridValue;
                }
            }
        }

        void printGrid() {
            for (int row = 0; row < rowCount(grid); row++) {
                for (int col = 0; col < columnCount(grid); col++) {
                    System.out.print(grid[row][col]);
//                    System.out.print('.');
                }
                System.out.println(System.lineSeparator());
            }

        }

        private int columnCount(int[][] grid) {
            return grid[0].length;
        }

        private int rowCount(int[][] grid) {
            return grid.length;
        }

        private int locateMaxCol(List<Point> points) {
            int max = 0;
            for (Point point :
                    points) {
                if (point.y > max) max = point.y;
            }
            return max;
        }

        private int locateMaxRow(List<Point> points) {
            int max = 0;
            for (Point point :
                    points) {
                if (point.x > max) max = point.x;
            }
            return max;
        }

        public int countOfLargestArea() {

            Map<Integer, Integer> cellCounts = new HashMap<>();

            for (int row = rowMinimum; row < rowMaximum; row++) {
                for (int col = colMinimum; col < colMaximum; col++) {

                    int gridValue = grid[row][col];
                    if (gridValue == 0) continue;

                    if (cellCounts.containsKey(gridValue)) {
                        Integer count = cellCounts.get(gridValue);
                        cellCounts.put(gridValue, ++count);
                    } else {
                        cellCounts.put(gridValue, 1);
                    }
                }
            }

            return Collections.max(cellCounts.values());
        }

        public void fillSafestRegion(int maxDistance) {

            for (int row = 0; row < rowCount(grid); row++) {
                for (int col = 0; col < columnCount(grid); col++) {

                    int totalDistance = 0;
                    for (Point coordinate :
                            initialCoordinates) {
                        Point target = new Point(row, col);

                        int distance = manhattanDistanceBetween(coordinate, target);
                        totalDistance += distance;
                    }

                    if (totalDistance < maxDistance) {
                        grid[row][col] = -1;
                    }
                }
            }
        }

        public int countOfRegion() {
            int regionCount = 0;
            for (int row = rowMinimum; row < rowMaximum; row++) {
                for (int col = colMinimum; col < colMaximum; col++) {

                    int gridValue = grid[row][col];
                    if (gridValue == -1) {
                        regionCount++;
                    }
                }
            }
            return regionCount;
        }
    }
}
