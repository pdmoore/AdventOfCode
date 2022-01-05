package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

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
        Map<Character, Integer> occurrences = countOccurrences(transformed);
        int actual = countOfMostCommon(occurrences) - countOfLeastCommmon(occurrences);

        assertEquals(1588, actual);
    }

    @Test
    void day14_part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day14");

        String transformed = solvePart1(input);
        Map<Character, Integer> occurrences = countOccurrences(transformed);
        int actual = countOfMostCommon(occurrences) - countOfLeastCommmon(occurrences);

        assertEquals(2988, actual);
    }


    private Map<Character, Integer> countOccurrences(String transformed) {
        Map<Character, Integer> result = new HashMap<>();
        for (int i = 0; i < transformed.length(); i++) {
            Character key = transformed.charAt(i);
            if (!result.containsKey(key)) {
                result.put(key, countOf(key, transformed));
            }
        }
        return result;
    }

    private Integer countOf(Character key, String polymer) {
        int count = 0;
        for (int i = 0; i < polymer.length(); i++) {
            if (key.equals(polymer.charAt(i))) count++;
        }
        return count;
    }

    private String solvePart1(List<String> input) {
        String startingPolymer = input.get(0);

        Map<String, Character> transforms = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            String mapping = input.get(i);
            String key = mapping.substring(0, 2);
            Character value = mapping.charAt(6);
            transforms.put(key, value);
        }

        String polymer = startingPolymer;
        for (int i = 1; i <= 10; i++) {
            polymer = applyTransformations(transforms, polymer);
        }

        return polymer;
    }

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

    private int countOfLeastCommmon(Map<Character, Integer> map) {
        Map.Entry<Character, Integer> minEntry =
                Collections.min(map.entrySet(), (Map.Entry<Character, Integer> e1, Map.Entry<Character, Integer> e2) -> e1.getValue()
                        .compareTo(e2.getValue()));
        return minEntry.getValue();
    }

    private int countOfMostCommon(Map<Character, Integer> map) {
        Map.Entry<Character, Integer> maxEntry =
                Collections.max(map.entrySet(), (Map.Entry<Character, Integer> e1, Map.Entry<Character, Integer> e2) -> e1.getValue()
                .compareTo(e2.getValue()));
        return maxEntry.getValue();
    }
}
