package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05 {

    MappingConversion _seedToSoil = new MappingConversion(Arrays.asList("50 98 2", "52 50 48"));
    MappingConversion _soilToFertilizer = new MappingConversion(Arrays.asList("0 15 37",
            "37 52 2",
            "39 0 15"));
    MappingConversion _fertilizerToWater = new MappingConversion(Arrays.asList("49 53 8",
            "0 11 42",
            "42 0 7",
            "57 7 4"));
    MappingConversion _waterToLight = new MappingConversion(Arrays.asList("88 18 7",
            "18 25 70"));
    MappingConversion _lightToTemperature = new MappingConversion(Arrays.asList("45 77 23",
            "81 45 19",
            "68 64 13"));
    MappingConversion _temperatureToHumidity = new MappingConversion(Arrays.asList("0 69 1",
            "1 0 69"));
    MappingConversion _humidityToLocation = new MappingConversion(Arrays.asList("60 56 37",
            "56 93 4"));

    @Test
    void convert_large_string_to_big_decimal() {
        BigInteger expected = BigInteger.valueOf(4188359137L);
        BigInteger actual = new BigInteger("4188359137");
        assertEquals(expected, actual);
    }

    @Test
    void get_seed_list_from_input() {
        List<BigInteger> actual = seedListFrom("seeds: 79 14 55 13");

        List<BigInteger> expected = new ArrayList<>();
        expected.add(BigInteger.valueOf(79));
        expected.add(BigInteger.valueOf(14));
        expected.add(BigInteger.valueOf(55));
        expected.add(BigInteger.valueOf(13));

        assertEquals(expected, actual);
    }

    @Test
    void find_location_given_seed() {
        BigInteger seedNumber = BigInteger.valueOf(79);

        // Seed 79, soil 81, fertilizer 81, water 81, light 74, temperature 78, humidity 78, location 82.
        BigInteger actual = findLocationForSeed(seedNumber);

        BigInteger expected = BigInteger.valueOf(82);
        assertEquals(expected, actual);
    }

    @Test
    void construct_mapping_conversion_for_example_seed_to_soil() {
        //seed-to-soil map:
        List<String> input = Arrays.asList("50 98 2", "52 50 48");

        MappingConversion sut = new MappingConversion(input);

        assertEquals(BigInteger.valueOf(1), sut.correspondsTo(BigInteger.valueOf(1)));
        assertEquals(BigInteger.valueOf(10), sut.correspondsTo(BigInteger.valueOf(10)));
        assertEquals(BigInteger.valueOf(49), sut.correspondsTo(BigInteger.valueOf(49)));
        assertEquals(BigInteger.valueOf(52), sut.correspondsTo(BigInteger.valueOf(50)));
        assertEquals(BigInteger.valueOf(53), sut.correspondsTo(BigInteger.valueOf(51)));
        assertEquals(BigInteger.valueOf(55), sut.correspondsTo(BigInteger.valueOf(53)));
        assertEquals(BigInteger.valueOf(98), sut.correspondsTo(BigInteger.valueOf(96)));
        assertEquals(BigInteger.valueOf(99), sut.correspondsTo(BigInteger.valueOf(97)));
        assertEquals(BigInteger.valueOf(50), sut.correspondsTo(BigInteger.valueOf(98)));
        assertEquals(BigInteger.valueOf(51), sut.correspondsTo(BigInteger.valueOf(99)));
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day05_part1_example");

        List<BigInteger> seeds = seedListFrom(input.get(0));
        assertEquals(4, seeds.size());

        populateMappingThingy(input);
        assertEquals(BigInteger.valueOf(81), _seedToSoil.correspondsTo(BigInteger.valueOf(79)));
        assertEquals(BigInteger.valueOf(81), _soilToFertilizer.correspondsTo(BigInteger.valueOf(81)));
        assertEquals(BigInteger.valueOf(81), _fertilizerToWater.correspondsTo(BigInteger.valueOf(81)));
        assertEquals(BigInteger.valueOf(74), _waterToLight.correspondsTo(BigInteger.valueOf(81)));
        assertEquals(BigInteger.valueOf(78), _lightToTemperature.correspondsTo(BigInteger.valueOf(74)));
        assertEquals(BigInteger.valueOf(78), _temperatureToHumidity.correspondsTo(BigInteger.valueOf(78)));
        assertEquals(BigInteger.valueOf(82), _humidityToLocation.correspondsTo(BigInteger.valueOf(78)));

        BigInteger lowest = BigInteger.valueOf(999999999);
        for (BigInteger seed :
                seeds) {

            BigInteger location = findLocationForSeed(seed);
            if (location.compareTo(lowest) < 0) {
                lowest = location;
            }
        }

        assertEquals(BigInteger.valueOf(35), lowest);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day05");

        List<BigInteger> seeds = seedListFrom(input.get(0));
        assertEquals(20, seeds.size());

        populateMappingThingy(input);

        BigInteger lowest = BigInteger.valueOf(999999999);
        for (BigInteger seed :
                seeds) {

            BigInteger location = findLocationForSeed(seed);
            if (location.compareTo(lowest) < 0) {
                lowest = location;
            }
        }

        assertEquals(BigInteger.valueOf(26273516), lowest);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day05_part1_example");
        List<BigInteger> seeds = seedListFrom(input.get(0));
        populateMappingThingy(input);

        BigInteger lowest = BigInteger.valueOf(999999999);
        for (int i = 0; i < seeds.size(); i += 2) {
            BigInteger startWith = seeds.get(i);
            BigInteger rangeLength = seeds.get(i+1);

            BigInteger upperLimit = startWith.add(rangeLength);
            for (BigInteger seed = startWith; seed.compareTo(upperLimit) < 0; seed = seed.add(BigInteger.ONE)) {
                BigInteger location = findLocationForSeed(seed);
                if (location.compareTo(lowest) < 0) {
                    lowest = location;
                }
            }
        }

        assertEquals(BigInteger.valueOf(46), lowest);
    }

    @Test
    @Disabled("Correct answer, takes 40+ minutes to get there")
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day05");
        List<BigInteger> seeds = seedListFrom(input.get(0));
        populateMappingThingy(input);

        BigInteger lowest = BigInteger.valueOf(9999999999L);
        int count = 1;
        for (int i = 0; i < seeds.size(); i += 2) {
            BigInteger startWith = seeds.get(i);
            System.out.println("Lowest so far: " + lowest);
            System.out.println("seeds start: " + startWith + " --" + LocalDateTime.now());
            BigInteger rangeLength = seeds.get(i+1);

            BigInteger upperLimit = startWith.add(rangeLength);

            // TODO - brute force, answer is in the second range....
            // Could try skipping a lot of values by checking
            // seed -> location
            // seed+100 -> location
            // and if the difference between the two is the same, skip all 98 in between
            // if diff between two is not same, divide into 50/25/10 back down to each 1
            for (BigInteger seed = startWith; seed.compareTo(upperLimit) < 0; seed = seed.add(BigInteger.ONE)) {
                BigInteger location = findLocationForSeed(seed);
                if (location.compareTo(lowest) < 0) {
                    lowest = location;
                }
                count++;
                if (count % 1000000 == 0) {
                    System.out.println("on it: " + count );
                }
            }
        }

        assertEquals(BigInteger.valueOf(34039469), lowest);
    }


    // ---------------------------------
    private BigInteger findLocationForSeed(BigInteger seedNumber) {
        BigInteger soil = _seedToSoil.correspondsTo(seedNumber);
        BigInteger fertilizer = _soilToFertilizer.correspondsTo(soil);
        BigInteger water = _fertilizerToWater.correspondsTo(fertilizer);
        BigInteger light = _waterToLight.correspondsTo(water);
        BigInteger temperature = _lightToTemperature.correspondsTo(light);
        BigInteger humidity = _temperatureToHumidity.correspondsTo(temperature);

        return _humidityToLocation.correspondsTo(humidity);
    }

    private List<BigInteger> seedListFrom(String inputLine) {
        String[] seedNumbers = inputLine.substring(7).split(" ");
        List<BigInteger> seeds = new ArrayList<>();
        for (String seedStr :
                seedNumbers) {
            seeds.add(new BigInteger(seedStr));
        }
        return seeds;
    }

    private static class MappingConversion {
        private final TreeMap<BigInteger, Range> ranges;

        public MappingConversion(List<String> input) {
            ranges = new TreeMap<>();
            for (String inputLine :
                    input) {
                String[] chunks = inputLine.split(" ");
                BigInteger sourceStart = new BigInteger(chunks[1]);
                BigInteger destination = new BigInteger(chunks[0]);
                BigInteger modifier = destination.subtract(sourceStart);
                Range r = new Range(sourceStart, destination, BigInteger.valueOf(Integer.parseInt(chunks[2])), modifier);
                ranges.put(sourceStart, r);
            }
        }

        static class Range {
            final BigInteger sourceStart;
            final BigInteger destination;
            final BigInteger length;
            final BigInteger modifier;

            public Range(BigInteger sourceStart, BigInteger destination, BigInteger length, BigInteger modifier) {
                this.sourceStart = sourceStart;
                this.destination = destination;
                this.length = length;
                this.modifier = modifier;
            }
        }

        public BigInteger correspondsTo(BigInteger source) {
            Set<BigInteger> bigDecimals = ranges.keySet();
            for (BigInteger sourceStart:
                    bigDecimals) {

                if (source.compareTo(sourceStart) >= 0) {
                    Range r = ranges.get(sourceStart);
                    if (source.compareTo(sourceStart.add(r.length)) < 0) {
                        return source.add(r.modifier);
                    }
                }
            }

            return source;
        }
    }

    private void populateMappingThingy(List<String> input) {
        int index = 0;
        while (index < input.size()) {
            String inputLine = input.get(index++);
            if (inputLine.isEmpty() || inputLine.startsWith("seeds:")) {
                continue;
            }

            String thisMappingThingy = inputLine.split(":")[0];

            inputLine = input.get(index++);
            List<String> mappings = new ArrayList<>();
            while (index < input.size() && !inputLine.isEmpty()) {
                mappings.add(inputLine);
                inputLine = input.get(index++);
            }

            if (thisMappingThingy.startsWith("seed-to")) {
                _seedToSoil = new MappingConversion(mappings);
            } else if (thisMappingThingy.startsWith("soil-to")) {
                _soilToFertilizer = new MappingConversion(mappings);
            } else if (thisMappingThingy.startsWith("fertilizer-to")) {
                _fertilizerToWater = new MappingConversion(mappings);
            } else if (thisMappingThingy.startsWith("water-to")) {
                _waterToLight = new MappingConversion(mappings);
            } else if (thisMappingThingy.startsWith("light-to")) {
                _lightToTemperature = new MappingConversion(mappings);
            } else if (thisMappingThingy.startsWith("temperature-to")) {
                _temperatureToHumidity = new MappingConversion(mappings);
            } else if (thisMappingThingy.startsWith("humidity-to")) {
                _humidityToLocation = new MappingConversion(mappings);
            }
        }
    }
}
