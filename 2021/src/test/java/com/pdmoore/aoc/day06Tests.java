package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day06Tests {

    String example_input = "3,4,3,1,2";

    //TODO - part 1 can be solved by the part2 logic (School class)

    @Test
    void day06_part1_example_populate() {
        List<Integer> lanternFish = populate(example_input);

        assertEquals(5, lanternFish.size());
    }

    @Test
    void day06_part1_example_tick18() {
        List<Integer> lanternFish = populate(example_input);

        lanternFish = tickMany(lanternFish, 18);

        assertEquals(26, lanternFish.size());
    }

    @Test
    void day06_part1_example_tick80() {
        List<Integer> lanternFish = populate(example_input);

        lanternFish = tickMany(lanternFish, 80);

        assertEquals(5934, lanternFish.size());
    }

    @Test
    void day06_part1_tick80() {
        List<Integer> lanternFish = populate(PuzzleInput.asStringFrom("data/day06"));

        lanternFish = tickMany(lanternFish, 80);

        assertEquals(386755, lanternFish.size());
    }

    @Test
    void day06_part2_example_InitialCount() {
        School lanternFish = new School(example_input);

        Integer expectedInt = 5; // 5 lanternFish total
        BigInteger expected = BigInteger.valueOf(expectedInt.intValue());

        assertEquals(expected, lanternFish.count());
    }

    @Test
    void day06_part2_example_tick18() {
        School lanternFish = new School(example_input);

        lanternFish.tickMany(18);

        BigInteger expected = new BigInteger("26");

        assertEquals(expected, lanternFish.count());
    }

    @Test
    void day06_part2_example_tick256() {
        School lanternFish = new School(example_input);

        lanternFish.tickMany(256);

        BigInteger expected = new BigInteger("26984457539");

        assertEquals(expected, lanternFish.count());
    }

    @Test
    void day06_part2_tick256() {
        School lanternFish = new School(PuzzleInput.asStringFrom("data/day06"));

        lanternFish.tickMany(256);

        BigInteger expected = new BigInteger("1732731810807");

        assertEquals(expected, lanternFish.count());
    }


    private List<Integer> tickMany(List<Integer> lanternFish, int ticks) {
        for (int i = 0; i < ticks; i++) {
            lanternFish = tick(lanternFish);
        }
        return lanternFish;
    }

    private List<Integer> tick(List<Integer> lanternFish) {
        List<Integer> nextFishes = new ArrayList<>();
        int newFishCount = 0;
        for (Integer fish :
                lanternFish) {
            Integer nextFish = fish - 1;
            if (nextFish >= 0) {
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

    private List<Integer> populate(String input) {
        String[] numbersAsString = input.split(",");
        List<Integer> numbers = new ArrayList<>();
        for (String numberString :
                numbersAsString) {
            numbers.add(Integer.parseInt(numberString));
        }
        return numbers;
    }

    private class School {

        Map<Integer, BigInteger> fishes = new HashMap<>();

        public School(String input) {
            fishes = createEmptyFishMap();

            String[] numbersAsString = input.split(",");
            List<Integer> numbers = new ArrayList<>();
            for (String numberString :
                    numbersAsString) {
                int fishValue = Integer.parseInt(numberString);
                BigInteger currentCount = fishes.get(fishValue);
                fishes.put(fishValue, currentCount.add(BigInteger.ONE));
            }
        }

        private Map<Integer, BigInteger> createEmptyFishMap() {
            Map<Integer, BigInteger> newSchool = new HashMap<>();
            for (int i = 0; i <= 8; i++) {
                newSchool.put(i, BigInteger.ZERO);
            }
            return newSchool;
        }

        public void tickMany(int tickCount) {
            for (int i = 0; i < tickCount; i++) {
                tick();
            }
        }

        private void tick() {
            Map<Integer, BigInteger> newSchool = createEmptyFishMap();
            BigInteger newFishCount = fishes.get(0); // these become the new 8, also added to 6
            fishes.put(0, fishes.get(1));
            fishes.put(1, fishes.get(2));
            fishes.put(2, fishes.get(3));
            fishes.put(3, fishes.get(4));
            fishes.put(4, fishes.get(5));
            fishes.put(5, fishes.get(6));
            fishes.put(6, fishes.get(7));
            fishes.put(7, fishes.get(8));
            fishes.put(8, newFishCount);
            BigInteger currentAt6 = fishes.get(6);
            fishes.put(6, currentAt6.add(newFishCount));
        }

        public BigInteger count() {
            BigInteger count = BigInteger.ZERO;
            for (int i = 0; i < fishes.size(); i++) {
                BigInteger thisFishCount = fishes.get(i);
                count = count.add(thisFishCount);
            }
            return count;
        }
    }
}
