package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day5Tests {

    @Test
    public void noSubstitions() {
        String input = "abcdef";
        Assertions.assertEquals("abcdef", day5.reduceAlgorithmByReplaceFirst(input));
    }

    @Test
    public void singleLowerUpperSubstitution() {
        String input = "aAz";
        Assertions.assertEquals("z", day5.reduceAlgorithmByReplaceFirst(input));
        Assertions.assertEquals("col", day5.reduceAlgorithmByReplaceFirst("bBy"));

    }

    @Test
    public void singleUpperLowerSubstitution() {
        String input = "Aaz";
        Assertions.assertEquals("z", day5.reduceAlgorithmByReplaceFirst(input));
    }

    @Test
    public void multiplePassesOnInput() {
        String input = "dabAcCaCBAcCcaDA";
        Assertions.assertEquals("dabCBAcaDA", day5.reduceAlgorithmByReplaceFirst(input));
    }

    @Test
    public void try2_noSubstitions() {
        String input = "abcdef";
        Assertions.assertEquals("abcdef", day5.try2(input));
    }

    @Test
    public void try2_singleLowerUpperSubstitution() {
        String input = "aAz";
        Assertions.assertEquals("z", day5.try2(input));
        Assertions.assertEquals("col", day5.try2("bBy"));

    }

    @Test
    public void try2_singleUpperLowerSubstitution() {
        String input = "Aaz";
        Assertions.assertEquals("z", day5.try2(input));
    }

    @Test
    public void try2_multiplePassesOnInput() {
        String input = "dabAcCaCBAcCcaDA";
        Assertions.assertEquals("dabCBAcaDA", day5.try2(input));
    }

    @Test
    @Disabled
    public void solution_1_testFile() {
        assertEquals(9900, day5.solution_1("data/aoc18.5.txt"));
    }

    @Test
    public void smallestByRemovingOne() {
        String input = "dabAcCaCBAcCcaDA";
        Assertions.assertEquals(4, day5.findSmallestReduction(input));
    }

    @Test
    public void solution_2_testFile() {
        assertEquals(4992, day5.solution_2("data/aoc18.5.txt"));
    }

}
