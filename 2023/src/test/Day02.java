package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02 {
    final int max_red = 12;
    final int max_green = 13;
    final int max_blue = 14;

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day02_part1_example");

        int actual = sumPossibleGames(input);

        assertEquals(8, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day02");

        int actual = sumPossibleGames(input);

        assertEquals(2101, actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day02");

        int actual = sumPowers(input);

        assertEquals(58269, actual);
    }

    @Test
    void part2_fewestCubesExamples() {
        String input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";
        List<Integer> expected = Arrays.asList(4, 2, 6);  // R G B
        assertEquals(expected, fewestCubesNeeded(input));
        assertEquals(Arrays.asList(1, 3, 4), fewestCubesNeeded("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"));
        assertEquals(Arrays.asList(20, 13, 6), fewestCubesNeeded("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"));
        assertEquals(Arrays.asList(14, 3, 15), fewestCubesNeeded("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"));
        assertEquals(Arrays.asList(6, 3, 2), fewestCubesNeeded("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"));
    }

    @Test
    void part2_powerOfSetOfCubes() {
        List<Integer> setOfCubes = Arrays.asList(4, 2, 6);
        int expected = 4 * 2 * 6;
        assertEquals(expected, powerOfSetOfCubes(setOfCubes));

        assertEquals(12, powerOfSetOfCubes(Arrays.asList(1, 3, 4)));
        assertEquals(1560, powerOfSetOfCubes(Arrays.asList(20, 13, 6)));
        assertEquals(630, powerOfSetOfCubes(Arrays.asList(14, 3, 15)));
        assertEquals(36, powerOfSetOfCubes(Arrays.asList(6, 3, 2)));
    }

//--------------------------------------
    private int powerOfSetOfCubes(List<Integer> setOfCubes) {
        return setOfCubes.stream().reduce(1, (a, b) -> a * b);
    }

    private int sumPossibleGames(List<String> input) {
        int sum = 0;
        for (String inputLine :
                input) {
            if (isPossible(inputLine)) {
                int gameNumber = gameNumberOf(inputLine);
                sum += gameNumber;
            }
        }
        return sum;
    }

    private int sumPowers(List<String> input) {
        int sum = 0;
        for (String inputLine :
                input) {
            List<Integer> setOfCubes = fewestCubesNeeded(inputLine);
            sum += powerOfSetOfCubes(setOfCubes);
        }
        return sum;
    }

    private List<Integer> fewestCubesNeeded(String inputLine) {
        List<Integer> result = new ArrayList<>();

        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;

        String rhs = inputLine.split(":")[1].trim();
        String[] subsets = rhs.split(";");

        for (String subset : subsets) {
            String[] cubesRevealed = subset.split(",");
            for (String s : cubesRevealed) {

                String[] check = s.trim().split(" ");
                int numCubes = Integer.parseInt(check[0]);

                if (check[1].trim().equals("red")) {
                    maxRed = Math.max(maxRed, numCubes);
                }
                if (check[1].trim().equals("green")) {
                    maxGreen = Math.max(maxGreen, numCubes);
                }
                if (check[1].trim().equals("blue")) {
                    maxBlue = Math.max(maxBlue, numCubes);
                }
            }
        }
        result.add(maxRed);
        result.add(maxGreen);
        result.add(maxBlue);

        return result;
    }

    private int gameNumberOf(String inputLine) {
        return Integer.parseInt(inputLine.split(":")[0].split(" ")[1]);
    }

    private boolean isPossible(String inputLine) {
        String rhs = inputLine.split(":")[1].trim();
        String[] subsets = rhs.split(";");

        // if any grab of rgb exceeds the max, game is not possible (return false)
        for (String subset : subsets) {
            String[] cubesRevealed = subset.split(",");
            for (String s : cubesRevealed) {

                String[] check = s.trim().split(" ");
                int numCubes = Integer.parseInt(check[0]);
                if (check[1].trim().equals("red")) {
                    if (numCubes > max_red) return false;
                }
                if (check[1].trim().equals("green")) {
                    if (numCubes > max_green) return false;
                }
                if (check[1].trim().equals("blue")) {
                    if (numCubes > max_blue) return false;
                }
            }
        }
        return true;
    }
}
