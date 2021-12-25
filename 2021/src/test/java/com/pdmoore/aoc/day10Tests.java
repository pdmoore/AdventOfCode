package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day10Tests {

    @Test
    @Disabled
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day10_example");

        int actual = calcSyntaxErrorScore(input);

        assertEquals(26397, actual);
    }

    @Test
    void part1_corruptedChunks() {
        Assertions.assertEquals("]", corruptedCharacterOf("(]"));
        Assertions.assertEquals(">", corruptedCharacterOf("{()()()>"));
        Assertions.assertEquals("}", corruptedCharacterOf("{([(<{}[<>[]}>{[]{[(<()>"));
        Assertions.assertEquals(")", corruptedCharacterOf("[[<[([]))<([[{}[[()]]]"));
        Assertions.assertEquals("]", corruptedCharacterOf("[{[{({}]{}}([{[{{{}}([]"));
        Assertions.assertEquals(")", corruptedCharacterOf("[<(<(<(<{}))><([]([]()"));
        Assertions.assertEquals(">", corruptedCharacterOf("<{([([[(<>()){}]>(<<{{"));
    }

    private String corruptedCharacterOf(String input) {
        Stack<Character> openCharacters = new Stack();

        List<Character> openers = Arrays.asList('[', '(', '{', '<');

        for (Character c :
                input.toCharArray()) {
            if (openers.contains(c)) {
                openCharacters.push(c);
            } else {
                Character popped = openCharacters.pop();
                switch (c) {
                    case ']':
                        if (popped != '[') return c.toString(); break;
                    case ')':
                        if (popped != '(') return c.toString(); break;
                    case '}':
                        if (popped != '{') return c.toString(); break;
                    case '>':
                        if (popped != '<') return c.toString(); break;
                }
            }
        }

        return null;
    }


    private int calcSyntaxErrorScore(List<String> input) {
        return 0;
    }
}
