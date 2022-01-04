package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day13Tests {

    @Test
    void day13_part1_example_FirstFoldOnly() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13_example");
        List<String> dotLocations = getDotsFrom(input);
        List<String> foldInstructions = getFoldsFrom(input);

        int actual = processFold(dotLocations, foldInstructions.get(0));

        assertEquals(17, actual);
    }

    private int processFold(List<String> dotLocations, String foldInstruction) {
        return 0;
    }

    private List<String> getFoldsFrom(List<String> input) {
        return null;
    }

    private List<String> getDotsFrom(List<String> input) {
        return null;
    }
}
