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

            if (unsolved.isEmpty()) return;

            String expression = unsolved.get("h");
            String[] tokens = expression.split(" ");

            //check expression type
            // assuming NOT here
            int value = solved.get(tokens[1]);

            int newValue = 65535 - value;
            solved.put("h", newValue);


            unsolved.remove("h");
        }

        public int valueOf(String key) {
            return solved.get(key);
        }
    }
}
