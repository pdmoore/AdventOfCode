package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

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

    private int solvePart1(List<String> input) {
        // read initial gates and states
        //blank line
        // read next section
        // process everything in next section
        // get result for z### gates

        Map<String, Integer> gateStates = new HashMap<>();
        int instructionIndex = 0;
        for (String line : input) {
            if (line.isEmpty()) break;
            String[] split = line.split(":");
            gateStates.put(split[0], Integer.parseInt(split[1].trim()));
            instructionIndex++;
        }

        instructionIndex++;  // skip the blank line

        for (int i = instructionIndex; i < input.size(); i++) {
            String line = input.get(i);
            String[] split = line.split(" -> ");
            String target = split[1].trim();

            if (split[0].contains(" AND ")) {
                String[] split2 = split[0].split(" AND ");

                String lhs = split2[0].trim();
                String rhs = split2[1].trim();

                Integer newValue = gateStates.get(lhs) & gateStates.get(rhs);
                gateStates.put(target, newValue);
            } else if (split[0].contains(" XOR ")) {
                String[] split2 = split[0].split(" XOR ");

                String lhs = split2[0].trim();
                String rhs = split2[1].trim();

                Integer newValue = gateStates.get(lhs) ^ gateStates.get(rhs);
                gateStates.put(target, newValue);
            } else if (split[0].contains(" OR ")) {
          //x02 OR y02 -> z02
                String[] split2 = split[0].split(" OR ");

                String lhs = split2[0].trim();
                String rhs = split2[1].trim();

                Integer newValue = gateStates.get(lhs) | gateStates.get(rhs);
                gateStates.put(target, newValue);
            } else {
                throw new RuntimeException("screwed up parsing " + line);
            }
        }


        // grab the Z's in order
        int maxZ = 2;
        // TODO - dynamically determine largest Z
        String resultString = "";
        for (int i = maxZ; i >=0; i--) {
            resultString += gateStates.get("z0"+i);

            // pass over if z## is not in gate states


        }

        // convert result string from boolean to int


        return Integer.parseInt(resultString, 2);
    }
}
