package com.pdmoore.aoc;

import com.google.common.base.CharMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day11Tests {

    private static final CharMatcher ILLEGAL_CHARACTERS = CharMatcher.anyOf("ilo");

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
    void PasswordCheck_InvalidPassword_LacksThreeCharacterStraight() {
        assertFalse(isValidPassword("abbceffg"));
    }

    @Test
    void PasswordCheck_InvalidPassword_LacksTwoNonOverlappingCharacterPairs() {
        assertFalse(isValidPassword("abbcegjkpqr"));
    }

    @Test
    void PassworkCheck_ValidPasswordExamples() {
        Assertions.assertAll("Examples of passwords that adhere to all rules",
                () -> assertTrue(isValidPassword("abcdffaa")),
                () -> assertTrue(isValidPassword("ghjaabcc"))
        );
    }

    @Test
    void nextValidPassword_examples() {
        // TODO - improve cases where input has an invalid character?
        // should be just to once increment each i -> j, l -> m, o -> p
        assertAll(
                () -> assertEquals("abcdffaa", nextPassword("abcdefgh")),
                () -> assertEquals("ghjaabcc", nextPassword("ghijklmn"))
        );
    }

    @Test
    void solution_Part1() {
        assertEquals("cqjxxyzz", nextPassword("cqjxjnds"));
    }

    @Test
    void solution_Part2() {
        assertEquals("cqkaabcc", nextPassword("cqjxxyzz"));
    }

    private String nextPassword(String currentPassword) {
        String nextPassword = increment(currentPassword);
        while (!isValidPassword(nextPassword)) {
            nextPassword = increment(nextPassword);
        }
        return nextPassword;
    }


    private boolean isValidPassword(String password) {
        return containsStraight(password) &&
                !containsIllegalCharacter(password) &&
                containsTwoNonOverlappingCharacterPairs(password);
    }

    private boolean containsTwoNonOverlappingCharacterPairs(String password) {
        boolean firstPairFound = false;

        for (int i = 0; i < password.length() - 1; i++) {
            char thisChar = password.charAt(i);
            if (thisChar == password.charAt(i + 1)) {
                if (!firstPairFound) {
                    firstPairFound = true;
                    i += 1;
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean containsIllegalCharacter(String password) {
        // TODO - make class const
        return ILLEGAL_CHARACTERS.matchesAnyOf(password);
    }

    private boolean containsStraight(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            char first = password.charAt(i);
            char second = password.charAt(i + 1);
            char third = password.charAt(i + 2);

            if ((second == (first + 1)) && (third == (second + 1))) {
                return true;
            }
        }

        return false;
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
