package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    private int gameNumberOf(String inputLine) {
        String[] firstsplit = inputLine.split(":");
        return Integer.parseInt(firstsplit[0].split(" ")[1]);
    }

    private boolean isPossible(String inputLine){
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
