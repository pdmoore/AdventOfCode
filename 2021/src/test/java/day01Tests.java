import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day01Tests {


    // count if current number is greater than preceding number
    @Test
    public void part1_example() {
        List<Integer> input = new ArrayList<>(Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263));

        int actual = countMeasurementsLargerThanPrevious(input);

        Assertions.assertEquals(7, actual);
    }

    @Test
    public void part1_solution() {
        List<Integer> input = PuzzleInput.fileToIntegerList("./data/day01");
        int actual = countMeasurementsLargerThanPrevious(input);
        Assertions.assertEquals(1754, actual);
    }

    private int countMeasurementsLargerThanPrevious(List<Integer> input) {
        int largerThanPrevious = 0;
        for (int i = 1; i < input.size(); i++) {
            if (input.get(i) > input.get(i - 1)) largerThanPrevious++;
        }
        return largerThanPrevious;
    }


}
