package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day19_part1_example");

        int actual = solvePart1(input);

        assertEquals(19114, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day19");

        int actual = solvePart1(input);

        assertEquals(432434, actual);
    }

    private int solvePart1(List<String> input) {

        // Read input until blank line encountered
        // List of workflows
        // List of part ratings
        Map<String, String> workflows = new HashMap<>();
        List<String> partRatings = new ArrayList<>();
        boolean foundBlankLine = false;
        for (String inputLine : input) {
            if (inputLine.isEmpty()) {
                foundBlankLine = true;
            } else if (!foundBlankLine) {
                int curlyIndex = inputLine.indexOf("{");
                String name = inputLine.substring(0, curlyIndex);
                String rules = inputLine.substring(curlyIndex);
                workflows.put(name, rules);
            } else {
                partRatings.add(inputLine);
            }
        }

        List<String> acceptedParts = new ArrayList<>();
        for (String rating : partRatings) {
            if (accepted(workflows, rating)) {
                acceptedParts.add(rating);
            }
        }

        return sumOfParts(acceptedParts);
    }

    private int sumOfParts(List<String> acceptedParts) {
        int sum = 0;

        for (String partList : acceptedParts) {
            String[] split = partList.split(",");
            int x = Integer.parseInt(split[0].substring(3));
            int m = Integer.parseInt(split[1].substring(2));
            int a = Integer.parseInt(split[2].substring(2));
            int s = Integer.parseInt(split[3].substring(2, split[3].length() - 1));
            sum += x + m + a + s;
        }

        return sum;
    }

    private boolean accepted(Map<String, String> workflows, String rating) {
        // DUPED CODE
        String[] split = rating.split(",");
        int x = Integer.parseInt(split[0].substring(3));
        int m = Integer.parseInt(split[1].substring(2));
        int a = Integer.parseInt(split[2].substring(2));
        int s = Integer.parseInt(split[3].substring(2, split[3].length() - 1));

        String currentWorkflow = workflows.get("in");
        return process(workflows, currentWorkflow, x, m, a, s);
    }

    private boolean process(Map<String, String> workflows, String currentWorkflow, int x, int m, int a, int s) {
//            qqz{s>2770:qs,m<1801:hdj,R}
        int curlyIndex = currentWorkflow.indexOf("{");
        String justSteps = currentWorkflow.substring(curlyIndex + 1, currentWorkflow.length() - 1);
        String[] steps = justSteps.split(",");

        for (int i = 0; i < steps.length; i++) {
            if (steps[i].contains(":")) {
                String[] step = steps[i].split(":");
                String condition = step[0];
                String jumpTo = step[1];
                int var = -1;
                switch (condition.charAt(0)) {
                    case 'x' -> var = x;
                    case 'm' -> var = m;
                    case 'a' -> var = a;
                    case 's' -> var = s;
                    default -> throw new IllegalArgumentException("bad part ID " + step);
                }
                int rhs = Integer.parseInt(condition.substring(2));
                switch (condition.charAt(1)) {
                    case '<' -> {
                        if (var < rhs) {
                            if (jumpTo.equals("A")) {
                                return true;
                            }
                            if (jumpTo.equals("R")) {
                                return false;
                            }
                            String nextStep = workflows.get(jumpTo);
                            return process(workflows, nextStep, x, m, a, s);
                        }
                        break;
                    }
                    case '>' -> {
                        if (var > rhs) {
                            if (jumpTo.equals("A")) {
                                return true;
                            }
                            if (jumpTo.equals("R")) {
                                return false;
                            }

                            String nextStep = workflows.get(jumpTo);
                            return process(workflows, nextStep, x, m, a, s);
                        }
                    }
                    default -> throw new IllegalArgumentException("unhandled condition " + condition);
                }
            } else {
                if (steps[i].equals("A")) {
                    return true;
                }
                if (steps[i].equals("R")) {
                    return false;
                }
                String nextStep = workflows.get(steps[i]);
                return process(workflows, nextStep, x, m , a, s);
            }


        }

        // process each steps, maybe jumping to a new workflow


//        if (rating.contains("787") || rating.contains("2036") || rating.contains("2127")) {
//            return true;
//        }

        return true;
    }
}
