package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Tests {

    /*
    Increase rightmost,
    xx, xy, xz, ya, yb

    PW rules
    - Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; abd doesn't count.
    - Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
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
    }

    private String increment(String input) {
        char lastCharacter = input.charAt(input.length() - 1);
        lastCharacter += 1;
        return input.substring(0, 1) + lastCharacter;
    }
}
