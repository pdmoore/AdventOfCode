package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Day05Tests {

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
        int actual = countNiceStrings(input, this::part1_isNice);
        assertEquals(3, actual);
    }

    @Test
    public void part01() {
        List<String> input = PuzzleInput.asStringListFrom("data/day04");
        assertEquals(255, countNiceStrings(input, this::part1_isNice));
    }

    @Test
    public void part02() {
        List<String> input = PuzzleInput.asStringListFrom("data/day04");
        assertEquals(55, countNiceStrings(input, this::part2_isNice));
    }

    private int countNiceStrings(List<String> input, Predicate<String> filterBy) {
        return (int) input.
                stream().
                filter(filterBy).
                count();
    }

    private boolean part1_isNice(String input) {
        return !hasTwoCharacterSequence(input) & hasThreeOrMoreVowels(input) & hasDoubleLetters(input);
    }

    private boolean part2_isNice(String input) {
        return letterPairAppearsTwice(input) && letterRepeatsWithOneLetterBetweeen(input);
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
        long count = getCountOfChar(input, 'a');
        count += getCountOfChar(input, 'e');
        count += getCountOfChar(input, 'i');
        count += getCountOfChar(input, 'o');
        count += getCountOfChar(input, 'u');

        return count >= 3;
    }

    private long getCountOfChar(String input, char target) {
        return input.chars().filter(ch -> ch == target).count();
    }

    @Test
    public void part2_pairs_appear_atLeastTwice() {
        Assertions.assertTrue(letterPairAppearsTwice("xyxy"));
        Assertions.assertTrue(letterPairAppearsTwice("aabcdefgaa"));
        Assertions.assertTrue(letterPairAppearsTwice("zaagaa"));
        Assertions.assertTrue(letterPairAppearsTwice("zaabcdefgaa"));
    }

    @Test
    public void part2_pairs_doNotAppear_atLeastTwice() {
        Assertions.assertFalse(letterPairAppearsTwice("aaa"));
    }

    @Test
    public void part2_oneLetter_repeats_withExactlyOneLetterBetween() {
        Assertions.assertTrue(letterRepeatsWithOneLetterBetweeen("xyx"));
        Assertions.assertTrue(letterRepeatsWithOneLetterBetweeen("abcdefeghi"));
        Assertions.assertFalse(letterRepeatsWithOneLetterBetweeen("abcdefghi"));
    }

    @Test
    public void part2_fullExamples() {
        Assertions.assertTrue(part2_isNice("qjhvhtzxzqqjkmpb"));
        Assertions.assertTrue(part2_isNice("xxyxx"));
        Assertions.assertFalse(part2_isNice("uurcxstgmygtbstg"));
        Assertions.assertFalse(part2_isNice("ieodomkazucvgmuy"));
    }

    private boolean letterPairAppearsTwice(String input) {
        int i = 0;
        while (i < input.length() - 2) {
            String currentPair = "" + input.charAt(i) + input.charAt(i + 1);
            if (input.indexOf(currentPair, i + 2) > 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    private boolean letterRepeatsWithOneLetterBetweeen(String input) {
        int i = 0;
        while (i <= input.length() - 3) {
            if (input.charAt(i) == input.charAt(i + 2)) {
                return true;
            }
            i++;
        }
        return false;
    }
}
