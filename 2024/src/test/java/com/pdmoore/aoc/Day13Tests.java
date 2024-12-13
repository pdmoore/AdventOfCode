package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Tests {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13_example.txt");

        int actual = solvePart1(input);

        assertEquals(480, actual);
    }

    @Test
    void part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13.txt");

        int actual = solvePart1(input);

        assertEquals(38839, actual);
    }

    @Test
    void verifyWinsPrize() {
        List<String> input = Stream.of("Button A: X+94, Y+34",
                "Button B: X+22, Y+67",
                "Prize: X=8400, Y=5400").collect(Collectors.toList());

        ButtonPushes actual = findWinningMoves(input);

        assertEquals(80, actual.a);
        assertEquals(40, actual.b);
        assertEquals(280, actual.tokenCost);

        input = Stream.of("Button A: X+17, Y+86",
                "Button B: X+84, Y+37",
                "Prize: X=7870, Y=6450").collect(Collectors.toList());

        actual = findWinningMoves(input);

        assertEquals(38, actual.a);
        assertEquals(86, actual.b);
        assertEquals(200, actual.tokenCost);
    }

    @Test
    void verifyNoWinners() {
        List<String> input = Stream.of("Button A: X+26, Y+66",
                "Button B: X+67, Y+21",
                "Prize: X=12748, Y=12176").collect(Collectors.toList());

        ButtonPushes actual = findWinningMoves(input);

        assertEquals(0, actual.a);
        assertEquals(0, actual.b);

        input = Stream.of("Button A: X+69, Y+23",
                "Button B: X+27, Y+71",
                "Prize: X=18641, Y=10279").collect(Collectors.toList());

        actual = findWinningMoves(input);

        assertEquals(0, actual.a);
        assertEquals(0, actual.b);

    }

    private class ButtonPushes {
        public int a;
        public int b;
        public int tokenCost;

        public ButtonPushes(int pushA, int pushB) {
            a = pushA;
            b = pushB;

            tokenCost = pushA * 3 + pushB;
        }
    }

    private ButtonPushes findWinningMoves(List<String> input) {
        String[] aValues = input.get(0).replace("Button A:", "").trim().split(",");// a deltas
        String[] bValues = input.get(1).replace("Button B:", "").trim().split(",");// a deltas
        String[] targets = input.get(2).replace("Prize:", "").trim().split(",");// a deltas

        int aXmovement = Integer.parseInt(aValues[0].split("\\+")[1]);
        int aYmovement = Integer.parseInt(aValues[1].split("\\+")[1]);

        int bXmovement = Integer.parseInt(bValues[0].split("\\+")[1]);
        int bYmovement = Integer.parseInt(bValues[1].split("\\+")[1]);


        int targetX = Integer.parseInt(targets[0].split("=")[1]);
        int targetY = Integer.parseInt(targets[1].split("=")[1]);


        for (int pushB = 100; pushB >= 0; pushB--) {

            int currentX = pushB * bXmovement;
            if (currentX > targetX) continue;

            if (currentX == targetX) {
//                throw new RuntimeException("Found a case where b equals x " + input);
            }

            // currentX is less than targetX
            int remainingX = targetX - currentX;
            if (remainingX % aXmovement == 0) {
                // this pair works for x
                int pushA = remainingX / aXmovement;
                int verifyY = pushB * bYmovement + pushA * aYmovement;
                if (verifyY == targetY) {
                    return new ButtonPushes(pushA, pushB);
                }
            }
        }

        return new ButtonPushes(0, 0);
    }



    private int solvePart1(List<String> input) {
        // need to split input by blank lines
        int tokensNeeded = 0;
        for (int i = 0; i < input.size(); i += 4) {
            ButtonPushes bp = findWinningMoves(input.subList(i, i + 3));
            tokensNeeded += bp.tokenCost;
        }

        return tokensNeeded;
    }
}
