package com.pdmoore.aoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class utilities {


    static String fileAsString(String filename) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            return new String(encoded).trim();
        } catch (IOException e) {
            System.out.println("ERROR reading " + filename);
        }
        return null;

        // Compare above to PuzzleInput, and move this over - what's the encoded byte/ReadAllBytes mean?
//        return PuzzleInput.AsStringFrom(filename);
    }

    public static char[][] convertInputToMap(List<String> inputAsStrings) {
        int rowCount = inputAsStrings.size();
        int colCount = inputAsStrings.get(0).length();
        char[][] map = new char[rowCount][colCount];
        int i = 0;
        for (String line :
                inputAsStrings) {
            map[i] = line.toCharArray();
            i++;
        }
        return map;
    }
}
