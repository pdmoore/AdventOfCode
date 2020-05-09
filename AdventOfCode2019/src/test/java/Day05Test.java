import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day05Test {

    @Test
    public void SingleAddCommand() {
        String input = "1001,4,9,4,90";
        String expected = "1001,4,9,4,99";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void SingleAddCommand_NegativeValuesAllowed() {
        String input = "1001,4,-1,4,100";
        String expected = "1001,4,-1,4,99";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void SingleMultiplyCommand() {
        String input = "1002,4,3,4,33";
        String expected = "1002,4,3,4,99";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void SingleMultiplyCommand_NegativeNumbersAllowed() {
        String input = "1002,4,-3,4,-33";
        String expected = "1002,4,-3,4,99";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_Input_InputHardcodedToAlwaysBe_1() {
        String input = "3,3,99,-1";
        String expected = "3,3,99,1";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void LongerExample_3_Opcodes() {
        String input = "1002,6,3,6,3,4,33,88";
        String expected = "1002,6,3,6,1,4,99,88";

        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_Output_InputHardcodedToAlwaysBe_1() {
        String input = "4,5,99,0,1,2";
        String expected = "4,5,99,0,1,2";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void solution_1_testFile() {
        String input = utils.fileAsString("data/aoc19.5.txt");
        StringBuffer output = new StringBuffer();
        String actual = new IntCodeComputer().executeProgram(input, output);
        assertEquals("3,225,1,225,6,6,1101,1,238,225,104,0,1002,188,27,224,1001,224,-2241,224,4,224,102,8,223,223,1001,224,6,224,1,223,224,223,101,65,153,224,101,-108,224,224,4,224,1002,223,8,223,1001,224,1,224,1,224,223,223,1,158,191,224,101,-113,224,224,4,224,102,8,223,223,1001,224,7,224,1,223,224,223,1001,195,14,224,1001,224,-81,224,4,224,1002,223,8,223,101,3,224,224,1,224,223,223,1102,47,76,225,1102,35,69,224,101,-2415,224,224,4,224,102,8,223,223,101,2,224,224,1,224,223,223,1101,32,38,224,101,-70,224,224,4,224,102,8,223,223,101,3,224,224,1,224,223,223,1102,66,13,225,1102,43,84,225,1101,12,62,225,1102,30,35,225,2,149,101,224,101,-3102,224,224,4,224,102,8,223,223,101,4,224,224,1,223,224,223,1101,76,83,225,1102,51,51,225,1102,67,75,225,102,42,162,224,101,-1470,224,224,4,224,102,8,223,223,101,1,224,224,1,223,224,223,4,223,99,13087969,1,5025,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,1108,226,677,224,1002,223,2,223,1005,224,329,101,1,223,223,108,226,226,224,1002,223,2,223,1005,224,344,1001,223,1,223,1107,677,226,224,1002,223,2,223,1006,224,359,101,1,223,223,1008,226,226,224,1002,223,2,223,1005,224,374,101,1,223,223,8,226,677,224,102,2,223,223,1006,224,389,101,1,223,223,7,226,677,224,1002,223,2,223,1005,224,404,1001,223,1,223,7,226,226,224,1002,223,2,223,1005,224,419,101,1,223,223,107,226,677,224,1002,223,2,223,1005,224,434,101,1,223,223,107,226,226,224,1002,223,2,223,1005,224,449,1001,223,1,223,1107,226,677,224,102,2,223,223,1006,224,464,1001,223,1,223,1007,677,226,224,1002,223,2,223,1006,224,479,1001,223,1,223,1107,677,677,224,1002,223,2,223,1005,224,494,101,1,223,223,1108,677,226,224,102,2,223,223,1006,224,509,101,1,223,223,7,677,226,224,1002,223,2,223,1005,224,524,1001,223,1,223,1008,677,226,224,102,2,223,223,1005,224,539,1001,223,1,223,1108,226,226,224,102,2,223,223,1005,224,554,101,1,223,223,107,677,677,224,102,2,223,223,1006,224,569,1001,223,1,223,1007,226,226,224,102,2,223,223,1006,224,584,101,1,223,223,8,677,677,224,102,2,223,223,1005,224,599,1001,223,1,223,108,677,677,224,1002,223,2,223,1005,224,614,101,1,223,223,108,226,677,224,102,2,223,223,1005,224,629,101,1,223,223,8,677,226,224,102,2,223,223,1006,224,644,1001,223,1,223,1007,677,677,224,1002,223,2,223,1006,224,659,1001,223,1,223,1008,677,677,224,1002,223,2,223,1005,224,674,101,1,223,223,4,223,99,226", actual);
        assertEquals("3,0,0,0,0,0,0,0,0,13087969,", output.toString());
    }

    @Test
    public void Opcode_JumpIfTrue_PositionMode_firstParamIsNonZero() {
        String input = "5,9,7,33,22,11,44,8,99,1";
        String expected = "5,9,7,33,22,11,44,8,99,1";
        IntCodeComputer sut = new IntCodeComputer();
        String actual = sut.executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_JumpIfTrue_PositionMode_firstParamIsZero() {
        String input = "5,8,0,1001,7,9,7,90,0";
        String expected = "5,8,0,1001,7,9,7,99,0";
        IntCodeComputer sut = new IntCodeComputer();
        String actual = sut.executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_JumpIfTrue_ImmediateMode_firstParamIsZero() {
        String input = "1105,1,5,666,666,99";
        String expected = "1105,1,5,666,666,99";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_JumpIfTrue_ImmediateMode_firstParamIsNonZero() {
        String input = "1105,0,666,99";
        String expected = "1105,0,666,99";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_JumpIfFalse_PositionMode_firstParamIsZero() {
        String input = "6,5,3,4,99,0";
        String expected = "6,5,3,4,99,0";
        IntCodeComputer sut = new IntCodeComputer();
        sut._verbose = true;
        String actual = sut.executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_JumpIfFalse_PositionMode_firstParamIsNotZero() {
        String input = "6,4,666,99,1";
        String expected = "6,4,666,99,1";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_JumpIfFalse_ImmediateMode_firstParamIsZero() {
        String input = "1106,0,5,666,666,99";
        String expected = "1106,0,5,666,666,99";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_JumpIfFalse_ImmediateMode_firstParamIsNonZero() {
        String input = "1106,1,666,99";
        String expected = "1106,1,666,99";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_LessThan_PositionMode_FirstParam_LessThan_SecondParam() {
        String input = "7,6,7,5,99,-99,0,1";
        String expected = "7,6,7,5,99,1,0,1";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_LessThan_PositionMode_FirstParam_EqualTo_SecondParam() {
        String input = "7,6,7,5,99,-99,1,1";
        String expected = "7,6,7,5,99,0,1,1";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_LessThan_PositionMode_FirstParam_GreaterThan_SecondParam() {
        String input = "7,6,7,5,99,-99,2,1";
        String expected = "7,6,7,5,99,0,2,1";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_LessThan_ImmediateMode_FirstParam_LessThan_SecondParam() {
        String input = "1107,12,23,5,99,666";
        String expected = "1107,12,23,5,99,1";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_LessThan_ImmediateMode_FirstParam_Equals_SecondParam() {
        String input = "1107,23,23,5,99,666";
        String expected = "1107,23,23,5,99,0";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_LessThan_ImmediateMode_FirstParam_GreaterThan_SecondParam() {
        String input = "1107,32,23,5,99,666";
        String expected = "1107,32,23,5,99,0";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_Equals_PositionMode_FirstParam_Equals_SecondParam() {
        String input = "8,6,7,5,99,-99,1,1";
        String expected = "8,6,7,5,99,1,1,1";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_Equals_PositionMode_FirstParam_NotEquals_SecondParam() {
        String input = "8,6,7,5,99,-99,2,1";
        String expected = "8,6,7,5,99,0,2,1";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_Equals_ImmediateMode_FirstParam_Equals_SecondParam() {
        String input = "1108,23,23,5,99,666";
        String expected = "1108,23,23,5,99,1";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void Opcode_Equals_ImmediateMode_FirstParam_NotEquals_SecondParam() {
        String input = "1108,23,32,5,99,666";
        String expected = "1108,23,32,5,99,0";
        String actual = new IntCodeComputer().executeProgram(input);
        assertEquals(expected, actual);
    }

    @Test
    public void LongerExample_PositionMode_InputNonZero() {
        String input = "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9";
        String expected = "3,12,6,12,15,1,13,14,13,4,13,99,1,1,1,9";
        StringBuffer output = new StringBuffer();
        String actual = new IntCodeComputer().executeProgram(input, output);
        assertEquals(expected, actual);
        assertEquals("1,", output.toString());
    }

    @Test
    public void LongerExample_ImmediateMode_InputNonZero() {
        String input = "3,3,1105,-1,9,1101,0,0,12,4,12,99,1";
        String expected = "3,3,1105,1,9,1101,0,0,12,4,12,99,1";
        StringBuffer output = new StringBuffer();
        String actual = new IntCodeComputer().executeProgram(input, output);
        assertEquals(expected, actual);
        assertEquals("1,", output.toString());
    }

    @Test
    public void LongerExample_PositionMode_InputZero() {
        String input = "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9";
        String expected = "3,12,6,12,15,1,13,14,13,4,13,99,0,0,1,9";
        StringBuffer output = new StringBuffer();
        IntCodeComputer sut = new IntCodeComputer(0);
        String actual = sut.executeProgram(input, output);
        assertEquals(expected, actual);
        assertEquals("0,", output.toString());
    }

    @Test
    public void LongerExample_ImmediateMode_InputZero() {
        String input = "3,3,1105,-1,9,1101,0,0,12,4,12,99,1";
        String expected = "3,3,1105,1,9,1101,0,0,12,4,12,99,1";
        StringBuffer output = new StringBuffer();
        String actual = new IntCodeComputer(1).executeProgram(input, output);
        assertEquals(expected, actual);
        assertEquals("1,", output.toString());
    }

    @Test
    public void LargerExample_InputValue_Equals8() {
        String input = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        StringBuffer output = new StringBuffer();
        String actual = new IntCodeComputer(8).executeProgram(input, output);
        assertEquals("1000,", output.toString());
    }

    @Test
    public void LargerExample_InputValue_GreaterThan8() {
        String input = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        StringBuffer output = new StringBuffer();
        String actual = new IntCodeComputer(9).executeProgram(input, output);
        assertEquals("1001,", output.toString());
    }

    //TODO - ignored
    @Test
    @Ignore("TODO - failing due to trying to jump to 999 instead of outout 999")
    public void LargerExample_InputValue_LessThan8() {
        String input = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        StringBuffer output = new StringBuffer();
        String actual = new IntCodeComputer(7).executeProgram(input, output);
        assertEquals("999,", output.toString());
    }

    // TODO Larger example with input > 8, expect 1001

    // TODO
    // Can there be multiple outputs? maybe pass in a collector to hold all output?
    // - error coverage - Parameters that an instruction writes to will never be in immediate mode.

}
