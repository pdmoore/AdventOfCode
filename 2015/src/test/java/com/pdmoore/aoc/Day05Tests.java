package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class Day05Tests {

    /*
    A nice string is one with all of the following properties:

It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).

//TODO - check this last piece
It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.

//TODO - going through each string many times, look for efficiences

For example:

ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
jchzalrnumimnmhp is naughty because it has no double letter.
haegwjzuvuyypxyu is naughty because it contains the string xy.
dvszwmarrgswjxmb is naughty because it contains only one vowel.
     */


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
