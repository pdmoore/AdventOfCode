package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class Day25Test {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day25_example.txt");

        int actual = solvePart1(input);

        assertEquals(3, actual);
    }

    @Test
    void confirmLockConversion() {
        List<String> input = List.of("#####", ".####", ".####", ".####", ".#.#.", ".#...", ".....");

        List<Integer> actual = convertToIntegerList(input);

        List<Integer> expected = List.of(0, 5, 3, 4, 3);
        assertEquals(expected, actual);
    }

    @Test
    void confirmKeyConversion() {
        List<String> input = List.of(
                ".....",
                "#....",
                "#....",
                "#...#",
                "#.#.#",
                "#.###",
                "#####"
        );

        List<Integer> actual = convertToIntegerList(input);

        List<Integer> expected = List.of(5, 0, 2, 1, 3);
        assertEquals(expected, actual);
    }

    private List<Integer> convertToIntegerList(List<String> input) {

        List<Integer> result = new ArrayList<>();

        int rowLength = input.get(0).length();
        for (int col = 0; col < rowLength; col++) {
            int hashCount = 0;
            for (int i = 1; i < input.size() - 1; i++) {
                String row = input.get(i);
                if (row.charAt(col) == '#') {
                    hashCount++;
                }
            }
            result.add(hashCount);
        }

        return result;
    }

    private int solvePart1(List<String> input) {
        List<List<Integer>> locks = new ArrayList<>();
        List<List<Integer>> keys = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            if (line.isEmpty()) continue;

            if (line.equals("#####")) {
                //lock
                List<String> lockAsString = new ArrayList<>();
//                lockAsString.add(line);
                for (int row = 0; row <= 6; row++) {
                    lockAsString.add(input.get(row + i));
                }
                locks.add(convertToIntegerList(lockAsString));
            } else {
                // assuming key
                List<String> keyAsString = new ArrayList<>();
//                keyAsString.add(line);
                for (int row = 0; row <= 6; row++) {
                    keyAsString.add(input.get(row + i));
                }
                keys.add(convertToIntegerList(keyAsString));
            }

            i += 6;
        }

        int locksThatKeyFitsInto = 0;
        for (List<Integer> key : keys) {

            for (List<Integer> lock : locks) {
                if (keyFitsInLock(lock, key)) {
                    locksThatKeyFitsInto++;
                }
            }
        }

        return locksThatKeyFitsInto;
    }

    private boolean keyFitsInLock(List<Integer> lock, List<Integer> key) {

        for (int i = 0; i < lock.size(); i++) {
            int currentLock = lock.get(i);
            int currentKey = key.get(i);
            if (currentLock + currentKey > 5) return false;
        }

        return true;
    }
}
