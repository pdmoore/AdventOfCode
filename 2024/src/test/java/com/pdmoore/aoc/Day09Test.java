package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {

    // TODO - Example is working but real data doesn't
    // It looks like the MoveBlocks logic is somehow injecting a new block in between (See Notes)


    public static final int FREE_SPACE = -1;

    @Test
    void part1_example() {
        String input = "2333133121414131402";
        BigInteger actual = solvePart1(input);
        assertEquals(BigInteger.valueOf(1928), actual);
    }


    class Node {
        Node next = null;
        Node prev = null;

        int idNumber;
        int length;
    }

    @Test
    void part1() {
        String input = PuzzleInput.asStringFrom("data/day09.txt");
        BigInteger actual = solvePart1(input);

        // after LL impl, got 6407066906765  which was too low
        // goofing with the move logic, got 7696963600441 which is too high
        assertEquals(BigInteger.valueOf(99), actual);
    }

    private List<Thingy> moveFileBlocks(List<Thingy> input) {
        // parameter has the gaps in it
        // Do I need a doubly linked list? or traverse whole list each time to find
        // left and right indices until they cross?
        // Want to bring IDs that are greater than 9 forward and swap rightmost with a '.'


//        String result = input;
//        int rightIndex = result.length() - 1;
//        while (true) {
//            int leftIndex = result.indexOf('.');
//            if (leftIndex >= rightIndex) {
//                return result.toString();
//            }
//
//            char rightChar = result.charAt(rightIndex);
//
//            char[] charArray = result.toCharArray();
//            charArray[leftIndex] = rightChar;
//            charArray[rightIndex] = '.';
//
//            while (charArray[rightIndex] == '.') {
//                rightIndex--;
//            }
//
//            result = new String(charArray);
//        }

        return null;
    }

    class Thingy {

        final int idNumber;
        final int count;
        boolean fileOrFreeSpace;

        public Thingy(boolean fileOrFreeSpace, int idNumber, int count) {
            this.fileOrFreeSpace = fileOrFreeSpace;
            this.idNumber = idNumber;
            this.count = count;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (fileOrFreeSpace) {
                for (int i = 0; i < count; i++) {
                    sb.append(idNumber);
                }
            } else {
                for (int i = 0; i < count; i++) {
                    sb.append('.');
                }
            }


            return sb.toString();
        }
    }

    private List<Thingy> diskMapToBlock(String input) {
        int idNumber = 0;
        boolean fileOrFreeSpace = true;
        List<Thingy> listOfThingy = new ArrayList<>();

        for (Character c : input.toCharArray()) {
            int num = Integer.parseInt(String.valueOf(c));
            if (fileOrFreeSpace) {
                Thingy t = new Thingy(true, idNumber, num);
                listOfThingy.add(t);
                idNumber++;
            } else {
                Thingy t = new Thingy(false, FREE_SPACE, num);
                listOfThingy.add(t);
            }

            fileOrFreeSpace = !fileOrFreeSpace;
        }

        return listOfThingy;
    }

    private BigInteger solvePart1(String input) {
        Node head = convertDiskMapToNodes(input);
        printLinkedList(head);

        // compact linked list
        moveFileBlocks(head);

        // calculate checksum
        return calculateChecksum(head);
    }

    private BigInteger calculateChecksum(Node head) {
        printLinkedList(head);

        int position = 0;
        BigInteger result = BigInteger.ZERO;
        Node current = head;
        while (current != null) {
            if (current.idNumber != FREE_SPACE) {
                BigInteger sum = BigInteger.ZERO;
                sum = sum.add(BigInteger.valueOf(position));
                sum = sum.multiply(BigInteger.valueOf(current.idNumber));

                result = result.add(sum);
            }
            if (current.length > 1) {
                current.length -= 1;
            } else {
                current = current.next;

            }

            position++;
        }

        return result;
    }

    private Node convertDiskMapToNodes(String input) {
        boolean fileOrFreeSpace = true;
        int nextIdNumber = 0;
        Node head = null;
        Node last = null;

        for (Character c : input.toCharArray()) {
            int length = Integer.parseInt(String.valueOf(c));
            Node n = new Node();
            if (fileOrFreeSpace) {
                n.idNumber = nextIdNumber;
                nextIdNumber++;
            } else {
                n.idNumber = FREE_SPACE;
            }
            n.length = length;

            n.prev = last;
            if (head == null) {
                head = n;
            }
            if (n.prev != null) {
                n.prev.next = n;
            }
            last = n;

            fileOrFreeSpace = !fileOrFreeSpace;
        }

        return head;
    }


    private void printLinkedList(Node head) {
        StringBuilder sb = new StringBuilder();
        Node n = head;
        while (n != null) {
            char c = '.';
            if (n.idNumber != FREE_SPACE) {
                String string = Integer.toString(n.idNumber);
                c = string.charAt(string.length() - 1);
            }
            for (int i = 0; i < n.length; i++) {
                sb.append(c);
            }
            n = n.next;
        }

        System.out.println(sb.toString());
    }

    private void moveFileBlocks(Node head) {
        Node tail = null;
        Node n = head;
        while (n.next != null) {
            n = n.next;
        }
        tail = n;

        while (true) {
            // find first free space
            Node nextFreeSpace = head;
            while (nextFreeSpace.idNumber != FREE_SPACE) {
                nextFreeSpace = nextFreeSpace.next;
                if (nextFreeSpace == null) {
                    throw new RuntimeException("didn't find any free space!");
                }
            }

            if (nextFreeSpace == tail) {
                return;
            }

            Node nextIdFromRight = tail;
            while (nextIdFromRight.idNumber == FREE_SPACE) {
                nextIdFromRight = nextIdFromRight.prev;
            }
            if (nextIdFromRight == null) {
                throw new RuntimeException("didn't find any non-Free Space");
            }
            if (nextIdFromRight.next == nextFreeSpace) {
                return;
            }

            int remainingFreeSpace = nextFreeSpace.length - 1;

            nextFreeSpace.idNumber = nextIdFromRight.idNumber;
            nextFreeSpace.length = 1;

            if (remainingFreeSpace > 0) {
//                if (tail.idNumber != FREE_SPACE) {
                    Node freeSpace = new Node();
                    freeSpace.idNumber = FREE_SPACE;
                    freeSpace.length = remainingFreeSpace;

                    freeSpace.next = nextFreeSpace.next;
                    freeSpace.prev = nextFreeSpace;
                    nextFreeSpace.next = freeSpace;
                    freeSpace.next.prev = freeSpace;
//                } else {
                    // something about remaing free space tacked onto existing tail
//                    int breakpoint = 66;
//                }
            }

            if (tail == nextIdFromRight) {
                Node newTail = new Node();
                newTail.idNumber = FREE_SPACE;
                newTail.length = 1;

                newTail.prev = nextIdFromRight;
                nextIdFromRight.next = newTail;
                tail = newTail;
            } else {
                tail.length = tail.length + 1;
            }

            nextIdFromRight.length = nextIdFromRight.length - 1;
            if (nextIdFromRight.length == 0) {
                nextIdFromRight.idNumber = FREE_SPACE;
            }
        }
    }
}
