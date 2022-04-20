package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
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
    void and_lhs_is_a_number() {
        List<String> input = Arrays.asList("456 -> y", "1 AND y -> d");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("d");

        assertEquals(0, actual);
    }

    @Test
    void or() {
        List<String> input = Arrays.asList("123 -> x", "456 -> y", "x OR y -> e");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("e");

        assertEquals(507, actual);
    }

    @Test
    void simpleSubstition() {
        List<String> input = Arrays.asList("lx -> a", "456 -> lx");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("a");

        assertEquals(456, actual);
    }

    @Test
    void part1_simpleCircuitExample() {
        Day7Thing sut = new Day7Thing(simpleCircuitInput());

        int actual = sut.valueOf("e");

        assertAll(
                () -> assertEquals(72, sut.valueOf("d")),
                () -> assertEquals(507, sut.valueOf("e")),
                () -> assertEquals(492, sut.valueOf("f")),
                () -> assertEquals(114, sut.valueOf("g")),
                () -> assertEquals(65412, sut.valueOf("h")),
                () -> assertEquals(65079, sut.valueOf("i")),
                () -> assertEquals(123, sut.valueOf("x")),
                () -> assertEquals(456, sut.valueOf("y"))
        );
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day07");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("a");

        assertEquals(3176, actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day07_part2");
        Day7Thing sut = new Day7Thing(input);

        int actual = sut.valueOf("a");

        assertEquals(14710, actual);
    }

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
                if (isANumber(tokens[0])) {
                    int value = Integer.parseInt(tokens[0]);
                    solved.put(tokens[1], value);
                } else {
                    unsolved.put(tokens[1], tokens[0]);
                }
            } else {
                unsolved.put(tokens[1], tokens[0]);
            }
        }

        private void solveUnsolved() {
            while (!unsolved.isEmpty()) {
                int numKeysBefore = unsolved.keySet().size();

                Set<String> keys = new HashSet<>(unsolved.keySet());
                for (String key :
                        keys) {

                    String expression = unsolved.get(key);
                    String[] tokens = expression.split(" ");

                    if (tokens.length == 1) {
                        if (hasBeenEvaluated(expression)) {
                            solved.put(key, solved.get(expression));
                            unsolved.remove(key);
                        }
                    } else if (expression.contains("NOT")) {
                        if (hasBeenEvaluated(tokens[1])) {

                            int value = solved.get(tokens[1]);

                            // is this the best way to do not? it passes the examples
                            // TODO - try cast to 'short' which should be 16 bit, or char which would be unsigned
                            int newValue = 65535 - value;
                            solved.put(key, newValue);

                            unsolved.remove(key);
                        }
                    } else if (expression.contains("LSHIFT")) {
                        String[] operands = expression.split(" LSHIFT ");
                        if (hasBeenEvaluated(operands[0])) {
                            int lhs = solved.get(operands[0]);
                            int shiftBy = Integer.parseInt(operands[1]);

                            int unsignedShiftResult = lhs << shiftBy;
                            solved.put(key, unsignedShiftResult);

                            unsolved.remove(key);
                        }
                    } else if (expression.contains("RSHIFT")) {
                        String[] operands = expression.split(" RSHIFT ");
                        if (hasBeenEvaluated(operands[0])) {

                            int lhs = solved.get(operands[0]);
                            int shiftBy = Integer.parseInt(operands[1]);

                            int unsignedShiftResult = lhs >>> shiftBy;
                            solved.put(key, unsignedShiftResult);

                            unsolved.remove(key);
                        }
                    } else if (expression.contains("AND")) {
                        String[] operands = expression.split(" AND ");
                        if (hasBeenEvaluated(operands[0]) && hasBeenEvaluated(operands[1])) {

                            int lhs;
                            if (isANumber(operands[0])) {
                                lhs = Integer.parseInt(operands[0]);
                            } else {
                                lhs = solved.get(operands[0]);
                            }

                            int rhs = solved.get(operands[1]);

                            int andedValue = lhs & rhs;
                            solved.put(key, andedValue);

                            unsolved.remove(key);
                        }
                    } else if (expression.contains("OR")) {
                        String[] operands = expression.split(" OR ");

                        if (hasBeenEvaluated(operands[0]) && hasBeenEvaluated(operands[1])) {

                            int lhs = solved.get(operands[0]);
                            int rhs = solved.get(operands[1]);

                            int oredValue = lhs | rhs;
                            solved.put(key, oredValue);

                            unsolved.remove(key);
                        }
                    } else {
                        System.out.println("Unknown expression: " + expression);
                        System.exit(-1);
                    }

                }

                if (numKeysBefore == unsolved.keySet().size()) {
                    System.out.println("Got stuck - expressions unsolved: " + numKeysBefore);
                    dumpSolved();
                    dumpUnsolved();
                    System.exit(-1);
                }

            }
        }

        private void dumpSolved() {
            for (String key :
                    solved.keySet()) {
                System.out.println(key + ": " + solved.get(key));
            }
        }

        private void dumpUnsolved() {
            for (String key :
                    unsolved.keySet()) {
                System.out.println(unsolved.get(key) + " -> " + key);
            }
        }

        private boolean isANumber(String couldBeAnotherWire) {
            return couldBeAnotherWire.matches("[0-9]+$");
        }

        private boolean hasBeenEvaluated(String key) {
            return isANumber(key) || solved.containsKey(key);
        }

        public int valueOf(String key) {
            return solved.get(key);
        }
    }
}
