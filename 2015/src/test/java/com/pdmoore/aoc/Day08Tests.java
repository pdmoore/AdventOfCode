package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(2, differenceOfCharsToMemory(input));
    }

    @Test
    void DifferenceOf_QuotedCharacterString() {
        String input = "\"aaa\\\"aaa\"";
        assertEquals(3, differenceOfCharsToMemory(input));
    }

    @Test
    void DifferenceOf_CharacterString() {
        String input = "\"abc\"";
        assertEquals(2, differenceOfCharsToMemory(input));
    }

    @Test
    void DifferenceOf_HexString() {
        String input = "\"\\x27\"";
        assertEquals(5, differenceOfCharsToMemory(input));
    }

    @Test
    @Disabled("Need a test to ingest day08 text file, process each line, and sum difference of all lines")
    void day01_Solution() {
        // ingest day08 as list of strings
        // confirm string contain quotes and escape characters
        // for each line, cal difference and tally as running total
        // sum is the answer to day 01
    }

    private int differenceOfCharsToMemory(String input) {
        int inputLength = input.length();

        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < inputLength - 1; i++) {
            // TODO need to look ahead is it \x? then consume next as single hex and append char
            if (input.charAt(i) == '\\') {
                if (input.charAt(i + 1) =='x') {
                    String hexValue = input.substring(i+2, i+4);
                    long l = Long.parseLong(hexValue, 16);
                    char ch = (char) l;
                    sb.append(ch);

                    i += 3;
                }
            }
            else {
                sb.append(input.charAt(i));
            }
        }
        String memory = sb.toString();
        return inputLength - memory.length();
    }
}
