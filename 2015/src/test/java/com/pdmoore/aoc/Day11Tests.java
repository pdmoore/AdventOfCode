package com.pdmoore.aoc;

import com.google.common.base.CharMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class Day11Tests {

    // Implementation assumes Illegal Characters are never sequential, or 'z'
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
    @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
    void nextValidPassword_examples() {
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
        if (ILLEGAL_CHARACTERS.matchesAnyOf(currentPassword)) {
            currentPassword = jumpAheadToNextLegalPassword(currentPassword);
        }

        String nextPassword = increment(currentPassword);
        while (!isValidPassword(nextPassword)) {
            nextPassword = increment(nextPassword);
        }
        return nextPassword;
    }

    private String jumpAheadToNextLegalPassword(String password) {
        // Locate the first illegal character
        // replace it with the next character
        // (assumes illegal characters are never consecutive)
        // pad remainder of password with 'a'
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (ILLEGAL_CHARACTERS.matches(c)) {
                c += 1;
                sb.append(c);
                for (int j = 1 + 2; j < password.length(); j++) {
                    sb.append('a');
                }
                return sb.toString();
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private boolean isValidPassword(String password) {
        return containsStraight(password) &&
                !containsIllegalCharacter(password) &&
                containsTwoNonOverlappingCharacterPairs(password);
    }

    private boolean containsTwoNonOverlappingCharacterPairs(String password) {
        boolean firstPairFound = false;

        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
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
        return ILLEGAL_CHARACTERS.matchesAnyOf(password);
    }

    private boolean containsStraight(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            char first = password.charAt(i);
            char second = password.charAt(i + 1);
            char third = password.charAt(i + 2);

            if (!areSequntial(second, third)) {
                // when 2nd and 3rd are not sequential, can start one past 3rd the next pass
                i += 3;
            } else if (areSequntial(first, second)) {
                return true;
            }
        }

        return false;
    }

    private boolean areSequntial(char first, char second) {
        return second == first + 1;
    }

    private String increment(String input) {
        char lastCharacter = input.charAt(input.length() - 1);
        if (lastCharacter != 'z') {
            do {
                lastCharacter += 1;
            } while (ILLEGAL_CHARACTERS.matches(lastCharacter));
            return input.substring(0, input.length() - 1) + lastCharacter;
        }

        String upToLastCharacter = input.substring(0, input.length() - 1);
        return increment(upToLastCharacter) + 'a';
    }
}
