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
        int actual = solvePart1(createSampleInput());

        assertEquals(436, actual);
    }

    @Test
    public void part1_solution() {
        int actual = solvePart1(createDay15Input());

        assertEquals(517, actual);
    }



    private int solvePart1(String stringInput) {
        List<Integer> input = Stream.of(stringInput.split(","))
                .map(s -> Integer.parseInt(s)).collect(Collectors.toList());

        final int lastIndex = 2020;
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
                spoken.get(0).add(turn);
            } else {
                //has been spoken
                List<Integer> spokeOnTurn = spoken.get(lastNumberSpoken);

                if (spokeOnTurn.size() == 1) {
                    lastNumberSpoken = 0;
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
