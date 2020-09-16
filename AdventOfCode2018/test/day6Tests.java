import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day6Tests {

    @Test
    public void solution1() {
        List<String> input = utilities.getFileContentsAsStrings("data/aoc18.6.txt");
        assertEquals(5941, day6.solution1(input));
    }

    @Test
    public void solution1_Sample() {
        List<String> input = Arrays.asList(
                "1, 1",
                "1, 6",
                "8, 3",
                "3, 4",
                "5, 5",
                "8, 9");

        assertEquals(17, day6.solution1(input));
    }

    @Test
    public void ManhattanDistance() {
        Point home   = new Point(1,1);
        Point adjacent = new Point(2, 1);

        assertEquals(1, day6.manhattanDistanceBetween(home, adjacent));

        Point diagonal = new Point(2, 2);
        assertEquals(2, day6.manhattanDistanceBetween(home, diagonal));
    }

    @Test
    public void solution2_Sample() {
        List<String> input = Arrays.asList(
                "1, 1",
                "1, 6",
                "8, 3",
                "3, 4",
                "5, 5",
                "8, 9");

        int maxDistance = 32;
        assertEquals(16, day6.solution2(input, maxDistance));
    }

    @Test
    public void solution2() {
        List<String> input = utilities.getFileContentsAsStrings("data/aoc18.6.txt");
        int maxDistance = 10000;
        assertEquals(40244, day6.solution2(input, maxDistance));
    }
}
