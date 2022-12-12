package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10 {

    @Test
    void part1_example_simple() {
        List<String> input = new ArrayList<>();
        input.add("noop");
        input.add("addx 3");
        input.add("addx -5");

        CPU sut = new CPU(input);
        sut.execute();

        assertEquals(-1, sut.register_x);
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day10_example");

        CPU sut = new CPU(input);
        sut.execute();

        assertEquals(13140, sut.signalStrenthSum);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day10");

        CPU sut = new CPU(input);
        sut.execute();

        assertEquals(12520, sut.signalStrenthSum);
    }


    private class CPU {
        private final List<String> input;
        public int signalStrenthSum;
        private int cycle;
        public int register_x;

        public CPU(List<String> input) {
            this.input = input;
            register_x = 1;
            cycle = 0;
        }

        public void execute() {

            int currentInputLine = 0;
            while (currentInputLine < input.size()) {

                String inputLine = input.get(currentInputLine);

                cycleIncrement();
                if ("noop".equals(inputLine)) {
                    currentInputLine++;
                } else {
                    // assuming addx
                    cycleIncrement();

                    String[] split = inputLine.split(" ");

                    int amount = Integer.parseInt(split[1]);
                    register_x += amount;

                    currentInputLine++;
                }
            }
        }

        private void cycleIncrement() {
            cycle++;

            if (cycle == 20) {
                signalStrenthSum += 20 * register_x;
            } else if (cycle == 60) {
                signalStrenthSum += 60 * register_x;
            } else if (cycle == 100) {
                signalStrenthSum += 100 * register_x;
            } else if (cycle == 140) {
                signalStrenthSum += 140 * register_x;
            } else if (cycle == 180) {
                signalStrenthSum += 180 * register_x;
            } else if (cycle == 220) {
                signalStrenthSum += 220 * register_x;
            }

        }
    }
}
