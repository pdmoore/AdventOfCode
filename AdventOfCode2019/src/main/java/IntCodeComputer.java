public class IntCodeComputer {
    static final int OPCODE_ADD = 1;
    static final int OPCODE_MULTIPLY = 2;
    static final int OPCODE_INPUT = 3;
    static final int OPCODE_OUTPUT = 4;
    static final int OPCODE_JUMP_IF_TRUE = 5;
    static final int OPCODE_JUMP_IF_FALSE = 6;
    static final int OPCODE_LESS_THAN = 7;
    static final int OPCODE_EQUALS = 8;

    static final int OPCODE_HALT = 99;
    static final int POSITION_MODE = 0;
    static final int IMMEDIATE_MODE = 1;
    static final int NUM_VALUES_IN_INPUT_OR_OUTPUT_INSTRUCTION = 2;
    static final int NUM_VALUES_IN_ADD_OR_MULTIPLY_INSTRUCTION = 4;
    static final int INPUT_IS_ALWAYS_THE_SAME = 1;

    // TODO would be nice to have each instruction be able to dump to log

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

            //TODO get rid of special handling of Halt here
            int nextInstruction = positions[instructionPointer];
            if (nextInstruction == IntCodeComputer.OPCODE_HALT) {
                return utils.convertIntArrayToCommaSeparatedString(positions);
            }

            Instruction instruction = grabNextInstruction(instructionPointer, positions);

            if (OPCODE_EQUALS == instruction._opcode) {

                int writeToIndex = positions[instructionPointer + 3];
                if (instruction._parameter1 == instruction._parameter2) {
                    positions[writeToIndex] = 1;
                } else {
                    positions[writeToIndex] = 0;
                }
            } else if (OPCODE_LESS_THAN == instruction._opcode) {
                int writeToIndex = positions[instructionPointer + 3];
                if (instruction._parameter1 < instruction._parameter2) {
                    positions[writeToIndex] = 1;
                } else {
                    positions[writeToIndex] = 0;
                }
            } else if (OPCODE_JUMP_IF_TRUE == instruction._opcode) {
                if (instruction._parameter1 == 0) {
                    instructionPointer += 3;
                } else {
                    instructionPointer = instruction._parameter2;
                }
            } else if (OPCODE_JUMP_IF_FALSE == instruction._opcode) {
                if (instruction._parameter1 == 0) {
                    instructionPointer = instruction._parameter2;
                } else {
                    instructionPointer += 3;
                }
            } else if (isTwoParameterInstruction(instruction._opcode)) {
                int writeToIndex = positions[instructionPointer + 3];
                if (instruction._opcode == IntCodeComputer.OPCODE_MULTIPLY) {
                    positions[writeToIndex] = instruction._parameter1 * instruction._parameter2;
                } else if (instruction._opcode == IntCodeComputer.OPCODE_ADD) {
                    positions[writeToIndex] = instruction._parameter1 + instruction._parameter2;
                }
            } else {
                if (instruction._opcode == IntCodeComputer.OPCODE_INPUT) {
                    positions[instruction._parameter1] = IntCodeComputer.INPUT_IS_ALWAYS_THE_SAME;
                } else if (instruction._opcode == IntCodeComputer.OPCODE_OUTPUT) {

                    //TODO - use param from instruction
                    int parameter_1 = positions[instructionPointer + 1];

                    output.append(positions[parameter_1]);
                    output.append(",");
                } else {
                    //TODO - validate the opcode inside the Instruction ctor - remove this check
                    throw new IllegalArgumentException("unknown opcode: " + instruction._opcode);
                }
            }

            instructionPointer += instruction._jumpLength;
        }
    }

    private Instruction grabNextInstruction(int instructionPointer, int[] positions) {
        int nextInstruction = positions[instructionPointer];
        int opcode = nextInstruction % 10;

        int mode1stParam = (nextInstruction / 100) % 10;
        int mode2ndParam = (nextInstruction / 1000) % 10;
        int mode3rdParam = (nextInstruction / 10000);

        int parameter1 = positions[instructionPointer + 1];
        int parameter2 = positions[instructionPointer + 2];

        int lefthand = -99;
        if (opcode == OPCODE_INPUT) {
            lefthand = parameter1;
        } else {
            if (mode1stParam == IntCodeComputer.POSITION_MODE) {
                lefthand = positions[parameter1];
            } else if (mode1stParam == IntCodeComputer.IMMEDIATE_MODE) {
                lefthand = parameter1;
            }
        }


        int righthand = parameter2;
        if (opcode == OPCODE_EQUALS ||
                opcode == OPCODE_LESS_THAN ||
                opcode == OPCODE_MULTIPLY ||
                opcode == OPCODE_ADD) {
            if (mode2ndParam == IntCodeComputer.POSITION_MODE) {
                righthand = positions[parameter2];
            } else if (mode2ndParam == IntCodeComputer.IMMEDIATE_MODE) {
                righthand = parameter2;
            }
        }
        if (opcode == IntCodeComputer.OPCODE_INPUT) {
            guardAgainstImmediateMode(opcode, mode1stParam, mode2ndParam, mode3rdParam);
        }

        Instruction instruction = new Instruction(opcode, lefthand, righthand);

        return instruction;
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

    class Instruction {

        final int _opcode;
        final int _parameter1;
        final int _parameter2;
        final int _jumpLength;

        public Instruction(int opcode, int parameter1, int parameter2) {
            _opcode = opcode;
            _parameter1 = parameter1;
            _parameter2 = parameter2;

            _jumpLength = opcodeSize(opcode);
        }

        private int opcodeSize(int opcode) {
            switch (opcode) {
                case OPCODE_EQUALS:
                    return 4;
                case OPCODE_LESS_THAN:
                    return 4;
                case OPCODE_MULTIPLY:
                    return NUM_VALUES_IN_ADD_OR_MULTIPLY_INSTRUCTION;
                case OPCODE_ADD:
                    return NUM_VALUES_IN_ADD_OR_MULTIPLY_INSTRUCTION;
                case OPCODE_INPUT:
                    return NUM_VALUES_IN_INPUT_OR_OUTPUT_INSTRUCTION;
                case OPCODE_OUTPUT:
                    return NUM_VALUES_IN_INPUT_OR_OUTPUT_INSTRUCTION;
            }
            return 0;
        }
    }
}
