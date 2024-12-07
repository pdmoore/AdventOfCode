package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07Test {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day07_example.txt");

        BigInteger actual = solvePart1(input);

        assertEquals(new BigInteger("3749"), actual);
    }

    @Test
    void part1_simplerExample() {
        List<String> input = Collections.singletonList("292: 11 6 16 20");

        BigInteger actual = solvePart1(input);

        assertEquals(new BigInteger("292"), actual);
    }

    @Test
    void part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day07.txt");

        BigInteger actual = solvePart1(input);

        assertEquals(new BigInteger("1260333054159"), actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day07_example.txt");

        BigInteger actual = solvePart2(input);

        assertEquals(new BigInteger("11387"), actual);
    }

    @Test
    void part2_simplerExample() {
        List<String> input = Collections.singletonList("156: 15 6");
        BigInteger actual = solvePart2(input);
        assertEquals(new BigInteger("156"), actual);

        input = Collections.singletonList("7290: 6 8 6 15");
        actual = solvePart2(input);
        assertEquals(new BigInteger("7290"), actual);
    }

    @Test
    void part2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day07.txt");

        BigInteger actual = solvePart2(input);

        assertEquals(new BigInteger("162042343638683"), actual);
    }

    private BigInteger solvePart2(List<String> input) {
        BigInteger result = BigInteger.ZERO;

        for (String line : input) {

            String[] split = line.split(":");
            String lhs = split[0];
            String[] rhs = split[1].trim().split(" ");

            if (canBeSolved2(lhs, rhs)) {
                result = result.add(new BigInteger(lhs));
            }
        }

        return result;
    }

    private boolean canBeSolved2(String lhs, String[] rhs) {
        BigInteger target = new BigInteger(lhs);

        List<BigInteger> bigIntegersRemaining = new ArrayList<>();
        for (String rh : rhs) {
            bigIntegersRemaining.add(new BigInteger(rh));
        }

        return canBeSolved2(target, bigIntegersRemaining);
    }

    private boolean canBeSolved2(BigInteger target, List<BigInteger> bigIntegersRemaining) {
        BigInteger x1 = bigIntegersRemaining.removeFirst();
        BigInteger x2 = bigIntegersRemaining.removeFirst();

        BigInteger concatenation = new BigInteger(x1.toString().concat(x2.toString()));
        if (bigIntegersRemaining.isEmpty()) {

            return (target.equals(concatenation)) ||
                    (target.equals(x1.add(x2))) ||
                    (target.equals(x1.multiply(x2)));
        } else {
            List<BigInteger> addedList = new ArrayList<>(bigIntegersRemaining);
            addedList.addFirst(x1.add(x2));
            if (canBeSolved2(target, addedList)) {
                return true;
            }

            List<BigInteger> multipliedList = new ArrayList<>(bigIntegersRemaining);
            multipliedList.addFirst(x1.multiply(x2));
            if (canBeSolved2(target, multipliedList)) {
                return true;
            }

            List<BigInteger> concatenationList = new ArrayList<>(bigIntegersRemaining);
            concatenationList.addFirst(concatenation);
            return canBeSolved2(target, concatenationList);
        }
    }

    private BigInteger solvePart1(List<String> input) {
        BigInteger result = BigInteger.ZERO;

        for (String line : input) {

            String[] split = line.split(":");
            String lhs = split[0];
            String[] rhs = split[1].trim().split(" ");

            if (canBeSolved(lhs, rhs)) {
                result = result.add(new BigInteger(lhs));
            }
        }

        return result;
    }

    private boolean canBeSolved(String lhs, String[] rhs) {
        BigInteger target = new BigInteger(lhs);

        List<BigInteger> bigIntegersRemaining = new ArrayList<>();
        for (String rh : rhs) {
            bigIntegersRemaining.add(new BigInteger(rh));
        }

        return canBeSolved(target, bigIntegersRemaining);
    }

    private boolean canBeSolved(BigInteger target, List<BigInteger> bigIntegersRemaining) {
        BigInteger x1 = bigIntegersRemaining.removeFirst();
        BigInteger x2 = bigIntegersRemaining.removeFirst();

        if (bigIntegersRemaining.isEmpty()) {
            return (target.equals(x1.add(x2))) ||
                    (target.equals(x1.multiply(x2)));
        } else {
            List<BigInteger> addedList = new ArrayList<>(bigIntegersRemaining);
            addedList.addFirst(x1.add(x2));
            if (canBeSolved(target, addedList)) {
                return true;
            }

            List<BigInteger> multipliedList = new ArrayList<>(bigIntegersRemaining);
            multipliedList.addFirst(x1.multiply(x2));
            return canBeSolved(target, multipliedList);
        }
    }
}
