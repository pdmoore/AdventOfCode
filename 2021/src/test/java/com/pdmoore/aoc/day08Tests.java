package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day08Tests {

    @Test
    void day08_part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day08_example");

        int actual = countUniqueSegments(input);

        assertEquals(26, actual);
    }

    @Test
    void day08_part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day08");

        int actual = countUniqueSegments(input);

        assertEquals(504, actual);
    }

    @Test
    void day08_part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day08_example");

        int actual = sumOfSegments(input);

        assertEquals(61229, actual);
    }

    @Test
    void day08_part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day08");

        int actual = sumOfSegments(input);

        assertEquals(1073431, actual);
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
        int sum = 0;
        for (String inputLine :
                input) {
            sum += solveSingleLine(inputLine);
        }

        return sum;
    }

    @Test
    void day08_part2_example_singleLineSolved() {
        String inputLine = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf";
        
        int actual = solveSingleLine(inputLine);
        
        assertEquals(5353, actual);
    }

    @Test
    void day08_part2_example_singleLineSolved_RemainingExamples() {
        int actual = solveSingleLine("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb |\n" +
                "fdgacbe cefdb cefbgd gcbe");
        assertEquals(8394, actual);

        actual = solveSingleLine("edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec |\n" +
                "fcgedb cgb dgebacf gc");
        assertEquals(9781, actual);

        actual = solveSingleLine("fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef |\n" +
                "cg cg fdcagb cbg");
        assertEquals(1197, actual);
    }

    private int solveSingleLine(String inputLine) {
        String[] splitLine = inputLine.split(Pattern.quote("|"));
        String leftHandSide = splitLine[0];
        String rightHandSide = splitLine[1].trim();

        Map<String, Integer> uniqueSignalPatterns = assignLedDigitToPatterns(leftHandSide);

        return calculateSumOf(rightHandSide, uniqueSignalPatterns);
    }

    private int calculateSumOf(String inputLine, Map<String, Integer> mapping) {
        String[] numbers = inputLine.split(" ");

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

    private Map<String, Integer> assignLedDigitToPatterns(String leftHandSide) {
        String[] signalWires = leftHandSide.split(" ");
        Map<String, Integer> solved = new HashMap<>();

        List<String> segmentsWithLength5 = new ArrayList<>();
        List<String> segmentsWithLength6 = new ArrayList<>();
        String segment_1 = "";
        String segment_4 = "";

        for (String codedNumber :
                signalWires) {
            String sorted = sortChars(codedNumber);

            switch (sorted.length()) {
                case 2:
                    solved.put(sorted, 1);
                    segment_1 = sorted;
                    break;
                case 3:
                    solved.put(sorted, 7);
                    break;
                case 4:
                    solved.put(sorted, 4);
                    segment_4 = sorted;
                    break;
                case 7:
                    solved.put(sorted, 8);
                    break;
                case 5:
                    segmentsWithLength5.add(sorted);
                    break;
                case 6:
                    segmentsWithLength6.add(sorted);
                    break;
            }
        }

        // Length 6 - 0/6/9
        // 0 does not have one of segment 4's four characters
        // 6 does not have one of segment 1's two characters
        // 9 is the else case
        for (String length6 :
                segmentsWithLength6) {
            if (checkForMissingSegment(length6, segment_1)) {
                solved.put(length6, 6);
            } else if (checkForMissingSegment(length6, segment_4)) {
                solved.put(length6, 0);
            } else {
                solved.put(length6, 9);
            }
        }

        // Length 5 - 2/3/5
        // 3 has both of segment 1's two characters
        // 5 is missing ONE of segment_4
        // 2 is missing TWO of segment_4
        for (String length5 :
                segmentsWithLength5) {
            if (!checkForMissingSegment(length5, segment_1)) {
                solved.put(length5, 3);
            } else if (1 == numMissingSegments(length5, segment_4)) {
                solved.put(length5, 5);
            } else {
                solved.put(length5, 2);
            }
        }

        return solved;
    }

    private int numMissingSegments(String inQuestion, String compareAgainst) {
        int numMissing = 0;
        for (char c : compareAgainst.toCharArray()) {
            if (!inQuestion.contains(Character.toString(c))) {
                numMissing++;
            }
        }
        return numMissing;
    }

    private boolean checkForMissingSegment(String inQuestion, String compareAgainst) {
        return numMissingSegments(inQuestion, compareAgainst) > 0;
    }

}
