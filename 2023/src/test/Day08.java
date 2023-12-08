package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08 {

    @Test
    void nod_data_structure_from_input_line() {

        String inputLine = "AAA = (BBB, CCC)";
        Node n = new Node(inputLine);
        assertEquals("AAA", n.key);
        assertEquals("BBB", n.left);
        assertEquals("CCC", n.right);
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day08_part1_example");
        int actual = stepCountToFind(input, "ZZZ");

        assertEquals(2, actual);
    }

    @Test
    void part1_solutions() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day08");
        int actual = stepCountToFind(input, "ZZZ");

        assertEquals(12361, actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day08_part2_example");
        BigInteger actual = stepCountToFindAllZ(input);

        assertEquals(new BigInteger("" + 6), actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day08");
        BigInteger actual = stepCountToFindAllZ(input);

        assertEquals(new BigInteger("18215611419223"), actual);
    }

    private BigInteger stepCountToFindAllZ(List<String> input) {
        String instructions = input.get(0);

        Map<String, Node> nodeMap = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            Node node = new Node(input.get(i));
            nodeMap.put(node.key, node);
        }

        List<String> currentKeys = new ArrayList<>();
        for (String key :
                nodeMap.keySet()) {
            if (key.endsWith("A")) {
                currentKeys.add(key);
            }
        }

        // for each key, get the stepCount
        // find the LCM of ll those stepCounts
        List<BigInteger> stepCounts = new ArrayList<>();
        for (String key :
                currentKeys) {
            int instructionIndex = 0;
            int stepCount = 0;
            System.out.print("key " + key);
            while (!key.endsWith("Z")) {
                stepCount++;

                if (instructions.charAt(instructionIndex) == 'L') {
                    key = nodeMap.get(key).left;
                } else {
                    key = nodeMap.get(key).right;
                }

                instructionIndex++;
                if (instructionIndex >= instructions.length()) {
                    instructionIndex = 0;
                }
            }

            stepCounts.add(new BigInteger("" + stepCount));
        }

        /*
            key DPA - stepcount 20777
            key QLA - stepcount 19199
            key VJA - stepcount 18673
            key AAA - stepcount 12361
            key XQA - stepcount 15517
            key GTA - stepcount 16043
            Plugged these stepcounts into a LCM calculator and got the right answer
         */
        BigInteger lcm = stepCounts.get(0);
        for (int i = 1; i < stepCounts.size(); i++) {
            lcm = lcm(lcm, stepCounts.get(i));
        }

        return lcm;
    }

    public static BigInteger lcm(BigInteger s, BigInteger s1)
    {
        // calculate multiplication of two bigintegers
        BigInteger mul = s.multiply(s1);

        // calculate gcd of two bigintegers
        BigInteger gcd = s.gcd(s1);

        // calculate lcm using formula: lcm * gcd = x * y
        BigInteger lcm = mul.divide(gcd);
        return lcm;
    }

    private int stepCountToFind(List<String> input, String target) {
        int stepCount = 0;
        String instructions = input.get(0);

        Map<String, Node> nodeMap = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            Node node = new Node(input.get(i));
            nodeMap.put(node.key, node);
        }

        int instructionIndex = 0;
        String currentKey = "AAA";
        while (!currentKey.equals("ZZZ")) {
            stepCount++;
            if (instructions.charAt(instructionIndex) == 'L') {
                currentKey = nodeMap.get(currentKey).left;
            } else {
                currentKey = nodeMap.get(currentKey).right;
            }
            instructionIndex++;
            if (instructionIndex >= instructions.length()) {
                instructionIndex = 0;
            }
        }


        return stepCount;
    }


    static class Node {

        public String key;
        public String left;
        public String right;

        public Node(String inputLine) {
            this.key = inputLine.substring(0, 3);
            this.left = inputLine.substring(7, 10);
            this.right = inputLine.substring(12, 15);
        }
    }
}
