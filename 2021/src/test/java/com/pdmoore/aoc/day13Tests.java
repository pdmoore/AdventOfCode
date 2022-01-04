package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day13Tests {

    @Test
    void day13_part1_example_FirstFoldOnly() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13_example");
        Set<Point> dotLocations = buildSet(getDotsFrom(input));
        List<String> foldInstructions = getFoldsFrom(input);

        Set<Point> locationsAfterFold = processFold(dotLocations, foldInstructions.get(0));
        int actual = locationsAfterFold.size();

        assertEquals(17, actual);
    }

    @Test
    void day13_part1_example_SecondFold() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13_example");
        Set<Point> dotLocations = buildSet(getDotsFrom(input));
        List<String> foldInstructions = getFoldsFrom(input);

        Set<Point> locationsAfterFold = processFold(dotLocations, foldInstructions.get(0));
        locationsAfterFold = processFold(locationsAfterFold, foldInstructions.get(1));
        int actual = locationsAfterFold.size();

        assertEquals(16, actual);
    }

    @Test
    void day13_part1_solution_FirstFoldOnly() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13");
        Set<Point> dotLocations = buildSet(getDotsFrom(input));
        List<String> foldInstructions = getFoldsFrom(input);

        Set<Point> points = processFold(dotLocations, foldInstructions.get(0));
        int actual = points.size();

        assertEquals(716, actual);
    }

    @Test
    void day13_part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day13");
        Set<Point> points = buildSet(getDotsFrom(input));
        List<String> foldInstructions = getFoldsFrom(input);

        for (String fold:
                foldInstructions) {
            points = processFold(points, fold);
        }

        displayResult(points);
        // Set a breakpoint and view the display variable
        // it reads vertical, not horizontal, and characters are mirrored
        //not sure if I feel like solving, or testing, for that
    }

    private Set<Point> processFold(Set<Point> dotLocations, String foldInstruction) {
        Character axis = foldInstruction.charAt(11);
        int foldValue = Integer.parseInt(foldInstruction.split("=")[1]);

        Set<Point> newLocations = new HashSet<>();

        if (axis.equals('x')) {
            for (Point p:
                    dotLocations) {
                if (p.x < foldValue) {
                    newLocations.add(p);
                } else {
                    int delta = p.x - foldValue;
                    int newX = foldValue - delta;

                    Point folded = new Point(newX, p.y);
                    newLocations.add(folded);
                }
            }
        } else {
            for (Point p:
                    dotLocations) {
                if (p.y < foldValue) {
                    newLocations.add(p);
                } else {
                    int delta = p.y - foldValue;
                    int newY = foldValue - delta;

                    Point folded = new Point(p.x, newY);
                    newLocations.add(folded);
                }
            }
        }

        return newLocations;
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
            }
        }
        return foldInstructions;
    }


    private void displayResult(Set<Point> points) {
        char[][] display = new char[50][10];
        for (int i = 0; i < display.length; i++) {
            Arrays.fill(display[i], '_');
        }
        for (Point p :
                points) {
            display[p.x][p.y] = '#';
        }

        int actual = points.size();
    }


}
