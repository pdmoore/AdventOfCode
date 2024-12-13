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
    void verifyWinsPrize() {
        List<String> input = Stream.of("Button A: X+94, Y+34",
                "Button B: X+22, Y+67",
                "Prize: X=8400, Y=5400").collect(Collectors.toList());

        ButtonPushes actual = findWinningMoves(input);

        assertEquals(80, actual.a);
        assertEquals(40, actual.b);
    }

    private class ButtonPushes {
        public int a;
        public int b;

        public ButtonPushes(int pushA, int pushB) {
            a = pushA;
            b = pushB;
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
                throw new RuntimeException("Found a case where b equals x " + input);
            }

            // currentX is less than targetX
            int remainingX = targetX - currentX;
            if (remainingX % aXmovement == 0) {
                // this pair works for x
                int pushA = remainingX / aXmovement;

                // check if it works for Y also
                // if it does, return that pair (assuming it is smallest)
                int breakpoint = 99;

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

        // for each set of three,
        // button a stats
        // button b stats
        // prize target
        //3 tokens for A button, 1 token for B button

        // solving a single set
        // would it be x mod a plus x mod b equal to 0
        // and y mod a plus y mod b equal to 0 for some pair of a and b

        // a or b can be pressed up to 100
        // so it could be 1 to 100 for each and whether both x and y are met
        // it is possible there is not solution



        // solve those for each set of inputs


        return 0;
    }
}
