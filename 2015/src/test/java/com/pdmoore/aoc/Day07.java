package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07 {

    List<String> simpleCircuitInput() {
        return Arrays.asList(
                "123 -> x",
                "456 -> y",
                "x AND y -> d",
                "x OR y -> e",
                "x LSHIFT 2 -> f",
                "y RSHIFT 2 -> g",
                "NOT x -> h",
                "NOT y -> i"
        );
    }

    @Test
    void signalToWire() {
        List<String> input = Arrays.asList("123 -> x");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("x");

        assertEquals(123, actual);
    }

    @Test
    void not() {
        List<String> input = Arrays.asList("123 -> x", "NOT x -> h");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("h");

        assertEquals(65412, actual);
    }

    // LSHIFT
    // RSHIFT
    // NOT
    // AND (2 values)
    // OR (2 values)
    // multiple passes to reduce unsolved to solved


    private class Day7Thing {
        Map<String, Integer> solved = new HashMap<>();
        Map<String, String> unsolved = new HashMap<>();

        public Day7Thing(List<String> input) {
            for (String inputLine :
                    input) {
                processInputLine(inputLine);
            }

            solveUnsolved();
        }

        public void processInputLine(String input) {
            String[] tokens = input.split(" -> ");

            if (!tokens[0].contains(" ")) {
                int value = Integer.parseInt(tokens[0]);
                solved.put(tokens[1], value);
            } else {
                unsolved.put(tokens[1], tokens[0]);
            }
        }

        private void solveUnsolved() {

            // might need to wrap in a while...do until all are solved

            for (String key:
                    unsolved.keySet()) {

                String expression = unsolved.get(key);
                String[] tokens = expression.split(" ");

                //check expression type
                // assuming NOT here
                // Also assumes it can be solved in this pass - need to confirm solved.get returns something
                int value = solved.get(tokens[1]);

                // is this the best way to do not? it passes the examples
                int newValue = 65535 - value;
                solved.put(key, newValue);

                unsolved.remove(key);
            }


        }

        public int valueOf(String key) {
            return solved.get(key);
        }
    }
}
