import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day01Tests {

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

    @Test
    public void part2_example() {
        List<Integer> input = new ArrayList<>(Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263));

        int actual = sumOf3MeasurementsLargerThanPrevious(input);

        Assertions.assertEquals(5, actual);
    }

    @Test
    public void part2_solution() {
        List<Integer> input = PuzzleInput.fileToIntegerList("./data/day01");
        int actual = sumOf3MeasurementsLargerThanPrevious(input);
        Assertions.assertEquals(1789, actual);
    }
    
    private int countMeasurementsLargerThanPrevious(List<Integer> input) {
        int largerThanPrevious = 0;
        for (int i = 1; i < input.size(); i++) {
            if (input.get(i) > input.get(i - 1)) largerThanPrevious++;
        }
        return largerThanPrevious;
    }

    private int sumOf3MeasurementsLargerThanPrevious(List<Integer> input) {
        int previousSum = input.get(0) + input.get(1);
        int largerThanPrevious = 0;
        for (int i = 2; i < input.size() - 1; i++) {
            previousSum = previousSum + input.get(i);
            int nextSum = previousSum - input.get(i - 2) + input.get(i + 1);
            if (nextSum > previousSum) largerThanPrevious++;
        }
        return largerThanPrevious;
    }



}
