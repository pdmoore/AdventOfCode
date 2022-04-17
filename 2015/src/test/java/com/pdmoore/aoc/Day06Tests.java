package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.List;

public class Day06Tests {

    // data structure
    // naive - create 1000,1000 array of boolean
    // or - store grid point and state of light in a map
    // or - use a packed bit array
    // parse a line - turn on
    // parse a line - turn off
    // parse a line - toggle
    // process a single line input
    // process >1 line of input
    // count number of lit lights
    // read file and process all input

    /*
    examples
- turn on 0,0 through 999,999
would turn on (or leave on) every light.
- toggle 0,0 through 999,0
would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.
- turn off 499,499 through 500,500
would turn off (or leave off) the middle four lights.
     */
    @Test
    public void processTurnOnCommand() {
        Grid grid = new Grid();

        grid.processInstruction("turn on 499,499 through 500,500");

        Assertions.assertEquals(4, grid.litCount());
    }

    @Test
    public void processTurnOffCommand() {
        Grid grid = new Grid();
        grid.processInstruction("turn on 0,0 through 4,4");

        grid.processInstruction("turn off 0,0 through 1,1");

        Assertions.assertEquals(21, grid.litCount());
    }

    @Test
    public void processToggleCommand() {
        Grid grid = new Grid();
        grid.processInstruction("toggle 0,0 through 4,4");
        Assertions.assertEquals(25, grid.litCount());

        grid.processInstruction("toggle 0,0 through 4,4");
        Assertions.assertEquals(0, grid.litCount());
    }

    @Test
    public void processMultipleCommands() {
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


    class Grid {

        boolean[][] lights;

        public Grid() {
            this.lights = new boolean[1000][1000];
        }

        public int litCount() {
            int litCount = 0;
            for (int x = 0; x <= lights.length - 1; x++) {
                for (int y = 0; y <= lights[0].length - 1; y++) {
                    if (lights[x][y]) litCount++;
                }
            }
            return litCount;
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
                    }
                }

            } else {

                boolean lightValue = true;
                if ("on".equals(tokens[1]) || ("off".equals(tokens[1]))) {
                    if ("off".equals(tokens[1])) {
                        lightValue = false;
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
