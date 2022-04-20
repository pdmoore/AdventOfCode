package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
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
        String input = "123 -> x";
        
        Day7Thing sut = new Day7Thing();
        sut.processInputLine(input);
        
        int actual = sut.valueOf("x");
        
        assertEquals(123, actual);
    }

    private class Day7Thing {
        Map<String, Integer> solved = new HashMap<>();

        public void processInputLine(String input) {
            String[] tokens = input.split(" -> ");
            int value = Integer.parseInt(tokens[0]);
            solved.put(tokens[1], value);
        }

        public int valueOf(String key) {
            return solved.get(key);
        }
    }
}
