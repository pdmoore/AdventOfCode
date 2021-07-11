import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Day03Tests {


    /*
> delivers presents to 2 houses: one at the starting location, and one to the east.
^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.

     */

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


}
