package com.pdmoore.aoc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Tests {

    @Test
    void emptyElements_SumToZero() {
        assertEquals(0, sumAllNumbersIn("[]"));
        assertEquals(0, sumAllNumbersIn("{}"));
    }

    @Test
    void sumPositiveNumbers_CommaDelimited() {
        String input = "[1,2,3]";

        int actual = sumAllNumbersIn(input);

        assertEquals(6, actual);
    }

    @Test
    void sumPositiveNumber_nestedBrackets() {
        String input = "[[[3]]]";
        int actual = sumAllNumbersIn(input);
        assertEquals(3, actual);
    }

    @Test
    void sumPositiveNumbers_CharactersMixedIn() {
        String input = "{\"a\":2,\"b\":4}";

        int actual = sumAllNumbersIn(input);

        assertEquals(6, actual);
    }

    @Test
    void sumNegativeNumber() {
        String input = "{\"a\":{\"b\":4},\"c\":-1}";
        int actual = sumAllNumbersIn(input);
        assertEquals(3, actual);

        input = "{\"a\":[-1,1]}";
        actual = sumAllNumbersIn(input);
        assertEquals(0, actual);

        input = "[-1,{\"a\":1}]";
        actual = sumAllNumbersIn(input);
        assertEquals(0, actual);
    }

    @Test
    void longerExample() {
        String input = "[\"violet\",{\"e\":\"blue\",\"a\":187,\"d\":115,\"j\":193,\"c\":119,\"h\":\"yellow\",\"b\":\"yellow\",\"g\":\"red\",\"f\":74,\"i\":25},\"orange\",0,-17,\"yellow\",-23]";
        int actual = sumAllNumbersIn(input);
        int expected = 187+115+193+119+74+25+0-17-23;
        assertEquals(expected, actual);
    }

    @Test
    void part1_solution() {
        String input = PuzzleInput.asStringFrom("data/day12");
        int actual = sumAllNumbersIn(input);
        assertEquals(156366, actual);
    }

    //Part 2
    //Ignore any object (and all of its children) which has any property
    // with the value "red".
    // Do this only for objects ({...}), not arrays ([...]).
    //DONE [1,2,3] still has a sum of 6.
    //DONE {"d":"red","e":[1,2,3,4],"f":5} now has a sum of 0, because the entire structure is ignored.
    //DONE [1,"red",5] has a sum of 6, because "red" in an array has no effect.
    //DONE{"d":2,"e":[1,2,3,4],"f":5} should be 17, need to sum array when it is value
    //DONE [1,{"c":"red","b":2},3] now has a sum of 4, because the middle object is ignored.
    // MAKE SURE HANDLING THE OBJECT INSIDE AREA AND ARRAY INSIDE OBJECT RECURSION


    @Test
    void part2_sum_array_of_numbers() {
        var input = "[1,2,3]";
        int actual = part2Thingy(input);
        assertEquals(6, actual);
    }

    @Test
    void part2_sum_array_that_has_red() {
        var input = "[1,\"red\",5]";
        int actual = part2Thingy(input);
        assertEquals(6, actual);
    }

    @Test
    void part2_ignore_object_that_has_red() {
        var input = "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}";
        int actual = part2Thingy(input);
        assertEquals(0, actual);
    }

    @Test
    void part2_sum_object_values() {
        var input = "{\"d\":4,\"f\":5}";
        int actual = part2Thingy(input);
        assertEquals(9, actual);
    }

    @Test
    void part2_sum_object_when_value_is_an_array() {
        var input = "{\"d\":2,\"e\":[1,2,3,4],\"f\":5}";
        int actual = part2Thingy(input);
        assertEquals(17, actual);
    }

    @Test
    void part2_sum_object_inside_array_contains_red() {
        var input = "[1,{\"c\":\"red\",\"b\":2},3]";
        int actual = part2Thingy(input);
        assertEquals(4, actual);
    }

    @Test
    void part2_sum_object_inside_array() {
        var input = "[1,{\"c\":6,\"b\":2},3]";
        int actual = part2Thingy(input);
        assertEquals(12, actual);
    }

    @Test
    void part2_sum_array_inside_array() {
        var input = "[1,2,[2,5],3]";
        int actual = part2Thingy(input);
        assertEquals(13, actual);
    }

    @Test
    @Disabled
    void part2_solution() {
        String input = PuzzleInput.asStringFrom("data/day12");
        int actual = part2Thingy(input);
        assertEquals(-99, actual);
    }

    private int part2Thingy(String input) {
        int sum = 0;
        JsonElement rootNode = JsonParser.parseString(input);
        if (rootNode.isJsonObject()) {
            sum += sumOfObject(rootNode.getAsJsonObject());
        } else if (rootNode.isJsonArray()) {
            sum += sumOfArray(rootNode.getAsJsonArray());
        }
        return sum;
    }

    private int sumOfObject(JsonObject jsonObject) {
        Iterator<String> keys = jsonObject.keySet().iterator();
        while(keys.hasNext()) {
            String key = keys.next();
            JsonElement jsonElement = jsonObject.get(key);
            if (jsonElement.isJsonPrimitive() &&
                    jsonElement.getAsJsonPrimitive().isString()) {
                String value = jsonElement.getAsString();
                if ("red".equals(value)) {
                    return 0;
                }
            }
        }

        keys = jsonObject.keySet().iterator();
        int objectSum = 0;
        while(keys.hasNext()) {
            String key = keys.next();
            JsonElement jsonElement = jsonObject.get(key);
            if (jsonElement.isJsonArray()) {
                objectSum += sumOfArray(jsonElement.getAsJsonArray());
            }
            if (jsonElement.isJsonPrimitive() &&
                jsonElement.getAsJsonPrimitive().isNumber()) {
                objectSum += jsonElement.getAsInt();
            }
        }
        return objectSum;
    }

    private int sumOfArray(JsonArray jsonArray) {
        int sum = 0;
        for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
                sum += sumOfObject(element.getAsJsonObject());
            }
            if (element.isJsonArray()) {
                sum += sumOfArray(element.getAsJsonArray());
            }
            if (element.isJsonPrimitive() &&
                    element.getAsJsonPrimitive().isNumber()) {
                sum += element.getAsInt();
            }
        }
        return sum;
    }

    private int sumAllNumbersIn(String input) {
        String stripped = input.replaceAll("[a-z\"\\:\\[\\]\\{\\}]", "");
        if (stripped.isEmpty()) return 0;
        return Arrays.stream(stripped.split(","))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt).sum();
    }
}
