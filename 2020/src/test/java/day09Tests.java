import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day09Tests {

    /*

// read input as list of ints

// preamble vs remainder

for each remainder number - loop through preamble list and check for a + b, if found, move on to next number




        List<String> input = Utilities.fileToStringList("./data/day09-part01");

     */


    @Test
    public void part1_example() {
        List<BigInteger> input = new ArrayList<>();
        input.add(BigInteger.valueOf(35));
        input.add(BigInteger.valueOf(20));
        input.add(BigInteger.valueOf(15));
        input.add(BigInteger.valueOf(25));
        input.add(BigInteger.valueOf(47));
        input.add(BigInteger.valueOf(40));
        input.add(BigInteger.valueOf(62));
        input.add(BigInteger.valueOf(55));
        input.add(BigInteger.valueOf(65));
        input.add(BigInteger.valueOf(95));
        input.add(BigInteger.valueOf(102));
        input.add(BigInteger.valueOf(117));
        input.add(BigInteger.valueOf(150));
        input.add(BigInteger.valueOf(182));
        input.add(BigInteger.valueOf(127));
        input.add(BigInteger.valueOf(219));
        input.add(BigInteger.valueOf(299));
        input.add(BigInteger.valueOf(277));
        input.add(BigInteger.valueOf(309));
        input.add(BigInteger.valueOf(576));

        for (int i = 5; i < input.size(); i++) {

            if (!findSum(input, i, 5)) {
                assertEquals(BigInteger.valueOf(127), input.get(i));
            }
        }
    }

    @Test
    public void part1_solution() {
        List<BigInteger> input = Utilities.fileToBigIntegerList("./data/day09-part01");

        for (int i = 25; i < input.size(); i++) {

            if (!findSum(input, i, 25)) {
                BigInteger x = input.get(i);
                assertEquals(BigInteger.valueOf(217430975), x);
            }
        }
    }

    @Test
    public void part2_example() {
        List<BigInteger> input = new ArrayList<>();
        input.add(BigInteger.valueOf(35));
        input.add(BigInteger.valueOf(20));
        input.add(BigInteger.valueOf(15));
        input.add(BigInteger.valueOf(25));
        input.add(BigInteger.valueOf(47));
        input.add(BigInteger.valueOf(40));
        input.add(BigInteger.valueOf(62));
        input.add(BigInteger.valueOf(55));
        input.add(BigInteger.valueOf(65));
        input.add(BigInteger.valueOf(95));
        input.add(BigInteger.valueOf(102));
        input.add(BigInteger.valueOf(117));
        input.add(BigInteger.valueOf(150));
        input.add(BigInteger.valueOf(182));
        input.add(BigInteger.valueOf(127));
        input.add(BigInteger.valueOf(219));
        input.add(BigInteger.valueOf(299));
        input.add(BigInteger.valueOf(277));
        input.add(BigInteger.valueOf(309));
        input.add(BigInteger.valueOf(576));

        BigInteger actual = findWeakness(input, BigInteger.valueOf(127));
        assertEquals(BigInteger.valueOf(62), actual);
    }

    // Tried twice and answers were too low
    // TODO - Why is the example coming up with an answer that is off?
    @Test
    @Disabled
    public void part2_solution() {
        List<BigInteger> input = Utilities.fileToBigIntegerList("./data/day09-part01");
        BigInteger actual = findWeakness(input, BigInteger.valueOf(217430975));
        assertEquals(BigInteger.valueOf(27244348), actual);
    }



// ------------------

    private boolean findSum(List<BigInteger> input, int i, int preambleSize) {
        BigInteger target = input.get(i);

        for (int j = i - preambleSize; j <= i; j++) {
            for (int k = j + 1; k <= i; k++) {
                BigInteger sumOfTwo = input.get(j).add(input.get(k));
                if ((sumOfTwo.equals(target))) {
                    return true;
                }
            }
        }
        return false;
    }


    // TODO the idea seems legit, but the implementation is off
    // when the target and sum matches, the result isn't correct
    // TODO the sum.compareTo doesn't even work
    private BigInteger findWeakness(List<BigInteger> input, BigInteger target) {
        BigInteger sum = BigInteger.valueOf(0);
        for (int smallest = 0; smallest < input.size(); smallest++) {
            sum = input.get(smallest);

            for (int i = smallest + 1; i < input.size(); i++) {
                sum = sum.add(input.get(i));

                if (sum.equals(target)) {
                    BigInteger small = input.get(smallest);
                    BigInteger large = input.get(i);  // NO IDEA WHY -1 !!!
                    BigInteger result = small.add(large);
                    return result;
                }

                if (sum.compareTo(target) < 0) {
                    continue;
                }
            }
        }

        return BigInteger.valueOf(0);
    }


}
