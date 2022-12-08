package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

public class Day07 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07_example");

        BigInteger actual = part1(input);

        Assertions.assertEquals(new BigInteger("95437"), actual);
    }

    private BigInteger part1(List<String> input) {
        boolean processing_ls = false;

        Directory current_dir = null;
        Map<String, Directory> directories = new HashMap<>();

        Directory root = new Directory("dir /");
        directories.put("dir \\", root);

        for (String inputLine :
                input) {
            if (processing_ls) {
                if (inputLine.startsWith("$")) {
                    processing_ls = false;
                } else {
                    current_dir.contents.add(inputLine);
                }
            }

            if (inputLine.startsWith("$ cd /")) {
                current_dir = root;
            } else if (inputLine.startsWith("$ cd ..")) {
                current_dir = current_dir.parent;
            } else if (inputLine.startsWith("$ cd")) {
                String dirname = "dir" + inputLine.substring(4);
                if (!directories.containsKey(dirname)) {
                    Directory currentDir = new Directory(dirname);
                    currentDir.parent = current_dir;
                    directories.put(dirname, currentDir);
                }
                current_dir = directories.get(dirname);
            } else if (inputLine.startsWith("$ ls")) {
                current_dir.contents = new ArrayList<>();
                processing_ls = true;
            }
        }

        // now process the keys of directories
        // sum each entry and store in new map - dirname to size
        // might need to recurse?

        // sum the directory plus any sub dirs
        // store sum in a list of big ints, pass to filterAndSum
        Map<String, BigInteger> dirsBySize = new HashMap<>();

        // i have directories - which has durName and everything in it
        // dirsBySize which starts off empty and is populated as leaf nodes sum, up to root
        // need to process each directories, parsing BigInteger or 'dir' and once contents are exhausted, add that to dirsBySize
        calcTotalSizes(directories, dirsBySize);

        return filterAndSumBelowLimit(dirsBySize.values());
    }

    private void calcTotalSizes(Map<String, Directory> directories, Map<String, BigInteger> dirsBySize) {

        for (String dirName :
                directories.keySet()) {

            BigInteger totalSize = totalSizeOf(dirName, directories, dirsBySize);
            dirsBySize.put(dirName, totalSize);
        }
    }

    private BigInteger totalSizeOf(String dirName, Map<String, Directory> directories, Map<String, BigInteger> dirsBySize) {
        if (dirsBySize.containsKey(dirName)) {
            return dirsBySize.get(dirName);
        }

        BigInteger result = BigInteger.ZERO;

        for (String dirContent :
                directories.get(dirName).contents) {
            if (dirContent.startsWith("dir")) {
                result = result.add(totalSizeOf(dirContent, directories, dirsBySize));
            } else {
                // not tracking filenamr
                String[] s = dirContent.split(" ");
                result = result.add(new BigInteger(s[0]));
            }
        }

//        dirsBySize.put(dirName, result);
        return result;
    }


    class Directory {
        String name;
        Directory parent = null;
        List<String> contents;

        public Directory(String name) {
            this.name = name;
            contents = new ArrayList<>();
        }
    }

    private BigInteger filterAndSumBelowLimit(Collection<BigInteger> sizes) {
        BigInteger limit = new BigInteger("100000");
        BigInteger result = BigInteger.ZERO;
        for (BigInteger size :
                sizes) {
            if (size.compareTo(limit) <= 0) {   // Assuming inclusive of limit, not strictly less than
                result = result.add(size);
            }
        }
        return result;
    }
}
