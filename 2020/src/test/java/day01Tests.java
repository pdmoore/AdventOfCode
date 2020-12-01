import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day01Tests {

    /*

- List of numbers
- iterate summing one to the next
- if sum is 2020, multiply the two and halt
- bigint?

     */


    @Test
    public void part1() {
        int[] input = {
                1721,
                979,
                366,
                299,
                675,
                1456};

        int actual = solve(input);

        assertEquals(514579, actual);
    }

    private int solve(int[] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                int sum = input[i] + input[j];
                if (2020 == sum) {
                    return input[i] * input[j];
                }
            }
        }

        return 0;
    }
}
