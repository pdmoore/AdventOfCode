package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07Tests {

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
        CircuitEmulator sut = new CircuitEmulator(input);

        int actual = sut.valueOf("x");

        assertEquals(123, actual);
    }

    @Test
    void not() {
        List<String> input = Arrays.asList("123 -> x", "NOT x -> h");
        CircuitEmulator sut = new CircuitEmulator(input);

        int actual = sut.valueOf("h");

        assertEquals(65412, actual);
    }

    @Test
    void lshift() {
        List<String> input = Arrays.asList("123 -> x", "x LSHIFT 2 -> f");
        CircuitEmulator sut = new CircuitEmulator(input);

        int actual = sut.valueOf("f");

        assertEquals(492, actual);
    }

    @Test
    void rshift() {
        List<String> input = Arrays.asList("456 -> y", "y RSHIFT 2 -> g");
        CircuitEmulator sut = new CircuitEmulator(input);

        int actual = sut.valueOf("g");

        assertEquals(114, actual);
    }

    @Test
    void and() {
        List<String> input = Arrays.asList("123 -> x", "456 -> y", "x AND y -> d");
        CircuitEmulator sut = new CircuitEmulator(input);

        int actual = sut.valueOf("d");

        assertEquals(72, actual);
    }

    @Test
    void and_lhs_is_a_number() {
        List<String> input = Arrays.asList("456 -> y", "1 AND y -> d");
        CircuitEmulator sut = new CircuitEmulator(input);

        int actual = sut.valueOf("d");

        assertEquals(0, actual);
    }

    @Test
    void or() {
        List<String> input = Arrays.asList("123 -> x", "456 -> y", "x OR y -> e");
        CircuitEmulator sut = new CircuitEmulator(input);

        int actual = sut.valueOf("e");

        assertEquals(507, actual);
    }

    @Test
    void simpleSubstition() {
        List<String> input = Arrays.asList("lx -> a", "456 -> lx");
        CircuitEmulator sut = new CircuitEmulator(input);

        int actual = sut.valueOf("a");

        assertEquals(456, actual);
    }

    @Test
    void part1_simpleCircuitExample() {
        CircuitEmulator sut = new CircuitEmulator(simpleCircuitInput());

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
        CircuitEmulator sut = new CircuitEmulator(input);

        int actual = sut.valueOf("a");

        assertEquals(3176, actual);
    }

    @Test
    void part2_solution() {
        // Now, take the signal you got on wire a from Part 1, override wire b to that signal, and reset the other
        // wires (including wire a).
        List<String> input = PuzzleInput.asStringListFrom("data/day07");
        input.remove("44430 -> b");
        input.add("3176 -> b");

        CircuitEmulator sut = new CircuitEmulator(input);

        int actual = sut.valueOf("a");

        assertEquals(14710, actual);
    }

    private class CircuitEmulator {
        Map<String, Integer> solved = new HashMap<>();
        Map<String, String> unsolved = new HashMap<>();

        public CircuitEmulator(List<String> input) {
            input.stream().forEach(this::processInputLine);
            solveUnsolved();
        }

        public void processInputLine(String input) {
            String[] tokens = input.split(" -> ");
            String wire = tokens[1];
            String signal = tokens[0];

            if (isANumber(signal)) {
                solved.put(wire, Integer.parseInt(signal));
            } else {
                unsolved.put(wire, signal);
            }
        }

        private void solveUnsolved() {
            while (!unsolved.isEmpty()) {

                Set<String> unsolvedWires = new HashSet<>(unsolved.keySet());
                for (String key :
                        unsolvedWires) {

                    String signal = unsolved.get(key);
                    String[] tokens = signal.split(" ");

                    solveIfPossible(key, signal, tokens);
                }
            }
        }

        private void solveIfPossible(String key, String signal, String[] tokens) {
            if (tokens.length == 1) {
                if (isASpecificValue(signal)) {
                    solved.put(key, solved.get(signal));
                    unsolved.remove(key);
                }
            } else if (signal.contains("NOT")) {
                if (isASpecificValue(tokens[1])) {

                    // is this the best way to do not? it passes the examples
                    // TODO - try cast to 'short' which should be 16 bit, or char which would be unsigned
                    int bitwiseComplement = 65535 - solved.get(tokens[1]);
                    solved.put(key, bitwiseComplement);

                    unsolved.remove(key);
                }
            } else if (signal.contains("LSHIFT")) {
                // TODO shifts are the same except for the operation
                // combine them
                // use a regex to ignore the L|R that starts the SHIFT
                String[] operands = signal.split(" LSHIFT ");
                if (isASpecificValue(operands[0])) {
                    int lhs = solved.get(operands[0]);
                    int shiftBy = Integer.parseInt(operands[1]);

                    int unsignedShiftResult = lhs << shiftBy;
                    solved.put(key, unsignedShiftResult);

                    unsolved.remove(key);
                }
            } else if (signal.contains("RSHIFT")) {
                String[] operands = signal.split(" RSHIFT ");
                if (isASpecificValue(operands[0])) {

                    int lhs = solved.get(operands[0]);
                    int shiftBy = Integer.parseInt(operands[1]);

                    int unsignedShiftResult = lhs >>> shiftBy;
                    solved.put(key, unsignedShiftResult);

                    unsolved.remove(key);
                }
            } else if (signal.contains("AND")) {
                String[] operands = signal.split(" AND ");
                if (isASpecificValue(operands[0]) && isASpecificValue(operands[1])) {

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
            } else if (signal.contains("OR")) {
                String[] operands = signal.split(" OR ");

                if (isASpecificValue(operands[0]) && isASpecificValue(operands[1])) {

                    int lhs = solved.get(operands[0]);
                    int rhs = solved.get(operands[1]);

                    int oredValue = lhs | rhs;
                    solved.put(key, oredValue);

                    unsolved.remove(key);
                }
            } else {
                System.out.println("Unknown expression: " + signal);
                System.exit(-1);
            }
        }

        private void dumpSolved() {
            solved.keySet().forEach(key -> System.out.println(key + ": " + solved.get(key)));
        }

        private void dumpUnsolved() {
            unsolved.keySet().forEach(key -> System.out.println(key + ": " + unsolved.get(key)));
        }

        private boolean isANumber(String expressionElement) {
            return !expressionElement.contains(" ") && expressionElement.matches("[0-9]+$");
        }

        private boolean isASpecificValue(String key) {
            return isANumber(key) || solved.containsKey(key);
        }

        public int valueOf(String key) {
            return solved.get(key);
        }
    }
}
