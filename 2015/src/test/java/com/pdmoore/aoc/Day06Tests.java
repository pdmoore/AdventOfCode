package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day06Tests {

    @Test
    void processTurnOnCommand() {
        Grid grid = new Grid();

        grid.processInstruction("turn on 499,499 through 500,500");

        assertEquals(4, grid.litCount());
    }

    @Test
    void processTurnOffCommand() {
        Grid grid = new Grid();
        grid.processInstruction("turn on 0,0 through 4,4");

        grid.processInstruction("turn off 0,0 through 1,1");

        assertEquals(21, grid.litCount());
    }

    @Test
    void processToggleCommand() {
        Grid grid = new Grid();
        grid.processInstruction("toggle 0,0 through 4,4");
        assertEquals(25, grid.litCount());

        grid.processInstruction("toggle 0,0 through 4,4");
        assertEquals(0, grid.litCount());
    }

    @Test
    void processMultipleCommands() {
        List<String> instructions = new ArrayList<>();
        instructions.add("turn on 0,0 through 4,4");
        instructions.add("turn off 0,0 through 1,1");
        Grid grid = new Grid();

        grid.process(instructions);

        assertEquals(21, grid.litCount());
    }

    @Test
    void part1_solution() {
        List<String> instructions = PuzzleInput.asStringListFrom("data/day06");

        Grid grid = new Grid();
        grid.process(instructions);

        assertEquals(377_891, grid.litCount());
    }

    @Test
    void part2_turnOn_increasesBrightness() {
        Grid grid = new Grid();

        grid.processInstruction("turn on 0,0 through 0,0");

        assertEquals(1, grid.totalBrightness());
    }

    @Test
    void part2_toggle_increasesBrightness() {
        Grid grid = new Grid();

        grid.processInstruction("toggle 0,0 through 999,999");

        assertEquals(2_000_000, grid.totalBrightness());
    }

    @Test
    void part2_TurnOffCommand_DecreasesBrightness() {
        Grid grid = new Grid();
        grid.processInstruction("turn on 0,0 through 0,0");
        assertEquals(1, grid.totalBrightness());

        grid.processInstruction("turn on 0,0 through 0,0");
        assertEquals(2, grid.totalBrightness());

        grid.processInstruction("turn on 0,0 through 0,0");
        grid.processInstruction("turn off 0,0 through 0,0");

        assertEquals(2, grid.totalBrightness());
    }

    @Test
    void part2_solution() {
        List<String> instructions = PuzzleInput.asStringListFrom("data/day06");

        Grid grid = new Grid();
        grid.process(instructions);

        assertEquals(14_110_788, grid.totalBrightness());
    }

    static class Grid {
        public static final int GRID_SIZE = 1000;
        int[][] isLit;
        int[][] brightness;

        public Grid() {
            this.isLit = new int[GRID_SIZE][GRID_SIZE];
            this.brightness = new int[GRID_SIZE][GRID_SIZE];
        }

        public int litCount() {
            return sumNonZeroGridElements(isLit);
        }

        public int totalBrightness() {
            return sumNonZeroGridElements(brightness);
        }

        public void process(List<String> instructions) {
            for (String instruction :
                    instructions) {
                processInstruction(instruction);
            }
        }

        public void processInstruction(String instruction) {
            create(instruction).performAction();
        }

        private int sumNonZeroGridElements(int[][] isLit) {
            return Arrays.stream(isLit)
                    .flatMapToInt(Arrays::stream)
                    .reduce(0, Integer::sum);
        }

        private LightAction create(String instruction) {
            String[] tokens = instruction.split(" ");

            if ("toggle".equals(tokens[0])) {
                String[] upperLeftPair = tokens[1].split(",");
                String[] lowerRightPair = tokens[3].split(",");
                return new ToggleLightAction(upperLeftPair, lowerRightPair);
            } else if ("on".equals(tokens[1]) || ("off".equals(tokens[1]))) {
                String[] upperLeftPair = tokens[2].split(",");
                String[] lowerRightPair = tokens[4].split(",");

                if ("on".equals(tokens[1])) {
                    return new TurnOnLightAction(upperLeftPair, lowerRightPair);
                } else if ("off".equals(tokens[1])) {
                    return new TurnOffLightAction(upperLeftPair, lowerRightPair);
                }
            }

            System.out.println("Unknown command " + instruction);
            System.exit(-1);
            return null;
        }

        private abstract class LightAction {
            protected final int upperLeftX;
            protected final int upperLeftY;
            protected final int lowerRightX;
            protected final int lowerRightY;

            public LightAction(String[] upperLeftPair, String[] lowerRightPair) {
                upperLeftX = Integer.parseInt(upperLeftPair[0]);
                upperLeftY = Integer.parseInt(upperLeftPair[1]);
                lowerRightX = Integer.parseInt(lowerRightPair[0]);
                lowerRightY = Integer.parseInt(lowerRightPair[1]);
            }

            public void performAction() {
                for (int x = upperLeftX; x <= lowerRightX; x++) {
                    for (int y = upperLeftY; y <= lowerRightY; y++) {
                        actUpon(x, y);
                    }
                }
            }

            protected abstract void actUpon(int x, int y);
        }

        private class TurnOnLightAction extends LightAction {
            public TurnOnLightAction(String[] upperLeftPair, String[] lowerRightPair) {
                super(upperLeftPair, lowerRightPair);
            }

            @Override
            protected void actUpon(int x, int y) {
                isLit[x][y] = 1;
                brightness[x][y] += 1;
            }
        }

        private class TurnOffLightAction extends LightAction {
            public TurnOffLightAction(String[] upperLeftPair, String[] lowerRightPair) {
                super(upperLeftPair, lowerRightPair);
            }

            @Override
            protected void actUpon(int x, int y) {
                isLit[x][y] = 0;
                brightness[x][y] = Math.max(0, brightness[x][y] - 1);
            }
        }

        private class ToggleLightAction extends LightAction {
            public ToggleLightAction(String[] upperLeftPair, String[] lowerRightPair) {
                super(upperLeftPair, lowerRightPair);
            }

            @Override
            protected void actUpon(int x, int y) {
                isLit[x][y] ^= 1;      // part 1 simply toggles on/off
                brightness[x][y] += 2;      // part 2 toggle means increase by 2
            }
        }
    }
}
