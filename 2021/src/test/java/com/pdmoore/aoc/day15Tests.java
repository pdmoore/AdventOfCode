package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day15Tests {

    @Test
    void day15_part1_example() {
        int[][] input = PuzzleInput.as2dIntArray("data/day15_example");

        CavernMap sut = new CavernMap(input);
        int actual = sut.findLowestRiskPath();

        assertEquals(40, actual);
    }

    @Test
    @Timeout(value = 8, unit = TimeUnit.SECONDS)
    void day15_part1() {
        int[][] input = PuzzleInput.as2dIntArray("data/day15");

        CavernMap sut = new CavernMap(input);
        int actual = sut.findLowestRiskPath();

        assertEquals(441, actual);
    }

    @Test
    void day15_part2_example() {
        int[][] input = PuzzleInput.as2dIntArray("data/day15_example");
        int[][] fiveTimes = expandInput(input);

        CavernMap sut = new CavernMap(fiveTimes);
        int actual = sut.findLowestRiskPath();

        assertEquals(315, actual);
    }

    @Test
    void day15_part2() {
        int[][] input = PuzzleInput.as2dIntArray("data/day15");
        int[][] fiveTimes = expandInput(input);

        CavernMap sut = new CavernMap(fiveTimes);
        int actual = sut.findLowestRiskPath();

        // gives 2045 - too low
        assertEquals(-99, actual);
    }

    private class CavernMap {
        private final int[][] cavernMap;

        public CavernMap(int[][] input) {
            this.cavernMap = input;
        }

        public int findLowestRiskPath() {
            Node startingPoint = new Node(0, 0, 0);

            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(startingPoint);

            while (!pq.isEmpty()) {
                Node popped = pq.poll();

                if (popped.isBottomRight()) {
                    return popped.totalRiskFromStart;
                } else {
                    cavernMap[popped.x][popped.y] = 0;

                    List<Node> neighbors = gatherUnvisitedNeighbors(popped);
                    // Works for part 1, well under a second
                    for (Node n :
                            neighbors) {
                        if (!pq.contains(n)) pq.add(n);
                    }
                    // Following works, takes around 8 seconds for part1
//                    pq.addAll(neighbors);
                }
            }

            throw new RuntimeException("Could not find a path to bottom right");
        }

        private List<Node> gatherUnvisitedNeighbors(Node current) {
            List<Node> neighbors = new LinkedList<>();

            int newX = current.x - 1;
            int newY = current.y;
            if ((newX > 0 && newX < cavernMap.length) && cavernMap[newX][newY] != 0) {
                neighbors.add(new Node(newX, newY, current.totalRiskFromStart + cavernMap[newX][newY]));
            }

            newX = current.x + 1;
            newY = current.y;
            if ((newX > 0 && newX < cavernMap.length) && cavernMap[newX][newY] != 0) {
                neighbors.add(new Node(newX, newY, current.totalRiskFromStart + cavernMap[newX][newY]));
            }

            newX = current.x;
            newY = current.y - 1;
            if ((newY > 0 && newY < cavernMap.length) && cavernMap[newX][newY] != 0) {
                neighbors.add(new Node(newX, newY, current.totalRiskFromStart + cavernMap[newX][newY]));
            }

            newX = current.x;
            newY = current.y + 1;
            if ((newY > 0 && newY < cavernMap.length) && cavernMap[newX][newY] != 0) {
                neighbors.add(new Node(newX, newY, current.totalRiskFromStart + cavernMap[newX][newY]));
            }

            return neighbors;
        }


        class Node implements Comparable {
            int x;
            int y;
            int totalRiskFromStart;

            Node(int x, int y, int totalRiskFromStart) {
                this.x = x;
                this.y = y;
                this.totalRiskFromStart = totalRiskFromStart;
            }

            public boolean isBottomRight() {
                return x == cavernMap.length - 1 && y == cavernMap.length - 1;
            }

            @Override
            public int compareTo(Object o) {
                return this.totalRiskFromStart - ((Node) o).totalRiskFromStart;
            }

            @Override
            public boolean equals(Object o) {
                return this.x == ((Node) o).x &&
                        this.y == ((Node) o).y;

            }
        }
    }

    private int[][] expandInput(int[][] input) {
        int[][] expanded = new int[input.length * 5][input.length * 5];

        // 0..input.length
        // 1-4 across
        // 2-5 down

        // Copy the original array into the upper left
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input.length; y++) {
                expanded[x][y] = input[x][y];
            }
        }

        // across the first "row" of arrays, copy 2 from 1, 3 from 2, 4 from 3, 5 from 4
        for (int across = 0; across <= 3; across++) {
            for (int x = 0; x < input.length; x++) {
                for (int y = 0; y < input.length; y++) {
                    int newX = x;
                    int newY = y + input.length + (across * 10);

                    expanded[newX][newY] = Math.max(1, ((expanded[newX][newY - input.length]) + 1) % 10);
                }
            }
        }


        // down the columns, and across the rows, copy from the one immediately above
        // do this 4 times
        int downOffset = input.length;
        for (int down = 1; down < 5; down++) {

            downOffset = down * input.length;

            for (int across = 0; across <= 4; across++) {
                for (int x = 0; x < input.length; x++) {
                    for (int y = 0; y < input.length; y++) {
                        int newX = x + downOffset;
                        int newY = y + (across * input.length);

                        expanded[newX][newY] = Math.max(1, ((expanded[newX - input.length][newY]) + 1) % 10);
                    }
                }
            }
        }


        return expanded;
    }
}
