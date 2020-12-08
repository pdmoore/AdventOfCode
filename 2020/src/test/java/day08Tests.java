import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day08Tests {

    /*

gonna need to cycle through entire input many times
have a toggle index that increments each time
- make a copy of the input
- if toggle index command is acc then skip this input
- if toggle is nop, change it to jmp
- if toggle is jmp, change it to nop

- then try to execute the program
- if the program cycles then abandon input and increment toggle index

     */


    @Test
    public void part1_example() {
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

    @Test
    public void part2_example() {
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

        int actual = fixTheProgram(input);

        assertEquals(8, actual);
    }

    @Test
    public void part2_solution() {
        List<String> input = Utilities.fileToStringList("./data/day08-part01");

        int actual = fixTheProgram(input);

        assertEquals(703, actual);
    }



    private int fixTheProgram(List<String> input) {
        int toggleIndex = 0;

        while (toggleIndex < input.size()) {

            String instruction = input.get(toggleIndex).substring(0, 3);
            if ("acc".equals(instruction)) {
                toggleIndex++;
            } else {

                List<String> copyOfInput = new ArrayList<>();
                copyOfInput.addAll(input);

                if ("jmp".equals(instruction)) {

                    // swap jmp with nop
                    String nopNotJmp = input.get(toggleIndex).replace("jmp", "nop");
                    copyOfInput.set(toggleIndex, nopNotJmp);


                } else {

                    // swap nop with jmp
                    String jmpNotNop = input.get(toggleIndex).replace("nop", "jmp");
                    copyOfInput.set(toggleIndex, jmpNotNop);
                }

                //check for infinite loop
                if (hasInfiniteLoop(copyOfInput)) {
                    toggleIndex++;
                } else {
                    return detectInfiniteLoop(copyOfInput);
                }

            }

        }

        return -1;

    }

    private boolean hasInfiniteLoop(List<String> input) {
        int currentInstruction = 0;

        List<Integer> visitedInstructions = new ArrayList<>();

        while (visitedInstructions.contains(currentInstruction) == false) {
            visitedInstructions.add(currentInstruction);

            String instruction = input.get(currentInstruction).substring(0, 3);


            if ("nop".equals(instruction)) {
                currentInstruction += 1;
            } else if ("acc".equals(instruction)) {
                currentInstruction += 1;
            } else if ("jmp".equals(instruction)) {
                String argument = input.get(currentInstruction).substring(4);
                int argValue = Integer.parseInt(argument);

                currentInstruction += argValue;
            } else {
                throw new RuntimeException("unknown instruction " + instruction + currentInstruction);
            }

            if (currentInstruction >= input.size()) return false;
        }

        return visitedInstructions.size() != input.size();
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

            if (currentInstruction >= input.size()) return accumulator;

        }

        return accumulator;
    }
}
