package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day05_example.txt");

        int actual = solvePart1(input);

        assertEquals(143, actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day05_example.txt");

        int actual = solvePart2(input);

        assertEquals(123, actual);
    }

    @Test
    void part2_correctThisOrder() {
        List<String> input = PuzzleInput.asStringListFrom("data/day05_example.txt");
        List<String> pageOrderingRulesInput = splitInput(input);
        Map<Integer,List<String>> pageOrderingRules = buildMapFrom(pageOrderingRulesInput);

        String actual = correctThisOrder(pageOrderingRules, "61,13,29");
        assertEquals("61,29,13", actual);

        assertEquals("97,75,47", correctThisOrder(pageOrderingRules, "75,97,47"));
        assertEquals("97,75,53", correctThisOrder(pageOrderingRules, "75,53,97"));
        assertEquals("97,75,47,61,53", correctThisOrder(pageOrderingRules, "75,97,47,61,53"));
        assertEquals("97,75,47,29,13", correctThisOrder(pageOrderingRules, "97,13,75,29,47"));
    }

    @Test
    void part2_solved() {
        List<String> input = PuzzleInput.asStringListFrom("data/day05.txt");

        int actual = solvePart2(input);

        assertEquals(5169, actual);
    }
    private int solvePart2(List<String> input) {
        List<String> pageOrderingRulesInput = splitInput(input);
        List<String> updatePageNumbers = splitInput2(input);
        Map<Integer,List<String>> pageOrderingRules = buildMapFrom(pageOrderingRulesInput);

        List<String> incorrectlyOrderedUpdates = new ArrayList<>();
        for (String update : updatePageNumbers) {
            if (!obeysPageOrderingRules(pageOrderingRules, update)) incorrectlyOrderedUpdates.add(update);
        }

        List<String> correctlyOrderedUpdates = new ArrayList<>();
        for (String incorrectUpdate : incorrectlyOrderedUpdates) {
            correctlyOrderedUpdates.add(correctThisOrder(pageOrderingRules, incorrectUpdate));
        }

        return sumMiddleValues(correctlyOrderedUpdates);
    }

    private String correctThisOrder(Map<Integer, List<String>> pageOrderingRules, String incorrectUpdate) {
        String[] pagesToPrintArray = incorrectUpdate.split(",");
        List<String> pagesToPrint = Arrays.asList(pagesToPrintArray);

        List<String> correctOrder = new ArrayList<>();
        while (pagesToPrint.size() != correctOrder.size()) {

            for (String pageToPrint : pagesToPrint) {
                if (correctOrder.contains(pageToPrint)) continue;

                List<String> dependencies = pageOrderingRules.get(Integer.parseInt(pageToPrint));
                if (dependencies == null) {
                    correctOrder.add(pageToPrint);
                    continue;
                }

                // if no dependencies are in pagesToPrint, then it is last
                boolean dependencyFound = false;
                for (String dependency : dependencies) {
                    String[] split1 = dependency.split("\\|");
                    if (pagesToPrint.contains(split1[1])) {
                        dependencyFound = true;
                        break;
                    }
                }
                if (!dependencyFound) {
                    correctOrder.add(pageToPrint);
                    break;
                }

                // if all in correct order are in dependencies then add it as next
                int coveredRules = 0;
                int uncoveredRules = 0;
                for (String dependency : dependencies) {
                    String[] split1 = dependency.split("\\|");
                    if (correctOrder.contains(split1[1])) {
                        coveredRules++;
                    } else if (incorrectUpdate.contains(split1[1])) {
                        uncoveredRules++;
                    }
                }

                if (coveredRules != 0 &&
                        uncoveredRules == 0 &&
                        coveredRules == correctOrder.size()) correctOrder.add(pageToPrint);
            }
        }

        Collections.reverse(correctOrder);
        return String.join(",", correctOrder);
    }

    private int solvePart1(List<String> input) {
        // split input into rules and updates
        List<String> pageOrderingRulesInput = splitInput(input);
        List<String> updatePageNumbers = splitInput2(input);

        // key must be printed before values
        Map<Integer,List<String>> pageOrderingRules = buildMapFrom(pageOrderingRulesInput);

        // validate whether an update obeys rules
        List<String> updatesInRightOrder = new ArrayList<>();
        for (String update : updatePageNumbers) {
            if (obeysPageOrderingRules(pageOrderingRules, update)) updatesInRightOrder.add(update);
        }

        // for those that obey, grab middle element and sum
        return sumMiddleValues(updatesInRightOrder);
    }

    private int sumMiddleValues(List<String> updates) {
        int result = 0;
        for (String update : updates) {

            String[] split = update.split(",");
            result += Integer.parseInt(split[split.length / 2]);
        }
        return result;
    }

    private boolean obeysPageOrderingRules(Map<Integer, List<String>> pageOrderingRules, String candidate) {
        String[] pagesToPrint = candidate.split(",");
        List<String> pagesAlreadyPrinted = new ArrayList<>();

        for (String pageToPrint : pagesToPrint) {
            //get rules for pages to print
            // for each rule, if rhs is in pages printed then fail
            List<String> followingRules = pageOrderingRules.get(Integer.parseInt(pageToPrint));
            if (followingRules != null) {

                for (String rule : followingRules) {
                    String[] split1 = rule.split("\\|");
                    if (pagesAlreadyPrinted.contains(split1[1])) {
                        return false;
                    }
                }
            }
            pagesAlreadyPrinted.add(pageToPrint);
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

    // TODO combine the to splitInput methods into one and return a Pair
    private List<String> splitInput2(List<String> input) {
        boolean append = false;
        List<String> result = new ArrayList<>();
        for (String s : input) {
            if (append) result.add(s);
            if (s.isEmpty()) append = true;
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
