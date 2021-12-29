package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day11Tests {

    @Test
    void day11_part1_example_10steps() {
        int[][] input = PuzzleInput.as2dIntArray("data/day11_example");
        Cavern sut = new Cavern(input);

        sut.takeSteps(10);
        int actual = sut.totalFlashes();

        assertEquals(204, actual);
    }

    @Test
    void day11_part1_example_100steps() {
        int[][] input = PuzzleInput.as2dIntArray("data/day11_example");
        Cavern sut = new Cavern(input);

        sut.takeSteps(100);
        int actual = sut.totalFlashes();

        assertEquals(1656, actual);
    }

    @Test
    void day11_part1_solution() {
        int[][] input = PuzzleInput.as2dIntArray("data/day11");
        Cavern sut = new Cavern(input);

        sut.takeSteps(100);
        int actual = sut.totalFlashes();

        assertEquals(1702, actual);
    }

    @Test
    void day11_part1_oneStep() {
        int[][] input = PuzzleInput.as2dIntArray("data/day11_smallExample");
        Cavern sut = new Cavern(input);

        sut.takeStep();

        String expected =
                "34543\n" +
                        "40004\n" +
                        "50005\n" +
                        "40004\n" +
                        "34543\n";

        assertEquals(expected, sut.currentState());
    }

    @Test
    void day11_part2_example() {
        int[][] input = PuzzleInput.as2dIntArray("data/day11_example");
        Cavern sut = new Cavern(input);

        int actual = sut.stepUntilAllFlash();

        assertEquals(195, actual);
    }

    @Test
    void day11_part2_solution() {
        int[][] input = PuzzleInput.as2dIntArray("data/day11");
        Cavern sut = new Cavern(input);

        int actual = sut.stepUntilAllFlash();

        assertEquals(251, actual);
    }


    private class Cavern {
        private final int _cavernSize;
        int[][] _currentState;
        private int _totalFlashes;
        private boolean _haltWhenAllFlash;

        public Cavern(int[][] input) {
            _currentState = input;
            _totalFlashes = 0;
            _cavernSize = input.length;
            _haltWhenAllFlash = false;
        }

        public void takeSteps(int stepCount) {
            for (int i = 1; i <= stepCount; i++) {
                takeStep();
            }
        }

        public void takeStep() {
            int[][] nextState = new int[_cavernSize][_cavernSize];

            // First, the energy level of each octopus increases by 1.
            IncreaseEnergyLevel(nextState);

            List<Point> flashedThisStep = FlashOctopuses(nextState);

            // Finally, any octopus that flashed during this step has its energy level set to 0, as it used all of its energy to flash.
            setFlashedThisStepToZero(nextState, flashedThisStep);

            if (didEveryOctopusFlash(flashedThisStep.size())) {
                _haltWhenAllFlash = false;
            }

            _totalFlashes += flashedThisStep.size();
            _currentState = nextState;
        }

        private boolean didEveryOctopusFlash(int flashedCount) {
            return flashedCount == (_cavernSize * _cavernSize);
        }

        private void setFlashedThisStepToZero(int[][] nextState, List<Point> flashedThisStep) {
            for (Point p :
                    flashedThisStep) {
                nextState[p.x][p.y] = 0;
            }
        }

        private List<Point> FlashOctopuses(int[][] nextState) {
            /*
            Then, any octopus with an energy level greater than 9 flashes.
            This increases the energy level of all adjacent octopuses by 1, including octopuses that are diagonally adjacent.
            If this causes an octopus to have an energy level greater than 9, it also flashes.
            This process continues as long as new octopuses keep having their energy level increased beyond 9.
            (An octopus can only flash at most once per step.)
             */

            List<Point> flashedThisStep = new ArrayList<>();
            List<Point> flashedJustNow = new ArrayList<>();

            for (int x = 0; x < _cavernSize; x++) {
                for (int y = 0; y < _cavernSize; y++) {
                    int currentValue = _currentState[x][y];
                    if (currentValue > 9) {
                        flashedJustNow.add(new Point(x, y));
                    }
                }
            }

            do {

                for (Point p :
                        flashedJustNow) {
                    if (!flashedThisStep.contains(p)) {
                        flashNeighbors(nextState, p);
                    }
                }

                flashedThisStep.addAll(flashedJustNow);
                flashedJustNow = new ArrayList<>();

                for (int x = 0; x < _cavernSize; x++) {
                    for (int y = 0; y < _cavernSize; y++) {
                        int nextValue = nextState[x][y];
                        if (nextValue > 9) {
                            if (!flashedThisStep.contains(new Point(x, y))) {
                                flashedJustNow.add(new Point(x, y));
                            }
                        }
                    }
                }

            } while (!flashedJustNow.isEmpty());
            return flashedThisStep;
        }

        private void IncreaseEnergyLevel(int[][] nextState) {
            for (int x = 0; x < _cavernSize; x++) {
                for (int y = 0; y < _cavernSize; y++) {
                    nextState[x][y] = _currentState[x][y] + 1;
                }
            }
        }

        private void flashNeighbors(int[][] nextState, Point p) {
            if ((p.x - 1 >= 0) && (p.y - 1 >= 0)) {  // 1
                nextState[p.x - 1][p.y - 1]++;
            }
            if (p.x - 1 >= 0) {  // 2
                nextState[p.x - 1][p.y]++;
            }
            if ((p.x - 1 >= 0) && (p.y + 1 < _cavernSize)) { // 3
                nextState[p.x - 1][p.y + 1]++;
            }
            if (p.y - 1 >= 0) {  // 4
                nextState[p.x][p.y - 1]++;
            }
            if (p.y + 1 < _cavernSize) {  // 6
                nextState[p.x][p.y + 1]++;
            }
            if ((p.x + 1 < _cavernSize) && (p.y - 1 >= 0)) {  // 7
                nextState[p.x + 1][p.y - 1]++;
            }
            if (p.x + 1 < _cavernSize) {  // 8
                nextState[p.x + 1][p.y]++;
            }
            if ((p.x + 1 < _cavernSize) && (p.y + 1 < _cavernSize)) {  // 9
                nextState[p.x + 1][p.y + 1]++;
            }
            int foo = 5;
            if (foo > 5) {
                System.out.println("breakpoint");
            }
        }

        public void dumpState() {
            for (int i = 0; i < _cavernSize; i++) {
                System.out.println(getRow(i));
            }
            System.out.println("total flashes: " + _totalFlashes);
            System.out.println();
        }

        public String getRow(int row) {
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < _cavernSize; y++) {
                sb.append(_currentState[row][y]);
            }
            return sb.toString();
        }


        public int stepUntilAllFlash() {
            _haltWhenAllFlash = true;
            int i = 1;
            while (_haltWhenAllFlash) {
                takeStep();
                i++;
            }
            return i - 1;
        }

        public String currentState() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < _cavernSize; i++) {
                sb.append(getRow(i));
                sb.append("\n");
            }
            return sb.toString();
        }

        public int totalFlashes() {
            return _totalFlashes;
        }
    }
}
