package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Day07 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07_example");

        BigInteger actual = part1(input);

        Assertions.assertEquals(95437, actual);
    }

    private BigInteger part1(List<String> input) {
        return null;
    }
}
