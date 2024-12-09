package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

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

    @Test
    void testMoveFileBlocks() {
        String input = "0..111....22222";
        String actual = moveFileBlocks(input);
        assertEquals("022111222......", actual);

        input = "00...111...2...333.44.5555.6666.777.888899";
        actual = moveFileBlocks(input);
        assertEquals("0099811188827773336446555566..............", actual);
    }

    @Test
    void testCalculateChecksum() {
        String input = "0099811188827773336446555566..............";
        int actual = checksumOf(input);
        assertEquals(1928, actual);
    }

    private int checksumOf(String input) {
        int result = 0;
        for (int position = 0; position < input.length(); position++) {
            char fileIdNumber = input.charAt(position);
            if (fileIdNumber == '.') return result;
            int n = position * Integer.parseInt(String.valueOf(fileIdNumber));
            result += n;
        }

        return result;
    }

    private String moveFileBlocks(String input) {
        String result = input;
        int rightIndex = result.length() - 1;
        while (true) {
            int leftIndex = result.indexOf('.');
            if (leftIndex >= rightIndex) {
                return result.toString();
            }

            char rightChar = result.charAt(rightIndex);

            char[] charArray = result.toCharArray();
            charArray[leftIndex] = rightChar;
            charArray[rightIndex] = '.';

            while (charArray[rightIndex] == '.') {
                rightIndex--;
            }

            result = new String(charArray);
        }

    }

    private String diskMapToBlock(String input) {
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
