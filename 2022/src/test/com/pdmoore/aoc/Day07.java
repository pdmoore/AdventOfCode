package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day07 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07_example");

        BigInteger actual = part1(input);

        Assertions.assertEquals(new BigInteger("95437"), actual);
    }

    private BigInteger part1(List<String> input) {
        // foreach input

        // process cd
            // track current directory

        // process ls
            // map of directory name to list of file name, size

        // sum the directory plus any sub dirs
        // store sum in a list of big ints, pass to filterAndSum

        BigInteger dir_e = new BigInteger("584");
        BigInteger dir_a = new BigInteger("94853");
        BigInteger dir_d = new BigInteger("24933642");
        BigInteger dir_root = new BigInteger("48381165");

        List<BigInteger> sizes = new ArrayList<>();
        sizes.add(dir_root);
        sizes.add(dir_a);
        sizes.add(dir_e);
        sizes.add(dir_d);

        BigInteger result = filterAndSumBelowLimit(sizes);

        return result;
    }

    private BigInteger filterAndSumBelowLimit(List<BigInteger> sizes) {
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
