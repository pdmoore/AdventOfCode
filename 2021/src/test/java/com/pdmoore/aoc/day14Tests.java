package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day14Tests {

    @Test
    void day14_part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example");

        List<Character> transformed = solve(input);
        Map<Character, BigInteger> occurrences = countOccurrences(transformed);
        BigInteger actual = countOfMostCommon(occurrences).subtract(countOfLeastCommmon(occurrences));

        BigInteger expected = new BigInteger("1588");
        assertEquals(expected, actual);
    }

    @Test
    void day14_part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14");

        List<Character> transformed = solve(input);
        Map<Character, BigInteger> occurrences = countOccurrences(transformed);
        BigInteger actual = countOfMostCommon(occurrences).subtract(countOfLeastCommmon(occurrences));

        BigInteger expected = new BigInteger("2988");
        assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void day14_part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example");

        List<Character> part2_solution = solve(input);
        Map<Character, BigInteger> occurrences = countOccurrences(part2_solution);
        BigInteger actual = countOfMostCommon(occurrences).subtract(countOfLeastCommmon(occurrences));

        BigInteger expected = new BigInteger("2188189693529");

        assertEquals(expected, actual);
    }

    private List<Character> applyTransformations(Map<String, Character> transforms, List<Character> polymer) {
        List<Character> result = new LinkedList<>();
        result.add(polymer.get(0));

        for (int i = 0; i < polymer.size() - 1; i++) {
            String key = "" + polymer.get(i) + polymer.get(i+1);
            result.add(transforms.get(key));
            result.add(polymer.get(i+1));
        }

        return result;
    }

    private Map<Character, BigInteger> countOccurrences(List<Character> part2_solution) {
        Map<Character, BigInteger> result = new HashMap<>();

        for (Character c:
             part2_solution) {
            if (!result.containsKey(c)) {
                result.put(c, countOf(c, part2_solution));
            }
        }

        return result;
    }

    private BigInteger countOf(Character key, List<Character> polymer) {
        BigInteger count = BigInteger.ZERO;

        for (Character c :
                polymer) {
            if (key.equals(c))
                count = count.add(BigInteger.ONE);
        }

        return count;
    }

    private List<Character> solve(List<String> input) {
        String polymerTemplate = input.get(0);

        Map<String, Character> pairInsertions = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            String mapping = input.get(i);
            String key = mapping.substring(0, 2);
            Character value = mapping.charAt(6);
            pairInsertions.put(key, value);
        }

        List<Character> polymer = polymerTemplate.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
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
