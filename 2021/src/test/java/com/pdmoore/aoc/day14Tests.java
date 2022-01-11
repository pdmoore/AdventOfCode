package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day14Tests {

    @Test
    void day14_part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example");

        BigInteger actual = solve(input, 10);

        BigInteger expected = new BigInteger("1588");
        assertEquals(expected, actual);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void day14_part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14");

        BigInteger actual = solve(input, 10);

        BigInteger expected = new BigInteger("2988");
        assertEquals(expected, actual);
    }

    @Test
    void day14_part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example");

        BigInteger actual = solve(input, 40);

        BigInteger expected = new BigInteger("2188189693529");
        assertEquals(expected, actual);
    }

    @Test
    void day14_part2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14");

        BigInteger actual = solve(input, 40);

        BigInteger expected = new BigInteger("3572761917024");
        assertEquals(expected, actual);
    }

    private BigInteger solve(List<String> input, int stepCount) {
        Map<String, String[]> pairToPairMappings = buildPairToPairMappings(input);

        String polymerTemplate = input.get(0);
        Map<String, BigInteger> polymerPairCountMap = buildFirstMappingWithCount(polymerTemplate);

        for (int i = 0; i < stepCount; i++) {
            polymerPairCountMap = expandPolymerPairs(polymerPairCountMap, pairToPairMappings);
        }

        Character veryFirstCharacter = input.get(0).charAt(0);
        Map<Character, BigInteger> characterCount = countCharactersInPolymer(polymerPairCountMap, veryFirstCharacter);
        return calcMaxMinusMin(characterCount);
    }

    private Map<String, String[]> buildPairToPairMappings(List<String> input) {
        Map<String, String[]> pairToPairMappings = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            createInsertions(input.get(i).split(" -> "), pairToPairMappings);
        }
        return pairToPairMappings;
    }

    private BigInteger calcMaxMinusMin(Map<Character, BigInteger> characterCount) {
        BigInteger max = characterCount.values().stream().max(BigInteger::compareTo).get();
        BigInteger min = characterCount.values().stream().min(BigInteger::compareTo).get();
        return max.subtract(min);
    }

    private Map<Character, BigInteger> countCharactersInPolymer(Map<String, BigInteger> polymerMap, Character firstCharacterInPolymer) {
        Map<Character, BigInteger> charMap = new HashMap<>();
        BigInteger countOfFirstCharacter = charMap.getOrDefault(firstCharacterInPolymer, BigInteger.ZERO);
        charMap.put(firstCharacterInPolymer, countOfFirstCharacter.add(BigInteger.ONE));
        for (String key : polymerMap.keySet()) {
            char c = key.charAt(1);
            BigInteger n = polymerMap.get(key);

            BigInteger charN = charMap.getOrDefault(c, BigInteger.ZERO);
            charMap.put(c, charN.add(n));
        }
        return charMap;
    }

    private Map<String, BigInteger> expandPolymerPairs(Map<String, BigInteger> polymerMap, Map<String, String[]> pairToPairMappings) {
        Map<String, BigInteger> transformedMap = new HashMap<>();

        for (String key : polymerMap.keySet()) {
            BigInteger currentKeyCount = polymerMap.get(key);

            String lhsAndInsertion = pairToPairMappings.get(key)[0];
            BigInteger lhsCount = transformedMap.getOrDefault(lhsAndInsertion, BigInteger.ZERO);
            transformedMap.put(lhsAndInsertion, lhsCount.add(currentKeyCount));

            String insertionAndRhs = pairToPairMappings.get(key)[1];
            BigInteger rhsCount = transformedMap.getOrDefault(insertionAndRhs, BigInteger.ZERO);
            transformedMap.put(insertionAndRhs, rhsCount.add(currentKeyCount));
        }

        return transformedMap;
    }

    private Map<String, BigInteger> buildFirstMappingWithCount(String line) {
        Map<String, BigInteger> map = new HashMap<>();
        for (int i = 0; i < line.length() - 1; i++) {
            String s = line.substring(i, i + 2);
            BigInteger n = map.getOrDefault(s, BigInteger.ZERO);
            map.put(s, n.add(BigInteger.ONE));
        }
        return map;
    }

    private void createInsertions(String[] mappingLine, Map<String, String[]> pairToPairMappings) {
        String lhs = mappingLine[0];
        String rhs = mappingLine[1];
        String[] replace = {lhs.charAt(0) + rhs, rhs + lhs.charAt(1)};
        pairToPairMappings.put(lhs, replace);
    }
}
