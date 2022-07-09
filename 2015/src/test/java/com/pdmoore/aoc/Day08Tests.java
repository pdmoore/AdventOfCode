package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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

    @Test
    void differenceEncodedString() {
        assertEquals(4, differenceOfEncodedLength(""));
        assertEquals(4, differenceOfEncodedLength("abc"));
        assertEquals(6, differenceOfEncodedLength("aaa\\\"aaa"));
        assertEquals(5, differenceOfEncodedLength("\\x27"));
    }

    @Test
    void Encode_StringEndingWithBackslash() {
        assertEquals("\\\"bidsptalmoicyorbv\\\\\\\"", encodedString("bidsptalmoicyorbv\\\\"));
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

        // 2988 too high - passing in strings from file with quotes included
        // 1788 too low - stripping quotes
        assertEquals(-99, actual);

        // Examples seem correct but are a bit off since I don't explicitly include the quotes around the input anymore
        // Need to run a subset of the file data exactly as it appears in file
        // TEHRE IS SOME DATA IN THE day08 input that isn't being encoded correctly
        // maybe sort it by length and look for exceptions
    }

    private String encodedString(String input) {
        StringBuffer sb = new StringBuffer();
        sb.append("\\\"");
        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == '\"') {
                sb.append("\\\\\"");
            } else if (input.charAt(i) == '\\') {
                if ((i < input.length() - 1) && (input.charAt(i + 1) == 'x')) {
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
        String encoded = encodedString(input);
        int result = encoded.length() - input.length();
        System.out.println(input + " --> " + encoded + ": " + result);
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
