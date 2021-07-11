import org.junit.jupiter.api.Assertions;
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

    private int deliverPresents_ReturnUniqueHouses(String s) {
        int x = 0;
        int y = 0;

        Point p = new Point(x, y);

        Map<Point, Integer> houses = new HashMap<>();
        houses.put(p, 1);

        for (Character c:
             s.toCharArray()) {

            x += 1;
            p = new Point(x, y);
            houses.put(p, 1);
        }



        return houses.keySet().size();
    }


}
