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
            } else {
//                throw new IllegalArgumentException("choked on " + inputLine);
            }

            // list of directories
            // list of files within directory

            // current directory (or path)

            // map dirname - list<string> contents


        }

        // now process the keys of directories
        // sum each entry and store in new map - dirname to size
        // might need to recurse?

        // sum the directory plus any sub dirs
        // store sum in a list of big ints, pass to filterAndSum
        Map<String, BigInteger> dirsBySize = new HashMap<>();

        BigInteger dir_e = new BigInteger("584");
        BigInteger dir_a = new BigInteger("94853");
        BigInteger dir_d = new BigInteger("24933642");
        BigInteger dir_root = new BigInteger("48381165");

        dirsBySize.put("dir /", dir_root);
        dirsBySize.put("dir a", dir_a);
        dirsBySize.put("dir d", dir_d);
        dirsBySize.put("dir e", dir_e);

        return filterAndSumBelowLimit(dirsBySize.values());
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
