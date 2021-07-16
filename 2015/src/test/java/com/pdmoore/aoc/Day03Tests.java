package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class Day03Tests {

    @Test
    public void simpleExample_UniqueHouses() {
        Assertions.assertEquals(2, deliverPresents_ReturnUniqueHouses(">"));
    }

    @Test
    public void simpleExample_DuplicateHouses_AllMoves() {
        Assertions.assertEquals(5, deliverPresents_ReturnUniqueHouses("^>v<<"));
    }

    @Test
    public void example_DuplicateHouses_TwoMoves() {
        Assertions.assertEquals(2, deliverPresents_ReturnUniqueHouses("^v^v^v^v^v"));
    }

    @Test
    public void roboSanta_simpleTwoMoveExample() {
        Assertions.assertEquals(3, deliverPresents_WithRoboSanta_ReturnUniqueHouses("^v"));
    }

    @Test
    public void roboSanta_largerExamples() {
        Assertions.assertEquals(3, deliverPresents_WithRoboSanta_ReturnUniqueHouses("^>v<"));
        Assertions.assertEquals(11, deliverPresents_WithRoboSanta_ReturnUniqueHouses("^v^v^v^v^v"));
    }

    @Test
    public void Day03_Part01() {
        String input = PuzzleInput.AsStringFromFile("data/day03-part01");
        Assertions.assertEquals(2572, deliverPresents_ReturnUniqueHouses(input));
    }

    @Test
    public void Day03_Part02() {
        String input = PuzzleInput.AsStringFromFile("data/day03-part01");
        Assertions.assertEquals(2631, deliverPresents_WithRoboSanta_ReturnUniqueHouses(input));
    }


    private int deliverPresents_ReturnUniqueHouses(String s) {
        Point currentAddress = new Point(0, 0);

        Set<Point> houseAddress = new HashSet<>();
        houseAddress.add(currentAddress);

        for (Character c:
             s.toCharArray()) {

            currentAddress = moveFrom(currentAddress, c);
            houseAddress.add(currentAddress);
        }

        return houseAddress.size();
    }

    private Point moveFrom(Point currentLocation, Character c) {
        int x = currentLocation.getX();
        int y = currentLocation.getY();
        if ('>' == c) {
            x += 1;
        } else if ('<' == c) {
            x -= 1;
        } else if ('^' == c) {
            y += 1;
        } else if ('v' == c) {
            y -= 1;
        }
        return new Point(x, y);
    }

    private int deliverPresents_WithRoboSanta_ReturnUniqueHouses(String s) {
        boolean isItSantasMove = true;

        Point currentSanta = new Point(0, 0);
        Point currentRobo = new Point(0, 0);

        Set<Point> houseAddress = new HashSet<>();
        houseAddress.add(currentSanta);
        houseAddress.add(currentRobo);

        for (Character c:
                s.toCharArray()) {

            if (isItSantasMove) {
                currentSanta = moveFrom(currentSanta, c);
                houseAddress.add(currentSanta);
            } else {
                currentRobo = moveFrom(currentRobo, c);
                houseAddress.add(currentRobo);
            }
            isItSantasMove = !isItSantasMove;
        }

//        return houses.keySet().size();
        return houseAddress.size();
    }
}
