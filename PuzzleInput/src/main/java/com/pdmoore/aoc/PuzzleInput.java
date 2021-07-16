package com.pdmoore.aoc;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

public class PuzzleInput {

    public static String AsStringFromFile(String filename) {
        try {
            return Files.asCharSource(new File(filename), Charsets.UTF_8).read();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't open puzzle input file: " + filename);
        }
    }

}
