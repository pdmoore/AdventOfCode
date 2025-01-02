package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {

    public static final int FREE_SPACE = -1;
    private static boolean SUPPRESS_PRINTING = true;

    @Test
    void part1_example() {
        String input = "2333133121414131402";
        BigInteger actual = solvePart1(input);
        assertEquals(new BigInteger("1928"), actual);
    }

    @Test
    void part1() {
        String input = PuzzleInput.asStringFrom("data/day09.txt");
        BigInteger actual = solvePart1(input);
        assertEquals(new BigInteger("6430446922192"), actual);
    }

    @Test
    void part2_example() {
        SUPPRESS_PRINTING = false;
        String input = "2333133121414131402";
        BigInteger actual = solvePart2(input);
        assertEquals(new BigInteger("2858"), actual);
    }

    static class Block {
        int idNumber;
        Block prev;
        Block next;
    }

    private BigInteger solvePart1(String input) {
        Block head = null;

        boolean processFile = true;
        int nextIdNumber = 0;
        Block current = new Block();
        for (Character c: input.toCharArray()) {
            int length = c - '0';
            if (head == null) {
                head = current;
            }

            int useIdNumber = FREE_SPACE;
            if (processFile) {
                useIdNumber = nextIdNumber;
                nextIdNumber++;
            }

            for (int i = 0; i < length; i++) {
                current.idNumber = useIdNumber;
                Block prev = current;
                current = new Block();
                current.prev = prev;
                prev.next = current;
            }
            
            processFile = !processFile;
        }

        // current was created but not used
        Block tail = current.prev;
        current.prev.next = null;

        printBlocks(head);
        moveFileBlocks(head, tail);
        printBlocks(head);

        return calculateChecksum(head);
    }

    private BigInteger solvePart2(String input) {
        Block head = null;

        boolean processFile = true;
        int nextIdNumber = 0;
        Block current = new Block();
        for (Character c: input.toCharArray()) {
            int length = c - '0';
            if (head == null) {
                head = current;
            }

            int useIdNumber = FREE_SPACE;
            if (processFile) {
                useIdNumber = nextIdNumber;
                nextIdNumber++;
            }

            for (int i = 0; i < length; i++) {
                current.idNumber = useIdNumber;
                Block prev = current;
                current = new Block();
                current.prev = prev;
                prev.next = current;
            }

            processFile = !processFile;
        }

        // current was created but not used
        Block tail = current.prev;
        current.prev.next = null;

        printBlocks(head);
        moveEntireFiles(head, tail);
        printBlocks(head);

        return calculateChecksum(head);
    }

    private void moveEntireFiles(Block head, Block tail) {
        List<Integer> idsThatHaveMoved = new ArrayList<>();

        while (true) {

            // TODO -
            // Current impl moves file 8888 to a singel free space location
            // Probably doesn't try to find the next lower Id 777 that will fit in a free space of ...


            Block nextFileToMove = findNextIdToMove(tail, idsThatHaveMoved);
            if (nextFileToMove == null) return;
            
            int fileSize = sizeOfFile(head, nextFileToMove);
            
            Block moveFileTo = findNodeToMoveTo(head, fileSize);
            if (moveFileTo != null) {
                int idBeingMoved = nextFileToMove.idNumber;

                Block copyTo = moveFileTo;
                for (int i = 0; i < fileSize; i++) {
                    copyTo.idNumber = idBeingMoved;
                    copyTo = copyTo.next;
                }

                Block eraseAt = nextFileToMove;
                for (int i = 0; i < fileSize; i++) {
                    eraseAt.idNumber = FREE_SPACE;
                    eraseAt = eraseAt.next;
                }

                idsThatHaveMoved.add(idBeingMoved);
            }
            
            printBlocks(head);
        }
    }

    private int sizeOfFile(Block head, Block nextFileToMove) {
        Block current = head;
        while (current.idNumber != nextFileToMove.idNumber) {
            current = current.next;
        }
        int size = 0;
        while (current != null && current.idNumber == nextFileToMove.idNumber) {
            size++;
            current = current.next;
        }

        return size;
    }

    private Block findNodeToMoveTo(Block head, int fileSize) {
        Block current = head;

        while (true) {
            current = startOfNextFreeSpace(current);
            if (current == null) return null;

            if (sizeOfFreeSpace(current) >= fileSize) return current;

            current = current.next;
            if (current == null) return null;
        }
    }

    private int sizeOfFreeSpace(Block current) {
        int size = 0;
        while (current.idNumber == FREE_SPACE) {
            size++;
            current = current.next;
        }
        return size;
    }

    private Block startOfNextFreeSpace(Block current) {
        while (true) {
            if (current.idNumber == FREE_SPACE) return current;

            current = current.next;
        }
    }

    private Block findNextIdToMove(Block tail, List<Integer> idsThatHaveMoved) {
        Block fromLeft = tail;
        while (true) {
            if (fromLeft == null) return null;

            if (fromLeft.idNumber == FREE_SPACE) {
                fromLeft = fromLeft.prev;
            } else if (idsThatHaveMoved.contains(fromLeft.idNumber)) {
                fromLeft = fromLeft.prev;
            } else {
                return startOfFile(fromLeft);
            }
        }
    }

    private Block startOfFile(Block startFrom) {
        Block current = startFrom;
        int targetId = current.idNumber;
        while (current.prev.idNumber == targetId) {
            current = current.prev;
        }
        return current;
    }

    private void moveFileBlocks(Block head, Block tail) {
        Block fromLeft = head;
        Block fromRight = tail;

        while (true) {
            if (fromLeft == fromRight) return;

            if (fromLeft.idNumber != FREE_SPACE) {
                fromLeft = fromLeft.next;
            } else if (fromRight.idNumber == FREE_SPACE) {
                fromRight = fromRight.prev;
            } else {
                fromLeft.idNumber = fromRight.idNumber;
                fromRight.idNumber = FREE_SPACE;

                printBlocks(head);
            }
        }
    }

    private BigInteger calculateChecksum(Block head) {
        int position = 0;
        BigInteger result = BigInteger.ZERO;
        Block current = head;
        while (current != null) {
            if (current.idNumber == FREE_SPACE) break;

            BigInteger sum = BigInteger.ZERO
                    .add(BigInteger.valueOf(position))
                    .multiply(BigInteger.valueOf(current.idNumber));
            result = result.add(sum);

            current = current.next;
            position++;
        }

        return result;
    }

    private void printBlocks(Block head) {
        if (SUPPRESS_PRINTING) return;
        StringBuilder sb = new StringBuilder();
        Block current = head;
        while (current != null) {
            if (current.idNumber == -1) {
                sb.append(".");
            } else {
                sb.append(current.idNumber);
            }
            current = current.next;
        }
        System.out.println(sb);
    }
}
