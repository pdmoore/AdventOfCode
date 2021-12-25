package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day10Tests {

    static final BigDecimal BD_2 = BigDecimal.valueOf(2);
    static final BigDecimal BD_3 = BigDecimal.valueOf(3);
    static final BigDecimal BD_4 = BigDecimal.valueOf(4);
    static final BigDecimal BD_5 = BigDecimal.valueOf(5);

    private static final Map<Character, Character> _braces;
    static {
        Map<Character, Character> aMap = new HashMap<>();
        aMap.put('(', ')');
        aMap.put('[', ']');
        aMap.put('{', '}');
        aMap.put('<', '>');
        _braces = Collections.unmodifiableMap(aMap);
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day10_example");

        int actual = calcSyntaxErrorScore(input);

        assertEquals(26397, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day10");

        int actual = calcSyntaxErrorScore(input);

        assertEquals(369105, actual);
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

    @Test
    void part2_incompleteScoreCalculator() {
        String input = "[({(<(())[]>[[{[]{<()<>>";

        BigDecimal actual = calculateIncompleteScoreOf(input);

        assertEquals(BigDecimal.valueOf(288957), actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day10_example");

        BigDecimal actual = findMiddleIncompleteScore(input);

        assertEquals(BigDecimal.valueOf(288957), actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day10");

        BigDecimal actual = findMiddleIncompleteScore(input);

        BigDecimal expected = new BigDecimal("3999363569");
        assertEquals(expected, actual);
    }

    private BigDecimal calculateIncompleteScoreOf(String input) {
        Stack<Character> stack = new Stack();

        for (Character c :
                input.toCharArray()) {
            if (_braces.keySet().contains(c)) {
                stack.push(c);
            } else {
                stack.pop();
            }
        }

        StringBuilder completionString = new StringBuilder();
        Iterator<Character> c = stack.iterator();
        while (c.hasNext()) {
            completionString.append(_braces.get(c.next()));
        }

        return calculateScoreOfCompletionString(completionString.reverse().toString());
    }

    private BigDecimal calculateScoreOfCompletionString(String input) {
        BigDecimal score = BigDecimal.ZERO;

        char[] chars = input.toCharArray();
        for (Character c :
                chars) {
            score = score.multiply(BD_5);
            switch (c) {
                case ')': score = score.add(BigDecimal.ONE); break;
                case ']': score = score.add(BD_2); break;
                case '}': score = score.add(BD_3); break;
                case '>': score = score.add(BD_4); break;
            }
        }

        return score;
    }


    private BigDecimal findMiddleIncompleteScore(List<String> input) {
        List<BigDecimal> incompleteScores = new ArrayList<>();

        for (String inputLine :
                input) {
            if (corruptedCharacterOf(inputLine) == null) {
                BigDecimal score = calculateIncompleteScoreOf(inputLine);
                incompleteScores.add(score);
            }
        }

        Collections.sort(incompleteScores);
        int middleIndex = incompleteScores.size() / 2;
        return incompleteScores.get(middleIndex);
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
        int score = 0;
        for (String inputLine :
                input) {
            String isCorrupt = corruptedCharacterOf(inputLine);
            if (isCorrupt != null) {
                switch (isCorrupt) {
                    case ")": score += 3; break;
                    case "]": score += 57; break;
                    case "}": score += 1197; break;
                    case ">": score += 25137; break;
                }
            }
        }
        return score;
    }
}
