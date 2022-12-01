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

        int actual = sumTopN(input);

        Assertions.assertEquals(45000, actual);
    }

    private int sumTopN(List<String> input) {
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

        int i = 0;
        for (int j = 0; j < 3; j++) {
            i += sums.get(j);
        }
        return i;
    }

    private int findMaxSum(List<String> input) {
        int maxSum = 0;

        int sum = 0;;
        for (String inputLine :
                input) {
            if (inputLine.isEmpty()) {

                if (sum > maxSum) maxSum = sum;
                sum = 0;
            } else {
                sum += Integer.parseInt(inputLine);
            }
        }

        return maxSum;
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day01");

        int actual = findMaxSum(input);

        Assertions.assertEquals(71471, actual);
    }    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day01");

        int actual = sumTopN(input);

        Assertions.assertEquals(211189, actual);
    }

}