package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {

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
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13_example.txt");

        BigInteger actual = solvePart2(input);

        assertEquals(new BigInteger("875318608908"), actual);
    }

    @Test
    void part2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13.txt");

        BigInteger actual = solvePart2(input);

        assertEquals(new BigInteger("75200131617108"), actual);
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
        public BigInteger tokenCostBI;
        public BigInteger aBI;
        public BigInteger bBI;
        public int a;
        public int b;
        public int tokenCost;

        public ButtonPushes(int pushA, int pushB) {
            a = pushA;
            b = pushB;

            tokenCost = pushA * 3 + pushB;
        }

        public ButtonPushes(BigInteger pushA, BigInteger pushB) {
            aBI = pushA;
            bBI = pushB;

            tokenCostBI = pushA.multiply(BigInteger.valueOf(3)).add(pushB);
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
        int tokensNeeded = 0;
        for (int i = 0; i < input.size(); i += 4) {
            ButtonPushes bp = findWinningMoves(input.subList(i, i + 3));
            tokensNeeded += bp.tokenCost;
        }

        return tokensNeeded;
    }

    private BigInteger solvePart2(List<String> input) {
        BigInteger tokensNeeded = BigInteger.ZERO;
        for (int i = 0; i < input.size(); i += 4) {
            ButtonPushes bp = findWinningMovesPart2(input.subList(i, i + 3));
            tokensNeeded = tokensNeeded.add(bp.tokenCostBI);
        }

        return tokensNeeded;
    }

    private ButtonPushes findWinningMovesPart2(List<String> input) {
        String[] aValues = input.get(0).replace("Button A:", "").trim().split(",");// a deltas
        String[] bValues = input.get(1).replace("Button B:", "").trim().split(",");// a deltas
        String[] targets = input.get(2).replace("Prize:", "").trim().split(",");// a deltas

        BigInteger aXmovement = new BigInteger(aValues[0].split("\\+")[1]);
        BigInteger aYmovement = new BigInteger(aValues[1].split("\\+")[1]);

        BigInteger bXmovement = new BigInteger(bValues[0].split("\\+")[1]);
        BigInteger bYmovement = new BigInteger(bValues[1].split("\\+")[1]);


        Long longX = Long.parseLong(targets[0].split("=")[1]);
        Long longY = Long.parseLong(targets[1].split("=")[1]);

//        BigInteger prize0 = BigInteger.valueOf(longX);
//        BigInteger prize1 = BigInteger.valueOf(longY);
        BigInteger prize0 = new BigInteger("10000000000000").add(BigInteger.valueOf(longX));
        BigInteger prize1 = new BigInteger("10000000000000").add(BigInteger.valueOf(longY));

        // ax * by - ay * bx
        BigInteger det = aXmovement.multiply(bYmovement)
                .subtract(aYmovement.multiply(bXmovement));
        //a = prize0 * bY - prize1 * bx) / det
        //b = prize1 * ax - prize0 * aY) / det
        BigInteger a = prize0.multiply(bYmovement)
                .subtract(prize1.multiply(bXmovement))
                .divide(det);

        BigInteger b = prize1.multiply(aXmovement)
                .subtract(prize0.multiply(aYmovement))
                .divide(det);

        BigInteger checkX = a.multiply(aXmovement)
                .add(b.multiply(bXmovement));
        BigInteger checkY = a.multiply(aYmovement)
                .add(b.multiply(bYmovement));
        if (prize0.equals(checkX) && prize1.equals(checkY)) {
            return new ButtonPushes(a, b);
        }

        return new ButtonPushes(BigInteger.ZERO, BigInteger.ZERO);
    }
}
