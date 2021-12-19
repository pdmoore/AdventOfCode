package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day07Tests {

    final String example_input = "16,1,2,0,4,2,7,1,2,14";

    @Test
    void day07_part01_example() {
        int[] horizontalPositions = PuzzleInput.csvStringAsIntArray(example_input);
        int actual = cheapestAlignmentByCheckingAll(horizontalPositions);
        assertEquals(37, actual);
    }

    @Test
    void day07_part01_solution() {
        int[] horizontalPositions = PuzzleInput.csvStringAsIntArray(PuzzleInput.asStringFrom("data/day07"));
        int actual = cheapestAlignmentByCheckingAll(horizontalPositions);

        assertEquals(364898, actual);
    }

    @Test
    void day07_part2_increasingFuelRateCalculation() {
        assertEquals(1, calculateFuelFor(4, 5));
        assertEquals(3, calculateFuelFor(3, 5));
        assertEquals(6, calculateFuelFor(2, 5));
        assertEquals(10, calculateFuelFor(1, 5));
        assertEquals(15, calculateFuelFor(0, 5));
        assertEquals(45, calculateFuelFor(14, 5));
        assertEquals(66, calculateFuelFor(16, 5));
    }

    @Test
    void day07_part02_example() {
        int[] horizontalPositions = PuzzleInput.csvStringAsIntArray(example_input);
        int actual = cheapestAlignmentByCheckingAll_part2(horizontalPositions);
        assertEquals(168, actual);
    }

    @Test
    void day07_part02_solution() {
        int[] horizontalPositions = PuzzleInput.csvStringAsIntArray(PuzzleInput.asStringFrom("data/day07"));
        int actual = cheapestAlignmentByCheckingAll_part2(horizontalPositions);

        assertEquals(104149091, actual);
    }

    private int calculateFuelFor(int currentPosition, int basePosition) {
        int fuelRate = 1;
        int lower = Math.min(currentPosition, basePosition);
        int upper = Math.max(currentPosition, basePosition);
        int consumption = 0;
        for (int i = lower; i < upper; i++) {
            consumption += fuelRate;
            fuelRate++;
        }
        return consumption;
    }

    private int cheapestAlignmentByCheckingAll(int[] integers) {
        int cheapest = Integer.MAX_VALUE;
        List<Integer> seenIntegers = new ArrayList<>();

        for (int position :
                integers) {
            if (!seenIntegers.contains(position)) {
                seenIntegers.add(position);

                int alignmentValue = alignmentComparedTo(integers, position);
                if (alignmentValue <= cheapest) {
                    cheapest = alignmentValue;
                }
            }
        }

        return cheapest;
    }

    private int cheapestAlignmentByCheckingAll_part2(int[] integers) {
        int cheapest = Integer.MAX_VALUE;
        List<Integer> seenIntegers = new ArrayList<>();

        int max_position = Arrays.stream(integers).max().getAsInt();
        for (int position = 0; position < max_position; position++) {
            if (!seenIntegers.contains(position)) {
                seenIntegers.add(position);

                int alignmentValue = alignmentWithIncreasingRate(integers, position);
                if (alignmentValue <= cheapest) {
                    cheapest = alignmentValue;
                }
            }
        }

        return cheapest;
    }

    private int alignmentWithIncreasingRate(int[] integers, int basePosition) {
        int sum = 0;
        for (int position :
                integers) {
            int difference = calculateFuelFor(position, basePosition);
            sum += difference;
        }
        return sum;
    }

    private int alignmentComparedTo(int[] integers, int basePosition) {
        int sum = 0;
        for (int position :
                integers) {
            int difference = Math.abs(basePosition - position);
            sum += difference;
        }
        return sum;
    }
}
