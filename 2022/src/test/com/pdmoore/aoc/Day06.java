package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06 {
    @Test
    void part1_examples() {
        assertEquals(7, part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
        assertEquals(5, part1("bvwbjplbgvbhsrlpgdmjqwftvncz"));
        assertEquals(6, part1("nppdvjthqldpwncqszvftbrmjlhg"));
        assertEquals(10, part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
        assertEquals(11, part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
    }

    @Test
    void part1_solution() {
        String input = PuzzleInput.asStringFrom("data/day06");

        int actual = part1(input);

        assertEquals(1538, actual);
    }

    private int part1(String input) {
        // for 4th char to length

        // throw each of index and preceding three into a set
        // if suze of set is 4, we're done


        for (int i = 3; i < input.length(); i++) {
            Set<Character> lastFour = new HashSet<>();
            lastFour.add(input.charAt(i));
            lastFour.add(input.charAt(i-1));
            lastFour.add(input.charAt(i-2));
            lastFour.add(input.charAt(i-3));

            if (lastFour.size() == 4) return i + 1;

        }



        return 0;
    }


}
