package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    @Test
    void part1_examples() {
        char[][] input = PuzzleInput.as2dCharArray("data/day12_example.txt");
        int actual = solvePart1(input);
        assertEquals(140, actual);

        input = PuzzleInput.as2dCharArray("data/day12_example2.txt");
        actual = solvePart1(input);
        assertEquals(772, actual);

//        input = PuzzleInput.asStringListFrom("data/day12_example3.txt");
//        actual = solvePart1(input);
//        assertEquals(1930, actual);
    }

    private int solvePart1(char[][] map) {


        // as 2-d grid?
        // start in upper left and grow the region out while adjacent cell equals seed
        // maybe replace a cell that's been tracked with .?
        // then find next untracked letter and begin growing that region out
        // perimeter is not a function of number cells, it needs to trace the outline?
        // perimeter probably needs to track positions and then trace around outside edge?

        // TODO - doesn't work for disjoint regions - need to rethink to track attached regions and then get
        //  fencing by iterating the points in each region..
        // List of all points
        // for first unsolved point, build region from that point (recursive grow up/down/left/right
        // adding each point to region list and removing from unsolved list
        // then get fencing needed for that region - # points in region * count fenced sides



        Map<Character, Integer> regionCount = new HashMap<>();
        Map<Character, Integer> fenceCount = new HashMap<>();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                char c = map[row][col];
                regionCount.put(c, regionCount.getOrDefault(c, 0) + 1);

                int fenceNeeded = countFencedSides(map, row, col);
                fenceCount.put(c, fenceCount.getOrDefault(c, 0) + fenceNeeded);

            }
        }

        int result = 0;
        for (Map.Entry<Character, Integer> entry : regionCount.entrySet()) {
            int regionPrice = entry.getValue() * fenceCount.get(entry.getKey());
            result += regionPrice;
        }

        return result;
    }

    private int countFencedSides(char[][] map, int row, int col) {
        int fencedSides = 0;

        char up    = safeCharGrab(map, row - 1, col);
        char right = safeCharGrab(map, row, col + 1);
        char down  = safeCharGrab(map, row + 1,col);
        char left  = safeCharGrab(map, row, col - 1);

        char c = map[row][col];
        if (c != left) fencedSides++;
        if (c != up) fencedSides++;
        if (c != right) fencedSides++;
        if (c != down) fencedSides++;

        return fencedSides;
    }

    private char safeCharGrab(char[][] map, int row, int col) {
        char c = '.';
        try {
            c = map[row][col];
        } catch (IndexOutOfBoundsException x) {
            //ignore
        }
        return c;
    }

}
