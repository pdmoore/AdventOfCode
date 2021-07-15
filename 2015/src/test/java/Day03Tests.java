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

    /*
    Santa and Robo-Santa start at the same location (delivering two presents to the same starting house),
    then take turns moving based on instructions from the elf, who is eggnoggedly reading from the same script as the previous year.

This year, how many houses receive at least one present?

For example:

^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
     */

    @Test
    public void roboSanta_simpleTwoMoveExample() {
        Assertions.assertEquals(3, deliverPresents_WithRoboSanta_ReturnUniqueHouses("^v"));
    }


    @Test
    public void Day03_Part01() {
        String input = getInputFromFile("data/day03-part01");
        Assertions.assertEquals(2572, deliverPresents_ReturnUniqueHouses(input));
    }


    private int deliverPresents_ReturnUniqueHouses(String s) {
        int x = 0;
        int y = 0;

        Point p = new Point(x, y);

        Map<Point, Integer> houses = new HashMap<>();
        houses.put(p, 1);

        for (Character c:
             s.toCharArray()) {

            if ('>' == c) {
                x += 1;
            } else if ('<' == c) {
                x -= 1;
            } else if ('^' == c) {
                y += 1;
            } else if ('v' == c) {
                y -= 1;
            }
            p = new Point(x, y);
            houses.put(p, 1);
        }

        return houses.keySet().size();
    }


    //TODO - loads of duplication
    private int deliverPresents_WithRoboSanta_ReturnUniqueHouses(String s) {
        int santaX = 0;
        int santaY = 0;
        int roboX = 0;
        int roboY = 0;
        int counter = 0;

        Point currentSanta = new Point(santaX, santaY);
        Point currentRobo = new Point(roboX, roboY);

        Map<Point, Integer> houses = new HashMap<>();
        houses.put(currentSanta, 1);

        for (Character c:
                s.toCharArray()) {

            if (counter++ % 2 == 0) {
                if ('>' == c) {
                    santaX += 1;
                } else if ('<' == c) {
                    santaX -= 1;
                } else if ('^' == c) {
                    santaY += 1;
                } else if ('v' == c) {
                    santaY -= 1;
                }
                currentSanta = new Point(santaX, santaY);
                houses.put(currentSanta, 1);
            } else {
                if ('>' == c) {
                    roboX += 1;
                } else if ('<' == c) {
                    roboX -= 1;
                } else if ('^' == c) {
                    roboY += 1;
                } else if ('v' == c) {
                    roboY -= 1;
                }
                currentRobo = new Point(roboX, roboY);
                houses.put(currentRobo, 1);
            }

        }

        return houses.keySet().size();
    }



}
