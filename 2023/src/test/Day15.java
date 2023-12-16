package com.pdmoore.aoc;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    void part1_solution_convert_input_to_steps() {
        String input = PuzzleInput.asStringFrom("./data/day15");

        List<String> inputLines = getStepsFrom(input);

        assertEquals(4000, inputLines.size());
    }

    @Test
    void part1_example() {
        String input = PuzzleInput.asStringFrom("./data/day15_part1_example");

        int actual = part1_sumAllSteps(input);

        assertEquals(1320, actual);
    }

    @Test
    void part1_solution() {
        String input = PuzzleInput.asStringFrom("./data/day15");

        int actual = part1_sumAllSteps(input);

        assertEquals(505379, actual);
    }

    @Test
    void part2_example() {
        String input = PuzzleInput.asStringFrom("./data/day15_part1_example");
        int actual = getFocusingPower(input);
        assertEquals(145, actual);
    }

    private int getFocusingPower(String input) {
        HashMap<Integer, List<String>> boxes = fillBoxes(input);

        int result = 0;
        for (Integer boxKey :
                boxes.keySet()) {
            List<String> box_contents = boxes.get(boxKey);
            result += calcFocusingPowerOf(box_contents, boxKey + 1);
        }

        return result;
    }

    private HashMap<Integer, List<String>> fillBoxes(String input) {
        List<String> stepsFrom = getStepsFrom(input);

        HashMap<Integer, List<String>> boxes = new HashMap<>();
        for (String step :
                stepsFrom) {

            // TODO - boxNumber is 30 for the first thing
            // but the example wants it in box 0
            // does it mean just put in the first available empty box?
            // Need to read instructions more carefully
            int boxNumber = currentValueOf(step);

            // check for = or -
            // remove, replace, or add to box contents
            // shift empty boxes forward per instructions (example may not require this)

            if (step.contains("=")) {
                List<String> lenses = boxes.get(boxNumber);
                if (lenses == null) {
                    lenses = new ArrayList<>();
                }
                lenses.add(step);
                boxes.put(boxNumber, lenses);
            } else if (step.contains("-")){



            } else {
                throw new IllegalArgumentException("couldn't find = or - in " + step);
            }

        }

        List<String> box0_contents = Arrays.asList("rn=1", "cm=2");
        boxes.put(0, box0_contents);
        List<String> box3_contents = Arrays.asList("ot=7", "ab=5", "pc=6");
        boxes.put(3, box3_contents);

        return boxes;
    }

    private int calcFocusingPowerOf(List<String> boxContents, int boxNumber) {
        if (boxContents.isEmpty()) return 0;

        int result = 0;
        for (int i = 0; i < boxContents.size(); i++) {
            String lens = boxContents.get(i);
            int focalLength = Integer.parseInt(lens.split("=")[1]);
            int focusingPower = boxNumber * (i + 1) * focalLength;
            result += focusingPower;
        }

        return result;
    }


    private int part1_sumAllSteps(String input) {
        List<String> stepsFrom = getStepsFrom(input);

        int result = 0;
        for (String step :
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
            int ascii = (int) c;
            currentValue += ascii;
            currentValue *= 17;
            currentValue %= 256;
        }

        return currentValue;
    }
}
