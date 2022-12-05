package com.pdmoore.aoc;

import com.google.common.base.Function;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04 {

    @Test
    public void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04_example");

        Function<String, Boolean> stringBooleanFunction = inputLine1 -> part2_overlaps(inputLine1);
        int actual = processEachInputLine(input, stringBooleanFunction);

        assertEquals(2, actual);
    }

    @Test
    public void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04");

        Function<String, Boolean> stringBooleanFunction = inputLine1 -> part2_overlaps(inputLine1);
        int actual = processEachInputLine(input, stringBooleanFunction);

        assertEquals(462, actual);
    }

    @Test
    public void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04");

        Function<String, Boolean> stringBooleanFunction = inputLine1 -> overlapAtAll(inputLine1);
        int actual = processEachInputLine(input, stringBooleanFunction);

        assertEquals(835, actual);
    }

    @Test
    public void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04_example");

        Function<String, Boolean> stringBooleanFunction = inputLine1 -> overlapAtAll(inputLine1);
        int actual = processEachInputLine(input, stringBooleanFunction);

        assertEquals(4, actual);
    }

    private int processEachInputLine(List<String> input, Function<String, Boolean> doesInputLineMatchRule) {
        int result = 0;

        for (String inputLine :
                input) {
            if (doesInputLineMatchRule.apply(inputLine)) {
                result++;
            }
        }

        return result;
    }


    //TODO - introduce data object for the 4 params, pass that to the strategy
    // then combine the dupe code and pass only the strategy
    private boolean overlapAtAll(String inputLine) {
        String[] split = inputLine.split(",");
        String lhs = split[0];
        String rhs = split[1];

        String[] lhsplit = lhs.split("-");
        String[] rhsplit = rhs.split("-");

        int l_lower = Integer.parseInt(lhsplit[0]);
        int l_upper = Integer.parseInt(lhsplit[1]);

        int r_lower = Integer.parseInt(rhsplit[0]);
        int r_upper = Integer.parseInt(rhsplit[1]);


        Range range1 = new Range(l_lower, l_upper);
        Range range2 = new Range(r_lower, r_upper);
        return range1.overlaps(range2);
    }

    private boolean part2_overlaps(String inputLine) {
        String[] split = inputLine.split(",");
        String lhs = split[0];
        String rhs = split[1];

        String[] lhsplit = lhs.split("-");
        String[] rhsplit = rhs.split("-");

        int l_lower = Integer.parseInt(lhsplit[0]);
        int l_upper = Integer.parseInt(lhsplit[1]);

        int r_lower = Integer.parseInt(rhsplit[0]);
        int r_upper = Integer.parseInt(rhsplit[1]);

        Range range1 = new Range(l_lower, l_upper);
        Range range2 = new Range(r_lower, r_upper);
        return range1.fullyContains(range2) || range2.fullyContains(range1);
    }

    static class Range {
        private final int lower;
        private final int upper;

        public Range(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public boolean overlaps(Range r2) {
            if (lower <= r2.lower && upper >= r2.lower) return true;
            if (r2.lower <= lower && r2.upper >= lower) return true;

            return false;
        }

        public boolean fullyContains(Range r2) {
            return lower <= r2.lower && upper >= r2.upper;
        }
    }
}
