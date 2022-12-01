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

        int actual = findMaxSum(input);

        Assertions.assertEquals(24000, actual);
    }
    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day01_example");

        int actual = sumTopN(input,3);

        Assertions.assertEquals(45000, actual);
    }

    private int sumTopN(List<String> input, int n) {
        List<Integer> sums = new ArrayList<>();
        int sum = 0;;
        for (String inputLine :
                input) {
            if (inputLine.isEmpty()) {
                sums.add(sum);

                sum = 0;
            } else {
                sum += Integer.parseInt(inputLine);
            }
        }
        sums.add(sum);
        Collections.sort(sums);
        Collections.reverse(sums);

        int sumOfTopN = 0;
        for (int i = 0; i < n; i++) {
            sumOfTopN += sums.get(i);
        }
        return sumOfTopN;
    }

    private int findMaxSum(List<String> input) {
        return sumTopN(input, 1);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day01");

        int actual = findMaxSum(input);

        Assertions.assertEquals(71471, actual);
    }    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day01");

        int actual = sumTopN(input, 3);

        Assertions.assertEquals(211189, actual);
    }

}