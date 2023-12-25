package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day24 {


    // Parse input and create Point2D with BigInteger values
    // track point x velocity
    // make a line form start point to point N in the future (velocity)
    // determine if two lines intersect
    // if they do is it inside the box


    @Test
    void parseInput() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day24_part1_example");

        Map<PointBigInt, Velocity> map = parseInput(input);

        assertEquals(5, map.size());
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day24_part1_example");

        Map<PointBigInt, Velocity> map = parseInput(input);

        int actual = findCollisions(map, BigInteger.valueOf(7), BigInteger.valueOf(27));

        assertEquals(2, actual);
    }

    private int findCollisions(Map<PointBigInt, Velocity> map, BigInteger boxLower, BigInteger boxUpper) {
        int result = 0;

        Set<PointBigInt> pointBigInts = map.keySet();
        for (PointBigInt a: pointBigInts) {
            for (PointBigInt b: pointBigInts) {
                if (a == b) continue;

                // create line from a to deltaA
                // create line from b to deltaB

                // do they collide?

                // is that point inside box?



            }
        }

        return result;
    }

    private Map<PointBigInt, Velocity> parseInput(List<String> input) {
        Map<PointBigInt, Velocity> result = new HashMap<>();

        for (String inputLine : input) {
            String[] split = inputLine.split(" @ ");
            PointBigInt p = new PointBigInt(split[0]);
            Velocity v = new Velocity(split[1]);
            result.put(p, v);
        }

        return result;
    }

    class PointBigInt {

        final BigInteger x;
        final BigInteger y;

        public PointBigInt(String csv) {
            String[] split = csv.split(",");
            this.x = new BigInteger(split[0].trim());
            this.y = new BigInteger(split[1].trim());
        }
    }

    class Velocity {

        final int deltaX;
        final int deltaY;

        public Velocity(String csv) {
            String[] split = csv.split(",");
            this.deltaX = Integer.parseInt(split[0].trim());
            this.deltaY = Integer.parseInt(split[1].trim());
        }
    }

}
