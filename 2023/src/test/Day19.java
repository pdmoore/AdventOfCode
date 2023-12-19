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
    void create_possibility_from_another() {
        Range xRange = new Range(1, 4000);
        Range mRange = new Range(1, 4000);
        Range aRange = new Range(1, 4000);
        Range sRange = new Range(1, 4000);
        Possibility p1 = new Possibility("in", xRange, mRange, aRange, sRange);
        Possibility p2 = new Possibility("qqz", p1);
        p2.sRange.end = 1350;

        assertEquals(4000, p1.sRange.end);

    }


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
        private List<Possibility> _acceptedPossibilites;

        public Part2(List<String> input, Possibility start) {
            _acceptedPossibilites = new ArrayList<>();
            // From input, build just the workflows, ignore anything after white space
            _workflows = grabWorkflowsFrom(input);

            // Then add start to List of wip, and begin processing
            _wip = new ArrayList<>();
            _wip.add(start);

//            int count = 0;
//            while (!_wip.isEmpty() && count < 50) {
            while (!_wip.isEmpty()) {
                processPossibility(_wip.remove(0));
//                count++;
            }

        }

        private void processPossibility(Possibility possibility) {
//            System.out.println(possibility);
//            if (possibility.workflowID.equals("qs")) {
//                int breakpoint = 0;
//                breakpoint++;
//            }
            if (possibility.workflowID.equals("A")) {
                _acceptedPossibilites.add(possibility);
                return;
            } else if (possibility.workflowID.equals("R")) {
                return;
            }
            // workflow ID in possibility
            // get workflow from map
            // cycle over steps
            // add to _wip as needed, splitting ranges
            String currentWorkflow = _workflows.get(possibility.workflowID);

            int curlyIndex = currentWorkflow.indexOf("{");
            String justSteps = currentWorkflow.substring(curlyIndex + 1, currentWorkflow.length() - 1);
            String[] steps = justSteps.split(",");

            for (int i = 0; i < steps.length; i++) {
                if (!steps[i].contains(":")) {
                    if (steps[i].equals("R")) {
                        return;
                    } else if (steps[i].equals("A")) {
                        _acceptedPossibilites.add(possibility);
                    } else {
                        // jump to another step with same ranges
                        String nextWorkflowID = steps[i];
                        Possibility newPossibility = new Possibility(nextWorkflowID, possibility);
                        _wip.add(newPossibility);
                        return;
                    }
                } else {
                    // add new possibility on any > or < and split the range appropriately

                    // need to split ranges
                    // Handle conditional step (contains ":"
                    String[] step = steps[i].split(":");
                    String condition = step[0];
                    String jumpToID  = step[1];
                    Range var;
                    switch (condition.charAt(0)) {
                        case 'x' -> var = possibility.xRange;
                        case 'm' -> var = possibility.mRange;
                        case 'a' -> var = possibility.aRange;
                        case 's' -> var = possibility.sRange;
                        default -> throw new IllegalArgumentException("bad part ID " + step);
                    }
                    int rhs = Integer.parseInt(condition.substring(2));

                    switch (condition.charAt(1)) {
                        case '<' -> {
                            if (var.end < rhs) {
                                Possibility newP = new Possibility(jumpToID, possibility);
                                _wip.add(newP);
                                return;
                            } else if (var.start >= rhs) {
                                // do nothing, let it fall to next step
                                //throw new IllegalArgumentException("everything in range does not fit");
                            } else {
                                Possibility newP = new Possibility(possibility.workflowID, possibility);
                                switch (condition.charAt(0)) {
                                    case 'x' -> newP.xRange.end = rhs - 1;
                                    case 'm' -> newP.mRange.end = rhs - 1;
                                    case 'a' -> newP.aRange.end = rhs - 1;
                                    case 's' -> newP.sRange.end = rhs - 1;
                                    default -> throw new IllegalArgumentException("bad part ID " + step);
                                }
                                _wip.add(newP);

                                switch (condition.charAt(0)) {
                                    case 'x' -> possibility.xRange.start = rhs;
                                    case 'm' -> possibility.mRange.start = rhs;
                                    case 'a' -> possibility.aRange.start = rhs;
                                    case 's' -> possibility.sRange.start = rhs;
                                    default -> throw new IllegalArgumentException("bad part ID " + step);
                                }
                                _wip.add(possibility);
                                return;
                            }
                        }
                        case '>' -> {
                            if (var.start > rhs) {
                                // everything fits
                                Possibility newP = new Possibility(jumpToID, possibility);
                                _wip.add(newP);
                                return;
                            } else if (var.end <= rhs) {
                                // expression fails, continue to next step
                            } else {
                                Possibility newP = new Possibility(possibility.workflowID, possibility);
                                switch (condition.charAt(0)) {
                                    case 'x' -> newP.xRange.start = rhs + 1;
                                    case 'm' -> newP.mRange.start = rhs + 1;
                                    case 'a' -> newP.aRange.start = rhs + 1;
                                    case 's' -> newP.sRange.start = rhs + 1;
                                    default -> throw new IllegalArgumentException("bad part ID " + step);
                                }
                                _wip.add(newP);

                                switch (condition.charAt(0)) {
                                    case 'x' -> possibility.xRange.end = rhs;
                                    case 'm' -> possibility.mRange.end = rhs;
                                    case 'a' -> possibility.aRange.end = rhs;
                                    case 's' -> possibility.sRange.end = rhs;
                                    default -> throw new IllegalArgumentException("bad part ID " + step);
                                }
                                _wip.add(possibility);
                                return;
                            }
                        }
                        //default -> throw new IllegalArgumentException("unhandled condition " + condition);
                    }


                }



            }


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
            // TODO continue here
            // need to process _acceptedPossibilites
            // and use the end-start+1 x/m/a/s formula with a BigInteger sum
            BigInteger result = BigInteger.ZERO;
            for (Possibility p: _acceptedPossibilites) {
                result = result.add(p.rangeCombos());
            }
            return result;
        }
    }

    static class Range {
        int start;
        int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public Range(Range other) {
            this.start = other.start;
            this.end = other.end;
        }

        @Override
        public String toString() {
            return " " + start + "-" + end;
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

        public Possibility(String workflowID, Possibility rangesFrom) {
            this.workflowID = workflowID;
            this.xRange = new Range(rangesFrom.xRange);
            this.mRange = new Range(rangesFrom.mRange);
            this.aRange = new Range(rangesFrom.aRange);
            this.sRange = new Range(rangesFrom.sRange);
        }

        public BigInteger rangeCombos() {
            int x = xRange.end - xRange.start + 1;
            int m = mRange.end - mRange.start + 1;
            int a = aRange.end - aRange.start + 1;
            int s = sRange.end - sRange.start + 1;
            BigInteger result = BigInteger.ONE;
            result = result.multiply(BigInteger.valueOf(x));
            result = result.multiply(BigInteger.valueOf(m));
            result = result.multiply(BigInteger.valueOf(a));
            result = result.multiply(BigInteger.valueOf(s));
            return result;
        }

        @Override
        public String toString() {
            return workflowID + " - x: " + xRange.toString()
                    + " - m: " + mRange.toString()
                    + " - a: " + aRange.toString()
                    + " - s: " + sRange.toString();
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
