package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class day22Tests {

    @Test
    public void calculateRiskLevel() {
        String filename = "data/aoc18.22a.txt";
        List<String> inputAsStrings = PuzzleInput.asListOfStringsFrom(filename);
        char[][] caveSystem = utilities.convertInputToMap(inputAsStrings);

        Point target = new Point(10, 10);
        int actual = day22.calculateRiskLevel(caveSystem, target);

        assertEquals(114, actual);
    }

    @Test
    public void solution1_example_caveSystem() {
        int depth = 510;
        Point target = new Point(10, 10);

        day22.Region[][] regions = new day22.Region[target.x + 1][target.y + 1];

        String filename = "data/aoc18.22b.txt";
        List<String> inputAsStrings = PuzzleInput.asListOfStringsFrom(filename);
        char[][] expected = utilities.convertInputToMap(inputAsStrings);

        char[][] actual = day22.solution1_caveSystem(regions, depth, target);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void solution1_example() {
        int depth = 510;
        Point target = new Point(10, 10);

        day22.Region[][] regions = new day22.Region[target.x + 1][target.y + 1];

        int actual = day22.solution1(regions, depth, target);

        assertEquals(114, actual);
    }

    @Test
    public void solution1() {
        int depth = 11541;
        Point target = new Point(14, 778);

        day22.Region[][] regions = new day22.Region[target.x + 1][target.y + 1];

        int actual = day22.solution1(regions, depth, target);

        assertEquals(11575, actual);
    }

}
