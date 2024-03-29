import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class day16Tests {


    //TODO LOTS OF CLEANUP
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
    void part1_sampleInput() {
        int actual = solvePart1(createSampleInput_Part1());
        assertEquals(71, actual);
    }

    @Test
    void part1_Solution() {
        int actual = solvePart1(PuzzleInput.asListOfStringsFrom("./data/day16-part01"));
        assertEquals(20231, actual);
    }

    @Test
    @Disabled("Skipping Test for part 2 sample input. Sample input doesn't match the problem which has specific fields starting with 'departure'")
    void part2_sampleInput() {
        BigInteger actual = solvePart2(createSampleInput_Part2());
        BigInteger expected = BigInteger.valueOf(1716);  // just multiplying all 3 fields in tis example
        assertEquals(expected, actual);
    }

    @Test
    void part2_Solution() {
        BigInteger actual = solvePart2(PuzzleInput.asListOfStringsFrom("./data/day16-part01"));
        assertEquals(new BigInteger("1940065747861"), actual);
    }


//-----------------------------

    private int solvePart1(List<String> input) {

        Map<Integer, String> validFields = new HashMap<>();

        //parse the input, build map of legal Numbers and the fields they map to
        int mode = 0;
        int result = 0;
        for (String inputLine :
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

    private BigInteger solvePart2(List<String> input) {
        List<TicketRule> ticketRules = createTicketRulesFrom(input);
        List<String> validNearbyTickets = discardInvalidTickets(ticketRules, findAllNearbyTicketsFrom(input));

        Map<Integer, Set<Integer>> valuesByFieldPosition = groupValuesByFieldPosition(validNearbyTickets);

        Map<String, Integer> ruleNameToFieldPosition = assignFieldPositionsToRules(ticketRules, validNearbyTickets, valuesByFieldPosition);

        String yourTicket = findYourTicketFrom(input);
        return productOfDepartureFields(ruleNameToFieldPosition, yourTicket);
    }

    private Map<String, Integer> assignFieldPositionsToRules(List<TicketRule> ticketRules, List<String> validNearbyTickets, Map<Integer, Set<Integer>> valuesByFieldPosition) {
        Map<String, Integer> ruleNameToFieldPosition = new HashMap<>();

        int numberOfFields = validNearbyTickets.get(0).split(",").length;
        List<Integer> unmatchedFields = new ArrayList<>();
        List<String> unmatchedRules = new ArrayList<>();
        for (int i = 0; i < numberOfFields; i++) {
            unmatchedRules.add(ticketRules.get(i).fieldName);
            unmatchedFields.add(i);
        }

        int i = 0;
        while (!unmatchedRules.isEmpty()) {
            if (unmatchedFields.contains(i)) {
                try {
                    Set<Integer> valuesForField = valuesByFieldPosition.get(i);
                    String ruleName = ruleThatWorksFor(ticketRules, valuesForField, unmatchedRules);
                    ruleNameToFieldPosition.put(ruleName, i);

                    // WOW - new Integer(i) is what I had, IDE showed it as strike-through and I removed it so was
                    // removing the i'th index element not the element i
                    // Didn't read the warning closely which pointed me to Integer.valueOf(i) which is correct
                    unmatchedFields.remove(Integer.valueOf(i));
                    unmatchedRules.remove(ruleName);
                } catch (Exception e) {
                    // FieldPosition data matches more than one remaining rule. Skip it for now
                    // and on a future pass the conflicting rule will be assigned to a different
                    // set of FieldPosition data allowing this to be assigned
                }
            }

            if (i++ > numberOfFields) i = 0;
        }

        return ruleNameToFieldPosition;
    }

    private Map<Integer, Set<Integer>> groupValuesByFieldPosition(List<String> validNearbyTickets) {
        Map<Integer, Set<Integer>> valuesByFieldPosition = new HashMap<>();
        int numberOfFields = validNearbyTickets.get(0).split(",").length;
        for (int i = 0; i < numberOfFields; i++) {
            Set<Integer> valuesForField = new TreeSet<>();
            for (String ticket :
                    validNearbyTickets) {
                String[] ticketFieldValues = ticket.split(",");
                valuesForField.add(Integer.parseInt(ticketFieldValues[i]));
            }
            valuesByFieldPosition.put(i, valuesForField);
        }
        return valuesByFieldPosition;
    }

    private List<String> findAllNearbyTicketsFrom(List<String> input) {
        int i = 0;
        while (!input.get(i++).startsWith("nearby tickets")) {
        }

        return input.subList(i, input.size());
    }

    private String findYourTicketFrom(List<String> input) {
        int i = 0;
        while (!input.get(i++).startsWith("your ticket")) {
        }
        return input.get(i);
    }

    private List<TicketRule> createTicketRulesFrom(List<String> input) {
        List<String> rules = new ArrayList<>();

        for (String inputLine :
                input) {
            if (inputLine.trim().isEmpty()) continue;
            if (inputLine.startsWith("your ticket")) break;

            rules.add(inputLine);
        }
        return createTicketRules(rules);
    }

    private BigInteger productOfDepartureFields(Map<String, Integer> ruleNameToFieldPosition, String yourTicket) {
        int d0 = ruleNameToFieldPosition.get("departure date");
        int d1 = ruleNameToFieldPosition.get("departure location");
        int d2 = ruleNameToFieldPosition.get("departure platform");
        int d3 = ruleNameToFieldPosition.get("departure station");
        int d4 = ruleNameToFieldPosition.get("departure time");
        int d5 = ruleNameToFieldPosition.get("departure track");

        String[] yourTicketFields = yourTicket.split(",");
        return new BigInteger(yourTicketFields[d0])
                .multiply(new BigInteger(yourTicketFields[d1]))
                .multiply(new BigInteger(yourTicketFields[d2]))
                .multiply(new BigInteger(yourTicketFields[d3]))
                .multiply(new BigInteger(yourTicketFields[d4]))
                .multiply(new BigInteger(yourTicketFields[d5]));
    }

    private String ruleThatWorksFor(List<TicketRule> ticketRules, Set<Integer> valuesToCheck, List<String> unmatchedRules) {
        List<String> viableRules = new ArrayList<>();
        for (TicketRule ticketRule :
                ticketRules) {
            if (unmatchedRules.contains(ticketRule.fieldName)) {
                viableRules.add(ticketRule.fieldName);
            }
        }

        if (viableRules.size() == 1) return viableRules.get(0);

        for (Integer fieldValue :
                valuesToCheck) {
            for (TicketRule ticketRule :
                    ticketRules) {
                if (!ticketRule.followsRule(fieldValue)) {
                    viableRules.remove(ticketRule.fieldName);
                    if (viableRules.size() == 1) {
                        return viableRules.get(0);
                    }
                }
            }
        }

        if (viableRules.size() > 0) throw new RuntimeException("matched many rules " + viableRules.size());
        return viableRules.get(0);
    }

    private List<String> discardInvalidTickets(List<TicketRule> ticketRules, List<String> allNearbyTickets) {
        List<String> validTickets = new ArrayList<>();

        Set<Integer> validFieldValues = new HashSet<>();
        for (TicketRule tr :
                ticketRules) {
            validNumbersIncludeRange(validFieldValues, tr.lowerLowBound, tr.lowerHighBound);
            validNumbersIncludeRange(validFieldValues, tr.higherLowBound, tr.higherHighBound);
        }

        boolean validTicket = true;
        for (String ticket :
                allNearbyTickets) {
            validTicket = true;
            String[] ticketFields = ticket.split(",");
            for (int i = 0; i < ticketFields.length; i++) {
                int fieldNum = Integer.parseInt(ticketFields[i]);
                if (!validFieldValues.contains(fieldNum)) {
                    validTicket = false;
                }
            }
            if (validTicket) validTickets.add(ticket);
        }

        return validTickets;
    }

    private void validNumbersIncludeRange(Set<Integer> validNumbers, int lowerBound, int higherBound) {
        for (int i = lowerBound; i <= higherBound; i++) {
            validNumbers.add(i);
        }
    }


    private List<TicketRule> createTicketRules(List<String> rules) {
        List<String> validTickets = new ArrayList<>();

        // gather all valid numbers from the rules
        Set<Integer> validNumbers = new HashSet<>();
        List<TicketRule> ticketRules = new ArrayList<>();
        for (String ruleLine :
                rules) {

            int colonIndex = ruleLine.indexOf(":");
            String fieldName = ruleLine.substring(0, colonIndex);

            int orIndex = ruleLine.indexOf(" or ");
            String firstRangeString = ruleLine.substring(colonIndex + 2, orIndex);
            String secondRangeString = ruleLine.substring(orIndex + 4);

            //TODO - duplication?
            String[] rangeBounds = firstRangeString.split("-");
            int lowerLowBound = Integer.parseInt(rangeBounds[0]);
            int lowerHighBound = Integer.parseInt(rangeBounds[1]);
            for (int i = lowerLowBound; i <= lowerHighBound; i++) {
                validNumbers.add(i);
            }

            rangeBounds = secondRangeString.split("-");
            int higherLowBound = Integer.parseInt(rangeBounds[0]);
            int higherHighBound = Integer.parseInt(rangeBounds[1]);
            for (int i = higherLowBound; i <= higherHighBound; i++) {
                validNumbers.add(i);
            }

            TicketRule tr = new TicketRule(fieldName, lowerLowBound, lowerHighBound, higherLowBound, higherHighBound);
            ticketRules.add(tr);
        }

        return ticketRules;
    }


    private class TicketRule {
        private final String fieldName;
        private final int lowerLowBound;
        private final int lowerHighBound;
        private final int higherLowBound;
        private final int higherHighBound;

        public TicketRule(String fieldName, int lowerLowBound, int lowerHighBound, int higherLowBound, int higherHighBound) {
            this.fieldName = fieldName;
            this.lowerLowBound = lowerLowBound;
            this.lowerHighBound = lowerHighBound;
            this.higherLowBound = higherLowBound;
            this.higherHighBound = higherHighBound;
        }

        public boolean followsRule(Integer fieldValue) {
            boolean withinLower = lowerLowBound <= fieldValue && fieldValue <= lowerHighBound;
            boolean withinHigher = higherLowBound <= fieldValue && fieldValue <= higherHighBound;
            return withinLower || withinHigher;
        }
    }
}
