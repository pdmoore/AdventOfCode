package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06 {
    @Test
    void part1_examples() {
        assertEquals(7, part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 4));
        assertEquals(5, part1("bvwbjplbgvbhsrlpgdmjqwftvncz", 4));
        assertEquals(6, part1("nppdvjthqldpwncqszvftbrmjlhg", 4));
        assertEquals(10, part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 4));
        assertEquals(11, part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 4));
    }

    @Test
    void part2_examples() {
        assertEquals(19, part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14));
        assertEquals(23, part1("bvwbjplbgvbhsrlpgdmjqwftvncz", 14));
        assertEquals(23, part1("nppdvjthqldpwncqszvftbrmjlhg", 14));
        assertEquals(29, part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14));
        assertEquals(26, part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14));
    }

    @Test
    void part1_solution() {
        String input = PuzzleInput.asStringFrom("data/day06");

        int actual = part1(input, 4);

        assertEquals(1538, actual);
    }

    @Test
    void part2_solution() {
        String input = PuzzleInput.asStringFrom("data/day06");

        int actual = part1(input, 14);

        assertEquals(2315, actual);
    }

    private int part1(String input, int length) {
        for (int i = length - 1; i < input.length(); i++) {
            Set<Character> lastFour = new HashSet<>();
            for (int j = 0; j < length; j++) {
                lastFour.add(input.charAt(i-j));
            }

            if (lastFour.size() == length) return i + 1;
        }

        return 0;
    }


}
