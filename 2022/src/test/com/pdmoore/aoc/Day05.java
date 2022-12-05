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

    private String part1(List<String> input) {
        // parse input to create starting stacks

        // get list of moves

        // process each move

        // get magic string from current stack state

        Stack<String> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();
        Stack<String> s3 = new Stack<>();
        List<Stack<String>> allStacks = new ArrayList<>();
        allStacks.add(s1);
        allStacks.add(s2);
        allStacks.add(s3);

        s1.push("C");

        s2.push("M");

        s3.push("P");
        s3.push("D");
        s3.push("N");
        s3.push("Z");

        String answer = topOfStacks(allStacks);


        return answer;
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
