package com.pdmoore.aoc;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
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

    public static String asStringFrom(String filename) {
        try {
            // Consider adding a trim() to the end - noticed a difference in 2018's impl compared to this
            return Files.asCharSource(new File(filename), Charsets.UTF_8).read();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't open puzzle input file: " + filename);
        }
    }

    // Name doesn't match others - should be asStringListFrom (or change others to match this)
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

    public static List<BigInteger> asBigIntegerListFrom(String pathAndFileName) {
        List<BigInteger> integers = new ArrayList<>();
        try {
            File f = new File(pathAndFileName);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                integers.add(scanner.nextBigInteger());
            }
            scanner.close();
        } catch (Exception err) {
            System.out.println("File not found in this directory " + System.getProperty("user.dir"));
            throw new RuntimeException("error while processing " + pathAndFileName);
        }
        return integers;
    }

    public static List<List<Integer>> asListOfListOfIntegers(String pathAndFileName) {
        List<List<Integer>> allLines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathAndFileName));
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> lineOfIntegers = new ArrayList<>();
                Scanner s = new Scanner(line);
                while (s.hasNextInt()) {
                    lineOfIntegers.add(s.nextInt());
                }
                allLines.add(lineOfIntegers);
            }
        } catch (IOException e) {
            System.out.println("File not found in this directory " + System.getProperty("user.dir"));
            throw new RuntimeException("error while processing " + pathAndFileName);
        }
        return allLines;
    }

    // AoC18 has
    // public static char[][] convertInputToMap(List<String> inputAsStrings)
    // AoC19 has
    // public static String fileAsString(String filenameWithPath)
    // public static String convertIntArrayToCommaSeparatedString(int[] ints)
    // public static int[] convertCommaSeparatedStringToIntArray(String input)

    // from aoc18 - this version does an explicit trim of the file contents
    // added a note to asStringFrom to consider
//    static String fileAsString(String filename) {
//        try {
//            byte[] encoded = java.nio.file.Files.readAllBytes(Paths.get(filename));
//            return new String(encoded).trim();
//        } catch (IOException e) {
//            System.out.println("ERROR reading " + filename);
//        }
//        return null;
//    }

}
