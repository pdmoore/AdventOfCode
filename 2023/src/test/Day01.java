package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class Day01 {

    @Test
    public void CalibrationValue_FromFirstAndLastNumber() {
        String input = "1abc2";
        int expected = 12;
        int actual = thingie(input);
        assertEquals(expected, actual);
    }

    @Test
    void CalibrationValue_NumbersAreInMiddle() {
        assertEquals(38, thingie("pqr3stu8vwx"));
    }

    @Test
    void CalibrationValue_MoreThanTwoNumbers() {
        assertEquals(15, thingie("a1b2c3d4e5f"));
    }

    @Test
    void CalibrationValue_OnlyOneNumber() {
        assertEquals(77, thingie("treb7uchet"));
    }

    private int thingie(String input) {
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
