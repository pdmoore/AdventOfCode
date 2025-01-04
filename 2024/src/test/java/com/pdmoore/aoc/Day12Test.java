package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    @Test
    void part1_examples() {
        char[][] input = PuzzleInput.as2dCharArray("data/day12_example.txt");
        int actual = solvePart1ByRegion(input);
        assertEquals(140, actual);

        input = PuzzleInput.as2dCharArray("data/day12_example2.txt");
        actual = solvePart1ByRegion(input);
        assertEquals(772, actual);

        input = PuzzleInput.as2dCharArray("data/day12_example3.txt");
        actual = solvePart1ByRegion(input);
        assertEquals(1930, actual);
    }

    @Test
    void part1() {
        char[][] input = PuzzleInput.as2dCharArray("data/day12.txt");
        int actual = solvePart1ByRegion(input);
        assertEquals(1370258, actual);
    }

    @Test
    void part2_examples() {
        char[][] input = PuzzleInput.as2dCharArray("data/day12_example.txt");
        int actual = solvePart2(input);
        assertEquals(80, actual);

        input = PuzzleInput.as2dCharArray("data/day12_example2.txt");
        actual = solvePart2(input);
        assertEquals(436, actual);

        input = PuzzleInput.as2dCharArray("data/day12_example4.txt");
        actual = solvePart2(input);
        assertEquals(236, actual);

        input = PuzzleInput.as2dCharArray("data/day12_example5.txt");
        actual = solvePart2(input);
        assertEquals(368, actual);

        input = PuzzleInput.as2dCharArray("data/day12_example3.txt");
        actual = solvePart2(input);
        assertEquals(1206, actual);
    }

    @Test
    void part2() {
        char[][] input = PuzzleInput.as2dCharArray("data/day12.txt");
        int actual = solvePart2(input);
        assertEquals(805814, actual);
    }

    static class Region {
        public Set<Point> gardenPlots;
        public int fencedSides;
        public char type;
        public int cornerCount;

        public Region() {
            this.gardenPlots = new HashSet<>();
        }

        public int priceByPerimeter() {
            return gardenPlots.size() * fencedSides;
        }

        public int priceBySides() {
            return gardenPlots.size() * cornerCount;
        }
    }

    private int solvePart1ByRegion(char[][] map) {
        return findRegions(map)
                .stream()
                .mapToInt(Region::priceByPerimeter)
                .sum();
    }

    private int solvePart2(char[][] map) {
        return findRegions(map)
                .stream()
                .mapToInt(Region::priceBySides)
                .sum();
    }

    private List<Region> findRegions(char[][] map) {
        List<Point> pointsToProcess = new ArrayList<>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                pointsToProcess.add(new Point(row, col));
            }
        }

        List<Region> regions = new ArrayList<>();
        while (!pointsToProcess.isEmpty()) {
            Point seed = pointsToProcess.removeFirst();
            Region r = growRegionFrom(seed, map);
            regions.add(r);
            pointsToProcess.removeAll(r.gardenPlots);
        }
        return regions;
    }

    private Region growRegionFrom(Point seed, char[][] map) {
        Region region = new Region();
        region.type = map[seed.x][seed.y];

        region.gardenPlots.add(seed);

        List<Point> growths = new ArrayList<>();
        growths.add(new Point(seed.x, seed.y));

        while (!growths.isEmpty()) {
            Point growFrom = growths.removeFirst();
            region.gardenPlots.add(growFrom);

            List<Point> neighbors = getNeighbors(region.type, map, growFrom);
            for (Point neighbor : neighbors) {
                if ((!region.gardenPlots.contains(neighbor) && !growths.contains(neighbor))) {
                    growths.add(neighbor);
                }
            }
        }

        region.fencedSides = computeFencedSides(map, region.gardenPlots);
        region.cornerCount = computeCorners(map, region.gardenPlots);
        return region;
    }

    private List<Point> getNeighbors(char type, char[][] map, Point growFrom) {
        List<Point> neighbors = new ArrayList<>();
        int row = growFrom.x;
        int col = growFrom.y;

        if (type == safeCharGrab(map, row - 1, col)) neighbors.add(new Point(row - 1, col));
        if (type == safeCharGrab(map, row + 1, col)) neighbors.add(new Point(row + 1, col));
        if (type == safeCharGrab(map, row, col - 1)) neighbors.add(new Point(row, col - 1));
        if (type == safeCharGrab(map, row, col + 1)) neighbors.add(new Point(row, col + 1));

        return neighbors;
    }

    private int computeFencedSides(char[][] map, Set<Point> gardenPlots) {
        int result = 0;
        for (Point gardenPlot : gardenPlots) {
            result += countFencedSides(map, gardenPlot.x, gardenPlot.y);
        }

        return result;
    }

    private int computeCorners(char[][] map, Set<Point> gardenPlots) {
        int result = 0;
        for (Point gardenPlot : gardenPlots) {
            result += countCorners(map, gardenPlot.x, gardenPlot.y);
        }

        return result;
    }
    private int countFencedSides(char[][] map, int row, int col) {
        int fencedSides = 0;

        char up    = safeCharGrab(map, row - 1, col);
        char right = safeCharGrab(map, row, col + 1);
        char down  = safeCharGrab(map, row + 1,col);
        char left  = safeCharGrab(map, row, col - 1);

        char c = map[row][col];
        if (c != left) fencedSides++;
        if (c != up) fencedSides++;
        if (c != right) fencedSides++;
        if (c != down) fencedSides++;

        return fencedSides;
    }

    private int countCorners(char[][] map, int row, int col) {
        int corners = 0;

        char up    = safeCharGrab(map, row - 1, col);
        char right = safeCharGrab(map, row, col + 1);
        char down  = safeCharGrab(map, row + 1,col);
        char left  = safeCharGrab(map, row, col - 1);
        char upleft = safeCharGrab(map, row - 1, col - 1);
        char upright = safeCharGrab(map, row - 1, col + 1);
        char downright = safeCharGrab(map, row + 1, col + 1);
        char downleft = safeCharGrab(map, row + 1, col - 1);

        char c = map[row][col];

        if (c != up && c != left) corners++;
        if (c != up && c != right) corners++;
        if (c != down && c != left) corners++;
        if (c != down && c != right) corners++;

        if (c == left && c == up && c != upleft) corners++;
        if (c == up && c == right && c != upright) corners++;
        if (c == right && c == down && c != downright) corners++;
        if (c == down && c == left && c != downleft) corners++;

        return corners;
    }

    private char safeCharGrab(char[][] map, int row, int col) {
        char c = '.';
        try {
            c = map[row][col];
        } catch (IndexOutOfBoundsException x) {
            //ignore
        }
        return c;
    }
}
