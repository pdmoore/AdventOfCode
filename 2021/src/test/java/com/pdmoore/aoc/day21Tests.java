package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day21Tests {

    @Test
    void part1_example() {
        int player1StartAt = 4;
        int player2StartAt = 8;
        int actual = playGame(player1StartAt, player2StartAt);

        int expected = 745 * 993;
        assertEquals(expected, actual);
    }

    @Test
    void part1_solution() {
        int player1StartAt = 1;
        int player2StartAt = 10;
        int actual = playGame(player1StartAt, player2StartAt);

        assertEquals(428736, actual);
    }

    private int playGame(int player1StartAt, int player2StartAt) {
        DeterministicDie die = new DeterministicDie();

        Player player1 = new Player(player1StartAt);
        Player player2 = new Player(player2StartAt);

        while (true) {
            player1.move(die);
            if (player1.score >= 1000) {
                return player2.score * die.rollCount;
            }

            player2.move(die);
            if (player2.score >= 1000) {
                return player1.score * die.rollCount;
            }
        }
    }

    private class DeterministicDie {
        int returnValue = 1;
        int rollCount = 0;

        int roll() {
            rollCount++;
            int dieResult = returnValue;
            if (returnValue++ >= 100) {
                returnValue = 1;
            }
            return dieResult;
        }

    }

    private class Player {
        int score;
        int currentPosition;

        public Player(int startingPosition) {
            this.currentPosition = startingPosition;
            this.score = 0;
        }

        public void move(DeterministicDie die) {
            currentPosition = (currentPosition + die.roll() + die.roll() + die.roll());
            currentPosition = ((currentPosition - 1) % 10) + 1;
            score += currentPosition;
        }
    }
}
