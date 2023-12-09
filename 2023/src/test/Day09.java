package com.pdmoore.aoc;

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

        List<List<Integer>> histories = new ArrayList<>();
        histories.add(firstRow);

        boolean allZeros = false;
        while (!allZeros)  {
            List<Integer> nextHistory = new ArrayList<>();
            List<Integer> lastHistory = histories.get(histories.size() - 1);
            allZeros = true;
            for (int i = 1; i < lastHistory.size(); i++) {
                int difference = lastHistory.get(i) - lastHistory.get(i - 1);
                nextHistory.add(difference);
                if (difference != 0) {
                    allZeros = false;
                }
            }
            histories.add(nextHistory);
        }


        // Now go backwards and add up to the top
        for (int i = histories.size() - 1; i > 0; i--) {
            List<Integer> bottomHistory = histories.get(i);
            List<Integer> historyOneAbove = histories.get(i - 1);
            int newEnd = bottomHistory.get(bottomHistory.size() - 1) + historyOneAbove.get(historyOneAbove.size() - 1);
            historyOneAbove.add(newEnd);
        }






        return histories.get(0).get(histories.get(0).size() - 1);
    }

    private int part1_solveSingleLine_take1(String input) {
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
        thingy.put(1, secondRow);

        List<Integer> thirdRow = new ArrayList<>();
        for (int i = 1; i < secondRow.size(); i++) {
            thirdRow.add(secondRow.get(i) - secondRow.get(i - 1));
        }
        thingy.put(2, thirdRow);

        int lastRow = thingy.size() - 1;
        if (allZeros(thingy.get(lastRow))) {
            // build back up to top row, and return last element
            thingy.get(lastRow).add(0);


//            for (int i = thingy.size() - 1; i >= 0; i--) {
//                List<Integer> rowAbove = thingy.get(i - 1);
//                rowAbove.add(rowAbove.get(thingy.get(i - 1).size() - 1));
//            }
//
            List<Integer> rowAbove = thingy.get(lastRow - 1);
            rowAbove.add(rowAbove.get(thingy.get(lastRow - 1).size() - 1) + thingy.get(lastRow).get(thingy.get(lastRow).size() - 1));

            List<Integer> firstIndex = thingy.get(lastRow - 2);
            firstIndex.add(secondRow.get(thingy.get(lastRow - 1).size() - 1)  + firstIndex.get(firstIndex.size() - 1));
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
