package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day03Test {

    @Test
    void part1_example() {
        String input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";

        int actual = solve(input);

        Assertions.assertEquals(161, actual);
    }

    @Test
    void part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day03.txt");
        int actual = 0;
        for (String line : input) {
            actual += solve(line);
        }

        Assertions.assertEquals(178794710, actual);

    }


    private int solve(String input) {
        int result = 0;
        // left to right, pattern mul(##,##)
        String[] split = input.split("mul");
        for (int i = 0; i < split.length; i++) {
            String each = split[i];
            if (each.length() > 0 && each.charAt(0) != '(') continue;

            int firstNumber = 0;
            int secondNumber = 0;
            try {
                int commaIx = each.indexOf(',');
                if (commaIx == -1) continue;
                firstNumber = Integer.parseInt(each.substring(1, commaIx));
                int closeParenIx = each.indexOf(')', commaIx + 1);
                if (closeParenIx == -1) continue;
                secondNumber = Integer.parseInt(each.substring(commaIx + 1, closeParenIx));
            } catch (NumberFormatException e) {
                continue;
            }

            result += firstNumber * secondNumber;
        }

        return result;
    }
}
