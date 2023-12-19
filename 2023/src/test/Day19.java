package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
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

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day19_part1_example");
        BigInteger actual = solvePart2(input);

        assertEquals(BigInteger.valueOf(167409079868000L), actual);
    }

    private BigInteger solvePart2(List<String> input) {
        List<Possibility> paths = new ArrayList<>();
        Range xRange = new Range(1, 4000);
        Range mRange = new Range(1, 4000);
        Range aRange = new Range(1, 4000);
        Range sRange = new Range(1, 4000);
        Possibility start = new Possibility("in", xRange, mRange, aRange, sRange);

        Part2 sut = new Part2(input, start);

        return sut.allAcceptedCombinations();
    }

    static class Part2 {

        private final Map<String, String> _workflows;
        private final ArrayList<Possibility> _wip;

        public Part2(List<String> input, Possibility start) {
            // From input, build just the workflows, ignore anything after white space
            _workflows = grabWorkflowsFrom(input);

            // Then add start to List of wip, and begin processing
            _wip = new ArrayList<>();
            _wip.add(start);

            while (!_wip.isEmpty()) {
                processPossibility(_wip.remove(0));
            }

        }

        private void processPossibility(Possibility possibility) {
            // workflow ID in possibility
            // get workflow from map
            // cycle over steps
            // add to _wip as needed, splitting ranges
            String currentWorkflow = _workflows.get(possibility.workflowID);

            int curlyIndex = currentWorkflow.indexOf("{");
            String justSteps = currentWorkflow.substring(curlyIndex + 1, currentWorkflow.length() - 1);
            String[] steps = justSteps.split(",");

            for (int i = 0; i < steps.length; i++) {

                // HANDLE STEPS



            }

            // end when A, and add to Accepted Range list
            // end when runs out, Add to Accepted Range list
            // end when R, do nothing
            // add new possibility on any > or < and split the range appropriately




        }

        private Map<String, String> grabWorkflowsFrom(List<String> input) {
            Map<String, String> workflows = new HashMap<>();
            for (String inputLine : input) {
                if (inputLine.isEmpty()) {
                    break;
                }
                int curlyIndex = inputLine.indexOf("{");
                String name = inputLine.substring(0, curlyIndex);
                String rules = inputLine.substring(curlyIndex);
                workflows.put(name, rules);
            }

            return workflows;
        }

        public BigInteger allAcceptedCombinations() {
            // iterate across Possibilities that match and return
            // range.end-range-start + 1 * x/m/a/s
            // call it range.span
            return null;
        }
    }

    static class Range {
        final int start;
        final int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    static class Possibility {
        private final Range xRange;
        private final Range mRange;
        private final Range aRange;
        private final Range sRange;
        private final String workflowID;

        public Possibility(String in, Range xRange, Range mRange, Range aRange, Range sRange) {
            this.xRange = xRange;
            this.mRange = mRange;
            this.aRange = aRange;
            this.sRange = sRange;
            this.workflowID = in;
        }
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
                return process(workflows, nextStep, x, m, a, s);
            }
        }

        return true;
    }
}
