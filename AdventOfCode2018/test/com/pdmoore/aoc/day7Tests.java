package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day7Tests {

    @Test
    public void calculateStepOrder() {
        Map<String, List<String>> rules = new HashMap<>();
        List<String> a_depends = Arrays.asList("C");
        rules.put("A", a_depends);
        List<String> b_depends = Arrays.asList("A");
        rules.put("B", b_depends);
        rules.put("C", Collections.EMPTY_LIST);
        List<String> d_depends = Arrays.asList("A");
        rules.put("D", d_depends);
        List<String> e_depends = Arrays.asList("B", "D", "F");
        rules.put("E", e_depends);
        List<String> f_depends = Arrays.asList("C");
        rules.put("F", f_depends);

        String actual = day7.calculateStepOrder(rules);

        assertEquals("CABDFE", actual);
    }

    @Test
    public void ruleCost() {
        assertEquals( 1, day7.ruleCost("A"));
        assertEquals( 2, day7.ruleCost("B"));
        assertEquals(26, day7.ruleCost("Z"));
    }

    @Test
    public void solution1_example() {
        String filename = "data/aoc18.7a.txt";

        String actual = day7.solution1(filename);

        assertEquals("CABDFE", actual);
    }

    @Test
    public void solution1() {
        String filename = "data/aoc18.7.txt";

        String actual = day7.solution1(filename);

        assertEquals("ACBDESULXKYZIMNTFGWJVPOHRQ", actual);
    }

    @Test
    public void solution2_example() {
        String filename = "data/aoc18.7a.txt";

        int workerCount = 2;
        int stepCost = 0;
        int actual = day7.solution2(filename, workerCount, stepCost);

        assertEquals(15, actual);
    }

    @Test
    public void solution2() {
        String filename = "data/aoc18.7.txt";

        int workerCount = 5;
        int stepCost = 60;
        int actual = day7.solution2(filename, workerCount, stepCost);

        assertEquals(980, actual);
    }

}
