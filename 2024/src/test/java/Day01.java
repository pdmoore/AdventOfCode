import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class Day01 {
    private void populateIntegerListsFrom(List<String> input, List<Integer> firstList, List<Integer> secondList) {
        for (String s : input) {
            var s1 = s.split("   ");
            firstList.add(Integer.parseInt(s1[0]));
            secondList.add(Integer.parseInt(s1[1]));
        }
    }

    private int computeDifferenceInListElements(List<Integer> firstList, List<Integer> secondList) {
        Collections.sort(firstList);
        Collections.sort(secondList);

        return IntStream
                .range(0, firstList.size())
                .map(i -> Math.abs(firstList.get(i) - secondList.get(i)))
                .sum();
    }

    private int computeSimilarityScore(List<Integer> firstList, List<Integer> secondList) {
        HashMap<Integer, Integer> secondListAppearance = new HashMap<>();
        for (Integer integer : secondList) {
            secondListAppearance.put(integer, secondListAppearance.getOrDefault(integer, 0) + 1);
        }

        return IntStream
                .range(0, firstList.size())
                .map(i -> firstList.get(i) * secondListAppearance.getOrDefault(firstList.get(i), 0))
                .sum();
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


    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day01_example.txt");

        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();

        populateIntegerListsFrom(input, firstList, secondList);

        int actual = computeSimilarityScore(firstList, secondList);
        Assertions.assertEquals(31, actual);
    }

    @Test
    void part2() {
        List<String> input = PuzzleInput.asStringListFrom("data/day01.txt");

        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();

        populateIntegerListsFrom(input, firstList, secondList);

        int actual = computeSimilarityScore(firstList, secondList);
        Assertions.assertEquals(23741109, actual);
    }
}
