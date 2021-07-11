import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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
