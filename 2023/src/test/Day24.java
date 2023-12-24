package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day24 {


    // Parse input and create Point2D with BigInteger values
    // track point x velocity
    // make a line form start point to point N in the future (velocity)
    // determine if two lines intersect
    // if they do is it inside the box


    @Test
    void something() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day24_part1_example");

        Map<PointBigInt, Velocity> map = parseInput(input);

        assertEquals(5, map.size());
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
