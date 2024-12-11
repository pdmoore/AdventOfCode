package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

    @Test
    void part1_example() {
        String input = "0 1 10 99 999";

        int actual = solvePart1_viaStrings(input, 1);

        assertEquals(7, actual);

        actual = solvePart1(input, 1);
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

    @Test
    void part2_longerExample() {
        String input = "125 17";
        int actual = solvePart2(input, 6);
        assertEquals(22, actual);

        actual = solvePart2(input, 25);
        assertEquals(55312, actual);
    }



    @Test
    void part2() {
        String input = "1750884 193 866395 7 1158 31 35216 0";
        int actual = solvePart2(input, 75);
        assertEquals(99, actual);
    }

    private int solvePart2(String input, int blinkCount) {

        // INSTEAD OF solving the whole thing as one monolith
        // split it into smaller pieces and sum those small piece counts

        // need to watch out for rule where things are split into two - that seems
        // require some extra logic to follow those paths to the end

        // approach it as recursion....


        return 0;
    }

        private int solvePart1_viaStrings(String input, int blinkCount) {
        String next = input;
        for (int i = 0; i < blinkCount; i++) {
            System.out.println("blink " + i);
            String[] split = next.split(" ");

            StringBuilder sb = new StringBuilder();
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

    private int solvePart1(String input, int blinkCount) {
        String[] split = input.split(" ");
        List<BigInteger> stones = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            stones.add(new BigInteger(split[i]));
        }

        for (int i = 0; i < blinkCount; i++) {
            List<BigInteger> nextStones = new ArrayList<>();

            for (BigInteger stone : stones) {
                String s = stone.toString();

                if (stone.equals(BigInteger.ZERO)) {
                    nextStones.add(BigInteger.ONE);
                } else if (s.length() % 2 == 0) {
                    int length = s.length();
                    String substring = s.substring(0, length / 2);
                    StringBuilder sb = new StringBuilder(substring);
                    nextStones.add(new BigInteger(substring));
                    substring = s.substring(length / 2);
                    nextStones.add(new BigInteger(substring));
                } else {
                    BigInteger multiplyBy2024 = stone.multiply(BigInteger.valueOf(2024));
                    nextStones.add(multiplyBy2024);
                }
            }

            stones = nextStones;
        }

        return stones.size();
    }

}
