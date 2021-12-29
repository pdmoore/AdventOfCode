package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day02Tests {

    private List<String> createSampleInput() {
        List<String> input = new ArrayList<>();
        input.add("forward 5");
        input.add("down 5");
        input.add("forward 8");
        input.add("up 3");
        input.add("down 8");
        input.add("forward 2");
        return input;
    }

    @Test
    void day2_part1_example() {
        List<String> input = createSampleInput();

        int actual = part1_processInput(input);

        assertEquals(150, actual);
    }

    @Test
    void day2_part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day02");

        int actual = part1_processInput(input);

        assertEquals(1727835, actual);
    }

    @Test
    void day2_part2_example() {
        List<String> input = createSampleInput();

        int actual = part2_processInput(input);

        assertEquals(900, actual);
    }

    @Test
    void day2_part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day02");

        int actual = part2_processInput(input);

        assertEquals(1544000595, actual);
    }

    // create a struct with horizontal, depth, aim
    // create a function that takes a struct and command and returns a new struct
    // return the last struct calculation of horizontal * depth
    private int part1_processInput(List<String> input) {
        int horizontal = 0;
        int depth = 0;

        for (String command :
                input) {
            String[] tokens = command.split(" ");
            int magnitude = Integer.parseInt(tokens[1]);
            switch (tokens[0]) {
                case "forward":
                    horizontal += magnitude;
                    break;
                case "down":
                    depth += magnitude;
                    break;
                case "up":
                    depth -= magnitude;
                    break;
            }
        }

        return horizontal * depth;
    }

    private int part2_processInput(List<String> input) {
        int horizontal = 0;
        int depth = 0;
        int aim = 0;

        for (String command :
                input) {
            String[] tokens = command.split(" ");
            int magnitude = Integer.parseInt(tokens[1]);
            switch (tokens[0]) {
                case "forward":
                    horizontal += magnitude;
                    depth += aim * magnitude;
                    break;
                case "down":
                    aim += magnitude;
                    break;
                case "up":
                    aim -= magnitude;
                    break;
            }
        }

        return horizontal * depth;
    }
}
