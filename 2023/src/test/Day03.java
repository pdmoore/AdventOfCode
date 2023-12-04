package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03 {

    public static final char GEAR_SYMBOL = '*';

    static public char[][] as2dCharArray(String filename) {
        List<String> input = PuzzleInput.asStringListFrom(filename);

        return as2dCharArray(input);
    }

    private static char[][] as2dCharArray(List<String> input) {
        int rowCount = input.size();
        int colCount = input.get(0).length();
        char[][] locations = new char[rowCount][colCount];
        for (int x = 0; x < input.size(); x++) {
            String thisLine = input.get(x);
            for (int y = 0; y < input.get(x).length(); y++) {
                locations[x][y] = thisLine.charAt(y);
            }
        }
        return locations;
    }

    @Test
    void get_all_the_numbers_in_the_grid() {
        List<Integer> expected = Arrays.asList(467, 114, 35, 633, 617, 58, 592, 755, 664, 598);
        Collections.sort(expected);

        Map<Point, Integer> actual = getAllTheNumbers("./data/day03_part1_example");

        Collection<Integer> values = actual.values();
        List<Integer> xxx = new ArrayList<>();
        xxx.addAll(values);
        Collections.sort(xxx);

        assertEquals(expected, xxx);
    }

    @Test
    void get_only_part_numbers_in_grid() {
        // number is a part number if adjacent to symbol
        // where symbol is not a number or period
        List<Integer> expected = Arrays.asList(467, 35, 633, 617, 592, 755, 664, 598);
        Collections.sort(expected);

        List<Integer> actual = getAllPartNumbers("./data/day03_part1_example");
        Collections.sort(actual);

        assertEquals(expected, actual);
    }

    @Test
    void part1_example() {
        List<Integer> partNumbers = getAllPartNumbers("./data/day03_part1_example");

        Integer actual = partNumbers.stream()
                .reduce(0, (a, b) -> a + b);

        assertEquals(4361, actual);
    }

    @Test
    void part1_example_from_reddit() {
        List<Integer> partNumbers = getAllPartNumbers("./data/day03_part1_reddit_example");

        Integer actual = partNumbers.stream()
                .reduce(0, (a, b) -> a + b);

        assertEquals(925, actual);
        // part 2 s/b 6756
    }

    @Test
    void part2_example_from_reddit() {
        int actual = solvePart2("./data/day03_part1_reddit_example");

        assertEquals(6756, actual);
    }

    @Test
    void part1_example_upandleft_bug() {
        List<Integer> partNumbers = getAllPartNumbers("./data/day03_part1_bugdata");

        Integer actual = partNumbers.stream()
                .reduce(0, (a, b) -> a + b);

        assertEquals(0, actual);
    }

    @Test
    void part1_solution() {
        List<Integer> partNumbers = getAllPartNumbers("./data/day03");

        Integer actual = partNumbers.stream()
                .reduce(0, (a, b) -> a + b);

        assertEquals(514969, actual);
    }

    @Test
    void part2_example() {
        int sumOfGearRatios = solvePart2("./data/day03_part1_example");

        assertEquals(467835, sumOfGearRatios);
    }

    @Test
    void part2_solution() {
        int sumOfGearRatios = solvePart2("./data/day03");

        assertEquals(78915902, sumOfGearRatios);
    }

//-----------------------------
    private int solvePart2(String filename) {
        Map<Point, Integer> partNumberPositions = getAllTheNumbers(filename);
        List<Point> gearLocations = getAllTheGears(filename);

        // for each partNumber
        // is it adjacent to gear?
        // if yes, add that partNumber to the gear's list of adjacent parts
        Map<Point, List<Integer>> gearAdjacentPartNumbers = new HashMap<>();
        for (Point p : partNumberPositions.keySet()) {
            int partNumber = partNumberPositions.get(p);

            Point adjacentGear = findAdjacentGear(gearLocations, p, partNumber);
            if (adjacentGear != null) {
                if (!gearAdjacentPartNumbers.containsKey(adjacentGear)) {
                    List<Integer> partNumberList = new ArrayList<>();
                    partNumberList.add(partNumber);
                    gearAdjacentPartNumbers.put(adjacentGear, partNumberList);
                } else {
                    gearAdjacentPartNumbers.get(adjacentGear).add(partNumber);
                }
            }
        }

        return sumOfGearRatios(gearAdjacentPartNumbers);
    }

    private static int sumOfGearRatios(Map<Point, List<Integer>> gearAdjacentPartNumbers) {
        // gear ratio is calculated when any gear is adjacent to exactly two part numbers
        // gear ratio is product of those two part numbers
        int result = 0;
        for (Point g: gearAdjacentPartNumbers.keySet()) {
            List<Integer> partNumbers = gearAdjacentPartNumbers.get(g);

            if (partNumbers.size() == 2) {
                result += partNumbers.get(0) * partNumbers.get(1);
            }
        }
        return result;
    }

    private Point findAdjacentGear(List<Point> gearLocations, Point p, int partNumber) {
        int numDigits = String.valueOf(partNumber).length();
        int lowY = p.y - 1;
        int highY = p.y + numDigits;
        int lowX = p.x - 1;
        int highX = p.x + 1;

        for (Point gearLocation :
                gearLocations) {
            if ((lowX <= gearLocation.x && gearLocation.x <= highX) &&
                    (lowY <= gearLocation.y && gearLocation.y <= highY))
                return gearLocation;
        }

        return null;
    }

    private List<Point> getAllTheGears(String filename) {
        char[][] input = as2dCharArray(filename);

        List<Point> result = new ArrayList<>();

        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < (input[x]).length; y++) {
                if (input[x][y] == GEAR_SYMBOL) {
                    result.add(new Point(x, y));
                }
            }
        }

        return result;
    }

    private List<Integer> getAllPartNumbers(String filename) {
        Map<Point, Integer> allTheNumbers = getAllTheNumbers(filename);

        List<Integer> result = new ArrayList<>();

        char[][] grid = as2dCharArray(filename);

        for (Point point : allTheNumbers.keySet()) {

            if (isPartNumber(grid, point, allTheNumbers.get(point))) {
                result.add(allTheNumbers.get(point));
            }
        }

        return result;
    }

    private boolean isPartNumber(char[][] grid, Point point, Integer integer) {
        int numDigits = String.valueOf(integer).length();

        for (int curY = point.y - 1; curY <= point.y + numDigits; curY++) {
            int curX = point.x;
            if (symbolAt(grid, curX - 1, curY)) return true;

            if (symbolAt(grid, curX, curY)) return true;
            if (symbolAt(grid, curX, curY)) return true;
            if (symbolAt(grid, curX, curY)) return true;

            if (symbolAt(grid, curX + 1, curY)) return true;
            if (symbolAt(grid, curX + 1, curY)) return true;
            if (symbolAt(grid, curX + 1, curY)) return true;
        }

        return false;
    }

    private boolean symbolAt(char[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length) return false;
        if (y < 0 || y >= grid[0].length) return false;

        char c = grid[x][y];
        if (c == '.') return false;
        if (c >= '0' && c <= '9') return false;

        return true;
    }

    private Map<Point, Integer> getAllTheNumbers(String filename) {
        char[][] input = as2dCharArray(filename);

        Map<Point, Integer> result = new HashMap<>();

        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < (input[x]).length; y++) {
                if (input[x][y] >= '0' && input [x][y] <= '9') {
                    Point startOfNumber = new Point(x, y);
                    String number = String.valueOf(input[x][y]);

                    // keep adding char until not a char or end of line
                    y++;
                    while (y < input[x].length &&
                           input[x][y] >= '0' && input[x][y] <= '9') {
                        number = number.concat(String.valueOf(input[x][y]));
                        y++;
                    }

                    result.put(startOfNumber, Integer.parseInt(number));
                }
            }
        }

        return result;
    }
}
