package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;
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

        Map<String, Integer> uniqueSignalPatterns = magicFormulaAlgorithm(leftHandSide);

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

    private Map<String, Integer> magicFormulaAlgorithm(String leftHandSide) {
//        acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab
        String[] signalWires = leftHandSide.split(" ");
        Map<String, Integer> solved = new HashMap<>();

        List<String> segmentLength5 = new ArrayList<>();
        List<String> segmentLength6 = new ArrayList<>();
        String segment_1 = "";
        String segment_4 = "";

        for (String codedNumber :
                signalWires) {
            String sorted = sortChars(codedNumber);

            switch (sorted.length()) {
                case 2: solved.put(sorted, 1); segment_1 = sorted;
                    break;
                case 3: solved.put(sorted, 7); break;
                case 4: solved.put(sorted, 4); segment_4 = sorted;
                    break;
                case 7: solved.put(sorted, 8); break;
                case 5: segmentLength5.add(sorted); break;
                case 6: segmentLength6.add(sorted); break;
            }
        }

        //TODO - figure out how to interpolate 5 segments (2/3/5)

        // Length 6 - 0/6/9
        // 0 does not have one of segment 4's four characters
        // 6 does not have one of segment 1's two characters
        // 9 is the else case
        for (String length6 :
                segmentLength6) {
            if (checkForMissingSegment(length6, segment_1)) {
                solved.put(length6, 6);
            } else if (checkForMissingSegment(length6, segment_4)) {
                solved.put(length6, 0);
            } else {
                solved.put(length6, 9);
            }
        }


        solved.put(sortChars("bcdef"), 2);
        solved.put(sortChars("abcdf"), 3);
        solved.put(sortChars("bcdef"), 5);

        return solved;
    }

    private boolean checkForMissingSegment(String inQuestion, String compareAgainst) {
        for (char c : compareAgainst.toCharArray()) {
            if (!inQuestion.contains(Character.toString(c))) {
                return true;
            }
        }
        return false;
    }

}
