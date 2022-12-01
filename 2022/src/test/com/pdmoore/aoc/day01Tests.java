package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day01Tests {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day01_example");

        int actual = sumTopN(input, 1);

        Assertions.assertEquals(24000, actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day01_example");

        int actual = sumTopN(input,3);

        Assertions.assertEquals(45000, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day01");

        int actual = sumTopN(input, 1);

        Assertions.assertEquals(71471, actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day01");

        int actual = sumTopN(input, 3);

        Assertions.assertEquals(211189, actual);
    }

    private int sumTopN(List<String> input, int n) {
        List<Integer> orderedCaloriesPerElf = sumCaloriesByElf(input);
        Collections.sort(orderedCaloriesPerElf, Collections.reverseOrder());

        return orderedCaloriesPerElf.stream()
                .limit(n)
                .reduce(0, Integer::sum);
    }

    private static List<Integer> sumCaloriesByElf(List<String> input) {
        List<Integer> caloriesPerElf = new ArrayList<>();
        int sumForOneElf = 0;

        for (String inputLine :
                input) {
            if (inputLine.isEmpty()) {
                caloriesPerElf.add(sumForOneElf);

                sumForOneElf = 0;
            } else {
                sumForOneElf += Integer.parseInt(inputLine);
            }
        }
        caloriesPerElf.add(sumForOneElf);
        return caloriesPerElf;
    }
}