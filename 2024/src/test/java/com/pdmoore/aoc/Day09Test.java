package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {

    public static final int FREE_SPACE = -1;

    @Test
    void part1_example() {
        String input = "2333133121414131402";
        int actual = solvePart1(input);
        assertEquals(1928, actual);
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
        int actual = solvePart1(input);

        // assuming I'm hitting issues above 9
        // 756138815 as is
        // using a string kind of sucks, could I do a linked list with the ID growing?
        assertEquals(99, actual);
    }

    @Test
    void testDiskMapToBlock() {
        String input = "12345";
        List<Thingy> actual = diskMapToBlock(input);
        StringBuilder sb = new StringBuilder();
        for (Thingy t : actual) {
            sb.append(t.toString());
        }
        assertEquals("0..111....22222", sb.toString());

        input = "2333133121414131402";
        actual = diskMapToBlock(input);
        for (Thingy t : actual) {
            sb.append(t.toString());
        }
        assertEquals("00...111...2...333.44.5555.6666.777.888899", sb.toString());
    }

//    @Test
//    void testMoveFileBlocks() {
//        String input = "0..111....22222";
//        String actual = moveFileBlocks(input);
//        assertEquals("022111222......", actual);
//
//        input = "00...111...2...333.44.5555.6666.777.888899";
//        actual = moveFileBlocks(input);
//        assertEquals("0099811188827773336446555566..............", actual);
//    }

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

    private int solvePart1(String input) {
//        List<Thingy> thingies = diskMapToBlock(input);
//        List<Thingy> compacted = moveFileBlocks(thingies);
//
//        int checksum = checksumOf(compacted);
//
//        return checksum;

        Node head = convertDiskMapToNodes(input);
        printLinkedList(head);

        // compact linked list
        moveFileBlocks(head);

        // calculate checksum
        return calculateChecksum(head);
    }

    private int calculateChecksum(Node head) {
        printLinkedList(head);

        int position = 0;
        int result = 0;
        Node current = head;
        while (current != null) {
            if (current.idNumber != FREE_SPACE) {
                int sum = position * current.idNumber;
                result += sum;
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
        /*
                boolean fileOrFreeSpace = true;
        List<Thingy> listOfThingy = new ArrayList<>();

        for (Character c : input.toCharArray()) {
            int num = Integer.parseInt(String.valueOf(c));
            if (fileOrFreeSpace) {
                Thingy t = new Thingy(true, idNumber, num);
                listOfThingy.add(t);
                idNumber++;
            } else {
                Thingy t = new Thingy(false, -1, num);
                listOfThingy.add(t);
            }

            fileOrFreeSpace = !fileOrFreeSpace;
        }

        return listOfThingy;
         */

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
                c = String.valueOf(n.idNumber).charAt(0);
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
                Node freeSpace = new Node();
                freeSpace.idNumber = FREE_SPACE;
                freeSpace.length = remainingFreeSpace;

                freeSpace.next = nextFreeSpace.next;
                freeSpace.prev = nextFreeSpace;
                nextFreeSpace.next = freeSpace;
                freeSpace.next.prev = freeSpace;
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
