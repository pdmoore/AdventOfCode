package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06 {
    @Test
    void part1_example() {
        List<String> input = Arrays.asList("Time:      7  15   30", "Distance:  9  40  200");

        List<Integer> race1 = findWinningNumbers(7, 9);
        List<Integer> race2 = findWinningNumbers(15, 40);
        List<Integer> race3 = findWinningNumbers(30, 200);

        assertEquals(4 * 8 * 9, race1.size() * race2.size() * race3.size());
    }

    @Test
    void part1_solution() {
        List<String> input = Arrays.asList("Time:        35     93     73     66", "Distance:   212   2060   1201   1044");

        List<Integer> race1 = findWinningNumbers(35, 212);
        List<Integer> race2 = findWinningNumbers(93, 2060);
        List<Integer> race3 = findWinningNumbers(73, 1201);
        List<Integer> race4 = findWinningNumbers(66, 1044);

        assertEquals(-99, race1.size() * race2.size() * race3.size() * race4.size());
    }

    @Test
    void part2_example_Int() {
        List<String> input = Arrays.asList("Time:      71530", "Distance:  940200");

        List<Integer> race1 = findWinningNumbers(71530, 940200);

        assertEquals(71503, race1.size());
    }

    @Test
    void part2_example_BD() {
        List<String> input = Arrays.asList("Time:      71530", "Distance:  940200");

        List<BigDecimal> race1 = findWinningNumbers(new BigDecimal(71530), new BigDecimal(940200));

        assertEquals(71503, race1.size());
    }

    @Test
    void part2_solution() {
        List<String> input = Arrays.asList("Time:        35937366", "Distance:   212206012011044");

        List<BigDecimal> race1 = findWinningNumbers(new BigDecimal(35937366L), new BigDecimal(212206012011044L));

        assertEquals(-99, race1.size());
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
            int speed = i;
            int distance = 0;
            for (int j = i + 1; j <= time; j++) {
                distance += speed;
            }

            if (distance > recordDistance) {
                result.add(i);
            }
        }

        return result;
    }

    private List<BigDecimal> findWinningNumbers(BigDecimal time, BigDecimal recordDistance) {
        List<BigDecimal> result = new ArrayList<>();
        for (BigDecimal i = BigDecimal.ONE; i.compareTo(time) <= 0; i = i.add(BigDecimal.ONE)) {
            BigDecimal speed = i;
            BigDecimal distance = speed.multiply(time.subtract(i));

            if (distance.compareTo(recordDistance) > 0) {
                result.add(i);
            }
        }

        return result;
    }

}
