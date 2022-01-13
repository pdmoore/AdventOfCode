package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.awt.*;
import java.util.*;
import java.util.List;
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
                    pq.addAll(neighbors);
                }
            }

            throw new RuntimeException("Could not find a path to bottom right");
        }

        private List<Node> gatherUnvisitedNeighbors(Node current) {
            List<Node> neighbors = new LinkedList<>();

            int newX = current.x - 1;
            int newY = current.y;
            if((newX > 0 && newX < cavernMap.length) && cavernMap[newX][newY] != 0) {
                neighbors.add(new Node(newX, newY, current.totalRiskFromStart + cavernMap[newX][newY]));
            }

            newX = current.x + 1;
            newY = current.y;
            if((newX > 0 && newX < cavernMap.length) && cavernMap[newX][newY] != 0) {
                neighbors.add(new Node(newX, newY, current.totalRiskFromStart + cavernMap[newX][newY]));
            }

            newX = current.x;
            newY = current.y - 1;
            if((newY > 0 && newY < cavernMap.length) && cavernMap[newX][newY] != 0) {
                neighbors.add(new Node(newX, newY, current.totalRiskFromStart + cavernMap[newX][newY]));
            }

            newX = current.x;
            newY = current.y + 1;
            if((newY > 0 && newY < cavernMap.length) && cavernMap[newX][newY] != 0) {
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
        }
    }
}
