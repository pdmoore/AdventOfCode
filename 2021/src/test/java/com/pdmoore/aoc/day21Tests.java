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

    @Test
    void part1_solution() {
        int player1StartAt = 1;
        int player2StartAt = 10;
        int trackLength = 10;
        int actual = playGame(trackLength, player1StartAt, player2StartAt);

        assertEquals(428736, actual);
    }

    private int playGame(int trackLength, int player1StartAt, int player2StartAt) {
        DeterministicDie die = new DeterministicDie();

        int player1Position = player1StartAt;
        int player2Position = player2StartAt;

        int player1Score = 0;
        int player2Score = 0;

        while (player1Score < 1000 && player2Score < 1000) {

            int roll1 = die.roll();
            int roll2 = die.roll();
            int roll3 = die.roll();
            int player1Rolls = roll1 + roll2 + roll3;
            player1Position = (player1Position + player1Rolls);
            while (player1Position > 10) {
                player1Position -= 10;
            }
            player1Score += player1Position;
            System.out.println("Player 1 rolls " + roll1 + "+" + roll2 + "+" + roll3 + " and moves to space "+ player1Position+" for a total score of " + player1Score+ ".");
            if (player1Score >= 1000) {
                return player2Score * die.rollCount;
            }

            roll1 = die.roll();
            roll2 = die.roll();
            roll3 = die.roll();
            int player2Rolls = roll1 + roll2 + roll3;
            player2Position = (player2Position + player2Rolls);
            while (player2Position > 10) {
                player2Position -= 10;
            }
            player2Score += player2Position;
            System.out.println("Player 2 rolls " + roll1 + "+" + roll2 + "+" + roll3 + " and moves to space "+ player2Position+" for a total score of " + player2Score+ ".");
            if (player2Score >= 1000) {
                return player1Score * die.rollCount;
            }
        }

        return -1;
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
}
