package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Tests {

    public static final String ENCODED_QUOTE = "\\\"";
    public static final String ENCODED_BACKSLASH = "\\\\";

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

    @Test
    void differenceEncodedString() {
        assertEquals(4, differenceOfEncodedLength(""));
        assertEquals(4, differenceOfEncodedLength("abc"));
        assertEquals(6, differenceOfEncodedLength("aaa\\\"aaa"));
        assertEquals(5, differenceOfEncodedLength("\\x27"));
    }

    @Test
    void Encode_StringEndingWithBackslash() {
        assertEquals("\\\"bidsptalmoicyorbv\\\\\\\\\\\"", encodedString("bidsptalmoicyorbv\\\\"));
    }

    @Test
    void Encode_StringEndingWithDoubleBackslash() {
        assertEquals("\\\"n\\\\\\\\c\\\"", encodedString("n\\\\c"));
    }

    @Test
    void part2Example() {
        List<String> input = new ArrayList<>();
        input.add("");
        input.add("abc");
        input.add("aaa\\\"aaa");
        input.add("\\x27");

        int actual = solvePart2(input);

        assertEquals(19, actual);
    }

    @Test
    void part2Example_FromFile() {
        List<String> rawInput = PuzzleInput.asStringListFrom("data/day08-example");

        int actual = solvePart2(removeQuotesFrom(rawInput));

        assertEquals(19, actual);
    }

    @Test
    void StripQuotesFromFileInput() {
        List<String> rawInput = PuzzleInput.asStringListFrom("data/day08");

        List<String> actual = removeQuotesFrom(rawInput);

        assertEquals("azlgxdbljwygyttzkfwuxv", actual.get(0));
    }

    private List<String> removeQuotesFrom(List<String> input) {
        List<String> result = new ArrayList<>();

        for (String inputLine :
                input) {
            String removed = inputLine.substring(1, inputLine.length() - 1);
            result.add(removed);
        }

        return result;
    }

    @Test
    void part02_Solution() {
        List<String> rawInput = PuzzleInput.asStringListFrom("data/day08");
        List<String> input = removeQuotesFrom(rawInput);

        int actual = solvePart2(input);

        assertEquals(2074, actual);
    }

    private String encodedString(String input) {
        StringBuffer sb = new StringBuffer();
        sb.append(ENCODED_QUOTE);
        for (int i = 0; i < input.length(); i++) {
            sb.append(encode(input.charAt(i)));
        }
        sb.append(ENCODED_QUOTE);

        return sb.toString();
    }

    private String encode(char nextChar) {
        if (nextChar == '\"') {
            return ENCODED_QUOTE;
        } else if (nextChar == '\\') {
            return ENCODED_BACKSLASH;
        }

        return String.valueOf(nextChar);
    }

    private int differenceOfEncodedLength(String input) {
        String encoded = encodedString(input);
        return encoded.length() - input.length();
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
        String inMemoryString = sb.toString();
        return inputLength - inMemoryString.length();
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

    private int solvePart2(List<String> input) {
        int result = 0;
        for (String inputLine :
                input) {
            int difference = differenceOfEncodedLength(inputLine);
            result += difference;
        }

        return result;
    }

}
