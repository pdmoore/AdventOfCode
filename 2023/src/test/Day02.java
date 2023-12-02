package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {
    final int max_red = 12;
    final int max_green = 13;
    final int max_blue = 14;

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day02_part1_example");

        int actual = sumPossibleGames(input);

        Assertions.assertEquals(8, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day02");

        int actual = sumPossibleGames(input);

        Assertions.assertEquals(2101, actual);
    }

    @Test
    void part2_fewestCubesExamples() {
        String input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";
        List<Integer> expected = Arrays.asList(4, 2, 6);  // R G B
        Assertions.assertEquals(expected, part2singlething(input));
    }

    //-------------------------
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

    private List<Integer> part2singlething(String inputLine) {
        List<Integer> result = new ArrayList<>();

        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;

        //Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        // split on colon
        // rhs of colon, split on semicolon
        // copy the parsing stuff from below
        String[] firstsplit = inputLine.split(":");
        String rhs = firstsplit[1].trim();
        String[] subsets = rhs.split(";");

        for (int i = 0; i < subsets.length; i++) {
            String[] cubesRevealed = subsets[i].split(",");
            for (int j = 0; j < cubesRevealed.length; j++) {

                String[] check = cubesRevealed[j].trim().split(" ");
                if (check[1].trim().equals("red")) {
                    maxRed = Math.max(maxRed, 4);
                }
                if (check[1].trim().equals("green")) {
                    maxGreen = Math.max(maxGreen, 2);
                }
                if (check[1].trim().equals("blue")) {
                    maxBlue = Math.max(maxBlue, 6);
                }
            }
        }
        result.add(maxRed);
        result.add(maxGreen);
        result.add(maxBlue);

        return result;
    }

    private int gameNumberOf(String inputLine) {
        String[] firstsplit = inputLine.split(":");
        return Integer.parseInt(firstsplit[0].split(" ")[1]);
    }

    private boolean isPossible(String inputLine) {
        //Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        String[] firstsplit = inputLine.split(":");
        String rhs = firstsplit[1].trim();
        String[] subsets = rhs.split(";");

        // foreach subset, split into rgb
        // if any rgb exceeds the max, return false
        for (int i = 0; i < subsets.length; i++) {
            String[] cubesRevealed = subsets[i].split(",");
            for (int j = 0; j < cubesRevealed.length; j++) {

                String[] check = cubesRevealed[j].trim().split(" ");
                Integer numCubes = Integer.parseInt(check[0]);
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
