import org.junit.jupiter.api.Test;

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
        List<String> slope = Utilities.fileToStringList("./data/day03-part01");
        int treeCount = toboggan(slope, 3, 1);

        assertEquals(162, treeCount);
    }



    private int toboggan(List<String> slope, int deltaX, int deltaY) {
        int treeCount = 0;
        int x = 0;
        int y = 0;

        while (y < slope.size()) {
            x += deltaX;
            if (x >= slope.get(y).length()) {
                x -= slope.get(y).length();
            }

            y += deltaY;
            if (y >= slope.size()) {
                break;
            }

            if (slope.get(y).charAt(x) == '#') {
                treeCount++;
            }
        }

        return treeCount;
    }

}
