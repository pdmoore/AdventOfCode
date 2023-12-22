package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14 {

    final char ROUND_ROCK = 'O';
    final char CUBE_ROCK = '#';
    final char SPACE = '.';

    final int CYCLE_COUNT = 1_000_000_000;

    List<Integer> _totalLoadValues = new ArrayList<>();

    @Test
    void part1_example_tilt_north() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day14_part1_example");
        char[][] expected = PuzzleInput.as2dCharArray("./data/day14_part1_example_expected");

        tiltNorth(input);

        assertEquals(new String(expected[0]), new String(input[0]));
    }

    @Test
    void part1_eample_cycle_once() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day14_part1_example");
        char[][] expected = PuzzleInput.as2dCharArray("./data/day14_part1_cycle1");

        spinCycle(input, 1);

        assertEquals(new String(expected[0]), new String(input[0]));
        assertEquals(new String(expected[1]), new String(input[1]));
        assertEquals(new String(expected[7]), new String(input[7]));
        assertEquals(new String(expected[9]), new String(input[9]));
    }

    @Test
    void part1_compute_load() {
        char[][] tilted = PuzzleInput.as2dCharArray("./data/day14_part1_example_expected");

        int actual = computeLoad(tilted);

        assertEquals(136, actual);
    }

    @Test
    void part1_solution() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day14");

        tiltNorth(input);
        int actual = computeLoad(input);

        assertEquals(110821, actual);
    }

    @Test
    void part2_example() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day14_part1_example");

        _totalLoadValues = new ArrayList<>();
//        spinCycle(input, CYCLE_COUNT);
        spinCycle(input, 1000);

        int actual = computeLoad(input);

        assertEquals(64, actual);
    }

    @Test
    void part2_solution() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day14");

        spinCycle(input, CYCLE_COUNT);

        int actual = computeLoad(input);

        assertEquals(999, actual);
    }

    private int computeLoad(char[][] map) {
        int result = 0;
        int max = map.length;
        for (int row = 0; row < max; row++) {
            for (int col = 0; col < max; col++) {
                if (map[row][col] == ROUND_ROCK) {
                    result += max - row;
                }
            }
        }

        return result;
    }

    private void spinCycle(char[][] map, int cycle_count) {
        for (int i = 0; i < cycle_count; i++) {
            tiltNorth(map);
            tiltWest(map);
            tiltSouth(map);
            tiltEast(map);
            _totalLoadValues.add(computeLoad(map));
            if (foundTotalLoadCycle() != -1) {
                return;
            }
            if (i % 1_000_000 == 0) {
                System.out.println("# cycles " + i + "  " + (cycle_count - i) + " to go");
            }
        }
    }

    private int foundTotalLoadCycle() {
        if (_totalLoadValues.size() <= 150) return -1;
        int searchFromIndex = 81;
        while (searchFromIndex < 150) {
            int seed = _totalLoadValues.get(searchFromIndex);
            int nextSeedIndex = searchFromIndex + 1;
            while (seed != _totalLoadValues.get(nextSeedIndex)) {
                nextSeedIndex++;
            };

            for (int i = searchFromIndex; i < nextSeedIndex - 1; i++) {
                int fromSeed = _totalLoadValues.get(i);
                int fromNextSeed = _totalLoadValues.get(nextSeedIndex - searchFromIndex + i);
                if (fromSeed != fromNextSeed) {
                    break;
                }
                return searchFromIndex;
            }
            searchFromIndex++;
        }

        return searchFromIndex;
    }


    private void tiltNorth(char[][] map) {
        int max_row = map.length;

        for (int col = 0; col < max_row; col++) {
            for (int row = 1; row < max_row; row++) {
                if (map[row][col] == ROUND_ROCK) {
                    int move_up = row - 1;
                    while (move_up >= 0 && map[move_up][col] == SPACE) {
                        map[move_up + 1][col] = SPACE;
                        map[move_up][col] = ROUND_ROCK;
                        move_up--;
                    }
                }
            }
        }
    }

    private void tiltSouth(char[][] map) {
        int max_row = map.length;

        for (int col = 0; col < max_row; col++) {
            for (int row = max_row - 1; row >= 0; row--) {
                if (map[row][col] == ROUND_ROCK) {
                    int move_down = row + 1;
                    while (move_down < max_row && map[move_down][col] == SPACE) {
                        map[move_down - 1][col] = SPACE;
                        map[move_down][col] = ROUND_ROCK;
                        move_down++;
                    }
                }
            }
        }
    }

    private void tiltWest(char[][] map) {
        int max_col = map.length;
        for (int row = 0; row < max_col; row++) {
            for (int col = 1; col < max_col; col++) {

                if (map[row][col] == ROUND_ROCK) {
                    int move_left = col - 1;
                    while (move_left >= 0 && map[row][move_left] == SPACE) {
                        map[row][move_left + 1] = SPACE;
                        map[row][move_left] = ROUND_ROCK;
                        move_left--;
                    }
                }
            }
        }
    }

    private void tiltEast(char[][] map) {
        int max_col = map.length;
        for (int row = 0; row < max_col; row++) {
            for (int col = max_col - 1; col >= 0; col--) {

                if (map[row][col] == ROUND_ROCK) {
                    int move_right = col + 1;
                    while (move_right < max_col && map[row][move_right] == SPACE) {
                        map[row][move_right - 1] = SPACE;
                        map[row][move_right] = ROUND_ROCK;
                        move_right++;
                    }
                }
            }
        }
    }

}
