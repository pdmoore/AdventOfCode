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

    @Test
    void part2_example() {
        String input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

        int actual = solve_part2(input);

        Assertions.assertEquals(48, actual);
    }

    @Test
    void part2() {
        // Puzzle Input comes in as separate lines but need to be concatted together to be solved as expected
        // Probably do the same to part 1 and works as expected.

        List<String> input = PuzzleInput.asStringListFrom("data/day03.txt");
        int actual = 0;
        StringBuilder concated = new StringBuilder();
        for (String line : input) {
//            actual += solve_part2(line);
            concated.append(line);
        }

        actual = solve_part2(concated.toString());

        // 130081408 not correct, no hint of too big or small
        //  89846869 not correct - search for do() and don't()
        Assertions.assertEquals(76729637, actual);
    }

    private int solve_part2(String input) {
        int result = 0;

        int startFrom = 0;
        int dontIndex = input.indexOf("don't");
        boolean keepGoing = true;
        while (keepGoing) {
            String substring_1 = input.substring(startFrom, dontIndex);
            result += solve(substring_1);

            startFrom = input.indexOf("do()", dontIndex + 7);
            if (startFrom == -1) {
                int something = result + 1;
                return result;
            }
            dontIndex = input.indexOf("don't()", startFrom + 7);
            if (dontIndex == -1) {
                dontIndex = input.length();
                substring_1 = input.substring(startFrom, dontIndex);
                result += solve(substring_1);

                keepGoing = false;
            }
        }

        return result;
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
