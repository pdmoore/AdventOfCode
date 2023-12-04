package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04 {

    @Test
    void part1_score_single_scratch_card() {
        String input = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";

        int actual = scoreScratchCard(input);

        assertEquals(8, actual);
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04_part1_example");

        int actual = solvePart1(input);

        assertEquals(13, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04");

        int actual = solvePart1(input);

        assertEquals(18619, actual);
    }

    private int solvePart1(List<String> input) {
        int sum = 0;
        for (String inputLine :
                input) {
            sum += scoreScratchCard(inputLine);
        }
        return sum;
    }

    private int scoreScratchCard(String input) {
        String[] firstSplit = input.split(":");
        String[] secondSplit = firstSplit[1].split("\\|");

        List<String> winningNumbers = Arrays.asList(secondSplit[0].split(" "));
        List<String> numbersYouHave = Arrays.asList(secondSplit[1].split(" "));

        int count = 0;
        for (String numberYouHave :
                numbersYouHave) {
            if (!numberYouHave.isEmpty() && winningNumbers.contains(numberYouHave)) {
                count++;
            }
        }

       // split on colon, only retain second half
        // split second half on '|'
        // [0] is the winning numbers - convert to list
        // [1] is numbers you have - for each instance in winning numbers, score is 2^(num matches - 1)

        return (int) Math.pow(2, count - 1);
    }
}
