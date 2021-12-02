package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day02Tests {


    //         List<Integer> input = PuzzleInput.asIntegerListFrom("./data/day02");
/*
forward X increases the horizontal position by X units.
down X increases the depth by X units.
up X decreases the depth by X units.

forward 5
down 5
forward 8
up 3
down 8
forward 2

After following these instructions, you would have a horizontal position of 15 and a depth of 10. (Multiplying these together produces 150.)

 */

    @Test
    void day2_part1_example() {

        List<String> input = new ArrayList<>();
        input.add("forward 5");
        input.add("down 5");
        input.add("forward 8");
        input.add("up 3");
        input.add("down 8");
        input.add("forward 2");

        int actual = processInput(input);

        assertEquals(150, actual);
    }

    @Test
    void day2_part1_solution() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day02");

        int actual = processInput(input);

        assertEquals(1727835, actual);
    }

    private int processInput(List<String> input) {
        int horizontal = 0;
        int depth = 0;

        for (String command :
                input) {
            String[] tokens = command.split(" ");
            int magnitude = Integer.parseInt(tokens[1]);
            switch (tokens[0]) {
                case "forward": horizontal += magnitude; break;
                case "down": depth += magnitude; break;
                case "up": depth -= magnitude; break;
            }
        }

        return horizontal * depth;
    }
}
