package com.pdmoore.aoc;

import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03 {

    @Test
    public void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day03_example");

        int actual = thing(input);

        assertEquals(157, actual);
    }

    @Test
    public void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day03_example");

        int actual = part2(input);

        assertEquals(70, actual);
    }

    @Test
    public void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day03");

        int actual = thing(input);

        assertEquals(8018, actual);
    }

    @Test
    public void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day03");

        int actual = part2(input);

        assertEquals(2518, actual);
    }

    private int thing(List<String> input) {

        int sum =0;
        for (String inputLine :
                input) {
            String rucksack1 = inputLine.substring(0, inputLine.length() / 2);
            String rucksack2 = inputLine.substring(inputLine.length() / 2, inputLine.length());

            for (int i = 0; i < rucksack1.length(); i++) {
                if (rucksack2.contains(""+rucksack1.charAt(i))) {
                    sum += valueOf(rucksack1.charAt(i));
                    break;
                }
            }
        }

        return sum;
    }

    private int valueOf(char c) {
        if (c >= 'a' && c <= 'z') return c - 'a' + 1;
        if (c >= 'A' && c <= 'Z') return c - 'A' + 27;
        return -9999;
    }

    private int part2(List<String> input) {
        int sum = 0;
        for (int i = 0; i < input.size(); i += 3) {
            String line1 = input.get(i);
            String line2 = input.get(i+1);
            String line3 = input.get(i+2);

            for (int j = 0; j < line1.length(); j++) {
                if (line2.contains(""+line1.charAt(j)) &&
                    line3.contains(""+line1.charAt(j))) {
                    sum += valueOf(line1.charAt(j));
                    break;
                }
            }
        }

        return sum;
    }
}
