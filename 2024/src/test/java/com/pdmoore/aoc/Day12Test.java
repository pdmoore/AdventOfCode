package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    @Test
    void part1_examples() {
        List<String> input = PuzzleInput.asStringListFrom("data/day12_example.txt");
        int actual = solvePart1(input);
        assertEquals(140, actual);

        input = PuzzleInput.asStringListFrom("data/day12_example2.txt");
        actual = solvePart1(input);
        assertEquals(772, actual);

        input = PuzzleInput.asStringListFrom("data/day12_example3.txt");
        actual = solvePart1(input);
        assertEquals(1930, actual);
    }

    private int solvePart1(List<String> input) {

        // as 2-d grid?
        // start in upper left and grow the region out while adjacent cell equals seed
        // maybe replace a cell that's been tracked with .?
        // then find next untracked letter and begin growing that region out
        // perimeter is not a function of number cells, it needs to trace the outline?
        // perimeter probably needs to track positions and then trace around outside edge?




        return 0;
    }

}
