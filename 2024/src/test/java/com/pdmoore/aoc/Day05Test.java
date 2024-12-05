package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Test {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day05_example.txt");

        int actual = solvePart1(input);

        assertEquals(143, actual);
    }

    @Test
    void part1_solved() {
        List<String> input = PuzzleInput.asStringListFrom("data/day05.txt");

        int actual = solvePart1(input);

        assertEquals(6242, actual);
    }


    private int solvePart1(List<String> input) {
        // split input into rules and updates
        List<String> pageOrderingRulesInput = splitInput(input);
        List<String> updatePageNumbers = splitInput2(input);

        // key must be printed before value
        // Needs to be key, then list of Strings
        Map<Integer,List<String>> pageOrderingRules = buildMapFrom(pageOrderingRulesInput);

        // validate whether an update obeys rules
        List<String> updatesInRightOrder = new ArrayList<>();
        for (String update : updatePageNumbers) {
            if (checksOut(pageOrderingRules, update)) updatesInRightOrder.add(update);
        }

        // for those that obey, grab middle element and sum
        int result = sumMiddleValues(updatesInRightOrder);
        return result;
    }

    private int sumMiddleValues(List<String> updates) {
        int result = 0;
        for (String update : updates) {

            String[] split = update.split(",");
            result += Integer.parseInt(split[split.length / 2]);
        }
        return result;
    }

    private boolean checksOut(Map<Integer, List<String>> pageOrderingRules, String candidate) {

        // TODO Thursday morning
        // need to walk through the rules logic in description
        // I have a map with the Page number and a list of all rules related to that page number

        // the input is a given rule from the second half of the problem


        // THIS ISN'T WORKING FOR THE 61,13,29 rule
        // 61 has precedence for 13/29/53
        // but 29 is supposed to precede 13
        // so the approach I'm using is not correct


        String[] pagesToPrint = candidate.split(",");
        List<String> pagesAlreadyPrinted = new ArrayList<>();

        for (int i = 0; i < pagesToPrint.length; i++) {
            //get rules for pages to print
            // for each rule, if rhs is in pages printed then fail
            List<String> followingRules = pageOrderingRules.get(Integer.parseInt(pagesToPrint[i]));
            if (followingRules != null) {

                for (String rule : followingRules) {
                    String[] split1 = rule.split("\\|");
                    if (pagesAlreadyPrinted.contains(split1[1])) {
                        return false;
                    }
                }
            }
            pagesAlreadyPrinted.add(pagesToPrint[i]);
        }

        return true;
    }

    private Map<Integer, List<String>> buildMapFrom(List<String> pageOrderingRulesInput) {
        Map<Integer, List<String>> result = new HashMap<>();

        for (String rule : pageOrderingRulesInput) {
            String[] split = rule.split("\\|");
            int key = Integer.parseInt(split[0]);
            if (!result.containsKey(key)) {
                List<String> rules = new ArrayList<>();
                rules.add(rule);
                result.put(key, rules);
            } else {
                List<String> list = result.get(key);
                list.add(rule);
                result.put(key, list);
            }
        }

        return result;
    }

    private List<String> splitInput2(List<String> input) {
        boolean append = false;
        List<String> result = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (append) result.add(input.get(i));
            if (input.get(i).isEmpty()) append = true;
        }

        return result;
    }

    private List<String> splitInput(List<String> input) {
        List<String> result = new ArrayList<>();

        for (String line : input) {
            if (line.isEmpty()) return result;
            result.add(line);
        }

        return null;
    }
}
