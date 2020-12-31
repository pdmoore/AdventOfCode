import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day16Tests {


    public List<String> createSampleInput() {
        List<String> i = new ArrayList<>();
        i.add("class: 1-3 or 5-7");
        i.add("row: 6-11 or 33-44");
        i.add("seat: 13-40 or 45-50");
        i.add("");
        i.add("your ticket:");
        i.add("7,1,14");
        i.add("");
        i.add("nearby tickets:");
        i.add("7,3,47");
        i.add("40,4,50");
        i.add("55,2,20");
        i.add("38,6,12");
        return i;
    }

    @Test
    public void part1_sampleInput() {
        int actual = solvePart1(createSampleInput());
        assertEquals(71, actual);
    }

    @Test
    public void part1_Solutiont() {
        int actual = solvePart1(Utilities.fileToStringList("./data/day16-part01"));
        assertEquals(999, actual);
    }

//-----------------------------

    private int solvePart1(List<String> input) {

        Map<Integer, String> validFields = new HashMap<>();

        //parse the input, build map of legal Numbers and the fields they map to
        int mode = 0;
        int result = 0;
        for (String inputLine:
                input) {
            if (inputLine.trim().isEmpty()) continue;
            if (inputLine.startsWith("your ticket")) mode = 1;
            if (inputLine.startsWith("nearby tickets")) mode = 2;

            if (mode == 0) {
                mapNumbersToField(validFields, inputLine);
            } else if (mode == 1) {
                // do nothing for part 1
            } else {
                if (inputLine.startsWith("nearby tickets")) continue;

                //input is a comma separated line of ints
                String[] ticketFields = inputLine.split(",");
                for (int i = 0; i < ticketFields.length; i++) {
                    int fieldNum = Integer.parseInt(ticketFields[i]);
                    if (!validFields.containsKey(fieldNum)) {
                        result += fieldNum;
                    }
                }
            }
        }
        return result;
    }

    private void mapNumbersToField(Map<Integer, String> validFields, String inputLine) {
        int colonIndex = inputLine.indexOf(":");
        String fieldName = inputLine.substring(0, colonIndex);

        int orIndex = inputLine.indexOf(" or ");
        String firstRangeString = inputLine.substring(colonIndex + 2, orIndex);
        String secondRangeString = inputLine.substring(orIndex + 4);

        populateMap(validFields, fieldName, firstRangeString);
        populateMap(validFields, fieldName, secondRangeString);
    }

    private void populateMap(Map<Integer, String> validFields, String fieldName, String rangeString) {
        String[] rangeBounds = rangeString.split("-");
        int lowerBound = Integer.parseInt(rangeBounds[0]);
        int upperBound = Integer.parseInt(rangeBounds[1]);
        for (int i = lowerBound; i <= upperBound; i++) {
            validFields.put(i, fieldName);
        }
    }
}
