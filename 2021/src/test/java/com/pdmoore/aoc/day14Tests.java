package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day14Tests {

    private char veryFirstChar;
    private static Map<String, String[]> insertions = new HashMap<>();

    @Test
    void day14_part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14_example");

        Map<String, BigInteger> polymerMap = processInput(input);
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
        BigInteger max = BigInteger.ZERO;
        BigInteger min = new BigInteger("999999999999999");

        for (Character c : characterCount.keySet()) {
            if (characterCount.get(c).compareTo(max) > 0) {
                max = characterCount.get(c);
            } else if (characterCount.get(c).compareTo(min) < 0) {
                min = characterCount.get(c);
            }
        }

        return max.subtract(min);
    }

    private Map<Character, BigInteger> countResultingCharacters(Map<String, BigInteger> polymerMap) {
        Map<Character, BigInteger> charMap = new HashMap<>();
        BigInteger firstN = charMap.getOrDefault(veryFirstChar, BigInteger.ZERO);
        charMap.put(veryFirstChar, firstN.add(BigInteger.ONE));
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

            if (insertions.containsKey(key)) {
                String mapsTo_1 = insertions.get(key)[0];
                String mapsTo_2 = insertions.get(key)[1];
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
        veryFirstChar = startTemplate.charAt(0);
        for (int i = 2; i < input.size(); i++) {
            createInsertions(input.get(i).split(" -> "));
        }

        return createInitialMapping(startTemplate);
    }

    private Map<String, BigInteger> createInitialMapping(String line) {
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
        insertions.put(lhs, replace);
    }

    private void applyTransformations_Node(Map<String, Character> transforms, Node head) {
        Node current = head;

        while (current.next != null) {
            String key = "" + current.c + current.next.c;
            Node insert = new Node(transforms.get(key));
            insert.next = current.next;
            current.next = insert;
            current = insert.next;
        }
    }

    private Map<Character, BigInteger> countOccurrences(List<Character> part2_solution) {
        Map<Character, BigInteger> result = new HashMap<>();

        for (Character c :
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
        return solve(input, 10);
    }

    private List<Character> solve(List<String> input, int steps) {
        String polymerTemplate = input.get(0);

        Map<String, Character> pairInsertions = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            String mapping = input.get(i);
            String key = mapping.substring(0, 2);
            Character value = mapping.charAt(6);
            pairInsertions.put(key, value);
        }

        Node head = new Node(polymerTemplate.charAt(0));
        Node current = head;
        for (int i = 1; i < polymerTemplate.length(); i++) {
            Node next = new Node(polymerTemplate.charAt(i));
            current.next = next;
            current = next;
        }

        for (int i = 1; i <= steps; i++) {
            System.out.println("Step " + i);
            applyTransformations_Node(pairInsertions, head);
        }

        List<Character> result = new ArrayList<>();
        current = head;
        while (current != null) {
            result.add(current.c);
            current = current.next;
        }

        return result;
    }

    class Node {
        Character c;
        Node next;

        public Node(char c) {
            this.c = c;
        }
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
