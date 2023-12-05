package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class Day05 {

    @Test
    void get_seed_list_from_input() {

        // Seed 79, soil 81, fertilizer 81, water 81, light 74, temperature 78, humidity 78, location 82.

        List<BigDecimal> actual = seedListFrom("seeds: 79 14 55 13");

        List<BigDecimal> expected = new ArrayList<>();
        expected.add(new BigDecimal(79));
        expected.add(new BigDecimal(14));
        expected.add(new BigDecimal(55));
        expected.add(new BigDecimal(13));

        assertEquals(expected, actual);
    }

    private List<BigDecimal> seedListFrom(String inputLine) {
        String[] seedNumbers = inputLine.substring(7).split(" ");
        List<BigDecimal> seeds = new ArrayList<>();
        for (String seedStr :
                seedNumbers) {
            seeds.add(new BigDecimal(Integer.parseInt(seedStr)));
        }
        return seeds;
    }
}
