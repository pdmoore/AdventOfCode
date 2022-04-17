package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class Day06Tests {

    @Test
    void processTurnOnCommand() {
        Grid grid = new Grid();

        grid.processInstruction("turn on 499,499 through 500,500");

        Assertions.assertEquals(4, grid.litCount());
    }

    @Test
    void processTurnOffCommand() {
        Grid grid = new Grid();
        grid.processInstruction("turn on 0,0 through 4,4");

        grid.processInstruction("turn off 0,0 through 1,1");

        Assertions.assertEquals(21, grid.litCount());
    }

    @Test
    void processToggleCommand() {
        Grid grid = new Grid();
        grid.processInstruction("toggle 0,0 through 4,4");
        Assertions.assertEquals(25, grid.litCount());

        grid.processInstruction("toggle 0,0 through 4,4");
        Assertions.assertEquals(0, grid.litCount());
    }

    @Test
    void processMultipleCommands() {
        List<String> instructions = new ArrayList<>();
        instructions.add("turn on 0,0 through 4,4");
        instructions.add("turn off 0,0 through 1,1");
        Grid grid = new Grid();

        grid.process(instructions);

        Assertions.assertEquals(21, grid.litCount());
    }

    @Test
    void part1_solution() {
        List<String> instructions = PuzzleInput.asStringListFrom("data/day06");

        Grid grid = new Grid();
        grid.process(instructions);

        Assertions.assertEquals(377891, grid.litCount());
    }

    @Test
    void part2_turnOn_increasesBrightness() {
        Grid grid = new Grid();

        grid.processInstruction("turn on 0,0 through 0,0");

        Assertions.assertEquals(BigInteger.ONE, grid.totalBrightness());
    }

    @Test
    void part2_toggle_increasesBrightness() {
        Grid grid = new Grid();

        grid.processInstruction("toggle 0,0 through 999,999");

        Assertions.assertEquals(BigInteger.valueOf(2_000_000), grid.totalBrightness());
    }

    @Test
    void part2_TurnOffCommand_DecreasesBrightness() {
        Grid grid = new Grid();
        grid.processInstruction("turn on 0,0 through 0,0");
        Assertions.assertEquals(BigInteger.ONE, grid.totalBrightness());

        grid.processInstruction("turn on 0,0 through 0,0");
        Assertions.assertEquals(BigInteger.TWO, grid.totalBrightness());

        grid.processInstruction("turn on 0,0 through 0,0");
        grid.processInstruction("turn off 0,0 through 0,0");

        Assertions.assertEquals(BigInteger.TWO, grid.totalBrightness());
    }


    @Test
    void part2_solution() {
        List<String> instructions = PuzzleInput.asStringListFrom("data/day06");

        Grid grid = new Grid();
        grid.process(instructions);

        Assertions.assertEquals(BigInteger.valueOf(14110788), grid.totalBrightness());
    }


    static class Grid {

        boolean[][] lights;
        int[][] brightness;

        public Grid() {
            this.lights = new boolean[1000][1000];
            this.brightness = new int[1000][1000];
        }

        public int litCount() {
            //TODO convert to stream
            int litCount = 0;
            for (int x = 0; x <= lights.length - 1; x++) {
                for (int y = 0; y <= lights[0].length - 1; y++) {
                    if (lights[x][y]) litCount++;
                }
            }
            return litCount;
        }

        public BigInteger totalBrightness() {
            //TODO convert to stream
            BigInteger totalBrightness = BigInteger.ZERO;
            for (int x = 0; x <= lights.length - 1; x++) {
                for (int y = 0; y <= lights[0].length - 1; y++) {
                    totalBrightness = totalBrightness.add(BigInteger.valueOf(brightness[x][y]));
                }
            }
            return totalBrightness;
        }

        public void processInstruction(String instruction) {

            String[] tokens = instruction.split(" ");

            if ("toggle".equals(tokens[0])) {
                String[] upperLeftPair = tokens[1].split(",");
                String[] lowerRightPair = tokens[3].split(",");

                int upperLeftX = Integer.parseInt(upperLeftPair[0]);
                int upperLeftY = Integer.parseInt(upperLeftPair[1]);
                int lowerRightX = Integer.parseInt(lowerRightPair[0]);
                int lowerRightY = Integer.parseInt(lowerRightPair[1]);
                for (int x = upperLeftX; x <= lowerRightX; x++) {
                    for (int y = upperLeftY; y <= lowerRightY; y++) {
                        boolean currentValue = lights[x][y];
                        lights[x][y] = !currentValue;
                        brightness[x][y] += 2;
                    }
                }

            } else {

                boolean lightValue = true;
                int brightnessDelta = 1;
                if ("on".equals(tokens[1]) || ("off".equals(tokens[1]))) {
                    if ("off".equals(tokens[1])) {
                        lightValue = false;
                        brightnessDelta = -1;
                    }
                    String[] upperLeftPair = tokens[2].split(",");
                    String[] lowerRightPair = tokens[4].split(",");

                    int upperLeftX = Integer.parseInt(upperLeftPair[0]);
                    int upperLeftY = Integer.parseInt(upperLeftPair[1]);
                    int lowerRightX = Integer.parseInt(lowerRightPair[0]);
                    int lowerRightY = Integer.parseInt(lowerRightPair[1]);
                    for (int x = upperLeftX; x <= lowerRightX; x++) {
                        for (int y = upperLeftY; y <= lowerRightY; y++) {
                            lights[x][y] = lightValue;

                            int newValue = brightness[x][y] + brightnessDelta;
                            if (newValue < 0) {
                                newValue = 0;
                            }
                            brightness[x][y] = newValue;
                        }
                    }
                } else {
                    System.out.println("Unknown command " + instruction);
                    System.exit(-1);
                }
            }

        }

        public void process(List<String> instructions) {
            for (String instruction :
                    instructions) {
                processInstruction(instruction);
            }
        }
    }
}
