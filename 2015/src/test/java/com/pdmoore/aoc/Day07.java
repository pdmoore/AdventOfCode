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

    @Test
    void lshift() {
        List<String> input = Arrays.asList("123 -> x", "x LSHIFT 2 -> f");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("f");

        assertEquals(492, actual);
    }

    @Test
    void rshift() {
        List<String> input = Arrays.asList("456 -> y", "y RSHIFT 2 -> g");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("g");

        assertEquals(114, actual);
    }

    @Test
    void and() {
        List<String> input = Arrays.asList("123 -> x", "456 -> y", "x AND y -> d");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("d");

        assertEquals(72, actual);
    }

    @Test
    void or() {
        List<String> input = Arrays.asList("123 -> x", "456 -> y", "x OR y -> e");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("e");

        assertEquals(507, actual);
    }


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

            // need to wrap in a while...do until all are solved

            for (String key:
                    unsolved.keySet()) {

                String expression = unsolved.get(key);
                String[] tokens = expression.split(" ");

                if (expression.contains("NOT")) {
                    // Also assumes it can be solved in this pass - need to confirm solved.get returns something
                    int value = solved.get(tokens[1]);

                    // is this the best way to do not? it passes the examples
                    // TODO - try cast to 'short' which should be 16 bit, or char which would be unsigned
                    int newValue = 65535 - value;
                    solved.put(key, newValue);

                    unsolved.remove(key);
                } else if (expression.contains("LSHIFT")) {
                    String[] operands = expression.split(" LSHIFT ");
                    int lhs = solved.get(operands[0]);
                    int shiftBy = Integer.parseInt(operands[1]);

                    int unsignedShiftResult = lhs << shiftBy;
                    solved.put(key, unsignedShiftResult);

                    unsolved.remove(key);
                } else if (expression.contains("RSHIFT")) {
                    String[] operands = expression.split(" RSHIFT ");
                    int lhs = solved.get(operands[0]);
                    int shiftBy = Integer.parseInt(operands[1]);

                    int unsignedShiftResult = lhs >>> shiftBy;
                    solved.put(key, unsignedShiftResult);

                    unsolved.remove(key);
                } else if (expression.contains("AND")) {
                    String[] operands = expression.split(" AND ");

                    int lhs = solved.get(operands[0]);
                    int rhs = solved.get(operands[1]);

                    int andedValue = lhs & rhs;
                    solved.put(key, andedValue);

                    unsolved.remove(key);
                } else if (expression.contains("OR")) {
                    String[] operands = expression.split(" OR ");

                    int lhs = solved.get(operands[0]);
                    int rhs = solved.get(operands[1]);

                    int oredValue = lhs | rhs;
                    solved.put(key, oredValue);

                    unsolved.remove(key);
                }
                else {
                    System.out.println("Uknown expression: " + expression);
                    System.exit(-1);
                }

            }


        }

        public int valueOf(String key) {
            return solved.get(key);
        }
    }
}
