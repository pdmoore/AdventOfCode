import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day08Tests {


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

        int actual = executeProgram(input);

        assertEquals(5, actual);
    }

    @Test
    public void part1_solution() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day08-part01");

        int actual = executeProgram(input);

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
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day08-part01");

        int actual = fixTheProgram(input);

        assertEquals(703, actual);
    }


//---------------------------

    // Returns value in accumulator
    // Either executes entire program to last instruction
    // Or halts when an instruction is executed for a second time
    private int executeProgram(List<String> input) {
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

    private int fixTheProgram(List<String> input) {
        int repairIndex = 0;

        while (repairIndex < input.size()) {

            String instruction = input.get(repairIndex).substring(0, 3);
            if ("acc".equals(instruction)) {
                repairIndex++;
            } else {

                List<String> copyOfInput = new ArrayList<>();
                copyOfInput.addAll(input);

                String repairedInstruction;
                if ("jmp".equals(instruction)) {
                    repairedInstruction = input.get(repairIndex).replace("jmp", "nop");
                } else {
                    repairedInstruction = input.get(repairIndex).replace("nop", "jmp");
                }
                copyOfInput.set(repairIndex, repairedInstruction);

                if (hasInfiniteLoop(copyOfInput)) {
                    repairIndex++;
                } else {
                    return executeProgram(copyOfInput);
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


}
