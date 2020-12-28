import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day14Tests {

    /*
- Read file as string
- Parse a line - either as a new bitmask (stored as string?)
- or as memory location and new value to write
- apply bitmask to value
- save value in memory location
- sum all memory location values

-- assuming Long is enough for mem/values

     */

    public List<String> createSampleInput() {
        List<String> input = new ArrayList<>();
        input.add("mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X");
        input.add("mem[8] = 11");
        input.add("mem[7] = 101");
        input.add("mem[8] = 0");
        return input;
    }

    @Test
    public void testMaskLineProcessing() {
        String input = "mask = 11110100010101111011001X0100XX00100X";
        String actual = processInputAsNewMask(input);
        assertEquals("11110100010101111011001X0100XX00100X", actual);
    }

    @Test
    public void testMemoryLineProcessing() {
        String input = "mem[17610] = 1035852";
        MemWrite actual = processInputAsMemoryWrite(input);
        assertEquals(17610, actual.address);
        assertEquals(1035852, actual.unmaskedValue);
    }

    @Test
    public void part1_sampleInput() {
        long actual = part1Solve(createSampleInput());
        assertEquals(165, actual);
    }

    @Test
    public void maskingExamples() {
        MemWrite sut = new MemWrite(999, 11);
        sut.applyMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X");
        assertEquals(73, sut.maskedValue);
    }

    @Test
    public void part1_solution() {
        List<String> input = Utilities.fileToStringList("./data/day14-part01");
        long actual = part1Solve(input);
        assertEquals(10885823581193L, actual);
    }

//-----------------------


    private long part1Solve(List<String> input) {
        String mask = "";

        Map<Long, MemWrite> memoryLocations = new HashMap<>();

        for (String inputLine:
              input) {
            if (inputLine.charAt(1) == 'a') {
                mask = processInputAsNewMask(inputLine);
            } else {
                // process input line as memwrite
                MemWrite mw = processInputAsMemoryWrite(inputLine);

                // apply mask to memwrite object
                mw.applyMask(mask);

                // update memoryLocations address, maskedValue
                if (memoryLocations.containsKey(mw.address)) {
                    memoryLocations.replace(mw.address, mw);
                } else {
                    memoryLocations.put(mw.address, mw);
                }
            }
        }

        return sumOf(memoryLocations);
    }

    private long sumOf(Map<Long, MemWrite> memoryLocations) {
        long sum = 0;
        for (MemWrite mw :
                memoryLocations.values()) {
            sum += mw.maskedValue;
        }

        return sum;
    }


    private MemWrite processInputAsMemoryWrite(String input) {
        int indexOfCloseBrace = input.indexOf("]");
        long address = Long.parseLong(input.substring(4, indexOfCloseBrace));
        int indexOfEqualSign = input.indexOf("=");
        long value = Long.parseLong(input.substring(indexOfEqualSign + 2));
        return new MemWrite(address, value);
    }

    private String processInputAsNewMask(String input) {
        return input.substring(7);
    }

    private class MemWrite {
        public long address;
        public long unmaskedValue;
        private long maskedValue;

        public MemWrite(long address, long value) {
            this.address = address;
            this.unmaskedValue = value;
        }

        public void applyMask(String mask) {
            String xAs1 = mask.replaceAll("X", "1");
            String xAs0 = mask.replaceAll("X", "0");

            long maskAs0 = Long.parseLong(xAs0, 2);
            long maskAs1 = Long.parseLong(xAs1, 2);
            long bitIsSetInValueOrMask = unmaskedValue | maskAs0;
            long clearThe0Bits = bitIsSetInValueOrMask & maskAs1;

            this.maskedValue = clearThe0Bits;
        }
    }
}
