package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class day04Tests {
    
    // DIAGONALS DON'T COUNT

    @Test
    void day04_part1_processInput_CreateDataStructures() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day04_example");

        Day04Part1 actual = processInput(input);

        assertEquals("7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1", actual.numbersToCall);
        assertEquals(3, actual.boards.size());
    }

    @Test
    void day04_part01_Win_Across() {
        Board board = new Board();
        board.addLine(0, "14 21 17 24  4");
        board.addLine(1, "10 16 15  9 19");
        board.addLine(2, "18  8 23 26 20");
        board.addLine(3, "22 11 13  6  5");
        board.addLine(4, " 2  0 12  3  7");

        board.callNumber(14);
        board.callNumber(21);
        board.callNumber(17);
        board.callNumber(4);
        board.callNumber(24);

        assertTrue(board.wins());
    }

    @Test
    void day04_part01_Win_Down() {
        Board board = new Board();
        board.addLine(0, "14 21 17 24  4");
        board.addLine(1, "10 16 15  9 19");
        board.addLine(2, "18  8 23 26 20");
        board.addLine(3, "22 11 13  6  5");
        board.addLine(4, " 2  0 12  3  7");

        board.callNumber(14);
        board.callNumber(18);
        board.callNumber(22);
        board.callNumber(2);
        board.callNumber(10);

        assertTrue(board.wins());
    }

    @Test
    void day04_part01_Win_score() {
        Board board = new Board();
        board.addLine(0, "14 21 17 24  4");
        board.addLine(1, "10 16 15  9 19");
        board.addLine(2, "18  8 23 26 20");
        board.addLine(3, "22 11 13  6  5");
        board.addLine(4, " 2  0 12  3  7");

        board.callNumber(7);
        board.callNumber(4);
        board.callNumber(9);
        board.callNumber(5);
        board.callNumber(11);
        board.callNumber(17);
        board.callNumber(23);
        board.callNumber(2);
        board.callNumber(0);
        board.callNumber(14);
        board.callNumber(21);
        board.callNumber(24);

        assertEquals(4512, board.score());
    }

    @Test
    void day04_part1() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day04");

        Day04Part1 actual = processInput(input);

        String[] numbersToCall = actual.numbersToCall.split(",");

        for (String stringyNumber :
                numbersToCall) {
            int numberCalled = Integer.parseInt(stringyNumber);
            for (Board board :
                    actual.boards) {
                board.callNumber(numberCalled);
                if (board.wins()) {
                    assertEquals(35711, board.score());
                    return;   // stop calling numbers
                }
            }
        }
        Assertions.fail("no winning board encountered");
    }

    @Test
    void day04_part2_example() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day04_example");

        Day04Part1 actual = processInput(input);

        String[] numbersToCall = actual.numbersToCall.split(",");
        int boardsInPlay = actual.boards.size();

        for (String stringyNumber :
                numbersToCall) {
            int numberCalled = Integer.parseInt(stringyNumber);
            for (Board board :
                    actual.boards) {
                if (board.boardActive) {
                    board.callNumber(numberCalled);
                    if (board.wins()) {
                        boardsInPlay--;
                        if (boardsInPlay == 0) {
                            assertEquals(1924, board.score());
                            return;
                        }
                    }
                }
            }
        }
        Assertions.fail("no winning board encountered");
    }

    @Test
    void day04_part2() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day04");

        Day04Part1 actual = processInput(input);

        String[] numbersToCall = actual.numbersToCall.split(",");

        int boardsInPlay = actual.boards.size();

        for (String stringyNumber :
                numbersToCall) {
            int numberCalled = Integer.parseInt(stringyNumber);
            for (Board board :
                    actual.boards) {
                if (board.boardActive) {
                    board.callNumber(numberCalled);
                    if (board.wins()) {
                        boardsInPlay--;
                        if (boardsInPlay == 0) {
                            assertEquals(5586, board.score());
                            return;
                        }
                    }
                }
            }
        }
        Assertions.fail("no winning board encountered");
    }


    private Day04Part1 processInput(List<String> input) {
        Day04Part1 actual = new Day04Part1();
        actual.numbersToCall = input.get(0);

        int gridLine = 0;
        Board board = new Board();
        for (int i = 1; i < input.size(); i++) {
            if (input.get(i).length() == 0) {
                board = new Board();
                actual.boards.add(board);
                gridLine = 0;
            } else {
                board.addLine(gridLine, input.get(i));
                gridLine++;
            }
        }

        return actual;
    }

    private class Board {
        Integer[][] grid = new Integer[5][5];
        private int _numberJustCalled = 0;
        boolean boardActive = true;

        public void addLine(int row, String s) {
            int column = 0;
            for (int i = 0; i < s.length(); i = i + 3) {
                grid[row][column] = Integer.parseInt((s.substring(i, i + 2).trim()));
                column++;
            }
        }

        public void callNumber(int calledNumber) {
            if (!boardActive) return;

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (grid[i][j] == calledNumber) {
                        grid[i][j] = -1;
                        _numberJustCalled = calledNumber;
                        return;
                    }
                }
            }
        }

        public boolean wins() {
            boolean isWinner = completeRow() || completeColumn();
            if (isWinner) {
                boardActive = false;
            }
            return isWinner;
        }

        private boolean completeRow() {
            for (int i = 0; i < 5; i++) {
                if (grid[i][0] == -1 &&
                    grid[i][1] == -1 &&
                    grid[i][2] == -1 &&
                    grid[i][3] == -1 &&
                    grid[i][4] == -1)
                    return true;
            }
            return false;
        }

        private boolean completeColumn() {
            for (int i = 0; i < 5; i++) {
                if (grid[0][i] == -1 &&
                    grid[1][i] == -1 &&
                    grid[2][i] == -1 &&
                    grid[3][i] == -1 &&
                    grid[4][i] == -1)
                    return true;
            }
            return false;
        }

        public int score() {
            return sumOfUnmarkedNumbers() * _numberJustCalled;
        }

        private int sumOfUnmarkedNumbers() {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (grid[i][j] != -1) {
                        sum += grid[i][j];
                    }
                }
            }
            return sum;
        }
    }

    private class Day04Part1 {
        public String numbersToCall;
        public List<Board> boards;

        public Day04Part1() {
            this.boards = new ArrayList<>();
        }
    }
}
