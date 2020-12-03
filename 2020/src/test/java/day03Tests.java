import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day03Tests {



    /*



Sample Input

..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#

right 3, down 1,
until bottom is reached

when right edge is exceeded, just rewrap to 0


"./data/day03-part01"


- read a line and get a char[] out of it
- all the lines are stacked
- start at top and move x + 3, y + 1
- if y > size return count
- if (this cell == #) treeCount++

     */

    @Test
    public void part1_simplestCase() {

        List<String> slope = new ArrayList<>();
        slope.add("..##.......");
        slope.add("#...#...#..");

        int treeCount = toboggan(slope);

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
        int treeCount = toboggan(slope);

        assertEquals(7, treeCount);
    }


    @Test
    public void day3_part1() {
        List<String> slope = Utilities.fileToStringList("./data/day03-part01");
        int treeCount = toboggan(slope);

        assertEquals(162, treeCount);
    }





    private int toboggan(List<String> slope) {
        int treeCount = 0;
        int x = 0;
        int y = 0;

        while (y < slope.size()) {
            x += 3;
            if (x >= slope.get(y).length()) {
                x -= slope.get(y).length();
            }

            y += 1;
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
