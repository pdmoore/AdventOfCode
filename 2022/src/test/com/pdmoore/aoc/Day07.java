package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

public class Day07 {

    final BigInteger total_space = new BigInteger("70000000");
    final BigInteger min_unused_space = new BigInteger("30000000");

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07_example");

        BigInteger actual = part1(input);

        Assertions.assertEquals(new BigInteger("95437"), actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07_example");

        BigInteger actual = part2(input);

        Assertions.assertEquals(new BigInteger("24933642"), actual);
    }

    @Test
    void part1_example_withDuplicateDirectoryName() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07_withDupes");

        BigInteger actual = part1(input);

        Assertions.assertEquals(new BigInteger("60"), actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07");

        BigInteger actual = part1(input);

        Assertions.assertEquals(new BigInteger("1306611"), actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07");

        BigInteger actual = part2(input);

        Assertions.assertEquals(new BigInteger("13210366"), actual);
    }

    private BigInteger part1(List<String> input) {

        Map<String, Directory> directories = new HashMap<>();
        extracted(input, directories);

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

    private void extracted(List<String> input, Map<String, Directory> directories) {
        Directory root = new Directory("/");
        directories.put(root.name, root);

        Directory current_dir = null;
        boolean processing_ls = false;
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
                String betterName = current_dir.path() + inputLine.substring(5) + "/";
                if (!directories.containsKey(betterName)) {
                    Directory currentDir = new Directory(betterName);
                    currentDir.parent = current_dir;
                    directories.put(betterName, currentDir);
                }
                current_dir = directories.get(betterName);
            } else if (inputLine.startsWith("$ ls")) {
                current_dir.contents = new ArrayList<>();
                processing_ls = true;
            }
        }
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
                String betterName = directories.get(dirName).path() + dirContent.substring(4) + "/";
                result = result.add(totalSizeOf(betterName, directories, dirsBySize));
            } else {
                // not tracking filename
                String[] s = dirContent.split(" ");
                result = result.add(new BigInteger(s[0]));
            }
        }

        return result;
    }

    private BigInteger part2(List<String> input) {
        Map<String, Directory> directories = new HashMap<>();
        extracted(input, directories);

        Map<String, BigInteger> dirsBySize = new HashMap<>();
        calcTotalSizes(directories, dirsBySize);

        BigInteger totalUsed = dirsBySize.get("/");
        BigInteger unusedSpace = total_space.subtract(totalUsed);
        BigInteger minSpaceNeeded = min_unused_space.subtract(unusedSpace);

        BigInteger result = totalUsed;
        for (String directoryName :
                dirsBySize.keySet()) {
            BigInteger thisDirSize = dirsBySize.get(directoryName);

            if (thisDirSize.compareTo(minSpaceNeeded) >= 0) {
                if (thisDirSize.compareTo(result) < 0) {
                    result = thisDirSize;
                }
            }
        }

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

        public String path() {
            return name;
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
