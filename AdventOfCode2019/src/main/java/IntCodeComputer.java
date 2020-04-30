public class IntCodeComputer {
    static final int OPCODE_ADD      = 1;
    static final int OPCODE_MULTIPLY = 2;
    static final int OPCODE_INPUT    = 3;
    static final int OPCODE_OUTPUT   = 4;
    static final int OPCODE_HALT = 99;
    static final int POSITION_MODE = 0;
    static final int IMMEDIATE_MODE = 1;
    static final int NUM_VALUES_IN_INPUT_OR_OUTPUT_INSTRUCTION = 2;
    static final int NUM_VALUES_IN_ADD_OR_MULTIPLY_INSTRUCTION = 4;
    static final int INPUT_IS_ALWAYS_THE_SAME = 1;


    //TODO - Day02 should be able to use this as is
    //TODO - Opcode 5
    //TODO - Opcode 6
    //TODO - Opcode 7
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

            //TODO - compose method to make loop readble

            int firstInstruction = positions[instructionPointer];

            if (firstInstruction == IntCodeComputer.OPCODE_HALT) {
                String result = utils.convertIntArrayToCommaSeparatedString(positions);
                return result;
            }

            int opcode = firstInstruction % 10;

            int mode1stParam = (firstInstruction / 100) % 10;
            int mode2ndParam = (firstInstruction / 1000) % 10;
            int mode3rdParam = (firstInstruction / 10000);

            if (isTwoParameterInstruction(opcode)) {
                int parameter_1 = positions[instructionPointer + 1];
                int parameter_2 = positions[instructionPointer + 2];

                int lefthand = -1;
                int righthand = -2;

                // TODO combine dupe logic
                if (mode1stParam == IntCodeComputer.POSITION_MODE) {
                    parameter_1 = positions[instructionPointer + 1];
                    lefthand = positions[parameter_1];
                } else if (mode1stParam == IntCodeComputer.IMMEDIATE_MODE) {
                    lefthand = parameter_1;
                }

                if (mode2ndParam == IntCodeComputer.POSITION_MODE) {
                    parameter_2 = positions[instructionPointer + 2];
                    righthand = positions[parameter_2];
                } else if (mode2ndParam == IntCodeComputer.IMMEDIATE_MODE) {
                    righthand = parameter_2;
                }

                int writeTo = positions[instructionPointer + 3];
                if (opcode == IntCodeComputer.OPCODE_MULTIPLY) {
                    positions[writeTo] = lefthand * righthand;
                } else if (opcode == IntCodeComputer.OPCODE_ADD) {
                    positions[writeTo] = lefthand + righthand;
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
                    throw new IllegalArgumentException();
                }
                instructionPointer += IntCodeComputer.NUM_VALUES_IN_INPUT_OR_OUTPUT_INSTRUCTION;
            }
        }
    }

    private boolean isTwoParameterInstruction(int opcode) {
        return opcode == IntCodeComputer.OPCODE_ADD || opcode == IntCodeComputer.OPCODE_MULTIPLY;
    }

}
