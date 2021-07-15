import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day03Tests {

    // TODO duplicated from day01, need common location
    private String getInputFromFile(String filename) {
        try {
            return Files.asCharSource(new File(filename), Charsets.UTF_8).read();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't open input file: " + filename);
        }
    }

    //TODO - count of presents never mattered, didn't need a map, just a Set of points
    //TODO - some duplication in part 02 where new location is calculated and a present added at that new location

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
        String input = getInputFromFile("data/day03-part01");
        Assertions.assertEquals(2572, deliverPresents_ReturnUniqueHouses(input));
    }

    @Test
    public void Day03_Part02() {
        String input = getInputFromFile("data/day03-part01");
        Assertions.assertEquals(2631, deliverPresents_WithRoboSanta_ReturnUniqueHouses(input));
    }


    private int deliverPresents_ReturnUniqueHouses(String s) {
        Point p = new Point(0, 0);

        Map<Point, Integer> houses = new HashMap<>();
        houses.put(p, 1);

        for (Character c:
             s.toCharArray()) {

            p = moveFrom(p, c);
            houses.put(p, 1);
        }

        return houses.keySet().size();
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

        Map<Point, Integer> houses = new HashMap<>();
        houses.put(currentSanta, 1);

        for (Character c:
                s.toCharArray()) {

            if (isItSantasMove) {
                currentSanta = moveFrom(currentSanta, c);
                houses.put(currentSanta, 1);
            } else {
                currentRobo = moveFrom(currentRobo, c);
                houses.put(currentRobo, 1);
            }
            isItSantasMove = !isItSantasMove;
        }

        return houses.keySet().size();
    }
}
