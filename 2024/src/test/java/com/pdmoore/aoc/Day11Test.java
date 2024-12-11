package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

    @Test
    void part1_example() {
        String input = "0 1 10 99 999";

        int actual = solvePart1(input, 1);

        assertEquals(7, actual);
    }

    @Test
    void part1_longerExample() {
        String input = "125 17";
        int actual = solvePart1(input, 6);
        assertEquals(22, actual);

        actual = solvePart1(input, 25);
        assertEquals(55312, actual);
    }

    @Test
    void test1000() {
        String input = "1000";
        int actual = solvePart1(input, 1);
        assertEquals(2, actual);
    }

    @Test
    void part1() {
        String input = "1750884 193 866395 7 1158 31 35216 0";
        int actual = solvePart1(input, 25);
        assertEquals(231278, actual);
    }


    private int solvePart1(String input, int blinkCount) {
        String next = input;
        for (int i = 0; i < blinkCount; i++) {
            StringBuilder sb = new StringBuilder();

            String[] split = next.split(" ");
            for (String s : split) {
                BigInteger engraving = new BigInteger(s);

                if (engraving.equals(BigInteger.ZERO)) {
                    sb.append("1");
                } else if (s.length() % 2 == 0) {
                    int length = s.length();
                    String substring = s.substring(0, length / 2);
                    sb.append(new BigInteger(substring));
                    sb.append(" ");
                    substring = s.substring(length / 2);
                    sb.append(new BigInteger(substring));
                }
                else {
                    BigInteger multiplyBy2024 = engraving.multiply(BigInteger.valueOf(2024));
                    sb.append(multiplyBy2024);
                }

                sb.append(" ");
            }
            next = sb.toString().trim();
        }

        return next.split(" ").length;
    }
}
