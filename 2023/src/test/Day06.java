package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day06 {
    @Test
    void part1_example() {
        // "Time:      7  15   30", "Distance:  9  40  200"
        List<Integer> race1 = findWinningNumbers(7, 9);
        List<Integer> race2 = findWinningNumbers(15, 40);
        List<Integer> race3 = findWinningNumbers(30, 200);

        assertEquals(4 * 8 * 9, race1.size() * race2.size() * race3.size());
    }

    @Test
    void part1_solution() {
        // "Time:        35     93     73     66", "Distance:   212   2060   1201   1044"
        List<Integer> race1 = findWinningNumbers(35, 212);
        List<Integer> race2 = findWinningNumbers(93, 2060);
        List<Integer> race3 = findWinningNumbers(73, 1201);
        List<Integer> race4 = findWinningNumbers(66, 1044);

        assertEquals(114400, race1.size() * race2.size() * race3.size() * race4.size());
    }

    @Test
    void part2_example_Int() {
        // "Time:      71530", "Distance:  940200"
        List<Integer> race1 = findWinningNumbers(71530, 940200);

        assertEquals(71503, race1.size());
    }

    @Test
    void part2_example_BD() {
        // "Time:      71530", "Distance:  940200"
        List<BigInteger> race1 = findWinningNumbers(BigInteger.valueOf(71530), BigInteger.valueOf(940200));

        assertEquals(71503, race1.size());
    }

    @Test
    void part2_solution() {
        // "Time:        35937366", "Distance:   212206012011044"
        List<BigInteger> race1 = findWinningNumbers(BigInteger.valueOf(35937366), new BigInteger("212206012011044"));

        assertEquals(21039729, race1.size());
    }

    @Test
    void part1_example_single_race_musing1() {
        int time = 7;
        int record = 9;

        List<Integer> actual = findWinningNumbers(time, record);

        List<Integer> expected = Arrays.asList(2, 3, 4, 5);
        assertEquals(expected, actual);
    }

    @Test
    void part1_example_single_race_musing2() {
        int time = 15;
        int record = 40;

        List<Integer> actual = findWinningNumbers(time, record);

        assertEquals(8, actual.size());
    }

    @Test
    void part1_example_single_race_musing3() {
        int time = 30;
        int record = 200;

        List<Integer> actual = findWinningNumbers(time, record);

        assertEquals(9, actual.size());
    }

    private List<Integer> findWinningNumbers(int time, int recordDistance) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= time; i++) {
            int distance = i * (time - i);
            if (distance > recordDistance) {
                result.add(i);
            }
        }

        return result;
    }

    private List<BigInteger> findWinningNumbers(BigInteger time, BigInteger recordDistance) {
        List<BigInteger> result = new ArrayList<>();
        for (BigInteger i = BigInteger.ONE; i.compareTo(time) <= 0; i = i.add(BigInteger.ONE)) {
            BigInteger distance = i.multiply(time.subtract(i));

            if (distance.compareTo(recordDistance) > 0) {
                result.add(i);
            }
        }

        return result;
    }
}
