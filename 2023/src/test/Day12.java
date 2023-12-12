package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day12 {


    // parsing/counting
    // permutations of each "?"
    // determine if permutation is ok or not
    // count valid permutations

    @Test
    void single_line_condition_record() {
        assertTrue(isPossibleArrangement("#.#.### 1,1,3"));
        assertTrue(isPossibleArrangement(".#.###.#.###### 1,3,1,6"));
        assertTrue(isPossibleArrangement("####.#...#... 4,1,1"));
        assertTrue(isPossibleArrangement("#....######..#####. 1,6,5"));
        assertTrue(isPossibleArrangement(".###.##....# 3,2,1"));

        assertFalse(isPossibleArrangement("#.#.#.# 1,1,3"));
        assertFalse(isPossibleArrangement(".######....# 3,2,1"));
    }

    @Test
    void single_line_more_damaged_than_recorded_is_not_valid() {
        assertFalse(isPossibleArrangement(".###.##.#..# 3,2,1"));
    }

    @Test
    void single_line_more_recorded_than_damaged_is_not_valid() {
        assertFalse(isPossibleArrangement(".###.##.#... 3,2,1,1"));
    }

    @Test
    void permutations_of_unknown_conditions() {
        List<String> actual = generatePermutations("???.### 1,1,3");
        assertEquals(8, actual.size());

        actual = generatePermutations("?###???????? 3,2,1");
        assertEquals(512, actual.size());  // assuming this is correct
    }

    @Test
    void count_valid_permutations() {
        assertEquals(1, countValidPermutations("???.### 1,1,3"));
        assertEquals(4, countValidPermutations(".??..??...?##. 1,1,3"));
        assertEquals(1, countValidPermutations("?#?#?#?#?#?#?#? 1,3,1,6"));
        assertEquals(1, countValidPermutations("????.#...#... 4,1,1"));
        assertEquals(4, countValidPermutations("????.######..#####. 1,6,5"));
    }

    @Test
    void count_valid_lots_of_unknowns() {
        assertEquals(10, countValidPermutations("?###???????? 3,2,1"));
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day12_part1_example");

        int actual = solve_part1(input);

        assertEquals(21, actual);
    }

    private int solve_part1(List<String> input) {
        int result = 0;
        for (String inputLine :
                input) {
            result += countValidPermutations(inputLine);
        }
        return result;
    }

    private int countValidPermutations(String inputLine) {
        List<String> permutations = generatePermutations(inputLine);

        int validCount = 0;

        for (String permutation :
                permutations) {
            if (isPossibleArrangement(permutation)) {
                validCount++;
            }
        }

        return validCount;
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
                    if (damagedSpringGroupings.isEmpty()) {
                        return false;
                    }

                    Integer expected = damagedSpringGroupings.remove(0);
                    if (damagedSpringCount != expected) {
                        return false;
                    }
                    damagedSpringCount = 0;
                }
            }
        }

        if (damagedSpringCount > 0) {
            if (damagedSpringGroupings.isEmpty()) {
                return false;
            }
            Integer expected = damagedSpringGroupings.remove(0);
            if (damagedSpringCount != expected) {
                return false;
            }
        }
        // Feels like I may be missing cases where damaged grouping is a superset of what's been found

        return damagedSpringGroupings.isEmpty();
    }

    private List<String> generatePermutations(String inputLine) {
        if (inputLine.indexOf('?') < 0) {
            throw new IllegalArgumentException("found input with no unknowns: " + inputLine);
        }

        List<String> allPermutations = permuteUnknowns(inputLine);

        return allPermutations;
    }

    private List<String> permuteUnknowns(String s) {
        List<String> both = new ArrayList<>();
        String b1 = s.replaceFirst("\\?", ".");
        String b2 = s.replaceFirst("\\?", "#");
        if (b1.contains("?")) {
            both.addAll(permuteUnknowns(b1));
        } else {
            both.add(b1);
        }
        if (b2.contains("?")) {
            both.addAll(permuteUnknowns(b2));
        } else {
            both.add(b2);
        }
        return both;
    }

}
