import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 {


    private void populateIntegerListsFrom(List<String> input, List<Integer> firstList, List<Integer> secondList) {
        for (String s : input) {
            String[] s1 = s.split("  ");
            firstList.add(Integer.parseInt(s1[0].trim()));
            secondList.add(Integer.parseInt(s1[1].trim()));
        }
    }

    private int computeDifferenceInListElements(List<Integer> firstList, List<Integer> secondList) {
        Collections.sort(firstList);
        Collections.sort(secondList);

        int result = 0;
        for (int i = 0; i < firstList.size(); i++) {
            result += Math.abs(firstList.get(i) - secondList.get(i));
        }
        return result;
    }

    @Test
    void part1_example_solved() {

        List<String> input = PuzzleInput.asStringListFrom("data/day01_example.txt");
        Assertions.assertEquals(6, input.size());

        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();

        populateIntegerListsFrom(input, firstList, secondList);

        Assertions.assertEquals(6, firstList.size());
        Assertions.assertEquals(6, secondList.size());

        int actual = computeDifferenceInListElements(firstList, secondList);
        Assertions.assertEquals(11, actual);
    }

    @Test
    void part1() {
        List<String> input = PuzzleInput.asStringListFrom("data/day01.txt");
        Assertions.assertEquals(1000, input.size());

        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();

        populateIntegerListsFrom(input, firstList, secondList);

        int actual = computeDifferenceInListElements(firstList, secondList);
        Assertions.assertEquals(2166959, actual);
    }
}
