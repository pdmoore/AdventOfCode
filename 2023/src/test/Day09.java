package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09 {

    @Test
    void part1_example() {
        List<String> input = Arrays.asList("0 3 6 9 12 15",
                "1 3 6 10 15 21",
                "10 13 16 21 30 45");

        int actual_1 = part1_solveSingleLine(input.get(0));
        int actual_2 = part1_solveSingleLine(input.get(1));
        int actual_3 = part1_solveSingleLine(input.get(2));


        assertEquals(114, actual_1 + actual_2 + actual_3);
    }

    @Test
    void part1_example_line_1() {
        String input = "0 3 6 9 12 15";

        int actual = part1_solveSingleLine(input);

        assertEquals(18, actual);
    }

    private int part1_solveSingleLine(String input) {
        Map<Integer, List<Integer>> thingy = new HashMap<>();
        String[] s = input.split(" ");

        List<Integer> firstRow = new ArrayList<>();
        for (String number :
                s) {
            firstRow.add(Integer.valueOf(number));
        }
        thingy.put(0, firstRow);

        List<Integer> secondRow = new ArrayList<>();
        for (int i = 1; i < firstRow.size(); i++) {
            secondRow.add(firstRow.get(i) - firstRow.get(i - 1));
        }

        List<Integer> thirdRow = new ArrayList<>();
        for (int i = 1; i < secondRow.size(); i++) {
            thirdRow.add(secondRow.get(i) - secondRow.get(i - 1));
        }

        if (allZeros(thirdRow)) {
            // build back up to top row, and return last element
            thirdRow.add(0);
            secondRow.add(secondRow.get(secondRow.size() - 1));
            firstRow.add(secondRow.get(secondRow.size() - 1)  + firstRow.get(firstRow.size() - 1));
        }

        return firstRow.get(firstRow.size() - 1);
    }

    private boolean allZeros(List<Integer> row) {
        for (Integer n :
                row) {
            if (n != 0) {
                return false;
            }

        }
        return true;
    }

    @Test
    void not_sure() {
        assertEquals(114, (18 + 28 + 68));
    }

    /*
    10  13  16  21  30  45  68  101  146
   3   3   5   9  15  23  33  45
     0   2   4   6   8  10  12
       2   2   2   2  2   2
         0   0   0  0   0
           0    0    0
              0
     */

}
