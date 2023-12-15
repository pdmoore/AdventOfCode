package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15 {

    @Test
    void example_current_value_HASH() {
        String input = "HASH";

        int actual = currentValueOf(input);

        assertEquals(52, actual);
    }

    // comma separated list of steps - IGNORE new lines!
    // get currentValue of each step
    // sum those results
    @Test
    void part1_example_convert_input_to_steps() {
        String input = PuzzleInput.asStringFrom("./data/day15_part1_example");

        List<String> inputLines = getStepsFrom(input);

        assertEquals(11, inputLines.size());
    }

    @Test
    void part1_example() {
        String input = PuzzleInput.asStringFrom("./data/day15_part1_example");

        int actual = part1_sumAllSteps(input);

        assertEquals(1320, actual);
    }

    private int part1_sumAllSteps(String input) {
        List<String> stepsFrom = getStepsFrom(input);

        int result = 0;
        for (String step:
        stepsFrom) {
            result += currentValueOf(step);
        }

        return result;
    }

    private List<String> getStepsFrom(String input) {
        String[] split = input.split(",");
        return Arrays.asList(split);
    }


    private int currentValueOf(String input) {
        int currentValue = 0;

        for (char c : input.toCharArray()) {
            int ascii = (int)c;
            currentValue += ascii;
            currentValue *= 17;
            currentValue %= 256;
        }

        return currentValue;
    }
}
