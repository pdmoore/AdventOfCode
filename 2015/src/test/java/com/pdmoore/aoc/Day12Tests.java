package com.pdmoore.aoc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
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
    //[1,{"c":"red","b":2},3] now has a sum of 4, because the middle object is ignored.
    //{"d":"red","e":[1,2,3,4],"f":5} now has a sum of 0, because the entire structure is ignored.
    //DONE [1,"red",5] has a sum of 6, because "red" in an array has no effect.

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


    private int part2Thingy(String input) {

        // convert string to JSON
        // detect json object is an array
        // sum the contents of an array

        int sum = 0;

        JsonElement rootNode = JsonParser.parseString(input);
        if (rootNode.isJsonObject()) {

            // flattens whole structure to one array
            //            JsonObject jsonObject = rootNode.getAsJsonObject();
//            JsonArray jsonArray = jsonObject.getAsJsonArray("myArray");
//            for (JsonElement element : jsonArray) {
//                System.out.println(element.getAsString());
//            }

            // probably need to do this - halt if value is "red", sum values/arrays otherwise
            JsonObject jsonObject = rootNode.getAsJsonObject();
            //                if (jsonObject.get(key).isJsonArray()) {
            //                    int sam = 0;
            //                }
//            {"d":"red","e":[1,2,3,4],"f":5}
            Iterator<String> keys = jsonObject.keySet().iterator();
            int objectSum = 0;
            while(keys.hasNext()) {
                String key = keys.next();
                JsonElement jsonElement = jsonObject.get(key);
                if (jsonElement.isJsonPrimitive() &&
                        jsonElement.getAsJsonPrimitive().isString()) {
                    String value = jsonElement.getAsString();
                    if ("red".equals(value)) {
                        objectSum = 0;
                        break;
                    }
                }
                if (jsonElement.isJsonPrimitive() &&
                    jsonElement.getAsJsonPrimitive().isNumber()) {
                    objectSum += jsonElement.getAsInt();
                }
            }
            sum += objectSum;
        } else if (rootNode.isJsonArray()) {
            JsonArray jsonArray = rootNode.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                if (element.isJsonPrimitive() &&
                        element.getAsJsonPrimitive().isNumber()) {
                    sum += element.getAsInt();
                }

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
