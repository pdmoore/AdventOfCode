package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class Day22 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day22_part1_example");

        List<Position> bricks = createBricksFrom(input);
        Assertions.assertEquals(7, bricks.size());
        // parse each line, create a 3D object
        // from bottom up, fall all the bricks until no bricks fall
        // process each brick, is it the only support for a brick above it
        // return the list of those bricks or just the count of those

        // not sure if it's a char[][][] or int[][][]
        // int could be the key to a map?

//        int actual = countSafeToDisintegrate(map);
//
//        assertEquals(5, actual);
    }

    private List<Position> createBricksFrom(List<String> input) {
        List<Position> result = new ArrayList<>();
        for (String inputLine: input) {
            String[] split = inputLine.split("~");
            result.add(new Position(split[0], split[1]));
        }
        return result;
    }

    class Position {
        int x_start;
        int y_start;
        int z_start;

        int x_end;
        int y_end;
        int z_end;

        public Position(String start, String end) {
            String[] startSplit = start.split(",");
            x_start = Integer.parseInt(startSplit[0]);
            y_start = Integer.parseInt(startSplit[1]);
            z_start = Integer.parseInt(startSplit[2]);
            String[] endSplit = end.split(",");
            x_end = Integer.parseInt(endSplit[0]);
            y_end = Integer.parseInt(endSplit[1]);
            z_end = Integer.parseInt(endSplit[2]);
        }
    }
}
