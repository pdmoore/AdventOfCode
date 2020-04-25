import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Day02Tests {

    private static final int OPCODE_ADD = 1;
    private static final int OPCODE_MULTIPLY = 2;
    private static final int OPCODE_HALT = 99;

    private static final int NUM_VALUES_IN_INSTRUCTION = 4;

    @Test
    public void SingleAddCommand() {
        String input = "1,0,0,0,99";
        String expected = "2,0,0,0,99";
        String actual = executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void SingleMultiplyCommand() {
        String input = "2,3,0,3,99";
        String expected = "2,3,0,6,99";
        String actual = executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void SingleMultiplyCommand_LargerNumbers() {
        String input = "2,4,4,5,99,0";
        String expected = "2,4,4,5,99,9801";
        String actual = executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void TwoCommands() {
        String input = "1,1,1,4,99,5,6,0,99";
        String expected = "30,1,1,4,2,5,6,0,99";
        String actual = executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void PartOne_InitialExample() {
        String input = "1,9,10,3,2,3,11,0,99,30,40,50";
        String expected = "3500,9,10,70,2,3,11,0,99,30,40,50";
        String actual = executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void PartOne_Solution() {
        String input = "1,12,2,3,1,1,2,3,1,3,4,3,1,5,0,3,2,9,1,19,1,19,5,23,1,23,5,27,2,27,10,31,1,31,9,35,1,35,5,39,1,6,39,43,2,9,43,47,1,5,47,51,2,6,51,55,1,5,55,59,2,10,59,63,1,63,6,67,2,67,6,71,2,10,71,75,1,6,75,79,2,79,9,83,1,83,5,87,1,87,9,91,1,91,9,95,1,10,95,99,1,99,13,103,2,6,103,107,1,107,5,111,1,6,111,115,1,9,115,119,1,119,9,123,2,123,10,127,1,6,127,131,2,131,13,135,1,13,135,139,1,9,139,143,1,9,143,147,1,147,13,151,1,151,9,155,1,155,13,159,1,6,159,163,1,13,163,167,1,2,167,171,1,171,13,0,99,2,0,14,0";
        String expected = "3654868";
        String actual = executeProgram(input);
        String[] positionZero = actual.split(",");
        assertEquals(expected, positionZero[0]);
    }

    @Test
    public void PartTwo_Solution() {
        String input = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,9,1,19,1,19,5,23,1,23,5,27,2,27,10,31,1,31,9,35,1,35,5,39,1,6,39,43,2,9,43,47,1,5,47,51,2,6,51,55,1,5,55,59,2,10,59,63,1,63,6,67,2,67,6,71,2,10,71,75,1,6,75,79,2,79,9,83,1,83,5,87,1,87,9,91,1,91,9,95,1,10,95,99,1,99,13,103,2,6,103,107,1,107,5,111,1,6,111,115,1,9,115,119,1,119,9,123,2,123,10,127,1,6,127,131,2,131,13,135,1,13,135,139,1,9,139,143,1,9,143,147,1,147,13,151,1,151,9,155,1,155,13,159,1,6,159,163,1,13,163,167,1,2,167,171,1,171,13,0,99,2,0,14,0";
        String programCompleted = executeProgram(input, 19690720);
        String[] programElements = programCompleted.split(",");
        int actual = 100 * Integer.parseInt(programElements[1]) + Integer.parseInt(programElements[2]);
        assertEquals(7014, actual);
    }

    private String executeProgram(String input, int targetOutput) {
        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                int[] positions = convertStringOfIntsToIntArray(input);

                positions[1] = noun;
                positions[2] = verb;

                String result = executeProgram(positions);
                String[] positionZero = result.split(",");
                if (targetOutput == Integer.parseInt(positionZero[0])) {
                    int answer = 100 * noun + verb;
                    System.out.println("noun: " + noun + " verb: " + verb + " -- answer: " + answer );
                    return result;
                }
            }
        }

        return null;
    }

    private String executeProgram(String input) {
        int[] positions = convertStringOfIntsToIntArray(input);
        return executeProgram(positions);
    }

    private String executeProgram(int[] positions) {
        int instructionPointer = 0;
        while (true) {
            int opcode = positions[instructionPointer];
            if (opcode == OPCODE_HALT) {
                String result = convertIntToCommaSeparatedString(positions);
                return result;
            }

            int parameter_1 = positions[instructionPointer + 1];
            int parameter_2 = positions[instructionPointer + 2];
            int writeTo = positions[instructionPointer + 3];

            if (opcode == OPCODE_ADD) {
                int lefthand = positions[parameter_1];
                int righthand = positions[parameter_2];
                positions[writeTo] = lefthand + righthand;
            } else if (opcode == OPCODE_MULTIPLY) {
                int lefthand = positions[parameter_1];
                int righthand = positions[parameter_2];
                positions[writeTo] = lefthand * righthand;
            }

            instructionPointer += NUM_VALUES_IN_INSTRUCTION;
        }
    }

    private String convertIntToCommaSeparatedString(int[] ints) {
        String result = "";
        for (int i :
                ints) {
            result += i + ",";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    // @TODO - could be a utilities method?
    private int[] convertStringOfIntsToIntArray(String input) {
        return Stream.of(input.split(",")).
                mapToInt(Integer::parseInt).
                toArray();
    }

}
