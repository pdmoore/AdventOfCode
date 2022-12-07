package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05 {

    @Test
    public void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day05_example");

        String actual = part1(input);

        assertEquals("CMZ", actual);
    }

    @Test
    public void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day05");

        String actual = part1(input);

        assertEquals("JCMHLVGMG", actual);
    }

    @Test
    public void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day05_example");

        String actual = part2(input);

        assertEquals("MCD", actual);
    }

    @Test
    public void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day05");

        String actual = part2(input);

        assertEquals("LVMRWSSPZ", actual);
    }

    private String part1(List<String> input) {
        List<Stack<String>> allStacks = createStartingPoint(input);

        for (String moveLine :
                input) {
            if (moveLine.startsWith("move")) {
                processMove(allStacks, moveLine);
            }
        }

        String answer = topOfStacks(allStacks);
        return answer;
    }

    private String part2(List<String> input) {
        List<Stack<String>> allStacks = createStartingPoint(input);

        for (String moveLine :
                input) {
            if (moveLine.startsWith("move")) {
                processMove2(allStacks, moveLine);
            }
        }

        String answer = topOfStacks(allStacks);
        return answer;}

    private void processMove2(List<Stack<String>> allStacks, String moveLine) {
        String[] s = moveLine.split(" ");
        int quantity  = Integer.parseInt(s[1]);
        Stack<String> fromStack = allStacks.get(Integer.parseInt(s[3]) - 1);
        Stack<String> toStack   = allStacks.get(Integer.parseInt(s[5]) - 1);
        StringBuilder popped = new StringBuilder();
        for (int i = 0; i < quantity; i++) {
            popped.append(fromStack.pop());
        }
        String reversed = String.valueOf(popped.reverse());
        for (int i = 0; i < reversed.length(); i++) {
            toStack.push("" + reversed.charAt(i));
        }
    }

    private void processMove(List<Stack<String>> allStacks, String moveLine) {
        String[] s = moveLine.split(" ");
        int quantity  = Integer.parseInt(s[1]);
        Stack<String> fromStack = allStacks.get(Integer.parseInt(s[3]) - 1);
        Stack<String> toStack   = allStacks.get(Integer.parseInt(s[5]) - 1);
        for (int i = 0; i < quantity; i++) {
            String popped = fromStack.pop();
            toStack.push(popped);
        }
    }

    private List<Stack<String>> createStartingPoint(List<String> input) {
        List<Stack<String>> allStacks = new ArrayList<>();
        int i = 0;
        String line = input.get(0);
        while (line.charAt(1) != '1') {
            line = input.get(++i);
        }

        int numStacks = Integer.parseInt("" + line.charAt(line.length() - 1));
        for (int j = 0; j < numStacks; j++) {
            Stack<String> s1 = new Stack<>();
            allStacks.add(s1);
        }

        // i is pointing to stack count, go backwards to and push onto stacks
        for (int j = i - 1; j >= 0 ; j--) {
            String inputLine = input.get(j);

            for (int k = 1; k < inputLine.length(); k+=4) {
                int stackNum = k / 4;
                Stack s = allStacks.get(stackNum);
                char c = inputLine.charAt(k);
                if (c != ' ') {
                    s.push("" + c);
                }
            }
        }

        return allStacks;
    }

    private String topOfStacks(List<Stack<String>> allStacks) {
        StringBuffer sb = new StringBuffer();
        for (Stack<String> s :
                allStacks) {
            sb.append(s.peek());
        }
        return sb.toString();
    }

}
