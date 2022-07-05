package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day08Tests {



    /*
"" is 2 characters of code (the two double quotes), but the string contains zero characters.
"abc" is 5 characters of code, but 3 characters in the string data.
"aaa\"aaa" is 10 characters of code, but the string itself contains six "a" characters and a single, escaped quote character, for a total of 7 characters in the string data.
"\x27" is 6 characters of code, but the string itself contains just one - an apostrophe ('), escaped using hexadecimal notation.

For example, given the four strings above,
the total number of characters of string code (2 + 5 + 10 + 6 = 23)
minus the total number of characters in memory for string values (0 + 3 + 7 + 1 = 11) is 23 - 11 = 12.
     */

    @Test
    void DifferenceOf_EmptyString() {
        String input = "\"\"";
        Assertions.assertEquals(2, differenceOfCharsToMemory(input));
    }

    private int differenceOfCharsToMemory(String input) {
        int inputLength = input.length();

        String memory = input.substring(1, inputLength - 1);


        return inputLength - memory.length();
    }
}
