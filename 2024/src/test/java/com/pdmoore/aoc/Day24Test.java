package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day24Test {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day24_example.txt");

        int actual = solvePart1(input);

        assertEquals(4, actual);
    }

    @Test
    void part1_larger_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day24_larger_example.txt");

        int actual = solvePart1(input);

        assertEquals(2024, actual);
    }

    private int solvePart1(List<String> input) {
        Map<String, Integer> gateStates = new HashMap<>();
        int instructionIndex = 0;
        for (String line : input) {
            if (line.isEmpty()) break;
            String[] split = line.split(":");
            gateStates.put(split[0], Integer.parseInt(split[1].trim()));
            instructionIndex++;
        }

        instructionIndex++;  // skip the blank line

        List<String> instructionsLeftToProcess = new ArrayList<>();
        for (int i = instructionIndex; i < input.size(); i++) {
            instructionsLeftToProcess.add(input.get(i).trim());
        }


        // TODO - left off here -
        // I have all of the unprocessed instructions, but some can't be processed
        // until later ones fire
        // Each loop through, process what I can in order, and remove those
        // Currently trying to remove the instruction being processed but that gives concurrent mod problem
        // Need to preserve ordering

        int maxZ = -1;
        List<String> processedInstructions = new ArrayList<>();
        while (processedInstructions.size() < instructionsLeftToProcess.size()) {

            for (String instruction : instructionsLeftToProcess) {
                if (processedInstructions.contains(instruction)) continue;

                String[] split = instruction.split(" -> ");
                String target = split[1].trim();
                if (target.startsWith("z")) {

                    int targetValue = Integer.parseInt(target.substring(1));
                    maxZ = Math.max(maxZ, targetValue);
                }

                if (split[0].contains(" AND ")) {
                    String[] split2 = split[0].split(" AND ");

                    String lhs = split2[0].trim();
                    String rhs = split2[1].trim();
                    if (!gateStates.containsKey(lhs) || !gateStates.containsKey(rhs)) {
                        continue;
                    }

                    Integer newValue = gateStates.get(lhs) & gateStates.get(rhs);
                    gateStates.put(target, newValue);
                } else if (split[0].contains(" XOR ")) {
                    String[] split2 = split[0].split(" XOR ");

                    String lhs = split2[0].trim();
                    String rhs = split2[1].trim();
                    if (!gateStates.containsKey(lhs) || !gateStates.containsKey(rhs)) {
                        continue;
                    }

                    Integer newValue = gateStates.get(lhs) ^ gateStates.get(rhs);
                    gateStates.put(target, newValue);
                } else if (split[0].contains(" OR ")) {
                    //x02 OR y02 -> z02
                    String[] split2 = split[0].split(" OR ");

                    String lhs = split2[0].trim();
                    String rhs = split2[1].trim();
                    if (!gateStates.containsKey(lhs) || !gateStates.containsKey(rhs)) {
                        continue;
                    }

                    Integer newValue = gateStates.get(lhs) | gateStates.get(rhs);
                    gateStates.put(target, newValue);
                } else {
                    throw new RuntimeException("screwed up parsing " + instruction);
                }

                processedInstructions.add(instruction);
            }
        }


//        for (int i = instructionIndex; i < input.size(); i++) {
//            String line = input.get(i);
//            String[] split = line.split(" -> ");
//            String target = split[1].trim();
//            if (target.startsWith("z")) {
//
//                int targetValue = Integer.parseInt(target.substring(1));
//                maxZ = Math.max(maxZ, targetValue);
//            }
//
//            if (split[0].contains(" AND ")) {
//                String[] split2 = split[0].split(" AND ");
//
//                String lhs = split2[0].trim();
//                String rhs = split2[1].trim();
//
//                Integer newValue = gateStates.get(lhs) & gateStates.get(rhs);
//                gateStates.put(target, newValue);
//            } else if (split[0].contains(" XOR ")) {
//                String[] split2 = split[0].split(" XOR ");
//
//                String lhs = split2[0].trim();
//                String rhs = split2[1].trim();
//
//                Integer newValue = gateStates.get(lhs) ^ gateStates.get(rhs);
//                gateStates.put(target, newValue);
//            } else if (split[0].contains(" OR ")) {
//                //x02 OR y02 -> z02
//                String[] split2 = split[0].split(" OR ");
//
//                String lhs = split2[0].trim();
//                String rhs = split2[1].trim();
//
//                Integer newValue = gateStates.get(lhs) | gateStates.get(rhs);
//                gateStates.put(target, newValue);
//            } else {
//                throw new RuntimeException("screwed up parsing " + line);
//            }
//        }

        String resultString = "";
        for (int i = maxZ; i >= 0; i--) {

            String prefix = "z";

            if (i < 10) {
                prefix +="0";
            } else {
            }
            Integer gateState = gateStates.get(prefix + i);
            if (gateState != null) {

                resultString += gateState;
            }

            // pass over if z## is not in gate states


        }

        // convert result string from boolean to int

//        return Integer.parseInt("0011111101000", 2);
        return Integer.parseInt(resultString, 2);
    }
}
