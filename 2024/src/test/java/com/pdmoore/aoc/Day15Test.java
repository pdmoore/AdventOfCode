package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {

    @Test
    void part1_smaller_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day15_smaller_example.txt");

        int actual = solvePart1(input);

        assertEquals(2028, actual);
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day15_example.txt");

        int actual = solvePart1(input);

        assertEquals(10092, actual);
    }

    @Test
    void part1_smaller_example_computeGPSscore() {
        char[][] map = PuzzleInput.as2dCharArray("data/day15_smaller_example_final_positions.txt");
        int actual = sumBoxGPScoordinates(map);

        assertEquals(2028, actual);
    }

    @Test
    void part1_example_computeGPSscore() {
        char[][] map = PuzzleInput.as2dCharArray("data/day15_example_final_positions.txt");
        int actual = sumBoxGPScoordinates(map);

        assertEquals(10092, actual);
    }


    private int sumBoxGPScoordinates(char[][] map) {
        int result = 0;
        for (int fromTop = 0; fromTop < map.length; fromTop++) {
            for (int fromLeft = 0; fromLeft < map[fromTop].length; fromLeft++) {
                if (map[fromTop][fromLeft] == 'O') {
                    int gps = 100 * fromTop + fromLeft;
                    result += gps;
                }
            }
        }

        return result;
    }


    private int solvePart1(List<String> input) {

        // robot doesn't have a direction, just a position

        // load map
        // load moves sequence
        // locate initial guard position (@ character)

        // foreach character in move sequence
        // if cell in the move direction is
        // '.' - just move in that direction (and replace old position with '.'
        // '#' - nothing
        // 'O' - determine if boxes can be moved in that direction
        //     - must have a '.' at the end of the sequence. Adjust position of 'O' on map
        //     - and move the robot in that direction

        // for each row/col position in grid
        // calculate GPS position
        // sum all GPS positions


        return 1;
    }
}
