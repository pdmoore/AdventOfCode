import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day10Tests {

    public List<Integer> createSimpleInput() {
        List<Integer> input = new ArrayList<>();
        input.add(16);
        input.add(10);
        input.add(15);
        input.add(5);
        input.add(1);
        input.add(11);
        input.add(7);
        input.add(19);
        input.add(6);
        input.add(12);
        input.add(4);
        return input;
    }

    private List<Integer> createSecondInput() {
        List<Integer> input = new ArrayList<>();
        input.add(28);
        input.add(33);
        input.add(18);
        input.add(42);
        input.add(31);
        input.add(14);
        input.add(46);
        input.add(20);
        input.add(48);
        input.add(47);
        input.add(24);
        input.add(23);
        input.add(49);
        input.add(45);
        input.add(19);
        input.add(38);
        input.add(39);
        input.add(11);
        input.add(1);
        input.add(32);
        input.add(25);
        input.add(35);
        input.add(8);
        input.add(17);
        input.add(7);
        input.add(9);
        input.add(4);
        input.add(2);
        input.add(34);
        input.add(10);
        input.add(3);
        return input;
    }

    @Test
    public void part1_simpleExample() {
        int result = solvePart1(createSimpleInput());

        assertEquals(7 * 5, result);
    }


    @Test
    public void part1_secondExample() {
        int result = solvePart1(createSecondInput());

        assertEquals(22 * 10, result);
    }

    @Test
    public void part1_solution() {
        List<Integer> input = Utilities.fileToIntegerList("./data/day10-part01");
        int result = solvePart1(input);

        assertEquals(2760, result);
    }


    private int solvePart1(List<Integer> input) {
        Collections.sort(input);

        int jolt1 = 0;
        int jolt3 = 0;
        int currentJoltage = 0;

        for (int i = 0; i < input.size(); i++) {
            int thisJoltage = input.get(i);
            if (thisJoltage - currentJoltage == 1) {
                jolt1++;
            } else if (thisJoltage - currentJoltage == 3) {
                jolt3++;
            }


            currentJoltage = thisJoltage;
        }

        jolt3++;  // the device has a final 3 step

        return jolt1 * jolt3;
    }


}
