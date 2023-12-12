package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day11 {

    @Test
    void expand_space() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day11_part1_example");
        char[][] expected = PuzzleInput.as2dCharArray("./data/day11_part1_example_expanded");

        char[][] actual = expand(input);

        assertEquals(expected.length, actual.length);
        assertEquals(expected[0].length, actual[0].length);
    }

    @Test
    void find_galaxies() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day11_part1_example");
        char[][] expanded = expand(input);

        List<Point> galaxies = findGalaxies(expanded);

        assertEquals(9, galaxies.size());
        assertTrue(galaxies.contains(new Point(0, 4)));
        assertTrue(galaxies.contains(new Point(11, 0)));
    }

    @Test
    void generate_galaxy_pairs() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day11_part1_example");
        char[][] expanded = expand(input);
        List<Point> galaxies = findGalaxies(expanded);

        List<GalaxyPair> pairs = generateGalaxyPairs(galaxies);

        assertEquals(36, pairs.size());
    }

    @Test
    void distance_between_galaxies() {
        Point galaxy5 = new Point(6, 1);
        Point galaxy9 = new Point(11, 5);

        GalaxyPair sut = new GalaxyPair(galaxy5, galaxy9);

        assertEquals(9, sut.distance());

        assertEquals(15, new GalaxyPair(new Point(0, 4), new Point(10, 9)).distance());
        assertEquals(17, new GalaxyPair(new Point(2, 0), new Point(7, 12)).distance());
        assertEquals(5, new GalaxyPair(new Point(11, 0), galaxy9).distance());
    }

    @Test
    void part1_example() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day11_part1_example");

        int actual = solvePart1(input);

        assertEquals(374, actual);
    }

    @Test
    void part1_solution() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day11");

        int actual = solvePart1(input);

        assertEquals(9974721, actual);
    }

    @Test
    void part2_examples() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day11_part1_example");

        assertEquals(BigInteger.valueOf(374), solvePart2(input, 1));
        // TODO - didn't bother to figure out why the larger examples are 1 less than distance
        assertEquals(BigInteger.valueOf(1030), solvePart2(input, 9));
        assertEquals(BigInteger.valueOf(8410), solvePart2(input, 99));
    }

    @Test
    void part2_solution() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day11");

        // TODO - didn't bother to figure out why the larger examples are 1 less than distance
        assertEquals(BigInteger.valueOf(702770569197L), solvePart2(input, 999_999));
    }



    private BigInteger solvePart2(char[][] input, int expansionDelta) {
        List<Integer> emptyRows = emptyRowsIn(input);
        List<Integer> emptyCols = emptyColumnsIn(input);
        List<Point> galaxies = findGalaxies(input);
        List<GalaxyPair> pairs = generateGalaxyPairs(galaxies);

        BigInteger totalDistance = BigInteger.ZERO;
        for (GalaxyPair gp :
                pairs) {
            BigInteger distance = computeDistance(gp, emptyRows, emptyCols, expansionDelta);
            totalDistance = totalDistance.add(distance);
        }

        return totalDistance;
    }

    private static List<Integer> emptyRowsIn(char[][] input) {
        List<Integer> emptyRows = new ArrayList<>();
        for (int row = 0; row < input.length; row++) {
            boolean emptySpace = true;
            for (int col = 0; col < input[row].length; col++) {
                if (input[row][col] != '.') {
                    emptySpace = false;
                    break;
                }
            }
            if (emptySpace) {
                emptyRows.add(row);
            }
        }
        return emptyRows;
    }

    private BigInteger computeDistance(GalaxyPair gp, List<Integer> emptyRows, List<Integer> emptyCols, int expansionDelta) {
        BigInteger delta = BigInteger.valueOf(expansionDelta);
        BigInteger result = BigInteger.valueOf(gp.distance());

        // add in the rows crossed over
        int startX = Math.min(gp.galaxy_1.x, gp.galaxy_2.x);
        int endX = Math.max(gp.galaxy_1.x, gp.galaxy_2.x);
        for (int i = startX; i < endX; i++) {
            if (emptyRows.contains(i)) {
                result = result.add(delta);
            }
        }

        // add in the columns crossed over
        int startY = Math.min(gp.galaxy_1.y, gp.galaxy_2.y);
        int endY = Math.max(gp.galaxy_1.y, gp.galaxy_2.y);
        for (int i = startY; i < endY; i++) {
            if (emptyCols.contains(i)) {
                result = result.add(delta);
            }
        }

        return result;
    }


    private int solvePart1(char[][] input) {
        char[][] expanded = expand(input);
        List<Point> galaxies = findGalaxies(expanded);

        List<GalaxyPair> pairs = generateGalaxyPairs(galaxies);
        return pairs.stream().mapToInt(GalaxyPair::distance).sum();
    }

    private List<GalaxyPair> generateGalaxyPairs(List<Point> galaxies) {
        List<GalaxyPair> result = new ArrayList<>();
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                result.add(new GalaxyPair(galaxies.get(i), galaxies.get(j)));
            }
        }
        return result;
    }

    static class GalaxyPair {
        Point galaxy_1;
        Point galaxy_2;

        public GalaxyPair(Point g1, Point g2) {
            this.galaxy_1 = g1;
            this.galaxy_2 = g2;
        }

        public int distance() {
            return Math.abs(galaxy_2.x - galaxy_1.x) + Math.abs(galaxy_2.y - galaxy_1.y);
        }
    }

    private char[][] expand(char[][] input) {
        List<Integer> emptyRows = emptyRowsIn(input);

        List<Integer> emptyCols = emptyColumnsIn(input);

        char[][] expanded = new char[input.length + emptyRows.size()][input[0].length + emptyCols.size()];

        int copyFromRow = 0;
        int copyToRow   = 0;
        while (copyToRow < input.length + emptyRows.size()) {
            if (emptyRows.contains(copyFromRow)) {
                for (int col = 0; col < input[0].length + emptyCols.size(); col++) {
                    expanded[copyToRow][col] = '.';
                }
                copyToRow++;
            }

            int copyFromCol = 0;
            int copyToCol   = 0;
            while (copyToCol < input[0].length + emptyCols.size()) {
                if (emptyCols.contains(copyFromCol)) {
                    expanded[copyToRow][copyToCol] = '.';
                    copyToCol++;
                    expanded[copyToRow][copyToCol] = '.';
                } else {
                    expanded[copyToRow][copyToCol] = input[copyFromRow][copyFromCol];
                }

                copyFromCol++;
                copyToCol++;
            }
            copyFromRow++;
            copyToRow++;
        }

        return expanded;
    }

    private static List<Integer> emptyColumnsIn(char[][] input) {
        List<Integer> emptyCols = new ArrayList<>();
        for (int col = 0; col < input[0].length; col++) {
            boolean emptySpace = true;
            for (char[] chars : input) {
                if (chars[col] != '.') {
                    emptySpace = false;
                    break;
                }
            }
            if (emptySpace) {
                emptyCols.add(col);
            }
        }
        return emptyCols;
    }

    private List<Point> findGalaxies(char[][] space) {
        List<Point> galaxies = new ArrayList<>();

        for (int row = 0; row < space.length; row++) {
            for (int col = 0; col < space[0].length; col++) {
                if (space[row][col] == '#') {
                    galaxies.add(new Point(row, col));
                }
            }
        }

        return galaxies;
    }


    private void dumpMap(char[][] map) {
        for (char[] chars : map) {
            System.out.println(chars);
        }
    }

}
