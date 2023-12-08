package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08 {

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


    class Node {

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
