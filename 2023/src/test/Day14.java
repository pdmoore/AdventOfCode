package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14 {

    // Read map
    // Tilt north
    // ech column, start form bottom and move O up until it can't
    // some load calculation

    enum Direction {north, south, east, west}
    final char ROUND_ROCK = 'O';
    final char CUBE_ROCK = '#';
    final char SPACE = '.';

    @Test
    void part1_example_tilt_north() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day14_part1_example");
        char[][] expected = PuzzleInput.as2dCharArray("./data/day14_part1_example_expected");

        char[][] actual = tilt(input, Direction.north);

        assertEquals(new String(expected[0]), new String(actual[0]));
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

        char[][] tilted = tilt(input, Direction.north);
        int actual = computeLoad(tilted);

        assertEquals(110821, actual);
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


    private char[][] tilt(char[][] map, Direction direction) {

        int max_row = map.length;

        // Tilt North (left to right, top to bottom)
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


        return map;
    }


}
