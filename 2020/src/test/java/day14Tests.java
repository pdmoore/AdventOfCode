import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<String> createPart_1_SampleInput() {
        List<String> input = new ArrayList<>();
        input.add("mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X");
        input.add("mem[8] = 11");
        input.add("mem[7] = 101");
        input.add("mem[8] = 0");
        return input;
    }

    public List<String> createPart_2_SampleInput() {
        List<String> input = new ArrayList<>();
        input.add("mask = 000000000000000000000000000000X1001X");
        input.add("mem[42] = 100");
        input.add("mask = 00000000000000000000000000000000X0XX");
        input.add("mem[26] = 1");
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
        long actual = part1Solve(createPart_1_SampleInput());
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

    @Test
    public void part2_sampleInput() {
        long actual = part2Solve(createPart_2_SampleInput());
        assertEquals(208, actual);
    }

    @Test
    public void part2_solution() {
        List<String> input = Utilities.fileToStringList("./data/day14-part01");
        long actual = part2Solve(input);
        assertEquals(3816594901962L, actual);
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

        return sumOfMasked(memoryLocations);
    }

    private long part2Solve(List<String> input) {
        String mask = "";

        Map<Long, MemWrite> memoryLocations = new HashMap<>();

        for (String inputLine:
                input) {
            if (inputLine.charAt(1) == 'a') {
                mask = processInputAsNewMask(inputLine);
            } else {
                // process input line as memwrite
                // recursively adding each mw to the memoryLocations
                MemWrite seed = processInputAsMemoryWrite(inputLine);

                writeToAllAddresses(seed.address, seed.unmaskedValue, mask, memoryLocations);

                // update memoryLocations address, maskedValue
//                if (memoryLocations.containsKey(seed.address)) {
//                    memoryLocations.replace(seed.address, seed);
//                } else {
//                    memoryLocations.put(seed.address, seed);
//                }
            }
        }

        return sumOfUnmasked(memoryLocations);
    }

    private void writeToAllAddresses(long initialAddress, long value, String mask, Map<Long, MemWrite> memoryLocations) {
        String addressWithX = applyMaskToAddress(initialAddress, mask);

//TODO - continue here
        // result has the correct address with X in all places
        // call the recurse method, passing in the string 'result', the value to write, and the memoryLocations
        // the recurse method:
        // no X found, then create a memWrite for the 'result' and value, and place it in memoryLocations
        // X found, replace it with 0 and call recurse again
        //          replace it with 1 and call recurse again

        recurse(addressWithX, value, memoryLocations);
    }

    private void recurse(String addressWithX, long value, Map<Long, MemWrite> memoryLocations) {
        int anyX = addressWithX.indexOf('X');
        if (anyX == -1) {
            // no X, it's a valid memory address
            long actualAddress = Long.parseLong(addressWithX, 2);
            MemWrite mw = new MemWrite(actualAddress, value);
            if (memoryLocations.containsKey(mw.address)) {
                memoryLocations.replace(mw.address, mw);
            } else {
                memoryLocations.put(mw.address, mw);
            }

            return;
        }

        // there was an X - replace it with a 0 then a 1, and try on those new address strings
        StringBuilder xBecomes0 = new StringBuilder(addressWithX);
        xBecomes0.setCharAt(anyX, '0');
        recurse(xBecomes0.toString(), value, memoryLocations);
        StringBuilder xBecomes1 = new StringBuilder(addressWithX);
        xBecomes1.setCharAt(anyX, '1');
        recurse(xBecomes1.toString(), value, memoryLocations);
    }

    private String applyMaskToAddress(long initialAddress, String mask) {
        String initialAddressBits = Long.toBinaryString(initialAddress);
        String paddedAddress = String.format("%1$" + mask.length() + "s", initialAddressBits).replace(' ', '0');
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) == 'X') {
                sb.append('X');
            } else if (mask.charAt(i) == '1') {
                sb.append('1');
            } else {
                sb.append(paddedAddress.charAt(i));
            }
        }
        return sb.toString();
    }


    private long sumOfMasked(Map<Long, MemWrite> memoryLocations) {
        return memoryLocations.values()
                .stream()
                .map(mw -> mw.maskedValue)
                .collect(Collectors.summingLong(Long::longValue));
    }

    private long sumOfUnmasked(Map<Long, MemWrite> memoryLocations) {
        return memoryLocations.values()
                .stream()
                .map(mw -> mw.unmaskedValue)
                .collect(Collectors.summingLong(Long::longValue));
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
