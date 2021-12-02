package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day1Tests {

    @Test
    public void parsePositiveLine() {
        int actual = day1.parseLine("+3");
        assertEquals(3, actual);
    }

    @Test
    public void parseNegativeLine() {
        int actual = day1.parseLine("-2");
        assertEquals(-2, actual);
    }

    @Test
    public void solution() {
        day1.solution_1();
    }

    @Test
    public void part2() {
        String input = "+3 +3 +4 -2 -4";

        assertEquals(10, day1.solution_2(input));

        input = "-6 +3 +8 +5 -6";
        assertEquals(5, day1.solution_2(input));

        input = "+7 +7 -2 -7 -4";
        assertEquals(14, day1.solution_2(input));
    }

    @Test
    public void solution_2() throws Exception {
        assertEquals(59908, day1.solution_2_from_file());
    }
}
