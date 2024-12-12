package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    void canZeroRun75Blinks() {
        String input = "0";
        int actual = solvePart1(input, 40);
        assertEquals(10174278, actual);
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

//        actual = solvePart2(input, 25);
//        assertEquals(55312, actual);
//
//        actual = solvePart2(input, 75);
//        assertEquals(99, actual);
    }



    @Test
    void part2() {
        String input = "1750884 193 866395 7 1158 31 35216 0";
        int actual = solvePart1(input, 75);
        assertEquals(99, actual);
    }

    private int solvePart2(String input, int blinkCount) {

        // INSTEAD OF solving the whole thing as one monolith
        // split it into smaller pieces and sum those small piece counts

        // need to watch out for rule where things are split into two - that seems
        // require some extra logic to follow those paths to the end

        // approach it as recursion....

        // Store engraving and count
        //

        String[] split = input.split(" ");
        List<BigInteger> stones = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            stones.add(new BigInteger(split[i]));
        }
        Map<BigInteger, Integer> engravingByCount = new HashMap<>();
        for (BigInteger stone : stones) {
//            engravingByCount.put(stone, engravingByCount.getOrDefault(stone, 0) + 1);
            engravingByCount.put(stone, 1);
        }

        for (int i = 1; i <= blinkCount; i++) {
            System.out.println("blink " + i);
//            List<BigInteger> nextStones = new ArrayList<>();


            // TODO - switching to map lost the fact there may be duplicates generated during
            // the processing of all previous.
            // Instead of just blindly doing a put, need to check if key is there already
            // and update by amount or put amount if not there
            Map<BigInteger, Integer> nextStonesByCount = new HashMap<>();
            for (BigInteger stone : engravingByCount.keySet()) {

                if (i == 4) {
                    int breakpoint = 55;
                }

                // Need to track amount and up those counts for the new stones
                int amount = engravingByCount.get(stone);
                if (amount == 0) continue;
                System.out.print(stone + "   ");

                engravingByCount.replace(stone, 0);

                String s = stone.toString();
                if (stone.equals(BigInteger.ZERO)) {
//                    nextStones.add(BigInteger.ONE);
                    nextStonesByCount.put(BigInteger.ONE, amount);
                } else if (s.length() % 2 == 0) {
                    int length = s.length();
                    String substring = s.substring(0, length / 2);
                    StringBuilder sb = new StringBuilder(substring);
//                    nextStones.add(new BigInteger(substring));
                    nextStonesByCount.put(new BigInteger(substring), amount);
                    substring = s.substring(length / 2);
//                    nextStones.add(new BigInteger(substring));
                    nextStonesByCount.put(new BigInteger(substring), amount);
                } else {
                    BigInteger multiplyBy2024 = stone.multiply(BigInteger.valueOf(2024));
//                    nextStones.add(multiplyBy2024);
                    nextStonesByCount.put(multiplyBy2024, amount);

                }

                System.out.println(" ");
            }

//            for (BigInteger nextStone : nextStones) {
            for (BigInteger nextStone : nextStonesByCount.keySet()) {
                engravingByCount.put(nextStone, engravingByCount.getOrDefault(nextStone, 0) + nextStonesByCount.get(nextStone));
            }
        }

        int result = 0;
        for (BigInteger stone : engravingByCount.keySet()) {
            result += engravingByCount.get(stone);
        }

        return result;
    }

    private int solvePart1(String input, int blinkCount) {
        String[] split = input.split(" ");
        List<BigInteger> stones = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            stones.add(new BigInteger(split[i]));
        }

        for (int i = 0; i < blinkCount; i++) {
            System.out.println("blink " + i + "   " + stones.size());

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
