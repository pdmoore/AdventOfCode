package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day14Tests {

    @Test
    void day14_part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example");

        String transformed = solvePart1(input);
        Map<Character, BigInteger> occurrences = countOccurrences(transformed);
        BigInteger actual = countOfMostCommon(occurrences).subtract(countOfLeastCommmon(occurrences));

        BigInteger expected = new BigInteger("1588");
        assertEquals(expected, actual);
    }

    @Test
    void day14_part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14");

        String transformed = solvePart1(input);
        Map<Character, BigInteger> occurrences = countOccurrences(transformed);
        BigInteger actual = countOfMostCommon(occurrences).subtract(countOfLeastCommmon(occurrences));

        BigInteger expected = new BigInteger("2988");
        assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void day14_part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example");

        // Need to deal with character array and BigInteger
        // LinkedList<Character> maybe?
        // max string length is 2,147,483,647 characters
        // May also need to spread the polymer across multiple "large" strings
        // and then work across those one-by-one
        // Probably solve part 1 with the new logic first, then expand to 40 iterations

        String transformed = solvePart1(input);
        Map<Character, BigInteger> occurrences = countOccurrences(transformed);
        BigInteger actual = countOfMostCommon(occurrences).subtract(countOfLeastCommmon(occurrences));

        BigInteger expected = new BigInteger("2188189693529");

        assertEquals(expected, actual);
    }

    // TODO - convert this to use List<Character> instead of string...
    // probably add as third param, switch over, then remove existing 'String polymer' param
    // Will eventually hit countOccurrences and CountOf
    private String applyTransformations(Map<String, Character> transforms, String polymer) {
        StringBuilder result = new StringBuilder();
        result.append(polymer.charAt(0));
        for (int i = 0; i < polymer.length() - 1; i++) {
            String key = polymer.substring(i, i+2);
            result.append(transforms.get(key));
            result.append(polymer.charAt(i+1));
        }

        return result.toString();
    }

    private Map<Character, BigInteger> countOccurrences(String transformed) {
        Map<Character, BigInteger> result = new HashMap<>();
        for (int i = 0; i < transformed.length(); i++) {
            Character key = transformed.charAt(i);
            if (!result.containsKey(key)) {
                result.put(key, countOf(key, transformed));
            }
        }
        return result;
    }

    private BigInteger countOf(Character key, String polymer) {
        BigInteger count = BigInteger.ZERO;
        for (int i = 0; i < polymer.length(); i++) {
            if (key.equals(polymer.charAt(i)))
                count = count.add(BigInteger.ONE);
        }
        return count;
    }

    private String solvePart1(List<String> input) {
        String polymerTemplate = input.get(0);

        Map<String, Character> pairInsertions = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            String mapping = input.get(i);
            String key = mapping.substring(0, 2);
            Character value = mapping.charAt(6);
            pairInsertions.put(key, value);
        }

        String polymer = polymerTemplate;
        for (int i = 1; i <= 10; i++) {
            polymer = applyTransformations(pairInsertions, polymer);
        }

        return polymer;
    }

    private BigInteger countOfLeastCommmon(Map<Character, BigInteger> map) {
        Map.Entry<Character, BigInteger> minEntry =
                Collections.min(map.entrySet(), (Map.Entry<Character, BigInteger> e1, Map.Entry<Character, BigInteger> e2) -> e1.getValue()
                        .compareTo(e2.getValue()));
        return minEntry.getValue();
    }

    private BigInteger countOfMostCommon(Map<Character, BigInteger> map) {
        Map.Entry<Character, BigInteger> maxEntry =
                Collections.max(map.entrySet(), (Map.Entry<Character, BigInteger> e1, Map.Entry<Character, BigInteger> e2) -> e1.getValue()
                .compareTo(e2.getValue()));
        return maxEntry.getValue();
    }
}
