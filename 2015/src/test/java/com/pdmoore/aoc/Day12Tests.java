package com.pdmoore.aoc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    @Test
    void part2_sum_array_of_numbers() {
        var input = "[1,2,3]";
        int actual = sumNumbersIgnoringObjectsContainingRed(input);
        assertEquals(6, actual);
    }

    @Test
    void part2_sum_array_that_has_red() {
        var input = "[1,\"red\",5]";
        int actual = sumNumbersIgnoringObjectsContainingRed(input);
        assertEquals(6, actual);
    }

    @Test
    void part2_ignore_object_that_has_red() {
        var input = "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}";
        int actual = sumNumbersIgnoringObjectsContainingRed(input);
        assertEquals(0, actual);
    }

    @Test
    void part2_sum_object_values() {
        var input = "{\"d\":4,\"f\":5}";
        int actual = sumNumbersIgnoringObjectsContainingRed(input);
        assertEquals(9, actual);
    }

    @Test
    void part2_sum_object_when_value_is_an_array() {
        var input = "{\"d\":2,\"e\":[1,2,3,4],\"f\":5}";
        int actual = sumNumbersIgnoringObjectsContainingRed(input);
        assertEquals(17, actual);
    }

    @Test
    void part2_sum_object_inside_array_contains_red() {
        var input = "[1,{\"c\":\"red\",\"b\":2},3]";
        int actual = sumNumbersIgnoringObjectsContainingRed(input);
        assertEquals(4, actual);
    }

    @Test
    void part2_sum_object_inside_array() {
        var input = "[1,{\"c\":6,\"b\":2},3]";
        int actual = sumNumbersIgnoringObjectsContainingRed(input);
        assertEquals(12, actual);
    }

    @Test
    void part2_sum_array_inside_array() {
        var input = "[1,2,[2,5],3]";
        int actual = sumNumbersIgnoringObjectsContainingRed(input);
        assertEquals(13, actual);
    }

    @Test
    void part2_sum_object_inside_object() {
        var input = "{\"d\":2,\"e\":{\"z\":9},\"f\":5}";
        int actual = sumNumbersIgnoringObjectsContainingRed(input);
        assertEquals(16, actual);
    }

    @Test
    void part2_solution() {
        String input = PuzzleInput.asStringFrom("data/day12");
        int actual = sumNumbersIgnoringObjectsContainingRed(input);
        assertEquals(96852, actual);
    }

    private int sumNumbersIgnoringObjectsContainingRed(String input) {
        int sum = 0;
        JsonElement rootNode = JsonParser.parseString(input);
        return sumFor(rootNode);
    }

    private int sumOfObject(JsonObject jsonObject) {
        if (containsValueRed(jsonObject)) {
            return 0;
        }

        return jsonObject.keySet().stream()
                .map(jsonObject::get)
                .mapToInt(this::sumFor)
                .sum();
    }

    private int sumFor(JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            return sumOfObject(jsonElement.getAsJsonObject());
        }
        if (jsonElement.isJsonArray()) {
            return sumOfArray(jsonElement.getAsJsonArray());
        }
        if (jsonElement.isJsonPrimitive() &&
                jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsInt();
        }
        return 0;
    }

    private boolean containsValueRed(JsonObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            JsonElement jsonElement = jsonObject.get(key);
            if (jsonElement.isJsonPrimitive() &&
                    jsonElement.getAsJsonPrimitive().isString()) {
                String value = jsonElement.getAsString();
                if ("red".equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int sumOfArray(JsonArray jsonArray) {
        int sum = 0;
        for (JsonElement element : jsonArray) {
            sum += sumFor(element);
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
