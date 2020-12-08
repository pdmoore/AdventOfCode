import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day08Tests {

    /*

locate infinite loop (ie, a position that has been visited already

need to parse lines
need to handle three commands
acc
jmp
nop

need a 'pointer' to current instruction

need to track in accumulator



     */


    @Test
    public void foo() {
        List<String> input = new ArrayList<>();
        input.add("nop +0");
        input.add("acc +1");
        input.add("jmp +4");
        input.add("acc +3");
        input.add("jmp -3");
        input.add("acc -99");
        input.add("acc +1");
        input.add("jmp -4");
        input.add("acc +6");

        int actual = detectInfiniteLoop(input);

        assertEquals(5, actual);
    }

    @Test
    public void part1_solution() {
        List<String> input = Utilities.fileToStringList("./data/day08-part01");

        int actual = detectInfiniteLoop(input);

        assertEquals(1586, actual);
    }


//---------------------------

    private int detectInfiniteLoop(List<String> input) {
        int accumulator = 0;
        int currentInstruction = 0;

        List<Integer> visitedInstructions = new ArrayList<>();

        while (visitedInstructions.contains(currentInstruction) == false) {
            visitedInstructions.add(currentInstruction);

            String instruction = input.get(currentInstruction).substring(0, 3);


            if ("nop".equals(instruction)) {
                currentInstruction += 1;
            } else if ("acc".equals(instruction)) {
                String argument = input.get(currentInstruction).substring(4);
                int argValue = Integer.parseInt(argument);

                accumulator += argValue;
                currentInstruction += 1;
            } else if ("jmp".equals(instruction)) {
                String argument = input.get(currentInstruction).substring(4);
                int argValue = Integer.parseInt(argument);

                currentInstruction += argValue;
            } else {
                throw new RuntimeException("unknown instruction " + instruction + currentInstruction);
            }
        }

        return accumulator;
    }
}
