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

        // Current impl takes 10 seconds
        assertEquals(441, actual);
    }

    private class CavernMap {
        private final int[][] cavernMap;
        private final Point starting;
        private final Point ending;
        private int lowestRiskTotal;

        public CavernMap(int[][] input) {
            this.cavernMap = input;
            starting = new Point(0, 0);
            ending = new Point(cavernMap.length - 1, cavernMap.length - 1);
        }

        public int findLowestRiskPath() {
            Node startingPoint = new Node(0, 0, 0);

            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(startingPoint);

            while (!pq.isEmpty()) {
                Node popped = pq.poll();

                if (popped.isBottomRight()) {
                    return popped.distanceFromStart;
                } else {
                    cavernMap[popped.x][popped.y] = 0;

                    List<Node> neighbors = gatherUnvisitedNeighbors(popped);
                    pq.addAll(neighbors);
                }
            }

            return -1;  // this should be an exception - bottom right was never found
        }

        private List<Node> gatherUnvisitedNeighbors(Node current) {
            List<Node> neighbors = new LinkedList<>();

            if((current.x-1 > 0 && current.x-1 < cavernMap.length) && cavernMap[current.x-1][current.y] != 0) {
                int newX = current.x - 1;
                int newY = current.y;
                neighbors.add(new Node(newX, newY, current.distanceFromStart + cavernMap[newX][newY]));
            }

            if((current.x+1 > 0 && current.x+1 < cavernMap.length) && cavernMap[current.x+1][current.y] != 0) {
                int newX = current.x + 1;
                int newY = current.y;
                neighbors.add(new Node(newX, newY, current.distanceFromStart + cavernMap[newX][newY]));
            }

            if((current.y-1 > 0 && current.y-1 < cavernMap.length) && cavernMap[current.x][current.y-1] != 0) {
                int newX = current.x;
                int newY = current.y - 1;
                neighbors.add(new Node(newX, newY, current.distanceFromStart + cavernMap[newX][newY]));
            }

            if((current.y+1 > 0 && current.y+1 < cavernMap.length) && cavernMap[current.x][current.y+1] != 0) {
                int newX = current.x;
                int newY = current.y + 1;
                neighbors.add(new Node(newX, newY, current.distanceFromStart + cavernMap[newX][newY]));
            }

            return neighbors;
        }


        class Node implements Comparable {
            int x;
            int y;
            int distanceFromStart;

            Node(int x, int y, int distanceFromStart) {
                this.x = x;
                this.y = y;
                this.distanceFromStart = distanceFromStart;
            }

            public boolean isBottomRight() {
                return x == cavernMap.length - 1 && y == cavernMap.length - 1;
            }

            @Override
            public int compareTo(Object o) {
                return this.distanceFromStart - ((Node) o).distanceFromStart;
            }
        }
    }
}
