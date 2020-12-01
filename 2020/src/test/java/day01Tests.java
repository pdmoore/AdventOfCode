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

- List of numbers
- iterate summing one to the next
- if sum is 2020, multiply the two and halt
- bigint?

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
    @Disabled
    public void part1_solution() {
//        List<Integer> input = fileToIntegerList("../data/day01-part01.txt);

//        int actual = solve(input);

//        assertEquals(-99, actual);
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
        try{
            File f = new File(puzzleInputFile);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()){
                int i = scanner.nextInt();
                masses.add(i);
            }
        }catch(Exception err){
            err.printStackTrace();
            throw new RuntimeException("error while processing " + puzzleInputFile);
        }
        return masses;
    }
}
