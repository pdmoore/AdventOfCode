package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import javax.swing.plaf.OptionPaneUI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Tests {

    @Test
    void part1_example() {
        //List<String> input = PuzzleInput.asStringListFrom("./data/day01_example");
        List<String> input = new ArrayList<>();
        input.add("A Y");
        input.add("B X");
        input.add("C Z");

        int actual = scoreFor(input);

        assertEquals(15, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day02");

        int actual = scoreFor(input);

        assertEquals(11841, actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day02");

        int actual = part2(input);

        assertEquals(13022, actual);
    }

    private int scoreFor(List<String> input) {
         int result = 0;

        for (String round :
                input) {

            char opponentPlayed = round.charAt(0);
            char iPlayed = round.charAt(2);

            int myMoveScored = scoreForWhat(iPlayed);
            int myPlayScored = whoWins(opponentPlayed, iPlayed);

            result += myMoveScored+myPlayScored;
        }
        return result;
    }

    private int whoWins(char opponentPlayed, char iPlayed) {
        //0 if you lost, 3 if the round was a draw, and 6 if you won
        if (opponentPlayed == 'A') { //rock
            switch (iPlayed) {
                case 'X': return 3;
                case 'Y': return 6;
                case 'Z': return 0;
            }
        }
        if (opponentPlayed == 'B') { //paper
            switch (iPlayed) {
                case 'X': return 0;
                case 'Y': return 3;
                case 'Z': return 6;
            }
        }
        if (opponentPlayed == 'C') { //scissors
            switch (iPlayed) {
                case 'X': return 6;
                case 'Y': return 0;
                case 'Z': return 3;
            }
        }

        throw new IllegalArgumentException("unknown move " + opponentPlayed);

    }

    private int scoreForWhat(char rps) {
        //1 for Rock X, 2 for Paper Y, and 3 for Scissors Z)
        if (rps == 'X') return 1;
        if (rps == 'Y') return 2;
        if (rps == 'Z') return 3;
        throw new IllegalArgumentException("unknown move " + rps);
    }

    @Test
    void part2_example() {
        //List<String> input = PuzzleInput.asStringListFrom("./data/day01_example");
        List<String> input = new ArrayList<>();
        input.add("A Y");
        input.add("B X");
        input.add("C Z");

        int actual = part2(input);

        assertEquals(12, actual);
    }

    private int part2(List<String> input) {
        //X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win.
        int result = 0;

        for (String round :
                input) {

            char opponentPlayed = round.charAt(0);
            char iNeedTo = round.charAt(2);

            char whatIShouldPlay = whatToPlay(opponentPlayed, iNeedTo);

            int myMoveScored = scoreForWhat(whatIShouldPlay);
            int myPlayScored = whoWins(opponentPlayed, whatIShouldPlay);

            result += myMoveScored+myPlayScored;
        }
        return result;
    }

    private char whatToPlay(char opponentPlayed, char iNeedTo) {
        //X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win.
//        X for Rock, Y for Paper, and Z for Scissors
        if (opponentPlayed == 'A') { //rock
            switch (iNeedTo) {
                case 'X': return 'Z';
                case 'Y': return 'X';
                case 'Z': return 'Y';
            }
        }if (opponentPlayed == 'B') { //PAPER
            switch (iNeedTo) {
                case 'X': return 'X';
                case 'Y': return 'Y';
                case 'Z': return 'Z';
            }
        }if (opponentPlayed == 'C') { //SCISSORS
            switch (iNeedTo) {
                case 'X': return 'Y';
                case 'Y': return 'Z';
                case 'Z': return 'X';
            }
        }
        throw new IllegalArgumentException("unknown move " + opponentPlayed);

    }

}
