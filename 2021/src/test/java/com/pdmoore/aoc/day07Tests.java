package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day07Tests {

    final String example_input = "16,1,2,0,4,2,7,1,2,14";

    // Calc difference between mode and position
    // Sum all differences

    @Test
    void day06_part1_example_findMode() {
        int[] horizontalPositions = processInput(example_input);

        int mode = findModeOf(horizontalPositions);

        assertEquals(2, mode);
    }

    @Test
    void day06_part01_example() {
        int[] horizontalPositions = processInput(example_input);
        int actual = cheapestAlignmentByCheckingAll(horizontalPositions);
        assertEquals(37, actual);
    }

    @Test
    void day06_part01_solution() {
        int[] horizontalPositions = processInput(PuzzleInput.asStringFrom("data/day07"));
        int actual = cheapestAlignmentByCheckingAll(horizontalPositions);

        assertEquals(364898, actual);
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

    private int cheapestAlignmentViaMode(int[] integers) {
        int mode = findModeOf(integers);

        return alignmentComparedTo(integers, mode);
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

    class Mode
    {
        public int Value;
        public int Count;
    }

    private int findModeOf(int[] integers) {
        Mode best = new Mode();
        Map<Integer, Integer> hashtable =
                new HashMap<>();

        for (int value :
                integers) {
            int curCount;
            int count = 1;
            if (hashtable.containsKey(value)) {
                curCount = hashtable.get(value);
                count = curCount + 1;
                hashtable.put(value, count);
            } else {
                hashtable.put(value, 1);
            }

            if (count > best.Count)
            {
                best.Value = value;
                best.Count = count;
            }
        }

        return best.Value;
    }

    private int[] processInput(String input) {
        return Arrays.stream(
                input.split(",")).
                mapToInt(Integer::parseInt).toArray();
    }

}
