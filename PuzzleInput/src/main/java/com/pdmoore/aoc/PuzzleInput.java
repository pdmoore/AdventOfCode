package com.pdmoore.aoc;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PuzzleInput {

    // To use in a new project
    // File | Project Structure
    // Project Settings | Libraries
    // + | Java
    // Navigate to PuzzleInput project's \out directory and drill down to the jar file

    // After adding a new method
    // Build | Build Artifacts -- PuzzleInput:jar - BuildAction - Rebuild
    // Then in the dependent project
    // File | Reload all from disk

    public static String AsStringFrom(String filename) {
        try {
            return Files.asCharSource(new File(filename), Charsets.UTF_8).read();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't open puzzle input file: " + filename);
        }
    }

    public static List<String> asListOfStringsFrom(String filename) {
        List<String> lines = new ArrayList<>();
        try {
            File f = new File(filename);
            Scanner scanner = new Scanner(f);

            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        } catch (Exception e) {
            throw new RuntimeException("Couldn't read puzzle input file: " + filename);
        }
        return lines;
    }

    public static List<Integer> asIntegerListFrom(String pathAndFileName) {
        List<Integer> integers = new ArrayList<>();
        try {
            File f = new File(pathAndFileName);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                integers.add(scanner.nextInt());
            }
            scanner.close();
        } catch (Exception err) {
            System.out.println("File not found in this directory " + System.getProperty("user.dir"));
            throw new RuntimeException("error while processing " + pathAndFileName);
        }
        return integers;
    }
}
