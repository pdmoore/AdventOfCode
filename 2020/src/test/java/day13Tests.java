import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day13Tests {

    String createSolutionInput() {
        return "29,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,41,x,x,x,37,x,x,x,x,x,653,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,17,x,x,x,x,x,23,x,x,x,x,x,x,x,823,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,19";
    }

    @Test
    public void part1_example() {

        int earliestTimestamp = 939;
        List<Integer> busNumbers = Arrays.asList(7, 13, 59, 31, 19);

        int result = part1Solve(earliestTimestamp, busNumbers);

        assertEquals(295, result);
    }

    @Test
    public void part1_solution() {
        int earliestTimestamp = 1008169;
        List<Integer> busNumbers = Arrays.asList(29, 41, 37, 653, 13, 17, 23, 823, 19);

        int result = part1Solve(earliestTimestamp, busNumbers);

        assertEquals(4938, result);
    }

    @Test
    public void part2_simpleInput() {
        String input = "17,x,13,19";
        BigInteger result = part2Solve(input);
        BigInteger expected = BigInteger.valueOf(3417);
        assertEquals(expected, result);
    }

    @Test
    public void part2_exampleInput() {
        String input = "7,13,x,x,59,x,31,19";
        BigInteger result = part2Solve(input);
        BigInteger expected = BigInteger.valueOf(1068781);
        assertEquals(expected, result);
    }

    @Test
    public void part2_solution() {
        String input = createSolutionInput();
        BigInteger result = part2Solve(input);
        BigInteger expected = BigInteger.valueOf(0);
        assertEquals(expected, result);
    }




    //--------------------------------------------

    private BigInteger part2Solve(String input) {
        BigInteger timestamp = BigInteger.valueOf(0);

        String[] busIDs = input.split(",");
        BigInteger timeStep = BigInteger.valueOf(Integer.parseInt(busIDs[0]));
        boolean foundSolution = false;
        while (!foundSolution) {
            timestamp = timestamp.add(timeStep);

            if (departsOnTime(timestamp, busIDs)) {
                foundSolution = true;
            }
        }

        return timestamp;
    }

    private boolean departsOnTime(BigInteger timestamp, String[] busIDs) {

        for (int i = 0; i < busIDs.length; i++) {
            if (!busIDs[i].equals("x")) {
                int busID = Integer.parseInt(busIDs[i]);
                BigInteger timePlusIndex = timestamp.add(BigInteger.valueOf(i));
                BigInteger mod = timePlusIndex.mod(BigInteger.valueOf(busID));
                if (!mod.equals(BigInteger.valueOf(0)))
                    return false;
            }
        }

        return true;
    }

    private int part1Solve(int timestamp, List<Integer> busNumbers) {
        int smallest = Integer.MAX_VALUE;
        int firstBus = 0;
        for (Integer busNumber :
                busNumbers) {

            int nextdepartureAfter = 0;
            while (nextdepartureAfter < timestamp) {
                nextdepartureAfter += busNumber;
            }

            int delta = nextdepartureAfter - timestamp;
            if (delta < smallest) {
                smallest = delta;
                firstBus = busNumber;
            }


        }

        return firstBus * smallest;
    }
}
