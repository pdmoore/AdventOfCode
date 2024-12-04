package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

    @Test
    void part1_example() {
        String input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";

        int actual = solve(input);
        int actualViaRegex = solveWithRegEx(input);

        assertEquals(actual, actualViaRegex);
        assertEquals(161, actual);
    }

    @Test
    void part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day03.txt");

        // input includes \n characters that need to be rejoined to form a single line of input
        int actual = solve(String.join("", input));

        assertEquals(178794710, actual);
    }

    @Test
    void part2_example() {
        String input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

        int actual = solve_part2(input);
        int actualViaRegEx = solve_part2_WithRegEx(input);

        assertEquals(actual, actualViaRegEx);
        assertEquals(48, actual);
    }

    private int solve_part2_WithRegEx(String input) {
        Pattern pattern = Pattern.compile("don't\\(\\).*?do\\(\\)", Pattern.DOTALL);
        return solveWithRegEx(String.join("", pattern.split(input)));
    }

    @Test
    void part2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day03.txt");

        int actual = solve_part2(String.join("", input));
        int actualViaRegex = solve_part2_WithRegEx(String.join("", input));

        assertEquals(actual, actualViaRegex);
        assertEquals(76729637, actual);
    }

    private int solve_part2(String input) {
        int result = 0;

        int startFrom = 0;
        int dontIndex = input.indexOf("don't");
        boolean keepGoing = true;
        while (keepGoing) {
            String substring = input.substring(startFrom, dontIndex);
            result += solveWithRegEx(substring);

            startFrom = input.indexOf("do()", dontIndex + 7);
            if (startFrom == -1) {
                return result;
            }
            dontIndex = input.indexOf("don't()", startFrom + 7);
            if (dontIndex == -1) {
                dontIndex = input.length();
                substring = input.substring(startFrom, dontIndex);
                result += solveWithRegEx(substring);

                keepGoing = false;
            }
        }

        return result;
    }

    private int solve(String input) {
        int result = 0;
        // left to right, pattern mul(##,##)
        String[] split = input.split("mul");
        for (String each : split) {
            if (!each.isEmpty() && each.charAt(0) != '(') continue;

            int firstNumber;
            int secondNumber;
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

    private int solveWithRegEx(String input) {
        Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
        Matcher matcher = pattern.matcher(input);

        int result = 0;
        while (matcher.find()) {
            String match = matcher.group().replace("mul(", "").replace(")", "");
            String[] split = match.split(",");
            result += Integer.parseInt(split[0]) * Integer.parseInt(split[1]);
        }
        return result;
    }
}