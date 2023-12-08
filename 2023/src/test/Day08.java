package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08 {

    @Test
    void something() {

        String inputLine = "AAA = (BBB, CCC)";
        Node n = new Node(inputLine);
        assertEquals("AAA", n.key);
        assertEquals("BBB", n.left);
        assertEquals("CCC", n.right);
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
