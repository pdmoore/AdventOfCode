package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {

    @Test
    void part1_example() {
        String input = "2333133121414131402";
        BigInteger actual = solvePart1(input);
        assertEquals(BigInteger.valueOf(1928), actual);
    }

    @Test
    void part1() {
        String input = PuzzleInput.asStringFrom("data/day09.txt");
        BigInteger actual = solvePart1(input);
        assertEquals(new BigInteger("6430446922192"), actual);
    }

    static class Block {
        int idNumber;
        Block prev;
        Block next;
    }

    private BigInteger solvePart1(String input) {

        Block head = null;

        // for each char in input, convert to int and build linked list
        // need head and tail at the finish
        boolean processFile = true;
        int nextIdNumber = 0;
        Block current = new Block();
        for (Character c: input.toCharArray()) {
            int length = c - '0';
            if (head == null) {
                head = current;
            }
            if (processFile) {
                for (int i = 0; i < length; i++) {
                    current.idNumber = nextIdNumber;
                    Block prev = current;
                    current = new Block();
                    current.prev = prev;
                    prev.next = current;
                }
                nextIdNumber++;
            } else {
                for (int i = 0; i < length; i++) {
                    current.idNumber = -1;
                    Block prev = current;
                    current = new Block();
                    current.prev = prev;
                    prev.next = current;
                }
            }

            processFile = !processFile;
        }

        // current was created but not used
        Block tail = current.prev;
        current.prev.next = null;

        printBlocks(head);

        // TODO - compress from head forward and tail backward
        // when head == tail it's done
        moveFileBlocks(head, tail);

        //
        printBlocks(head);

        return calculateChecksum(head);
    }

    private void moveFileBlocks(Block head, Block tail) {
        Block fromLeft = head;
        Block fromRight = tail;

        while (true) {
            if (fromLeft == fromRight) return;

            if (fromLeft.idNumber != -1) {
                fromLeft = fromLeft.next;
            } else if (fromRight.idNumber == -1) {
                fromRight = fromRight.prev;
            } else {
                fromLeft.idNumber = fromRight.idNumber;
                fromRight.idNumber = -1;

                printBlocks(head);
            }
        }
    }

    private BigInteger calculateChecksum(Block head) {
        int position = 0;
        BigInteger result = BigInteger.ZERO;
        Block current = head;
        while (current != null) {
            if (current.idNumber != -1) {
                BigInteger sum = BigInteger.ZERO;
                sum = sum.add(BigInteger.valueOf(position));
                sum = sum.multiply(BigInteger.valueOf(current.idNumber));

                result = result.add(sum);
            }

            current = current.next;

            position++;
        }

        return result;
    }

    private void printBlocks(Block head) {
        return;
//        StringBuilder sb = new StringBuilder();
//        Block current = head;
//        while (current != null) {
//            if (current.idNumber == -1) {
//                sb.append(".");
//            } else {
//                sb.append(current.idNumber);
//            }
//            current = current.next;
//        }
//        System.out.println(sb.toString());
    }
}
