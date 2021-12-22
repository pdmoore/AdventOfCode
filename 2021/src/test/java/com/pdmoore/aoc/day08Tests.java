package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day08Tests {

    @Test
    void day08_part1_example() {
        List<String> input = PuzzleInput.asListOfStringsFrom("data/day08_example");

        int actual = countUniqueSegments(input);

        assertEquals(26, actual);
    }

    @Test
    void day08_part1_solution() {
        List<String> input = PuzzleInput.asListOfStringsFrom("data/day08");

        int actual = countUniqueSegments(input);

        assertEquals(504, actual);
    }

    @Test
    void day08_part2_example() {
        List<String> input = PuzzleInput.asListOfStringsFrom("data/day08_example");

        int actual = sumOfSegments(input);

        assertEquals(61229, actual);
    }

    private int countUniqueSegments(List<String> input) {
        int uniqueSegmentCount = 0;

        for (String line :
                input) {
            String rightHandSide = line.split(Pattern.quote("|"))[1];
            String[] segments = rightHandSide.split(" ");
            for (String segment :
                    segments) {
                int segmentLength = segment.length();
                if (segmentLength == 2 || segmentLength == 3 || segmentLength == 4 || segmentLength == 7) {
                    uniqueSegmentCount++;
                }
            }
        }

        return uniqueSegmentCount;
    }

    private int sumOfSegments(List<String> input) {
        return 0;
    }


    @Test
    void day08_part2_singleLineSolved() {
        String input = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf";

        int actual = solveSingleLine(input);

        int expected = 5353;
        assertEquals(expected, actual);
    }

    private int solveSingleLine(String input) {
        String[] splitLine = input.split(Pattern.quote("|"));
        String leftHandSide = splitLine[0];
        String rightHandSide = splitLine[1].trim();

        // figure out mapping from left
        // Will need a map of integer to segments that comprise that integer for this line
        Map<String, Integer> uniqueSignalPatterns = magicFormulaAlgorithm(leftHandSide);

        // apply mapping to right
        // right should be sorted - order doesn't matter
        int result = calculateSumOf(rightHandSide, uniqueSignalPatterns);

        return result;
    }

    private int calculateSumOf(String input, Map<String, Integer> mapping) {
        String[] numbers = input.split(" ");

        StringBuffer sb = new StringBuffer();

        for (String codedNumber :
                numbers) {
            String sorted = sortChars(codedNumber);
            sb.append(String.valueOf(mapping.get(sorted)));
        }

        int result = Integer.parseInt(sb.toString());
        return result;
    }

    private String sortChars(String input) {
        char tempArray[] = input.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    private Map<String, Integer> magicFormulaAlgorithm(String input) {

        //TODO
        // easy to put 1/4/7/8 in the map
        //TODO - figure out how to interpolate 6 segments (0/6/9)
        //TODO - figure out how to interpolate 5 segments (2/3/5)

        // Probably sort the strings....
        Map<String, Integer> solved = new HashMap<>();
        solved.put(sortChars("ab"), 1);
        solved.put(sortChars("cdfbe"), 2);
        solved.put(sortChars("fbcad"), 3);
        solved.put(sortChars("eafb"), 4);
        solved.put(sortChars("cdfbe"), 5);
        solved.put(sortChars("cdfgeb"), 6);
        solved.put(sortChars("dab"), 7);
        solved.put(sortChars("acedgfb"), 8);
        solved.put(sortChars("cefabd"), 9);
        return solved;
    }

}
