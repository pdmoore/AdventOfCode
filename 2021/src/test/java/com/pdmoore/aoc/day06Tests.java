package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day06Tests {

    String example_input = "3,4,3,1,2";

    @Test
    void day06_part1_example_populate() {
        List<Integer> lanternFish = new ArrayList<>();
        lanternFish = populate(example_input);

        assertEquals(5, lanternFish.size());
    }

    @Test
    void day06_part1_example_tick18() {
        List<Integer> lanternFish = new ArrayList<>();
        lanternFish = populate(example_input);

        lanternFish = tickMany(lanternFish, 18);

        assertEquals(26, lanternFish.size());
    }

    @Test
    void day06_part1_example_tick80() {
        List<Integer> lanternFish = new ArrayList<>();
        lanternFish = populate(example_input);

        lanternFish = tickMany(lanternFish, 80);

        assertEquals(5934, lanternFish.size());
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

System.out.println(nextFishes);
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
}
