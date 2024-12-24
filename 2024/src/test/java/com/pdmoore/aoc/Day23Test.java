package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day23Test {

    @Test
    void verifySortedSetBehavior() {
        Set<String> alphabeticalSet = new TreeSet<>();
        alphabeticalSet.add("ag");
        alphabeticalSet.add("cg");
        alphabeticalSet.add("yn");

        Set<String> mixedSet = new TreeSet<>();
        mixedSet.add("yn");
        mixedSet.add("ag");
        mixedSet.add("cg");

        assertEquals(alphabeticalSet, mixedSet);
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day23_example.txt");

        List<Set<String>> actual = findSetsOfThree(input);
        assertEquals(12, actual.size());

        int actualCount = setsOfThreeThatContainInitialT(actual);
        assertEquals(7, actualCount);
    }

    @Test
    void canFindSetsOfThree() {
        List<String> input = List.of("aq-yn", "yn-cg", "aq-cg", "dd-ee", "ee-ff");

        List<Set<String>> actual = findSetsOfThree(input);

        assertEquals(1, actual.size());
        assertEquals(3, actual.get(0).size());
        assertTrue(actual.get(0).contains("aq"));
        assertTrue(actual.get(0).contains("cg"));
        assertTrue(actual.get(0).contains("yn"));
    }

    @Test
    void part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day23.txt");

        List<Set<String>> actual = findSetsOfThree(input);
        int actualCount = setsOfThreeThatContainInitialT(actual);
        assertEquals(1151, actualCount);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day23_example.txt");

        String actual = solvePart2(input);

        assertEquals("co,de,ka,ta", actual);
    }

    private List<Set<String>> findSetsOfThree(List<String> input) {

        Map<String, List<String>> connections = createConnectionsMap(input);

        List<Set<String>> threeComputerSets = new ArrayList<>();
        for (String firstComputer : connections.keySet()) {
            for (String secondComputer : connections.get(firstComputer)) {
                for (String thirdComputer : connections.get(secondComputer)) {
                    if (connections.get(thirdComputer).contains(firstComputer)) {
                        Set<String> computerSet = new TreeSet<>();
                        computerSet.add(firstComputer);
                        computerSet.add(secondComputer);
                        computerSet.add(thirdComputer);
                        if (!threeComputerSets.contains(computerSet)) {
                            threeComputerSets.add(computerSet);
                        }
                    }
                }
            }
        }

        return threeComputerSets;
    }

    private static Map<String, List<String>> createConnectionsMap(List<String> input) {
        Map<String, List<String>> connections = new HashMap<>();
        for (String connection : input) {
            String[] split = connection.split("-");
            String lhs = split[0];
            String rhs = split[1];
            addConnection(connections, lhs, rhs);
            addConnection(connections, rhs, lhs);
        }
        return connections;
    }

    private static void addConnection(Map<String, List<String>> connections, String lhs, String rhs) {
        if (!connections.containsKey(lhs)) {
            List<String> connectedTo = new ArrayList<>();
            connectedTo.add(rhs);
            connections.put(lhs, connectedTo);
        } else {
            connections.get(lhs).add(rhs);
        }
    }

    private int setsOfThreeThatContainInitialT(List<Set<String>> threeComputerSets) {
        int result = 0;
        for (Set<String> set : threeComputerSets) {
            for (String computer : set) {
                if (computer.startsWith("t")) {
                    result++;
                    break;
                }
            }
        }

        return result;
    }

    private String solvePart2(List<String> input) {
        Map<String, List<String>> connections = createConnectionsMap(input);

        Set<String> connectionsToProcess = connections.keySet();
        Set<String> connectionsToKeepGrowing = new TreeSet<>();
        for (String connection : connectionsToProcess) {




            connectionsToProcess = connectionsToKeepGrowing;
        }


        return "";
    }


}
