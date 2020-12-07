import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day07Tests {

    /*


light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.

left hand side of "bags contain" is the key
right hand side is a list of contents:
split on comma
# and key

- build the list of keys and values
- then for each key, drill down to all leafs and see if shiny gold is in it's child list
  - if yes, increment count

     */


    @Test
    public void testKeyFromInputLine() {
        String inputLine = "light red bags contain 1 bright white bag, 2 muted yellow bags.";
        String actual = keyFrom(inputLine);
        assertEquals("light red", actual);
    }

    @Test
    public void testValuesFromInputLine() {
        String inputLine = "light red bags contain 1 bright white bag, 2 muted yellow bags.";
        List<String> actual = valuesFrom(inputLine);
        List<String> expected = new ArrayList<>();
        expected.add("1 bright white");
        expected.add("2 muted yellow");
        assertEquals(expected, actual);
    }

    @Test
    public void testValuesFromInputLine_bagDoesNotContainOthers() {
        String inputLine = "dotted black bags contain no other bags.";
        List<String> actual = valuesFrom(inputLine);
        List<String> expectedEmptyList = new ArrayList<>();
        assertEquals(expectedEmptyList, actual);
    }

    @Test
    public void part1_exampleMap() {

        List<String> input = createSampleInput();

        Map<String, List<String>> bagMap = new HashMap<>();
        bagMap = buildMap(input);

        assertEquals(9, bagMap.size());
    }

    private List<String> createSampleInput() {
        List<String> input = new ArrayList<>();
        input.add("light red bags contain 1 bright white bag, 2 muted yellow bags.");
        input.add("dark orange bags contain 3 bright white bags, 4 muted yellow bags.");
        input.add("bright white bags contain 1 shiny gold bag.");
        input.add("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.");
        input.add("shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.");
        input.add("dark olive bags contain 3 faded blue bags, 4 dotted black bags.");
        input.add("vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.");
        input.add("faded blue bags contain no other bags.");
        input.add("dotted black bags contain no other bags.");
        return input;
    }

    @Test
    public void part1_example_solution() {
        List<String> input = createSampleInput();

        String target = "shiny gold";

        int actual = findBagsContaining(input, target);
        assertEquals(4, actual);
    }

    @Test
    public void part1_solution() {
        List<String> input = Utilities.fileToStringList("./data/day07-part01");

        String target = "shiny gold";
        int actual = findBagsContaining(input, target);
        assertEquals(302, actual);
    }

    @Test
    public void part2_example1() {
        List<String> input = createSampleInput();
        Map<String, List<String>> bagMap  = buildMap(input);

        String target = "shiny gold";

        int actual = countBagsContainedBy(bagMap, target);
        assertEquals(32, actual);
    }

    @Test
    public void part2_solution() {
        List<String> input = Utilities.fileToStringList("./data/day07-part01");
        Map<String, List<String>> bagMap  = buildMap(input);

        String target = "shiny gold";

        int actual = countBagsContainedBy(bagMap, target);
        assertEquals(0, actual);
    }



    private int countBagsContainedBy(Map<String, List<String>> bagMap, String target) {
        List<String> keysWithin = bagMap.get(target);

        return countBagsContainedBy(0, bagMap, keysWithin);
    }

    private int countBagsContainedBy(int bagCount, Map<String, List<String>> bagMap, List<String> values) {

        for (String value:
               values) {
            int x = value.indexOf(" ");
            String valueAsKey = justTheKey(value);
            int countOfValue = Integer.parseInt(value.substring(0, x));
            int countOfContainedBags = countBagsContainedBy(0, bagMap, bagMap.get(valueAsKey));
            int countOfTheseAndContained = countOfValue + countOfValue * countOfContainedBags;
            bagCount += countOfTheseAndContained;
        }

        return bagCount;
    }


//-----------------------------


    private int findBagsContaining(List<String> input, String target) {
        Map<String, List<String>> bagMap  = buildMap(input);

        int bagsContainingTarget = 0;

        // for each key in the bagmap
        // if key contains target, increment result
        // otherwise, repeat search for each value as a key
        Set<String> keys = bagMap.keySet();

        for (String key :
                keys) {

            if (bagSearchFindsTarget(bagMap, key, target)) {
                bagsContainingTarget++;
            }
        }

        return bagsContainingTarget;
    }

    private boolean bagSearchFindsTarget(Map<String, List<String>> bagMap, String key, String target) {
        if (target.equals(key)) {
            return false;
        }

        List<String> values = bagMap.get(key);

        if (values == null || values.isEmpty()) return false;

        if (targetInValues(values, target)) {
            return true;
        }

        for (String valueIsNowKey :
                values) {

            // NEED TO REMOVE THE NUMBERS FROM THE VALUE AT THIS POINT
            String keyFromValue = justTheKey(valueIsNowKey);

            if (bagSearchFindsTarget(bagMap, keyFromValue, target)) {
                return true;
            }
        }

        return false;
    }

    private String justTheKey(String valueIsNowKey) {
        int x = valueIsNowKey.indexOf(" ");
        String result = valueIsNowKey.substring(x + 1);
        return result;
    }

    private boolean targetInValues(List<String> values, String target) {
        for (String value :
                values) {
            if (value.contains(target)) {
                return true;
            }
        }
        return false;
    }


    private String keyFrom(String inputLine) {
        return inputLine.substring(0, inputLine.indexOf(" bags contain"));
    }

    private List<String> valuesFrom(String inputLine) {
        int x = inputLine.indexOf(" bags contain ") + " bags contain ".length();

        List<String> result = new ArrayList<>();

        if (inputLine.contains("no other")) {
            return result;
        }

        String valuesInput = inputLine.substring(x);
        String[] split = valuesInput.split(", ");
        for (String value :
                split) {

            int y = value.indexOf(" bag");

            result.add(value.substring(0, y));
        }
        return result;
    }

    private Map<String, List<String>> buildMap(List<String> input) {
        Map<String, List<String>> result = new HashMap<>();

        for (String inputLine :
                input) {

            String key = keyFrom(inputLine);
            List<String> value = valuesFrom(inputLine);

            result.put(key, value);
        }

        return result;
    }





}
