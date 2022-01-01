package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day12Tests {

    @Test
    void day1_smallExample() {
        List<String> input = PuzzleInput.asStringListFrom("data/day12_smallExample");

        int actual = findDistinctPaths(input);

        assertEquals(10, actual);
    }

    @Test
    void day1_slightlyLargerExample() {
        List<String> input = PuzzleInput.asStringListFrom("data/day12_slightlyLargerExample");

        int actual = findDistinctPaths(input);

        assertEquals(19, actual);
    }

    @Test
    void day1_lastExample() {
        List<String> input = PuzzleInput.asStringListFrom("data/day12_lastExample");

        int actual = findDistinctPaths(input);

        assertEquals(226, actual);
    }

    @Test
    void day1_part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day12");

        int actual = findDistinctPaths(input);

        assertEquals(5178, actual);
    }

    private int findDistinctPaths(List<String> input) {

        Cave cave = new Cave(input);

        cave.findPathsToEnd();

        return cave._paths.size();
    }

    private class Cave {
        private final Map<String, Set> _caveMap;
        private final List<String> _paths;

        public Cave(List<String> input) {
            _caveMap = new HashMap<>();
            for (String inputLine :
                    input) {
                String[] split = inputLine.split("-");
                String lhs = split[0];
                String rhs = split[1];

                if (_caveMap.get(lhs) == null) {
                    if (!rhs.equals("start")) {
                        Set<String> mapsTo = new HashSet();
                        mapsTo.add(rhs);
                        _caveMap.put(lhs, mapsTo);
                    }
                } else {
                    if (!rhs.equals("start")) {
                        _caveMap.get(lhs).add(rhs);
                    }
                }

                if (_caveMap.get(rhs) == null) {
                    if (!lhs.equals("start")) {
                        Set<String> mapsTo = new HashSet<>();
                        mapsTo.add(lhs);
                        _caveMap.put(rhs, mapsTo);
                    }
                } else {
                    if (!lhs.equals("start")) {
                        _caveMap.get(rhs).add(lhs);
                    }
                }
            }

            _paths = new ArrayList<>();
        }

        public void findPathsToEnd() {
            // path must go start--end
            Set<String> startConnections = _caveMap.get("start");
            for (String connection :
                    startConnections) {
                String pathSteps = "start,";
                List<String> smallCavesVisited = new ArrayList<>();
                recurse(pathSteps, smallCavesVisited, connection);
            }

            // small caves, lowercase, can only be visited once
            // big Caves can be visited many times
            // any need to worry about cyclic loops in Big caves?

            // get values for 'start' node
            // add start to Path
            // foreach entry in values
            // if 'end' add to Path and return (need to capture as a valid Path)
            // if value is small
            // check if already in small SmallVisited list
            // if not, add to path,
            // if small, add to SmallVisited list
            // get values for current node
            // for each entry in values
            // recurse - pass in Path, SmallVisited list, currentnode

        }

        private void recurse(String pathSteps, List<String> smallCavesVisited, String connection) {
            if (connection.equals("end")) {
                pathSteps = pathSteps.concat("end");
                _paths.add(pathSteps);
                return;
            }
            if (smallCavesVisited.contains(connection)) {
                return;
            }

            List<String> newSmallCavesVisited = new ArrayList<>();
            newSmallCavesVisited.addAll(smallCavesVisited);

            pathSteps = pathSteps.concat(connection);
            pathSteps = pathSteps.concat(",");

            if (connection.equals(connection.toLowerCase())) {
                newSmallCavesVisited.add(connection);
            }

            Set<String> connections = _caveMap.get(connection);
            for (String next :
                    connections) {
                recurse(pathSteps, newSmallCavesVisited, next);
            }
        }
    }
}
