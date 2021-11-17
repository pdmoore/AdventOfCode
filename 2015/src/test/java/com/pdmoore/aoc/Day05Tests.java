package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Day05Tests {

//TODO - going through each string many times, look for efficiencies

    @Test
    public void part1_niceString_Examples() {
        Assertions.assertTrue(part1_isNice("aaa"));
        Assertions.assertTrue(part1_isNice("ugknbfddgicrmopn"));
    }

    @Test
    public void part1_naughtyString_LessThanThreeVowels() {
        assertFalse(part1_isNice("aa"));
    }

    @Test
    public void part1_naughtyString_NoDoubleLetter() {
        assertFalse(part1_isNice("jchzalrnumimnmhp"));
    }

    @Test
    public void part1_naughtyString_ContainsCertainTwoCharacterSequence() {
        //ab, cd, pq, or xy
        String aNiceString = "aieoooo";
        assertFalse(part1_isNice(aNiceString + "ab"));
        assertFalse(part1_isNice(aNiceString + "cd"));
        assertFalse(part1_isNice(aNiceString + "pq"));
        assertFalse(part1_isNice(aNiceString + "xy"));
    }

    @Test
    public void countOfNiceStrings() {
        List<String> input = Stream.of("naughty", "niceaaa", "nice2ooo", "nice3aieouuu", "naughty2").collect(Collectors.toList());
        int actual = countNiceStrings(input);
        assertEquals(3, actual);
    }

    @Test
    public void part01() {
        List<String> input = PuzzleInput.asListOfStringsFromFile("data/day04");
        assertEquals(255, countNiceStrings(input));
    }

    @Test
    public void part02() {
        List<String> input = PuzzleInput.asListOfStringsFromFile("data/day04");
        assertEquals(-99, countNiceStrings_part2(input));
    }


    private int countNiceStrings(List<String> input) {
        //TODO - switch to stream/filter
        int countOfNice = 0;
        for (String s :
                input) {
            if (part1_isNice(s)) countOfNice++;
        }
        return countOfNice;
    }

    private int countNiceStrings_part2(List<String> input) {
        //TODO - switch to stream/filter
        int countOfNice = 0;
        for (String s :
                input) {
            if (part2_isNice(s)) countOfNice++;
        }
        return countOfNice;
    }

    private boolean part1_isNice(String input) {
        return hasThreeOrMoreVowels(input) & hasDoubleLetters(input) & !hasTwoCharacterSequence(input);
    }

    private boolean hasTwoCharacterSequence(String input) {
        return input.contains("ab") || input.contains("cd") || input.contains("pq") || input.contains("xy");
    }

    private boolean hasDoubleLetters(String input) {
        char prevChar = input.charAt(0);
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == prevChar) return true;
            prevChar = input.charAt(i);
        }

        return false;
    }

    private boolean hasThreeOrMoreVowels(String input) {
        long count = input.chars().filter(ch -> ch == 'a').count();
        count += input.chars().filter(ch -> ch == 'e').count();
        count += input.chars().filter(ch -> ch == 'i').count();
        count += input.chars().filter(ch -> ch == 'o').count();
        count += input.chars().filter(ch -> ch == 'u').count();

        return count >= 3;
    }

    @Test
    public void part2_pairs_appear_atLeastTwice() {
        Assertions.assertTrue(part2_firstRule("xyxy"));
        Assertions.assertTrue(part2_firstRule("aabcdefgaa"));
        Assertions.assertTrue(part2_firstRule("zaagaa"));
        Assertions.assertTrue(part2_firstRule("zaabcdefgaa"));
    }

    @Test
    public void part2_pairs_doNotAppear_atLeastTwice() {
        Assertions.assertFalse(part2_firstRule("aaa"));
    }

    @Test
    public void part2_oneLetter_repeats_withExactlyOneLetterBetween() {
        Assertions.assertTrue(part2_secondRule("xyx"));
        Assertions.assertTrue(part2_secondRule("abcdefeghi"));
        Assertions.assertFalse(part2_secondRule("abcdefghi"));
    }

    @Test
    public void part2_fullExamples() {
        Assertions.assertTrue(part2_isNice("qjhvhtzxzqqjkmpb"));
        Assertions.assertTrue(part2_isNice("xxyxx"));
        Assertions.assertFalse(part2_isNice("uurcxstgmygtbstg"));
        Assertions.assertFalse(part2_isNice("ieodomkazucvgmuy"));
    }

    private boolean part2_isNice(String input) {
        return part2_firstRule(input) && part2_secondRule(input);
    }

    private boolean part2_firstRule(String input) {
        int i = 0;
        while (i < input.length() - 2) {
            String currentPair = "" + input.charAt(i) + input.charAt(i + 1);

            int checkFrom = i + 2;
            while (checkFrom <= input.length() - 2) {
                String thisPair = "" + input.charAt(checkFrom) + input.charAt(checkFrom + 1);
                if (thisPair.equals(currentPair)) {
                    return true;
                }
                checkFrom++;
            }
            i++;
        }
        return false;
    }

    private boolean part2_secondRule(String input) {
        int i = 0;
        while (i <= input.length() - 3) {
            if (input.charAt(i) == input.charAt(i + 2)) {
                return true;
            }
            i++;
        }

        return false;
    }


    //TODO part 2
    // - rule 1 assumes pair is in positions [0][1] - need to write a test to show it can find others
    // - part 2 solution -
    //    - need test (see public void part01())
    //    - need new method countNiceStrings_part2
}
