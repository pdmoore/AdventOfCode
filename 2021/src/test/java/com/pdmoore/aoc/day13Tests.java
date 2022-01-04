package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day13Tests {

    @Test
    void day13_part1_example_FirstFoldOnly() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13_example");
        Set<Point> dotLocations = buildSet(getDotsFrom(input));
        List<String> foldInstructions = getFoldsFrom(input);

        int actual = processFold(dotLocations, foldInstructions.get(0));

        assertEquals(17, actual);
    }

    private Set<Point> buildSet(List<String> locationsAsStrings) {
        Set<Point> dotLocations = new HashSet<>();
        for (String location :
                locationsAsStrings) {
            String[] split = location.split(",");
            Point p = new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            dotLocations.add(p);
        }
        
        return dotLocations;
    }

    private List<String> getDotsFrom(List<String> input) {
        List<String> dotLocations = new ArrayList<>();
        for (String inputLine :
                input) {
            if (inputLine.isEmpty()) {
                return dotLocations;
            }
            dotLocations.add(inputLine);
        }
        return dotLocations;
    }

    private List<String> getFoldsFrom(List<String> input) {
        List<String> foldInstructions = new ArrayList<>();
        for (String inputLine :
                input) {
            if (inputLine.startsWith("fold along")) {
                foldInstructions.add(inputLine);
                return foldInstructions;
            }
        }
        return foldInstructions;
    }

    private int processFold(Set<Point> dotLocations, String foldInstruction) {

        Character axis = foldInstruction.charAt(11);
        int foldValue = Integer.parseInt(foldInstruction.split("=")[1]);

        Set<Point> newLocations = new HashSet<>();

        if (axis.equals('x')) {
            throw new UnsupportedOperationException("TODO - handle x axis");
        } else {
            for (Point p:
                 dotLocations) {
                if (p.y < foldValue) {
                    newLocations.add(p);
                } else {
                    int newY = p.y - foldValue + 1;
                    Point folded = new Point(p.x, newY);
                    newLocations.add(folded);
                }
            }

        }

        // determine x or y

        // process each dotLocation per the fold

        // count the number of dots in the resulting set


        return newLocations.size();
    }

}
