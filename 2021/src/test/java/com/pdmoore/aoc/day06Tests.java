package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day06Tests {

    String example_input = "3,4,3,1,2";

    @Test
    void day06_part1_example_populate() {
        List<BigInteger> lanternFish = populate(example_input);

        assertEquals(5, lanternFish.size());
    }

    @Test
    void day06_part1_example_tick18() {
        List<BigInteger> lanternFish = populate(example_input);

        lanternFish = tickMany(lanternFish, 18);

        assertEquals(26, lanternFish.size());
    }

    @Test
    void day06_part1_example_tick80() {
        List<BigInteger> lanternFish = populate(example_input);

        lanternFish = tickMany(lanternFish, 80);

        assertEquals(5934, lanternFish.size());
    }

    @Test
    void day06_part1_tick80() {
        List<BigInteger> lanternFish = populate(PuzzleInput.asStringFrom("data/day06"));

        lanternFish = tickMany(lanternFish, 80);

        assertEquals(386755, lanternFish.size());
    }

    @Test
    void day06_part1_tick256() {
        List<BigInteger> lanternFish = populate(PuzzleInput.asStringFrom("data/day06"));

        lanternFish = tickMany(lanternFish, 256);

        assertEquals(26984457539, lanternFish.size());
    }


    private List<Integer> tickMany(List<BigInteger> lanternFish, int ticks) {
        for (int i = 0; i < ticks; i++) {
            lanternFish = tick(lanternFish);
        }
        return lanternFish;
    }

    private List<BigInteger> tick(List<BigInteger> lanternFish) {
        List<BigInteger> nextFishes = new ArrayList<>();
        int newFishCount = 0;
        for (BigInteger fish :
                lanternFish) {
            BigInteger nextFish = fish.subtract(new BigInteger(1));
            if (nextFish.compareTo(BigInteger.ZERO) > 0) {
                nextFishes.add(nextFish);
            } else {
                nextFishes.add(6);
                newFishCount++;
            }
        }

        for (int i = 0; i < newFishCount; i++) {
            nextFishes.add(8);
        }

        return nextFishes;
    }


    private List<BigInteger> populate(String input) {
        String[] numbersAsString = input.split(",");
        List<BigInteger> numbers = new ArrayList<>();
        for (String numberString :
                numbersAsString) {
            numbers.add(new BigInteger(numberString));
        }
        return numbers;
    }
}
