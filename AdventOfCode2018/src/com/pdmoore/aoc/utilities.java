package com.pdmoore.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class utilities {


    //TODO - copied over to PuzzleInput and renamed as2DcharArray

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
