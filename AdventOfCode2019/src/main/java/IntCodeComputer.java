public class IntCodeComputer {
    static final int OPCODE_ADD = 1;
    static final int OPCODE_MULTIPLY = 2;
    static final int OPCODE_INPUT = 3;
    static final int OPCODE_OUTPUT = 4;
    static final int OPCODE_JUMP_IF_TRUE  = 5;
    static final int OPCODE_JUMP_IF_FALSE = 6;
    static final int OPCODE_LESS_THAN = 7;

    static final int OPCODE_HALT = 99;
    static final int POSITION_MODE = 0;
    static final int IMMEDIATE_MODE = 1;
    static final int NUM_VALUES_IN_INPUT_OR_OUTPUT_INSTRUCTION = 2;
    static final int NUM_VALUES_IN_ADD_OR_MULTIPLY_INSTRUCTION = 4;
    static final int INPUT_IS_ALWAYS_THE_SAME = 1;


    //TODO - Opcode 8

    public String executeProgram(String input) {
        int[] positions = utils.convertCommaSeparatedStringToIntArray(input);
        return executeProgram(positions, new StringBuffer());
    }

    public String executeProgram(String input, StringBuffer output) {
        int[] positions = utils.convertCommaSeparatedStringToIntArray(input);
        return executeProgram(positions, output);
    }

    public String executeProgram(int[] positions, StringBuffer output) {
        int instructionPointer = 0;
        while (true) {

            //TODO - compose method to make loop readable

            int nextInstruction = positions[instructionPointer];

            if (nextInstruction == IntCodeComputer.OPCODE_HALT) {
                return utils.convertIntArrayToCommaSeparatedString(positions);
            }

            int opcode = nextInstruction % 10;

            int mode1stParam = (nextInstruction / 100) % 10;
            int mode2ndParam = (nextInstruction / 1000) % 10;
            int mode3rdParam = (nextInstruction / 10000);

            if (OPCODE_LESS_THAN == opcode) {
                guardAgainstImmediateMode(opcode, mode1stParam, mode2ndParam, mode3rdParam);

                int parameter_1 = positions[instructionPointer + 1];
                int parameter_2 = positions[instructionPointer + 2];

                int writeToIndex = positions[instructionPointer + 3];
                if (parameter_1 < parameter_2) {
                    positions[writeToIndex] = 1;
                } else {
                    positions[writeToIndex] = 0;
                }

                instructionPointer += 4;
            } else if (OPCODE_JUMP_IF_TRUE == opcode) {
                int parameter_1 = positions[instructionPointer + 1];
                int parameter_2 = positions[instructionPointer + 2];

                int lefthand = -99;
                if (mode1stParam == IntCodeComputer.POSITION_MODE) {
                    lefthand = positions[parameter_1];
                } else if (mode1stParam == IntCodeComputer.IMMEDIATE_MODE) {
                    lefthand = parameter_1;
                }

                if (lefthand == 0) {
                    instructionPointer += 3;
                } else {
                    instructionPointer = parameter_2;
                }
            } else if (OPCODE_JUMP_IF_FALSE == opcode) {
                guardAgainstImmediateMode(opcode, mode1stParam, mode2ndParam, mode3rdParam);

                int parameter_1 = positions[instructionPointer + 1];
                int parameter_2 = positions[instructionPointer + 2];

                int lefthand = -99;
                if (mode1stParam == IntCodeComputer.POSITION_MODE) {
                    lefthand = positions[parameter_1];
                } else if (mode1stParam == IntCodeComputer.IMMEDIATE_MODE) {
                    lefthand = parameter_1;
                }

                if (lefthand == 0) {
                    instructionPointer = parameter_2;
                } else {
                    instructionPointer += 3;
                }
            } else if (isTwoParameterInstruction(opcode)) {
                int parameter_1 = positions[instructionPointer + 1];
                int parameter_2 = positions[instructionPointer + 2];

                int lefthand = -1;
                int righthand = -2;

                // TODO combine dupe logic
                if (mode1stParam == IntCodeComputer.POSITION_MODE) {
                    lefthand = positions[parameter_1];
                } else if (mode1stParam == IntCodeComputer.IMMEDIATE_MODE) {
                    lefthand = parameter_1;
                }

                if (mode2ndParam == IntCodeComputer.POSITION_MODE) {
                    righthand = positions[parameter_2];
                } else if (mode2ndParam == IntCodeComputer.IMMEDIATE_MODE) {
                    righthand = parameter_2;
                }

                int writeToIndex = positions[instructionPointer + 3];
                if (opcode == IntCodeComputer.OPCODE_MULTIPLY) {
                    positions[writeToIndex] = lefthand * righthand;
                } else if (opcode == IntCodeComputer.OPCODE_ADD) {
                    positions[writeToIndex] = lefthand + righthand;
                }

                instructionPointer += IntCodeComputer.NUM_VALUES_IN_ADD_OR_MULTIPLY_INSTRUCTION;
            } else {
                if (opcode == IntCodeComputer.OPCODE_INPUT) {
                    int parameter_1 = positions[instructionPointer + 1];
                    positions[parameter_1] = IntCodeComputer.INPUT_IS_ALWAYS_THE_SAME;
                } else if (opcode == IntCodeComputer.OPCODE_OUTPUT) {
                    int parameter_1 = positions[instructionPointer + 1];
                    output.append(positions[parameter_1]);
                    output.append(",");
                } else {
                    throw new IllegalArgumentException("unknown opcode: " + opcode);
                }
                instructionPointer += IntCodeComputer.NUM_VALUES_IN_INPUT_OR_OUTPUT_INSTRUCTION;
            }
        }
    }

    private void guardAgainstImmediateMode(int opcode, int mode1stParam, int mode2ndParam, int mode3rdParam) {
        if (mode1stParam == IMMEDIATE_MODE ||
            mode2ndParam == IMMEDIATE_MODE ||
                mode3rdParam == IMMEDIATE_MODE) {
            throw new IllegalArgumentException("Immediate mode not supported for: " + opcode);
        }
    }

    private boolean isTwoParameterInstruction(int opcode) {
        return opcode == IntCodeComputer.OPCODE_ADD || opcode == IntCodeComputer.OPCODE_MULTIPLY;
    }

}
