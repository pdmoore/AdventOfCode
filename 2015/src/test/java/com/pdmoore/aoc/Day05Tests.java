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

    private int countNiceStrings(List<String> input) {
        //TODO - switch to stream/filter
        int countOfNice = 0;
        for (String s :
                input) {
            if (part1_isNice(s)) countOfNice++;
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
        Assertions.assertTrue(part2_secondRule("xyxy"));
        Assertions.assertTrue(part2_secondRule("aabcdefgaa"));
        Assertions.assertTrue(part2_secondRule("aaa"));
    }

    @Test
    public void part2_pairs_doNotAppear_atLeastTwice() {
        Assertions.assertFalse(part2_rule1("aaa"));
    }

    @Test
    public void part2_oneLetter_repeats_withExactlyOneLetterBetween() {
        Assertions.assertTrue(part2_secondRule("xyx"));
        Assertions.assertTrue(part2_secondRule("abcdefeghi"));
    }

    @Test
    public void part2_fullExamples() {
        Assertions.assertTrue(part2_isNice("qjhvhtzxzqqjkmpb"));
        Assertions.assertTrue(part2_isNice("xxyxx"));
        Assertions.assertFalse(part2_isNice("uurcxstgmygtbstg"));
        Assertions.assertFalse(part2_isNice("ieodomkazucvgmuy"));
    }

    private boolean part2_isNice(String input) {
        return part2_rule1(input) && part2_secondRule(input);
    }

    private boolean part2_rule1(String input) {
        // start with first pair, then scan from first pair + 2 to end, looking for same pair
        //TODO - only works when the very first pair is the match
        // need to cycle through i from 0 to length - 2 (or fewer, since last pairs must be -3/-2 -1/0
        int i = 0;
        String currentPair = String.valueOf(input.charAt(i) + input.charAt(i + 1));

        int checkFrom = i + 2;
        while (checkFrom <= input.length() - 2) {
            String thisPair = String.valueOf(input.charAt(checkFrom) + input.charAt(checkFrom + 1));
            if (thisPair.equals(currentPair)) {
                return true;
            }
            checkFrom++;
        }

        return false;
    }

    private boolean part2_secondRule(String xyxy) {
        return true;
    }



}
