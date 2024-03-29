package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.nio.file.LinkPermission;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day13_part1_example");

        int actual = solvePart1(input);

        assertEquals(405, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day13");

        int actual = solvePart1(input);

        assertEquals(29130, actual);
    }

    private int solvePart1(List<String> input) {
        // Chunk the input via blank lines
        // determine if a chunk is vertical or horizontal reflected
        // sum num of vertical + (100 * num of horizontal)

        // Reflections need to match rows above and below the two lines that match,
        // not just the two lines that match

        int numColumns = 0;
        int numRows = 0;
        List<String> nextPattern = new ArrayList<>();
        for (String inputLine :
                input) {
            if (inputLine.isEmpty()) {
                int vertical = checkForVerticalReflection(nextPattern);
                if (vertical > 0) {
                    numColumns += vertical;
                } else {
                    int horizontal = checkForHorizontalReflection(nextPattern);
                    numRows += horizontal;
                }

                nextPattern = new ArrayList<>();
            } else {
                nextPattern.add(inputLine);
            }
        }

        // Last chunk
        int vertical = checkForVerticalReflection(nextPattern);
        if (vertical > 0) {
            numColumns += vertical;
        } else {
            int horizontal = checkForHorizontalReflection(nextPattern);
            numRows += horizontal;
        }

        return numColumns + (100 * numRows);
    }

    private int checkForHorizontalReflection(List<String> nextPattern) {
        for (int i = 1; i < nextPattern.size(); i++) {
            if (nextPattern.get(i - 1).equals(nextPattern.get(i))) {
                if (confirmHorizontalReflectAt(nextPattern, i)) {
                    return i;
                }
            }
        }

        throw new IllegalArgumentException("couldn't find horizontal reflection");
    }

    private int checkForVerticalReflection(List<String> nextPattern) {
        // convert to 2d array
        // start with row 1
        // if row 2 is the same, return row 1
        char[][] twoDChars = as2dCharArray(nextPattern);
        String previousColumn = getColumn(0, twoDChars);
        int loopLimit1 = twoDChars.length;
        int loopLimit2 = twoDChars[0].length;
        for (int i = 1; i < twoDChars[0].length; i++) {
            try {
                String currentColumn = getColumn(i, twoDChars);
                if (previousColumn.equals(currentColumn)) {
                    // if row matches previous, then have to check above and below!
                    if (confirmVerticalReflectionAt(twoDChars, i)) {
                        return i;
                    }
                }
                previousColumn = currentColumn;
            } catch (Exception x) {
                int breakpoint = 1;
                breakpoint++;
            }
        }

        return -1;
    }

    private boolean confirmVerticalReflectionAt(char[][] pattern, int colIndex) {
        int leftIndex = colIndex - 1;
        int rightIndex = colIndex;

        while (leftIndex >= 0 && rightIndex < pattern[0].length) {
            String rightColumn = getColumn(rightIndex, pattern);
            String leftColumn  = getColumn(leftIndex, pattern);
            if (!leftColumn.equals(rightColumn)) {
                return false;
            }

            leftIndex--;
            rightIndex++;
        }

        return true;
    }

    private String getColumn(int i, char[][] map) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < map.length; j++) {
            sb.append(map[j][i]);
        }
        return sb.toString();
    }


    public static char[][] as2dCharArray(List<String> inputAsStrings) {
        int rowCount = inputAsStrings.size();
        int colCount = inputAsStrings.get(0).length();
        char[][] map = new char[rowCount][colCount];
        int i = 0;
        for (String line :
                inputAsStrings) {
            map[i] = line.toCharArray();
            i++;
        }
        return map;
    }

    private boolean confirmHorizontalReflectAt(List<String> pattern, int rowIndex) {
        int upwardIndex = rowIndex - 1;
        int downwardIndex = rowIndex;

        while (upwardIndex >= 0 && downwardIndex < pattern.size()) {
            if (!pattern.get(upwardIndex).equals(pattern.get(downwardIndex))) {
                return false;
            }

            upwardIndex--;
            downwardIndex++;
        }

        return true;
    }
}
