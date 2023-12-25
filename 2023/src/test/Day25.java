package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day25 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day25_part1_example");

        int actual = solvePart1(input);

        assertEquals(54, actual);
    }

    private int solvePart1(List<String> input) {
        // map - component name to list of other components
        Map<String, List<String>> wiringDiagram = new HashMap<>();
        for (String inputLine: input) {
            String[] split = inputLine.split(":");
            String key = split[0];
            String[] otherComponents = split[1].split(" ");

            List<String> componentNames = new ArrayList<>();
            for (int i = 0; i < otherComponents.length; i++) {
                String trimmed = otherComponents[i].trim();
                if (!trimmed.isEmpty()) componentNames.add(trimmed);
            }

            wiringDiagram.put(key, componentNames);
        }

        List<Set<String>> groups = new ArrayList<>();
        List<String> visitedNames = new ArrayList<>();
        for (String componentName : wiringDiagram.keySet()) {

            List<String> thisSet = new ArrayList<>();
            thisSet.add(componentName);
            thisSet.addAll(wiringDiagram.get(componentName));

            if (!Collections.disjoint(visitedNames, thisSet)) {
                // Component is in a group, find it and add all connected to this component

                for (Set<String> group: groups) {
                    if (group.contains(componentName)) {
                        group.addAll(thisSet);
                    }
                }
            } else {
                // component name not already in a group
                Set<String> group = new HashSet<>();
                group.addAll(thisSet);
                groups.add(group);

                visitedNames.addAll(thisSet);
            }
        }


        return wiringDiagram.size();
    }
}
