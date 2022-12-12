package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Day10 {

    @Test
    void part1_example_simple() {
        List<String> input = new ArrayList<>();
        input.add("noop");
        input.add("addx 3");
        input.add("addx -5");

        CPU sut = new CPU(input);
        sut.execute();

        Assertions.assertEquals(-1, sut.register_x);

    }


    private class CPU {
        private final List<String> input;
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

                cycle++;
                if ("noop".equals(inputLine)) {
                    currentInputLine++;
                } else {
                    // assuming addx
                    cycle++;

                    String[] split = inputLine.split(" ");

                    int amount = Integer.parseInt(split[1]);
                    register_x += amount;

                    currentInputLine++;
                }
            }
        }
    }
}
