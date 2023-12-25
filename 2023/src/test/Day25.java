package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day25 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day25_part1_example");

        int actual = solvePart1_take2(input);

        assertEquals(54, actual);
    }

    private int solvePart1_take2(List<String> input) {
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

        List<Connection> connections = grabConnectionsFrom(wiringDiagram);
        for (int i = 0; i < connections.size(); i++) {
            for (int j = i + 1; j < connections.size(); j++) {
                for (int k = j + 1; k < connections.size(); k++) {

                    List<Connection> attempt = new ArrayList<>(connections);

                    attempt.remove(k);
                    attempt.remove(j);
                    attempt.remove(i);

                    List<Set<String>> groups = groupsFrom(attempt);
                    if (groups.size() == 2) {
                        int result = groups.get(0).size() * groups.get(1).size();
                        return result;
                    }

                }
            }
        }

        return 0;
    }

    private List<Set<String>> groupsFrom(List<Connection> connections) {
        List<Set<String>> groups = new ArrayList<>();

        for (Connection c: connections) {
            if (groups.isEmpty()) {
                Set<String> group = new HashSet<>();
                group.addAll(c.set());
                groups.add(group);
            } else {
                boolean added = false;
                for (Set<String> knownGroup : groups) {
                    if (!Collections.disjoint(knownGroup, c.set())) {
                        knownGroup.addAll(c.set());
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    Set<String> group = new HashSet<>();
                    group.addAll(c.set());
                    groups.add(group);
                }
            }
        }

        boolean settled = false;
        while (!settled) {
            settled = true;
            for (int i = 0; i < groups.size(); i++) {
                for (int j = i + 1; j < groups.size(); j++) {
                    if (!Collections.disjoint(groups.get(j), groups.get(i))) {
                        // need to combine and empty
                        groups.get(j).addAll(groups.get(i));
                        groups.get(i).clear();
                        settled = false;
                    }
                }
            }
        }

        for (int i = groups.size() - 1; i >= 0; i--) {
            if (groups.get(i).size() == 0) {
                groups.remove(i);
            }
        }

        return groups;
    }

    private List<Connection> grabConnectionsFrom(Map<String, List<String>> wiringDiagram) {
        List<Connection> connections = new ArrayList<>();
        for (String key: wiringDiagram.keySet()) {
            for (String to : wiringDiagram.get(key)) {
                connections.add(new Connection(key, to));
            }
        }
        return connections;
    }

    class Connection {
        String from;
        String to;

        public Connection(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public Collection<String> set() {
            // TODO - do once in ctor
            Set<String> set = new HashSet<>();
            set.add(from);
            set.add(to);
            return set;
        }
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

        List<Set<String>> groups = constructGroupsFrom(wiringDiagram);
        List<String> everyComponentName = new ArrayList<>();
        for (Set<String> group: groups) {
            everyComponentName.addAll(group);
        }
        Collections.sort(everyComponentName);

        // 3 loops for each outer key component name
        for (int i = 0; i < everyComponentName.size(); i++) {

            // inner loop for each connection to that component name
//            List<String> loop1_List = wiringDiagram.get(everyComponentName.get(i));
            // TODO - Stuck on best way to generate the permutations of wiring diagram less one
            // connection....


            for (int j = i + 1; j < everyComponentName.size(); j++) {
                for (int k = j + 1; k < everyComponentName.size(); k++) {


                    List<Set<String>> mutant = constructGroupsFrom(wiringDiagram);
                    if (mutant.size() == 2) {
                        return mutant.get(0).size() * mutant.get(1).size();
                    }


                }
            }
        }


        return wiringDiagram.size();
    }

    private static List<Set<String>> constructGroupsFrom(Map<String, List<String>> wiringDiagram) {
        List<Set<String>> groups = new ArrayList<>();
        for (String componentName : wiringDiagram.keySet()) {

            List<String> thisSet = new ArrayList<>();
            thisSet.add(componentName);
            thisSet.addAll(wiringDiagram.get(componentName));

            if (groups.isEmpty()) {
                Set<String> group = new HashSet<>();
                group.addAll(thisSet);
                groups.add(group);
            } else {

                for (Set<String> knownGroup: groups) {
                    if (Collections.disjoint(knownGroup, thisSet)) {
                        Set<String> group = new HashSet<>();
                        group.addAll(thisSet);
                        groups.add(group);
                    } else {
                        knownGroup.addAll(thisSet);
                        break;
                    }
                }
            }
        }
        return groups;
    }
}
