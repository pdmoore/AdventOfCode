package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05 {

    @Test
    void get_seed_list_from_input() {
        List<BigDecimal> actual = seedListFrom("seeds: 79 14 55 13");

        List<BigDecimal> expected = new ArrayList<>();
        expected.add(new BigDecimal(79));
        expected.add(new BigDecimal(14));
        expected.add(new BigDecimal(55));
        expected.add(new BigDecimal(13));

        assertEquals(expected, actual);
    }

    @Test
    void find_location_given_seed() {
        BigDecimal seedNumber = new BigDecimal(79);

        // Seed 79, soil 81, fertilizer 81, water 81, light 74, temperature 78, humidity 78, location 82.

        BigDecimal actual = findLocationForSeed(seedNumber);

        BigDecimal expected = new BigDecimal(82);
        assertEquals(expected, actual);
    }

    @Test
    void construct_mapping_thingy() {
        //seed-to-soil map:
        List<String> input = Arrays.asList("50 98 2", "52 50 48");

        MappingThingy sut = new MappingThingy(input);

        assertEquals(new BigDecimal(1), sut.correspondsTo(new BigDecimal(1)));
        assertEquals(new BigDecimal(10), sut.correspondsTo(new BigDecimal(10)));
        assertEquals(new BigDecimal(49), sut.correspondsTo(new BigDecimal(49)));
        assertEquals(new BigDecimal(52), sut.correspondsTo(new BigDecimal(50)));
        assertEquals(new BigDecimal(53), sut.correspondsTo(new BigDecimal(51)));
        assertEquals(new BigDecimal(55), sut.correspondsTo(new BigDecimal(53)));
        assertEquals(new BigDecimal(98), sut.correspondsTo(new BigDecimal(96)));
        assertEquals(new BigDecimal(99), sut.correspondsTo(new BigDecimal(97)));
        assertEquals(new BigDecimal(50), sut.correspondsTo(new BigDecimal(98)));
        assertEquals(new BigDecimal(51), sut.correspondsTo(new BigDecimal(99)));
    }


    // ---------------------------------
    private BigDecimal findLocationForSeed(BigDecimal seedNumber) {
        BigDecimal soil = _seedToSoil.correspondsTo(seedNumber);
        BigDecimal fertilizer = _soilToFertilizer.correspondsTo(soil);
        BigDecimal water = _fertilizerToWater.correspondsTo(fertilizer);
        BigDecimal light = _waterToLight.correspondsTo(water);
        BigDecimal temperature = _lightToTemperature.correspondsTo(light);
        BigDecimal humidity = _temperatureToHumidity.correspondsTo(temperature);
        BigDecimal location = _humidityToLocation.correspondsTo(humidity);

        return location;
    }

    //Seed 79, soil 81, fertilizer 81, water 81, light 74, temperature 78, humidity 78, location 82.
    MappingThingy _seedToSoil = new MappingThingy(Arrays.asList("50 98 2", "52 50 48"));
    MappingThingy _soilToFertilizer = new MappingThingy(Arrays.asList("0 15 37",
            "37 52 2",
            "39 0 15"));
    MappingThingy _fertilizerToWater = new MappingThingy(Arrays.asList("49 53 8",
            "0 11 42",
            "42 0 7",
            "57 7 4"));
    MappingThingy _waterToLight = new MappingThingy(Arrays.asList("88 18 7",
            "18 25 70"));
    MappingThingy _lightToTemperature = new MappingThingy(Arrays.asList("45 77 23",
            "81 45 19",
            "68 64 13"));
    MappingThingy _temperatureToHumidity = new MappingThingy(Arrays.asList("0 69 1",
            "1 0 69"));
    MappingThingy _humidityToLocation = new MappingThingy(Arrays.asList("60 56 37",
            "56 93 4"));

    private List<BigDecimal> seedListFrom(String inputLine) {
        String[] seedNumbers = inputLine.substring(7).split(" ");
        List<BigDecimal> seeds = new ArrayList<>();
        for (String seedStr :
                seedNumbers) {
            seeds.add(new BigDecimal(Integer.parseInt(seedStr)));
        }
        return seeds;
    }

    private class MappingThingy {
        private final TreeMap<BigDecimal, Range> ranges;

        public MappingThingy(List<String> input) {
            ranges = new TreeMap();
            // TODO construct from List<String> of input lines
            for (String inputLine :
                    input) {
                String[] chunks = inputLine.split(" ");
                BigDecimal sourceStart = new BigDecimal(Integer.parseInt(chunks[1]));
                BigDecimal destination = new BigDecimal(Integer.parseInt(chunks[0]));
                // TODO ignoring 3rd param for now - will probaby bite me in the real data
                // since the test data lines up nicely and doesn't have gaps
                // To fix, at the end of the run, add a new BigDecimal and a modifier of 0
                BigDecimal modifier = destination.subtract(sourceStart);
                Range r = new Range(sourceStart, destination, new BigDecimal(Integer.parseInt(chunks[2])), modifier);
                ranges.put(sourceStart, r);
            }
        }

        class Range {
            final BigDecimal sourceStart;
            final BigDecimal destination;
            final BigDecimal length;
            final BigDecimal modifier;

            public Range(BigDecimal sourceStart, BigDecimal destination, BigDecimal length, BigDecimal modifier) {
                this.sourceStart = sourceStart;
                this.destination = destination;
                this.length = length;
                this.modifier = modifier;
            }
        }

        public BigDecimal correspondsTo(BigDecimal source) {
            Set<BigDecimal> bigDecimals = ranges.keySet();
            for (BigDecimal sourceStart:
                    bigDecimals) {

                if (source.compareTo(sourceStart) >= 0) {
                    Range r = ranges.get(sourceStart);
                    if (source.compareTo(sourceStart.add(r.length)) < 0) {
                        BigDecimal corresponds = source.add(r.modifier);
                        return corresponds;
                    }
                }
            }

            return source;
        }
    }
}
