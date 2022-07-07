package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Tests {
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
    void DifferenceOf_SingleBackslash() {
        String input = "\"x\\\"\\xcaj\\\\xwwvpdldz\"";
        assertEquals(7, differenceOfCharsToMemory(input));
    }

    @Test
    void DifferenceOf_HexString() {
        String input = "\"\\x27\"";
        assertEquals(5, differenceOfCharsToMemory(input));
    }

    @Test
    void part01_Solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day08");
        int actual = solvePart1(input);

        assertEquals(1342, actual);
    }

    @Test
    void Encode_EmptyString() {
        String input = "";
        String actual = encodedString(input);

        assertEquals("\\\"\\\"", actual);
    }

    @Test
    void Encode_JustCharacters() {
        String actual = encodedString("abc");

        assertEquals("\\\"abc\\\"", actual);
    }

    @Test
    void Encode_QuoteInTheMiddle() {
        String actual = encodedString("aaa\\\"aaa");

        assertEquals("\\\"aaa\\\\\\\"aaa\\\"", actual);
    }

    @Test
    void Encode_HexEncoding() {
        String actual = encodedString("\\x27");

        assertEquals("\\\"\\\\x27\\\"", actual);
    }


    private String encodedString(String input) {
        StringBuffer sb = new StringBuffer();
        sb.append("\\\"");
        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == '\"') {
                sb.append("\\\\\"");
            } else if (input.charAt(i) == '\\') {
                if (input.charAt(i + 1) == 'x') {
                    sb.append("\\\\");
                } else {
                    sb.append("\\");
                }
            } else {
                sb.append(input.charAt(i));
            }

        }
        sb.append("\\\"");

        String encoded = sb.toString();
        return encoded;
    }

    private int differenceOfEncodedLength(String input) {

        int inputLength = input.length();

        StringBuffer sb = new StringBuffer();
        sb.append("\"");
        for (int i = 0; i < inputLength; i++) {

            if (input.charAt(i) == '\"') {
                sb.append("\\\"");
            } else {
                sb.append(input.charAt(i));
            }

        }
        sb.append("\"");

        String encoded = sb.toString();
        return encoded.length() - inputLength;
    }

    private int solvePart1(List<String> input) {
        int result = 0;
        for (String inputLine :
                input) {
            int difference = differenceOfCharsToMemory(inputLine);
            result += difference;
        }

        return result;
    }

    private int differenceOfCharsToMemory(String input) {
        int inputLength = input.length();

        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < inputLength - 1; i++) {
            if (input.charAt(i) == '\\') {

                char nextChar = input.charAt(i + 1);
                if (nextChar == 'x') {
                    String hexValue = input.substring(i + 2, i + 4);
                    long l = Long.parseLong(hexValue, 16);
                    char ch = (char) l;
                    sb.append(ch);

                    i += 3;
                } else if (nextChar == '\\') {
                    sb.append("\\");
                    i += 1;
                } else {
                    sb.append("\"");
                    i += 1;
                }
            } else {
                sb.append(input.charAt(i));
            }
        }
        String memory = sb.toString();
        return inputLength - memory.length();
    }
}
