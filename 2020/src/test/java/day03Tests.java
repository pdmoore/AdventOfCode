import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day03Tests {

    @Test
    public void part1_simplestCase() {

        List<String> slope = new ArrayList<>();
        slope.add("..##.......");
        slope.add("#...#...#..");

        int treeCount = toboggan(slope, 3, 1);

        assertEquals(0, treeCount);
    }

    @Test
    public void part1_example() {
        List<String> slope = new ArrayList<>();
        slope.add("..##.......");
        slope.add("#...#...#..");
        slope.add(".#....#..#.");
        slope.add("..#.#...#.#");
        slope.add(".#...##..#.");
        slope.add("..#.##.....");
        slope.add(".#.#.#....#");
        slope.add(".#........#");
        slope.add("#.##...#...");
        slope.add("#...##....#");
        slope.add(".#..#...#.#");

        int treeCount = toboggan(slope, 3, 1);

        assertEquals(7, treeCount);
    }

    @Test
    public void day3_part1() {
        List<String> slope = PuzzleInput.asListOfStringsFrom("./data/day03-part01");
        int treeCount = toboggan(slope, 3, 1);

        assertEquals(162, treeCount);
    }

    @Test
    public void day3_part2_example() {
        List<String> slope = new ArrayList<>();
        slope.add("..##.......");
        slope.add("#...#...#..");
        slope.add(".#....#..#.");
        slope.add("..#.#...#.#");
        slope.add(".#...##..#.");
        slope.add("..#.##.....");
        slope.add(".#.#.#....#");
        slope.add(".#........#");
        slope.add("#.##...#...");
        slope.add("#...##....#");
        slope.add(".#..#...#.#");

        int actual = toboggan(slope, 1, 1) *
                toboggan(slope, 3, 1) *
                toboggan(slope, 5, 1) *
                toboggan(slope, 7, 1) *
                toboggan(slope, 1, 2);

        assertEquals(336, actual);
    }

    @Test
    public void day3_part2() {
        List<String> slope = PuzzleInput.asListOfStringsFrom("./data/day03-part01");

        BigInteger actual1 = BigInteger.valueOf(toboggan(slope, 1, 1));
        BigInteger actual2 = BigInteger.valueOf(toboggan(slope, 3, 1));
        BigInteger actual3 = BigInteger.valueOf(toboggan(slope, 5, 1));
        BigInteger actual4 = BigInteger.valueOf(toboggan(slope, 7, 1));
        BigInteger actual5 = BigInteger.valueOf(toboggan(slope, 1, 2));

        BigInteger expected = new BigInteger("3064612320");

        assertEquals(expected, actual1.multiply(actual2).multiply(actual3).multiply(actual4).multiply(actual5));
    }

    private int toboggan(List<String> slope, int deltaX, int deltaY) {
        int treeCount = 0;
        int x = 0;
        int y = 0;
        int slopeWidth = slope.get(0).length();

        while (y < slope.size()) {
            y += deltaY;
            if (y >= slope.size()) {
                break;
            }

            x = (x + deltaX) % slopeWidth;

            if (slope.get(y).charAt(x) == '#') {
                treeCount++;
            }
        }

        return treeCount;
    }

}
