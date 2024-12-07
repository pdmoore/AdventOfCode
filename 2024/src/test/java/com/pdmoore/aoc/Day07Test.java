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


    private BigInteger solvePart1(List<String> input) {

       // for each line
       // test combos to see if it can work for + or *
       // if it works, add to result
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
        for (int i = 0; i < rhs.length; i++) {
            bigIntegersRemaining.add(new BigInteger(rhs[i]));
        }

        return canBeSolved(target, bigIntegersRemaining);
    }

    private boolean canBeSolved(BigInteger target, List<BigInteger> bigIntegersRemaining) {
        BigInteger x1 = bigIntegersRemaining.remove(0);
        BigInteger x2 = bigIntegersRemaining.remove(0);

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
