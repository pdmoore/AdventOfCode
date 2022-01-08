package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
    @Timeout(value = 150, unit = TimeUnit.MILLISECONDS)
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

        List<Character> part2_solution = solve(input, 40);
        Map<Character, BigInteger> occurrences = countOccurrences(part2_solution);
        BigInteger actual = countOfMostCommon(occurrences).subtract(countOfLeastCommmon(occurrences));

        BigInteger expected = new BigInteger("2188189693529");

        assertEquals(expected, actual);
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


    private LinkedList<Character> applyTransformations(Map<String, Character> transforms, LinkedList<Character> polymer) {
        LinkedList<Character> result = new LinkedList<>();

        result.add(polymer.get(0));

        for (int i = 0; i < polymer.size() - 1; i++) {
            String key = "" + polymer.get(i) + polymer.get(i+1);
            result.add(transforms.get(key));
            result.add(polymer.get(i+1));
        }

        return result;

//        int i = 0;
//        while (i < polymer.size() - 1) {
//            String key = "" + polymer.get(i) + polymer.get(i+1);
//            polymer.add(i + 1, transforms.get(key));
//            i += 2;
//        }
//
//        return polymer;

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

        //TODO - create LL at once, not convert
        List<Character> polymer = polymerTemplate.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        LinkedList<Character> polymer2 = new LinkedList<>(polymer);

        Node head = new Node(polymerTemplate.charAt(0));
        Node current = head;
        for (int i = 1; i < polymerTemplate.length(); i++) {
            Node next = new Node(polymerTemplate.charAt(i));
            current.next = next;
            current = next;
        }

        for (int i = 1; i <= steps; i++) {
//            polymer2 = applyTransformations(pairInsertions, polymer2);
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
