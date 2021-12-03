package com.pdmoore.aoc;

import com.google.common.graph.AbstractNetwork;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day03Tests {

/*
00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010
 */

    @Test
    void day3_part1_GammaValue() {
        List<String> input = new ArrayList<>(Arrays.asList(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010"));

        int actual = calculateGammaRate(input);

        assertEquals(22, actual);
    }

    @Test
    void day3_part1_EpsilonValue() {
        List<String> input = new ArrayList<>(Arrays.asList(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010"));

        int actual = calculateEpsilonRate(input);

        assertEquals(9, actual);
    }

    @Test
    void day3_part1_example() {
        List<String> input = new ArrayList<>(Arrays.asList(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010"));

        int actual = calculatePowerConsumption(input);

        assertEquals(198, actual);
    }

    @Test
    void day3_part1_solution() {
        List<String> input = PuzzleInput.asListOfStringsFrom("./data/day03");
        
        int actual = calculatePowerConsumption(input);

        assertEquals(845186, actual);
    }

    private int calculatePowerConsumption(List<String> input) {
        return calculateGammaRate(input) * calculateEpsilonRate(input);
    }

    private int calculateEpsilonRate(List<String> input) {
        String result = "";
        int currentPosition = 0;
        int currentBit = 0;
        int countOf0 = 0;
        int countOf1 = 0;

        for (int i = 0; i < input.get(0).length(); i++) {
            for (String inputLine :
                    input) {
                if (inputLine.charAt(currentPosition) == '0') {
                    countOf0++;
                } else {
                    countOf1++;
                }
            }

            if (countOf0 < countOf1) {
                result = result.concat("0");
            } else {
                result = result.concat("1");
            }

            currentPosition++;
            countOf0 = 0;
            countOf1 = 0;
        }

        return Integer.parseInt(result, 2);
    }

    private int calculateGammaRate(List<String> input) {
        String result = "";
        int currentPosition = 0;
        int currentBit = 0;
        int countOf0 = 0;
        int countOf1 = 0;

        for (int i = 0; i < input.get(0).length(); i++) {
            for (String inputLine :
                    input) {
                if (inputLine.charAt(currentPosition) == '0') {
                    countOf0++;
                } else {
                    countOf1++;
                }
            }

            if (countOf0 > countOf1) {
                result = result.concat("0");
            } else {
                result = result.concat("1");
            }

            currentPosition++;
            countOf0 = 0;
            countOf1 = 0;
        }

        return Integer.parseInt(result, 2);
    }
}
