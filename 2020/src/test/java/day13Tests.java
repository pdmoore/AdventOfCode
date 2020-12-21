import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

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
        long result = part2Solve(input);
        long expected = 3417L;
        assertEquals(expected, result);
    }

    @Test
    public void part2_exampleInput1() {
        String input = "7,13,x,x,59,x,31,19";
        long result = part2Solve(input);
        long expected = 1068781L;
        assertEquals(expected, result);
    }

    @Test
    public void part2_exampleInput2() {
        String input = "67,7,59,61";
        long result = part2Solve(input);
        long expected = 754018L;
        assertEquals(expected, result);
    }

    @Test
    public void part2_exampleInput3() {
        String input = "67,x,7,59,61";
        long result = part2Solve(input);
        long expected = 779210L;
        assertEquals(expected, result);
    }

    @Test
    public void part2_exampleInput4() {
        String input = "67,7,x,59,61";
        long result = part2Solve(input);
        long expected = 1261476L;
        assertEquals(expected, result);
    }

    @Test
    public void part2_exampleInput5() {
        String input = "1789,37,47,1889";
        long result = part2Solve(input);
        long expected = 1202161486L;
        assertEquals(expected, result);
    }

    @Test
    public void part2_solution() {
        String input = createSolutionInput();
        long result = part2Solve(input);
        long expected = 230903629977901L;
        assertEquals(expected, result);
    }

    //--------------------------------------------

    private long part2Solve(String input) {
        List<Long> busIDs = new ArrayList<>();
        for (String busID :
                input.split(",")) {
            busIDs.add(busID.equals("x") ? -1 : Long.parseLong(busID));
        }

        // assumes first entry is always a valid busID
        final long firstBusID = busIDs.get(0);
        long lowestCommonMultiple = firstBusID;
        long time = firstBusID;

        int index = 1;
        while (true) {
            final long busID = busIDs.get(index);
            if (busID == -1) {
                index++;
                continue;
            }

            // does this busID land on the expected Departure timestamp?
            if ((time + index) % busID == 0) {
                if (++index >= busIDs.size()) {
                    return time;
                }

                lowestCommonMultiple *= busID;
                continue;
            }

            time += lowestCommonMultiple;
        }
    }

    private BigInteger part2Solve_Slow(String input) {
        String[] busIDs = input.split(",");
        Map<BigInteger, Integer> busIDandOffset = populateMap(busIDs);

        BigInteger timestamp = BigInteger.valueOf(0);
        BigInteger timeStep = BigInteger.valueOf(Integer.parseInt(busIDs[0]));

        boolean foundSolution = false;
        while (!foundSolution) {
            timestamp = timestamp.add(timeStep);

            if (departsOnTimeQuicker(timestamp, busIDandOffset)) {
                foundSolution = true;
            }
        }

        return timestamp;
    }

    private Map<BigInteger, Integer> populateMap(String[] busIDs) {

        Map<BigInteger, Integer> map = new TreeMap<>(/* Collections.reverseOrder() */);
        for (int i = 0; i < busIDs.length; i++) {
            if (!busIDs[i].equals("x")) {

                BigInteger busID = BigInteger.valueOf(Integer.parseInt(busIDs[i]));
                map.put(busID, i);
            }
        }
        return map;
    }

    private BigInteger part2Solve_tooSlow(String input) {
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

    private boolean departsOnTimeQuicker(BigInteger timestamp, Map<BigInteger, Integer> busIDandOffset) {
        for (BigInteger busID :
                busIDandOffset.keySet()) {

            int offset = busIDandOffset.get(busID);

            BigInteger timePlusIndex = timestamp.add(BigInteger.valueOf(offset));
            BigInteger mod = timePlusIndex.mod(busID);
            if (!mod.equals(BigInteger.valueOf(0)))
                return false;
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
