package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {

    @Test
    void part1_example() {
        String input = "2333133121414131402";
        int actual = solvePart1(input);
        assertEquals(0, actual);
    }

    @Test
    void testDiskMapToBlock() {
        String input = "12345";
        String actual = diskMapToBlock(input);
        assertEquals("0..111....22222", actual);

        input = "2333133121414131402";
        actual = diskMapToBlock(input);
        assertEquals("00...111...2...333.44.5555.6666.777.888899", actual);
    }

    private String diskMapToBlock(String input) {

        //char by char, expand and track IDNumber
        int idNumber = 0;
        boolean fileOrFreeSpace = true;
        StringBuilder result = new StringBuilder();
        for (Character c : input.toCharArray()) {
            int num = Integer.parseInt(String.valueOf(c));
            if (fileOrFreeSpace) {
                for (int i = 0; i < num; i++) {
                    result.append(idNumber);
                }
                idNumber++;
            } else {
                for (int i = 0; i < num; i++) {
                    result.append(".");
                }
            }

            fileOrFreeSpace = !fileOrFreeSpace;
        }

        return result.toString();
    }


    private int solvePart1(String input) {




        return 0;
    }


}
