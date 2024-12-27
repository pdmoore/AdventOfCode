package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO lots of copypaste from part1 to part2
// duplicate effort in collecting antinodes and just counting # afterwards
class Day08Test {

    @Test
    void part1_example() {
        char[][] input = PuzzleInput.as2dCharArray("data/day08_example.txt");

        int actual = solvePart1(input);

        assertEquals(14, actual);
    }

    @Test
    void part1_simpleexample() {
        char[][] input = PuzzleInput.as2dCharArray("data/day08_simpleexample.txt");

        int actual = solvePart1(input);

        assertEquals(4, actual);
    }

    @Test
    void part1() {
        char[][] input = PuzzleInput.as2dCharArray("data/day08.txt");
        int actual = solvePart1(input);
        assertEquals(394, actual);
    }

    @Test
    void part2_example() {
        char[][] input = PuzzleInput.as2dCharArray("data/day08_example.txt");

        int actual = solvePart2(input);

        assertEquals(34, actual);
    }

    @Test
    void part2_example2() {
        char[][] input = PuzzleInput.as2dCharArray("data/day08_example2.txt");

        int actual = solvePart2(input);

        assertEquals(9, actual);
    }

    @Test
    void part2() {
        char[][] input = PuzzleInput.as2dCharArray("data/day08.txt");
        int actual = solvePart2(input);
        assertEquals(1277, actual);
    }

    private int solvePart1(char[][] input) {
        Map<Character, List<Point>> antennas = locateAntennas(input);

        // for each pair of positions, create an anti-node that is in opposite directions of the nodes
        // remove any anti-nodes that are outside the bounds of the image (less than 0 or greater than lnegth)
        List<Point> antinodes = new ArrayList<>();
        Set<Character> characters = antennas.keySet();
        for (Character character : characters) {
            List<Point> points = antennas.get(character);

            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(j);

                    int dx = Math.abs(p1.x - p2.x);
                    int dy = Math.abs(p1.y - p2.y);

                    int newx = -1;
                    int newy = -1;
                    if (p1.x <= p2.x) {
                        newx = p1.x - dx;
                        if (p1.y <= p2.y) {
                            newy = p1.y - dy;
                        } else {
                            newy = p1.y + dy;
                        }
                    }
                    // check if newx/newy in bounds
                    addWhenInBounds(input, antinodes, newx, newy);

                    int sex = -2;
                    int sey = -2;
                    if (p2.x >= p1.x) {
                        sex = p2.x + dx;
                        if (p2.y >= p1.y) {
                            sey = p2.y + dy;
                        } else {
                            sey = p2.y - dy;
                        }
                    }
                    addWhenInBounds(input, antinodes, sex, sey);
                }
            }
        }

        // TODO - antinodes colllection of points is 1 larger than antinodeCount
        // not sure which one is being added that shouldn't
        int antinodeCount = 0;
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {

                if (input[x][y] == '#') {
                    antinodeCount++;
                }
            }
        }
        return antinodeCount;
//        return antinodes.size();
    }

    private static Map<Character, List<Point>> locateAntennas(char[][] input) {
        // go through chars and find all the unique non-. characters
        // need character and position
        // map of Character, List<Point>
        Map<Character, List<Point>> antennas = new HashMap<>();
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {

                if (input[x][y] != '.') {
                    Character character = input[x][y];

                    if (!antennas.containsKey(character)) {
                        List<Point> points = new ArrayList<>();
                        points.add(new Point(x, y));
                        antennas.put(character, points);
                    } else {
                        antennas.get(character).add(new Point(x, y));
                    }
                }
            }
        }
        return antennas;
    }

    private void addWhenInBounds(char[][] input, List<Point> antinodes, int x, int y) {
        if (x >= 0 && x < input.length && y >= 0 && y < input.length) {
            antinodes.add(new Point(x, y));
            input[x][y] = '#';
        }
    }

    private boolean inBounds(int length, int n) {
        return (n >= 0 && n < length);
    }

    private int solvePart2(char[][] input) {
        Map<Character, List<Point>> antennas = locateAntennas(input);

        // for each pair of positions, create an anti-node that is in opposite directions of the nodes
        // remove any anti-nodes that are outside the bounds of the image (less than 0 or greater than lnegth)
        List<Point> antinodes = new ArrayList<>();
        Set<Character> characters = antennas.keySet();
        for (Character character : characters) {
            List<Point> points = antennas.get(character);

            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(j);

                    int dx = Math.abs(p1.x - p2.x);
                    int dy = Math.abs(p1.y - p2.y);

                    // loop and do this until out of bounds
                    int newx = p1.x;
                    int newy = p1.y;
                    while (inBounds(input.length, newx) && inBounds(input.length, newy)) {
                        if (p1.x <= p2.x) {
                            newx = newx - dx;
                            if (p1.y <= p2.y) {
                                newy = newy - dy;
                            } else {
                                newy = newy + dy;
                            }
                        }
                        addWhenInBounds(input, antinodes, newx, newy);
                    }

                    // loop and do this until out of bounds
                    int sex = p1.x;
                    int sey = p1.y;
                    while (inBounds(input.length, sex) && inBounds(input.length, sey)) {
                        if (p2.x >= p1.x) {
                            sex = sex + dx;
                            if (p2.y >= p1.y) {
                                sey = sey + dy;
                            } else {
                                sey = sey - dy;
                            }
                        }
                        addWhenInBounds(input, antinodes, sex, sey);
                    }
                }

                // TODO change both the antennas to antinodes
                Point p1 = points.get(i);
                antinodes.add(new Point(p1.x, p1.y));
                input[p1.x][p1.y] = '#';
            }
        }

        // TODO - antinodes colllection of points is 1 larger than antinodeCount
        // not sure which one is being added that shouldn't
        int antinodeCount = 0;
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {

                if (input[x][y] == '#') {
                    antinodeCount++;
                }
            }
        }
        return antinodeCount;
//        return antinodes.size();

    }

    static class Point {
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
}
