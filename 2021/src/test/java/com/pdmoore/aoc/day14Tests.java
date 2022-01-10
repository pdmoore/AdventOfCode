package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day14Tests {

    private char firstCharacterInPolymer;
    private static Map<String, String[]> pairToPairMappings = new HashMap<>();

    @Test
    void day14_part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example");

        Map<String, BigInteger> polymerMap = processInput(input);
//TODO - rather than global variables,
// pass in the polymerMap and stepCOunt, get the answer out

        polymerMap = solve_new(polymerMap, 10);

        Map<Character, BigInteger> characterCount = countResultingCharacters(polymerMap);
        BigInteger actual = calcMaxMinusMin(characterCount);

        BigInteger expected = new BigInteger("1588");
        assertEquals(expected, actual);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void day14_part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14");

        Map<String, BigInteger> polymerMap = processInput(input);
        polymerMap = solve_new(polymerMap, 10);

        Map<Character, BigInteger> characterCount = countResultingCharacters(polymerMap);
        BigInteger actual = calcMaxMinusMin(characterCount);

        BigInteger expected = new BigInteger("2988");
        assertEquals(expected, actual);
    }

    @Test
    void day14_part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example");

        Map<String, BigInteger> polymerMap = processInput(input);
        polymerMap = solve_new(polymerMap, 40);

        Map<Character, BigInteger> characterCount = countResultingCharacters(polymerMap);
        BigInteger actual = calcMaxMinusMin(characterCount);

        BigInteger expected = new BigInteger("2188189693529");
        assertEquals(expected, actual);
    }

    @Test
    void day14_part2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14");

        Map<String, BigInteger> polymerMap = processInput(input);
        polymerMap = solve_new(polymerMap, 40);

        Map<Character, BigInteger> characterCount = countResultingCharacters(polymerMap);
        BigInteger actual = calcMaxMinusMin(characterCount);

        BigInteger expected = new BigInteger("3572761917024");
        assertEquals(expected, actual);
    }

    private BigInteger calcMaxMinusMin(Map<Character, BigInteger> characterCount) {
        BigInteger max = characterCount.values().stream().max(BigInteger::compareTo).get();
        BigInteger min = characterCount.values().stream().min(BigInteger::compareTo).get();
        return max.subtract(min);
    }

    private Map<Character, BigInteger> countResultingCharacters(Map<String, BigInteger> polymerMap) {
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

    private Map<String, BigInteger> solve_new(Map<String, BigInteger> polymerMap, int steps) {
        for (int i = 0; i < steps; i++) {
            polymerMap = transform(polymerMap);
        }
        return polymerMap;
    }

    private Map<String, BigInteger> transform(Map<String, BigInteger> polymerMap) {
        Map<String, BigInteger> transformedMap = new HashMap<>();

        for (String key : polymerMap.keySet()) {
            BigInteger countKey = polymerMap.get(key);

            if (pairToPairMappings.containsKey(key)) {
                String mapsTo_1 = pairToPairMappings.get(key)[0];
                String mapsTo_2 = pairToPairMappings.get(key)[1];
                BigInteger count1 = transformedMap.getOrDefault(mapsTo_1, BigInteger.ZERO);
                BigInteger count2 = transformedMap.getOrDefault(mapsTo_2, BigInteger.ZERO);
                transformedMap.put(mapsTo_1, count1.add(countKey));
                transformedMap.put(mapsTo_2, count2.add(countKey));
            } else {
                transformedMap.put(key, countKey);
            }
        }

        return transformedMap;
    }

    private Map<String, BigInteger> processInput(List<String> input) {

        String startTemplate = input.get(0);
        firstCharacterInPolymer = startTemplate.charAt(0);
        for (int i = 2; i < input.size(); i++) {
            createInsertions(input.get(i).split(" -> "));
        }

        return createFirstMappingWithCount(startTemplate);
    }

    private Map<String, BigInteger> createFirstMappingWithCount(String line) {
        Map<String, BigInteger> map = new HashMap<>();
        for (int i = 0; i < line.length() - 1; i++) {
            String s = line.substring(i, i + 2);
            BigInteger n = map.getOrDefault(s, BigInteger.ZERO);
            map.put(s, n.add(BigInteger.ONE));
        }
        return map;
    }

    private void createInsertions(String[] mappingLine) {
        String lhs = mappingLine[0];
        String rhs = mappingLine[1];
        String[] replace = {lhs.charAt(0) + rhs, rhs + lhs.charAt(1)};
        pairToPairMappings.put(lhs, replace);
    }
}
