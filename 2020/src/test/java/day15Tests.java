import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day15Tests {

    public String createDay15Input() {
        return "5,2,8,16,18,0,1";
    }

    public String createSampleInput() {
        return "0,3,6";
    }

    @Test
    public void part1_sample() {
        int actual = solvePart1(createSampleInput(), 2020);

        assertEquals(436, actual);
    }

    @Test
    public void part1_solution() {
        int actual = solvePart1(createDay15Input(), 2020);

        assertEquals(517, actual);
    }

    @Test
    public void part2_sample() {
        int actual = solvePart1(createSampleInput(), 30000000);
        assertEquals(175594, actual);
    }

    @Test
    public void part2_sample2() {
        int actual = solvePart1("1,3,2", 30000000);
        assertEquals(2578, actual);
    }

    @Test
    public void part2_sample3() {
        int actual = solvePart1("2,1,3", 30000000);
        assertEquals(3544142, actual);
    }

    @Test
    public void part2_sample4() {
        int actual = solvePart1("1,2,3", 30000000);
        assertEquals(261214, actual);
    }

    @Test
    public void part2_sample5() {
        int actual = solvePart1("2,3,1", 30000000);
        assertEquals(6895259, actual);
    }

    @Test
    public void part2_sample6() {
        int actual = solvePart1("3,2,1", 30000000);
        assertEquals(18, actual);
    }

    @Test
    public void part2_sample7() {
        int actual = solvePart1("3,1,2", 30000000);
        assertEquals(362, actual);
    }

    @Test
    public void part2_solution() {
        int actual = solvePart1(createDay15Input(), 30000000);

        assertEquals(1047739, actual);
    }

//-------------------------------

    private int solvePart1(String stringInput, int lastIndex) {
        List<Integer> input = Stream.of(stringInput.split(","))
                .map(s -> Integer.parseInt(s)).collect(Collectors.toList());

        Map<Integer, List<Integer>> spoken = new HashMap<>();
        int turn = 1;
        int lastNumberSpoken = 0;
        for (Integer inputNumber :
                input) {
            lastNumberSpoken = inputNumber;
            if (!spoken.containsKey(inputNumber)) {
                List<Integer> spokenOnTurn = new ArrayList<>();
                spokenOnTurn.add(turn);
                spoken.put(inputNumber, spokenOnTurn);
            } else {
              throw new RuntimeException("handle case when input string is found in map");
            }
            turn++;
        }

        while (turn <= lastIndex) {

            if (!spoken.containsKey(lastNumberSpoken)) {
                lastNumberSpoken = 0;
                if (!spoken.containsKey(0)) {
                    List<Integer> spokenOnTurn = new ArrayList<>();
                    spoken.put(0, spokenOnTurn);
                }
                spoken.get(0).add(turn);
            } else {
                //has been spoken
                List<Integer> spokeOnTurn = spoken.get(lastNumberSpoken);

                if (spokeOnTurn.size() == 1) {
                    lastNumberSpoken = 0;
                    if (!spoken.containsKey(0)) {
                        List<Integer> spokenOnTurn = new ArrayList<>();
                        spoken.put(0, spokenOnTurn);
                    }
                    spoken.get(0).add(turn);
                } else {
                    int lastTurnSpoken = spokeOnTurn.size() - 1;
                    int difference = spokeOnTurn.get(lastTurnSpoken) - spokeOnTurn.get(lastTurnSpoken - 1);
                    if (spoken.containsKey(difference)) {
                        lastNumberSpoken = difference;
                        spoken.get(lastNumberSpoken).add(turn);
                    } else {
                        List<Integer> spokenOnTurn = new ArrayList<>();
                        spokenOnTurn.add(turn);
                        spoken.put(difference, spokenOnTurn);
                        lastNumberSpoken = difference;
                    }
                }
            }

            turn++;
        }


        return lastNumberSpoken;
    }
}
