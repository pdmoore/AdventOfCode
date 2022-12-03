import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03 {

    @Test
    public void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day03_example");

        int actual = thing(input);

        assertEquals(157, actual);
    }
    @Test
    public void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day03");

        int actual = thing(input);

        assertEquals(8018, actual);
    }

    private int thing(List<String> input) {

        int sum =0;
        for (String inputLine :
                input) {
            String rucksack1 = inputLine.substring(0, inputLine.length() / 2);
            String rucksack2 = inputLine.substring(inputLine.length() / 2, inputLine.length());

            for (int i = 0; i < rucksack1.length(); i++) {
                if (rucksack2.contains(""+rucksack1.charAt(i))) {
                    sum += valueOf(rucksack1.charAt(i));
                    break;
                }
            }
//            continue;

        }




        return sum;
    }

    private int valueOf(char c) {
        if (c >= 'a' && c <= 'z') return c - 'a' + 1;
        if (c >= 'A' && c <= 'Z') return c - 'A' + 27;
        return -9999;
    }

}
