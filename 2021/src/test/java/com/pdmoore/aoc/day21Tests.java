package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day21Tests {

    @Test
    void part1_example() {
        int player1StartAt = 4;
        int player2StartAt = 8;
        int trackLength = 10;
        int actual = playGame(trackLength, player1StartAt, player2StartAt);

        int expected = 745 * 993;
        assertEquals(expected, actual);
    }

    private int playGame(int trackLength, int player1StartAt, int player2StartAt) {
        return 0;
    }
}
