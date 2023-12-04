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

    private int scoreScratchCard(String input) {
//        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";

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
