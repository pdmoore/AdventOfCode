package com.pdmoore.aoc;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.lang.reflect.AnnotatedArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day12 {


    // parsing/counting
    // permutations of each "?"
    // determine if permutation is ok or not
    // count valid permutations


    @Test
    void single_line_condition_record() {
        String inputLine = "#.#.### 1,1,3";

        boolean actual = isPossibleArrangement(inputLine);

        assertTrue(actual);
    }

    private boolean isPossibleArrangement(String inputLine) {
        String[] s = inputLine.split(" ");
        String springs = s[0];
        String[] nums = s[1].trim().split(",");
        List<Integer> damagedSpringGroupings = Arrays
                .stream(nums)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int damagedSpringCount = 0;
        for (char c :
                springs.toCharArray()) {
            if (c == '#') {
                damagedSpringCount++;
            } else {
                if (damagedSpringCount > 0) {
                    Integer expected = damagedSpringGroupings.remove(0);
                    if (damagedSpringCount != expected) {
                        return false;
                    }
                    damagedSpringCount = 0;
                }
            }
        }

        return true;
    }
}
