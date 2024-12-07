package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07Test {

    public static final boolean TRY_CONCATENATION = true;

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day07_example.txt");

        BigInteger actual = solve(input, !TRY_CONCATENATION);

        assertEquals(new BigInteger("3749"), actual);
    }

    @Test
    void part1_simplerExample() {
        List<String> input = Collections.singletonList("292: 11 6 16 20");

        BigInteger actual = solve(input, !TRY_CONCATENATION);

        assertEquals(new BigInteger("292"), actual);
    }

    @Test
    void part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day07.txt");

        BigInteger actual = solve(input, !TRY_CONCATENATION);

        assertEquals(new BigInteger("1260333054159"), actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day07_example.txt");

        BigInteger actual = solve(input, TRY_CONCATENATION);

        assertEquals(new BigInteger("11387"), actual);
    }

    @Test
    void part2_simplerExamples() {
        List<String> input = Collections.singletonList("156: 15 6");
        BigInteger actual = solve(input, TRY_CONCATENATION);
        assertEquals(new BigInteger("156"), actual);

        input = Collections.singletonList("7290: 6 8 6 15");
        actual = solve(input, TRY_CONCATENATION);
        assertEquals(new BigInteger("7290"), actual);
    }

    @Test
    void part2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day07.txt");

        BigInteger actual = solve(input, TRY_CONCATENATION);

        assertEquals(new BigInteger("162042343638683"), actual);
    }

    private boolean canBeCalibrated(BigInteger target, List<BigInteger> bigIntegersRemaining, boolean tryConcatenation) {
        BigInteger x1 = bigIntegersRemaining.removeFirst();
        BigInteger x2 = bigIntegersRemaining.removeFirst();

        BigInteger concatenation = new BigInteger(x1.toString().concat(x2.toString()));
        if (bigIntegersRemaining.isEmpty()) {

            boolean isConcatenated = tryConcatenation && target.equals(concatenation);
            boolean isAdded = target.equals(x1.add(x2));
            boolean isMultiplied = target.equals(x1.multiply(x2));
            return isConcatenated ||
                    isAdded ||
                    isMultiplied;
        } else {
            List<BigInteger> addedList = new ArrayList<>(bigIntegersRemaining);
            addedList.addFirst(x1.add(x2));
            if (canBeCalibrated(target, addedList, tryConcatenation)) {
                return TRY_CONCATENATION;
            }

            List<BigInteger> multipliedList = new ArrayList<>(bigIntegersRemaining);
            multipliedList.addFirst(x1.multiply(x2));
            if (canBeCalibrated(target, multipliedList, tryConcatenation)) {
                return TRY_CONCATENATION;
            }

            if (tryConcatenation) {
                List<BigInteger> concatenationList = new ArrayList<>(bigIntegersRemaining);
                concatenationList.addFirst(concatenation);
                return canBeCalibrated(target, concatenationList, tryConcatenation);
            }

            return false;
        }
    }

    private BigInteger solve(List<String> input, boolean tryConcatenation) {
        BigInteger result = BigInteger.ZERO;

        for (String line : input) {
            String[] split = line.split(":");
            String lhs = split[0];
            String[] rhs = split[1].trim().split(" ");

            if (canBeCalibrated(lhs, rhs, tryConcatenation)) {
                result = result.add(new BigInteger(lhs));
            }
        }

        return result;
    }

    private boolean canBeCalibrated(String lhs, String[] rhs, boolean tryConcatenation) {
        BigInteger target = new BigInteger(lhs);

        List<BigInteger> bigIntegersRemaining = new ArrayList<>();
        for (String rh : rhs) {
            bigIntegersRemaining.add(new BigInteger(rh));
        }
        return canBeCalibrated(target, bigIntegersRemaining, tryConcatenation);
    }
}
