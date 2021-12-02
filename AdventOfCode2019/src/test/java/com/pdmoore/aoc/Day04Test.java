package com.pdmoore.aoc;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day04Test {

/*
    Two adjacent digits are the same (like 22 in 122345).
    Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
 */
    @Test
    public void ValidPassword_HasTwoAdjacentDigitsThatAreSame() {
        Assert.assertTrue(isValidPassword(112345));
        Assert.assertTrue(isValidPassword(122345));
        Assert.assertTrue(isValidPassword(123345));
        Assert.assertTrue(isValidPassword(123445));
        Assert.assertTrue(isValidPassword(123455));
        Assert.assertTrue(isValidPassword(135699));
    }

    @Test
    public void ValidPassword_DigitsSameOrIncreaseLeftToRight() {
        Assert.assertTrue(isValidPassword(123445));
        Assert.assertTrue(isValidPassword(266789));
        Assert.assertTrue(isValidPassword(111123));
        Assert.assertTrue(isValidPassword(135677));
    }

    @Test
    public void InvalidPassword_DoesNot_DigitsSameOrIncreaseLeftToRight() {
        Assert.assertFalse(isValidPassword(123443));
        Assert.assertFalse(isValidPassword(266289));
        Assert.assertFalse(isValidPassword(123451));
        Assert.assertFalse(isValidPassword(334451));
    }

    @Test
    public void InvalidPassword_DoesNot_HasTwoAdjacentDigitsThatAreSame() {
        Assert.assertFalse(isValidPassword(123456));
        Assert.assertFalse(isValidPassword(123457));
        Assert.assertFalse(isValidPassword(234589));
        Assert.assertFalse(isValidPassword(234578));
        Assert.assertFalse(isValidPassword(345689));
        Assert.assertFalse(isValidPassword(456789));
    }

    @Test
    public void Day4_Part1() {
        int lowerBound = 387638;
        int upperBound = 919123;
        List actualPasswords = findAllValidPasswords(lowerBound, upperBound);
        assertEquals(466, actualPasswords.size());
    }

    @Test
    public void Part2_InvalidPassword_DoesNot_HasOnlyTwoOfAnyDigit() {
        Assert.assertFalse(part2Rule(123444));
    }

    @Test
    public void Part2_ValidPassword_HasOnlyTwoOfAnyDigit() {
        Assert.assertTrue(part2Rule(112233));
        Assert.assertTrue(part2Rule(111122));
    }

    @Test
    public void Day4_Part2() {
        int lowerBound = 387638;
        int upperBound = 919123;
        List validPart1passwords = findAllValidPasswords(lowerBound, upperBound);
        List actual = applyPart2Rule(validPart1passwords);
        assertEquals(292, actual.size());
    }


    /*
        Solution code below
     */

    private boolean part2Rule(int password) {
        int[] counts = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        String s = Integer.toString(password);
        for (int i = 0; i < s.length(); i++) {
            int digit = Integer.parseInt(String.valueOf(s.charAt(i)));
            counts[digit] = counts[digit] + 1;
        }

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == 2) return true;
        }
        return false;
    }

    private List applyPart2Rule(List passwords) {
        List goodPasswords = new ArrayList();

        Iterator<Integer> iter = passwords.iterator();
        while (iter.hasNext()) {
            Integer password = iter.next();
            if (part2Rule(password)) {
                goodPasswords.add(password);
            }
        }
        return goodPasswords;
    }


    private List findAllValidPasswords(int lowerBound, int upperBound) {
        List validPasswords = new ArrayList();
        for (int i = lowerBound; i <= upperBound; i++) {
            if (isValidPassword(i)) {
                validPasswords.add(i);
            }
        }
        return validPasswords;
    }

    private boolean isValidPassword(int i) {
        return hasTwoSameDigitsAdjacent(i) && digitsSameOrIncreaseLeftToRight(i);
    }

    private boolean digitsSameOrIncreaseLeftToRight(int i) {
        String s = Integer.toString(i);
        for (int j = 0; j < s.length() - 1; j++) {
            if (s.charAt(j) > s.charAt(j + 1)) return false;
        }

        return true;
    }

    private boolean hasTwoSameDigitsAdjacent(int i) {
        String s = Integer.toString(i);
        for (int j = 0; j < s.length() - 1; j++) {
            if (s.charAt(j) == s.charAt(j + 1)) return true;
        }
        return false;
    }


}
