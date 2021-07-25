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
    public void niceString_Examples() {
        Assertions.assertTrue(isNice("aaa"));
        Assertions.assertTrue(isNice("ugknbfddgicrmopn"));
    }

    @Test
    public void naughtyString_LessThanThreeVowels() {
        assertFalse(isNice("aa"));
    }

    @Test
    public void naughtyString_NoDoubleLetter() {
        assertFalse(isNice("jchzalrnumimnmhp"));
    }

    @Test
    public void naughtyString_ContainsCertainTwoCharacterSequence() {
        //ab, cd, pq, or xy
        String aNiceString = "aieoooo";
        assertFalse(isNice(aNiceString + "ab"));
        assertFalse(isNice(aNiceString + "cd"));
        assertFalse(isNice(aNiceString + "pq"));
        assertFalse(isNice(aNiceString + "xy"));
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
        int countOfNice = 0;
        for (String s :
                input) {
            if (isNice(s)) countOfNice++;
        }
        return countOfNice;
    }
    
    private boolean isNice(String input) {
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
}
