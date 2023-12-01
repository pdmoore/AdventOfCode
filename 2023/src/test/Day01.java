package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01 {

    private Map<String, Integer> digitFromSpelling = Stream.of( new Object[][] {
            {"one", 1},
            {"two", 2},
            {"three", 3},
            {"four", 4},
            {"five", 5},
            {"six", 6},
            {"seven", 7},
            {"eight", 8},
            {"nine", 9}
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

    @Test
    public void CalibrationValue_FromFirstAndLastNumber() {
        String input = "1abc2";
        int expected = 12;
        int actual = calibrationFromDigitsOnly(input);
        assertEquals(expected, actual);
    }

    @Test
    void CalibrationValue_NumbersAreInMiddle() {
        assertEquals(38, calibrationFromDigitsOnly("pqr3stu8vwx"));
    }

    @Test
    void CalibrationValue_MoreThanTwoNumbers() {
        assertEquals(15, calibrationFromDigitsOnly("a1b2c3d4e5f"));
    }

    @Test
    void CalibrationValue_OnlyOneNumber() {
        assertEquals(77, calibrationFromDigitsOnly("treb7uchet"));
    }

    @Test
    void sumAllCalibrationValues() {
        List<String> input = new ArrayList<>();
        input.add("1abc2");
        input.add("pqr3stu8vwx");
        input.add("a1b2c3d4e5f");
        input.add("treb7uchet");

        int actual = day01part1(input);

        assertEquals(142, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day01");

        int actual = day01part1(input);

        assertEquals(54159, actual);
    }

    @Test
    void CalibrationValaue_spelledNumbers() {
        String input = "two1nine";
        assertEquals(29, calibrationValueFrom(input));
    }

    //-------------------------------------------------------

    private int calibrationValueFrom(String input) {
        StringBuilder sb = new StringBuilder();
        sb.append(findFirstNumber(input));
        sb.append(findSecondNumber(input));

        return Integer.parseInt(sb.toString());
    }

    private int findSecondNumber(String input) {
        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.charAt(i);
            if (c >= '0' && c <= '9') {
                return Integer.parseInt(String.valueOf(c));
            }

            // if substring starting at c matches any of the map's keys, return that substring key
            String remainderOfInput = input.substring(i);
            for (String key :
                    digitFromSpelling.keySet()) {
                if (remainderOfInput.startsWith(key)) {
                    return digitFromSpelling.get(key);
                }
            }
        }
        throw new IllegalArgumentException("could not find a number from left in: " + input);

    }

    private int findFirstNumber(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= '0' && c <= '9') {
                return Integer.parseInt(String.valueOf(c));
            }

            // if substring starting at c matches any of the map's keys, return that substring key
            String remainderOfInput = input.substring(i);
            for (String key :
                    digitFromSpelling.keySet()) {
                if (remainderOfInput.startsWith(key)) {
                    return digitFromSpelling.get(key);
                }
            }
        }
        throw new IllegalArgumentException("could not find a number from left in: " + input);
    }


    private int day01part1(List<String> input) {
        int sum = 0;
        for (String inputLine :
                input) {
            sum += calibrationFromDigitsOnly(inputLine);
        }
        return sum;
    }


    private int calibrationFromDigitsOnly(String input) {
        String firstDigitFromLeft = fromLeft(input);
        String firstDigitDromRight = fromRight(input);
        String concat = firstDigitFromLeft + firstDigitDromRight;
        return Integer.parseInt(concat);
    }

    private String fromRight(String input) {
        StringBuilder reversed = new StringBuilder();
        reversed.append(input);
        reversed.reverse();
        return fromLeft(reversed.toString());
    }

    private String fromLeft(String input) {
        for (char c :
                input.toCharArray()) {
            if (c >= '0' && c <= '9') {
                return String.valueOf(c);
            }
        }
        throw new IllegalArgumentException("couldn't find a digit in: " + input);
    }
}
