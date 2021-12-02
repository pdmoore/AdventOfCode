package com.pdmoore.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class utils {
    public static String fileAsString(String filenameWithPath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filenameWithPath));
            return new String(encoded).trim();
        } catch (IOException e) {
            throw new IllegalArgumentException("ERROR reading " + filenameWithPath);
        }
    }

    public static String convertIntArrayToCommaSeparatedString(int[] ints) {
        String result = "";
        for (int i :
                ints) {
            result += i + ",";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    public static int[] convertCommaSeparatedStringToIntArray(String input) {
        return Stream.of(input.split(",")).
                mapToInt(Integer::parseInt).
                toArray();
    }
}
