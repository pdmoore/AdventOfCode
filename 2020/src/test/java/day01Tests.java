import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day01Tests {

    /*
- bigint?
- refactor
- utils
- hint as to what the path should be for the file!

     */


    @Test
    public void part1_example() {
        List<Integer> input = Arrays.asList(new Integer[]
                {
                        1721,
                        979,
                        366,
                        299,
                        675,
                        1456});

        int actual = solve(input);

        assertEquals(514579, actual);
    }

    @Test
    public void part2_example() {
        List<Integer> input = Arrays.asList(new Integer[]
                {
                        1721,
                        979,
                        366,
                        299,
                        675,
                        1456});

        int actual = solvePart2(input);

        assertEquals(241861950, actual);
    }

    private int solvePart2(List<Integer> input) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = i + 1; j < input.size(); j++) {
                for (int k = j + 1; k < input.size(); k++) {

                    int sum = input.get(i) + input.get(j) + input.get(k);
                    if (2020 == sum) {
                        return input.get(i) * input.get(j) * input.get(k);
                    }
                }
            }
        }

        return 0;
    }


    @Test
    public void part1_solution() {
        List<Integer> input = fileToIntegerList("./data/day01-part01");

        int actual = solve(input);

        assertEquals(802011, actual);
    }

    @Test
    public void part2_solution() {
        List<Integer> input = fileToIntegerList("./data/day01-part01");

        int actual = solvePart2(input);

        assertEquals(248607374, actual);
    }


    private int solve(List<Integer> input) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = i + 1; j < input.size(); j++) {
                int sum = input.get(i) + input.get(j);
                if (2020 == sum) {
                    return input.get(i) * input.get(j);
                }
            }
        }

        return 0;
    }


    static List<Integer> fileToIntegerList(String puzzleInputFile) {
        List<Integer> masses = new ArrayList<>();
        try {
            File f = new File(puzzleInputFile);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                int i = scanner.nextInt();
                masses.add(i);
            }
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException("error while processing " + puzzleInputFile);
        }
        return masses;
    }
}
