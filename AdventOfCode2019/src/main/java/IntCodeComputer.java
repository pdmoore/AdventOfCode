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
    static final int INPUT_IS_ALWAYS_THE_SAME = 1;;
    private final int _inputValue;

    public IntCodeComputer() {
        _inputValue = INPUT_IS_ALWAYS_THE_SAME;
    }
    public IntCodeComputer(int inputValue) {
        _inputValue = inputValue;
    }

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

            Instruction instruction = grabNextInstruction(instructionPointer, positions);

            switch (instruction._opcode) {
                case OPCODE_HALT: return utils.convertIntArrayToCommaSeparatedString(positions);
                case OPCODE_EQUALS: performEquals(positions, instruction); break;
                case OPCODE_LESS_THAN: performLessThan(positions, instruction); break;
                case OPCODE_JUMP_IF_TRUE: instructionPointer = performJumpIfTrue(instruction, instructionPointer); break;
                case OPCODE_JUMP_IF_FALSE: instructionPointer = performJumpIfFalse(instruction, instructionPointer); break;
                case OPCODE_INPUT: performInput(positions, instruction); break;
                case OPCODE_OUTPUT: output.append(performOutput(positions, instruction)); break;
            }

            //TODO - set the writeToIndex in the instruction (line 145ish)
            // then split this if and add to switch statement above
                if (instruction._opcode == IntCodeComputer.OPCODE_MULTIPLY) {
                    positions[instruction._writeToIndex] = instruction._parameter1 * instruction._parameter2;
                } else if (instruction._opcode == IntCodeComputer.OPCODE_ADD) {
                    positions[instruction._writeToIndex] = instruction._parameter1 + instruction._parameter2;
                }

            instructionPointer += instruction._jumpLength;
        }
    }

    // TODO - Can the logic in this mini-methods be moved to the instruction itself?
    // subclass instruction and have a perform method?
    private String performOutput(int[] positions, Instruction instruction) {
        return positions[instruction._parameter1] + ",";
    }

    private void performInput(int[] positions, Instruction instruction) {
        positions[instruction._parameter1] = _inputValue;
    }

    private int performJumpIfFalse(Instruction instruction, int instructionPointer) {
        if (instruction._parameter1 == 0) {
            return instruction._parameter2;
        } else {
            return instructionPointer + 3;
        }
    }

    private int performJumpIfTrue(Instruction instruction, int instructionPointer) {
        if (instruction._parameter1 == 0) {
            return instructionPointer + 3;
        } else {
            return instruction._parameter2;
        }
    }

    private void performLessThan(int[] positions, Instruction instruction) {
        if (instruction._parameter1 < instruction._parameter2) {
            positions[instruction._writeToIndex] = 1;
        } else {
            positions[instruction._writeToIndex] = 0;
        }
    }

    private void performEquals(int[] positions, Instruction instruction) {
        if (instruction._parameter1 == instruction._parameter2) {
            positions[instruction._writeToIndex] = 1;
        } else {
            positions[instruction._writeToIndex] = 0;
        }
    }

    // TODO This is by far the largest method - can it be simpler?
    private Instruction grabNextInstruction(int instructionPointer, int[] positions) {
        int nextInstruction = positions[instructionPointer];
        if (nextInstruction == OPCODE_HALT) {
            return new Instruction(nextInstruction, -1, -1);
        }

        int opcode = nextInstruction % 10;
        validateOpcode(opcode);

        int mode1stParam = (nextInstruction / 100) % 10;
        int mode2ndParam = (nextInstruction / 1000) % 10;
        int mode3rdParam = (nextInstruction / 10000);

        int parameter1 = positions[instructionPointer + 1];
        int parameter2 = positions[instructionPointer + 2];

        int lefthand = -99;
        if (opcode == OPCODE_INPUT || opcode == OPCODE_OUTPUT) {
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
        if (opcode == OPCODE_EQUALS || opcode == OPCODE_LESS_THAN ||
                (opcode == OPCODE_ADD || opcode == OPCODE_MULTIPLY)) {
            instruction._writeToIndex = positions[instructionPointer + 3];
        }

        return instruction;
    }

    private void validateOpcode(int opcode) {
        if (opcode == OPCODE_HALT) return;

        if (opcode < OPCODE_ADD || opcode > OPCODE_EQUALS)
            throw new IllegalArgumentException("unknown opcode: " + opcode);
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
        int _writeToIndex;

        public Instruction(int opcode, int parameter1, int parameter2) {
            _opcode = opcode;
            _parameter1 = parameter1;
            _parameter2 = parameter2;

            _jumpLength = opcodeSize(opcode);
        }

        private int opcodeSize(int opcode) {
            switch (opcode) {
                case OPCODE_EQUALS:
                case OPCODE_LESS_THAN:
                    return 4;
                case OPCODE_MULTIPLY:
                case OPCODE_ADD:
                    return NUM_VALUES_IN_ADD_OR_MULTIPLY_INSTRUCTION;
                case OPCODE_INPUT:
                case OPCODE_OUTPUT:
                    return NUM_VALUES_IN_INPUT_OR_OUTPUT_INSTRUCTION;
            }
            return 0;
        }
    }
}
