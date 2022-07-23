package com.pdmoore.aoc;

import com.google.common.base.CharMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day11Tests {

    /*
    DONE - Increase rightmost,
    xx, xy, xz, ya, yb

    PW rules
    - Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; abd doesn't count.
    - DONE Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
    - Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.

    Examples
     - hijklmmn meets the first requirement (because it contains the straight hij) but fails the second requirement requirement (because it contains i and l).
     - abbceffg meets the third requirement (because it repeats bb and ff) but fails the first requirement.
     - abbcegjk fails the third requirement, because it only has one double letter (bb).
     - The next password after abcdefgh is abcdffaa.
     - The next password after ghijklmn is ghjaabcc, because you eventually skip all the passwords that start with ghi..., since i is not allowed.

     */

    @Test
    void simpleIncrementToNextCharacter() {
        assertEquals("xy", increment("xx"));
        assertEquals("xz", increment("xy"));
        assertEquals("yb", increment("ya"));
    }

    @Test
    void increment_ZWrapsToA() {
        assertEquals("ya", increment("xz"));
    }

    @Test
    void PasswordCheck_InvalidPassword_InvalidCharacters() {
        Assertions.assertAll("Examples of password with one invalid character",
                () -> assertFalse(isValidPassword("hijklmmn")),
                () -> assertFalse(isValidPassword("abckimmn")),
                () -> assertFalse(isValidPassword("abckmmno"))
        );
    }

    @Test
    void PassworkCheck_ValidPasswordExamples() {
        Assertions.assertAll("Examples of passwords that adhere to all rules",
                () -> assertTrue(isValidPassword("abcdffaa")),
                () -> assertTrue(isValidPassword("ghjaabcc"))
        );
    }


    private boolean isValidPassword(String password) {
        // TODO - make class const
        CharMatcher illegalCharacters = CharMatcher.anyOf("ilo");
        return !illegalCharacters.matchesAnyOf(password);
    }


    private String increment(String input) {

        char lastCharacter = input.charAt(input.length() - 1);
        if (lastCharacter != 'z') {
            lastCharacter += 1;
            return input.substring(0, input.length() - 1) + lastCharacter;
        }

        String lessLastCharacter = input.substring(0, input.length() - 1);
        return increment(lessLastCharacter) + 'a';
    }
}
