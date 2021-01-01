import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day16Tests {

    /*
    first pass, figure out valid numbers
    discard tickets that contain invalid numbers
    with remaining valid tickets,
      - group the numbers by position in the input (all #s i first pos, all #s in second pos, etc) - there are 20 of those
      - then for each group find which position completely contains items in that group
      - emit the name of that group (and the position 0-19 of the input line)
      - visually match all the "departure" positions to the 'your ticket' and multiply the values
     */


    public List<String> createSampleInput_Part1() {
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

    public List<String> createSampleInput_Part2() {
        List<String> i = new ArrayList<>();
        i.add("class: 0-1 or 4-19");
        i.add("row: 0-5 or 8-19");
        i.add("seat: 0-13 or 16-19");
        i.add("");
        i.add("your ticket:");
        i.add("11,12,13");
        i.add("");
        i.add("nearby tickets:");
        i.add("3,9,18");
        i.add("15,1,5");
        i.add("5,14,9");
        return i;
    }

    @Test
    public void part1_sampleInput() {
        int actual = solvePart1(createSampleInput_Part1());
        assertEquals(71, actual);
    }

    @Test
    public void part1_Solutiont() {
        int actual = solvePart1(Utilities.fileToStringList("./data/day16-part01"));
        assertEquals(20231, actual);
    }

    @Test
    public void part2_sampleInput() {
        long actual = solvePart2(createSampleInput_Part2());
        long expected = 1716;  // just multiplying all 3 fields in tis example
        assertEquals(expected, actual);
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


    private long solvePart2(List<String> input) {
        List<String> rules = new ArrayList<>();
        String yourTicket = "";
        List<String> allNearbyTickets = new ArrayList<>();

        // Split input into three different sets of data: rules, your ticket, all nearby tickets
        int mode = 0;
        for (String inputLine:
                input) {
            if (inputLine.trim().isEmpty()) continue;
            if (inputLine.startsWith("your ticket")) mode = 1;
            if (inputLine.startsWith("nearby tickets")) mode = 2;

            if (mode == 0) {
                rules.add(inputLine);
            } else if (mode == 1) {
                if (!inputLine.startsWith("your ticket")) yourTicket = inputLine;
            } else {
                if (!inputLine.startsWith("nearby tickets")) allNearbyTickets.add(inputLine);
            }
        }

        // discard tickets with fields that don't appear in the rules
        List<String> validNearbyTickets = discardInvalidTickets(rules, allNearbyTickets);

        //TODO
        // - Collect all the field position values together (all items in field1, field2, etc)
        //   - collection with be a set of integers and the position in the ticket
        // - Determine which rule a collection belongs to
        //   - let's me map the field position to the rule name
        // - finally for all the "departure" rules, use the field position to multiply the values on my your ticket


        return 0;
    }

    private List<String> discardInvalidTickets(List<String> rules, List<String> allNearbyTickets) {
        List<String> validTickets = new ArrayList<>();

        // gather all valid numbers from the rules

        Set<Integer> validNumbers = new HashSet<>();
        for (String ruleLine :
                rules) {

            int colonIndex = ruleLine.indexOf(":");
            String fieldName = ruleLine.substring(0, colonIndex);

            int orIndex = ruleLine.indexOf(" or ");
            String firstRangeString = ruleLine.substring(colonIndex + 2, orIndex);
            String secondRangeString = ruleLine.substring(orIndex + 4);

            String[] rangeBounds = firstRangeString.split("-");
            int lowerBound = Integer.parseInt(rangeBounds[0]);
            int upperBound = Integer.parseInt(rangeBounds[1]);
            for (int i = lowerBound; i <= upperBound; i++) {
                validNumbers.add(i);
            }

            rangeBounds = secondRangeString.split("-");
            lowerBound = Integer.parseInt(rangeBounds[0]);
            upperBound = Integer.parseInt(rangeBounds[1]);
            for (int i = lowerBound; i <= upperBound; i++) {
                validNumbers.add(i);
            }
        }

        // TODO - the sample input doesn't reject any ticket, have not confirmed this logic with a test
        for (String ticket :
                allNearbyTickets) {

            String[] ticketFields = ticket.split(",");
            for (int i = 0; i < ticketFields.length; i++) {
                int fieldNum = Integer.parseInt(ticketFields[i]);
                if (!validNumbers.contains(fieldNum)) {
                    continue;
                }
            }
            validTickets.add(ticket);
        }

        return validTickets;
    }


}
