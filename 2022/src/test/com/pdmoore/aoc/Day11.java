package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11 {

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day11_example");

        int actual = part1(input);

        assertEquals(101 * 105, actual);
    }

    @Test
    @Disabled
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day11");

        int actual = part1(input);

        assertEquals(99, actual);
    }

    private int part1(List<String> input) {

        List<Monkey> monkies = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 7) {
            Monkey m = new Monkey(input.subList(i, i + 6));
            monkies.add(m);
        }

        // loop twenty times, process each monkey and update monkies
        for (int round = 0; round < 20; round++) {

            //TODO - processing is in place but I think it's not quite right
            // Check this in debugger or add logging to match example
            // Seemed like Monkey 3 had one extra item at end of round 1

            for (Monkey currentMonkey :
                    monkies) {

                List<Integer> itemsToProcess = new ArrayList(currentMonkey.itemList);
                currentMonkey.itemList.clear();
                for (Integer item:
                     itemsToProcess) {

                    Integer newValue = currentMonkey.operation.calcNewValue(item);
                    Integer divBy3   = newValue / 3;
                    if (divBy3 % currentMonkey.testDivValue == 0) {
                        monkies.get(currentMonkey.monkeyIfTrue).itemList.add(divBy3);
                    } else {
                        monkies.get(currentMonkey.monkeyIfFalse).itemList.add(divBy3);
                    }
                }

            }


        }



        // at the end find the top two monkey inspections




        return 0;
    }

    private class Monkey {
        private final Integer monkeyNumber;
        private final Operation operation;
        List<Integer> itemList;
        Integer testDivValue;
        Integer monkeyIfTrue;
        Integer monkeyIfFalse;

        public Monkey(List<String> input) {
            itemList = new ArrayList<>();

            monkeyNumber = Integer.valueOf(input.get(0).replace(":", "").split(" ")[1]);
            String[] items = input.get(1).substring(18).split(", ");
            for (String item :
                    items) {
                itemList.add(Integer.valueOf(item));
            }

            operation = new Operation(input.get(2));

            testDivValue = Integer.valueOf(input.get(3).substring(21));
            monkeyIfTrue  = Integer.valueOf(input.get(4).substring(29));
            monkeyIfFalse = Integer.valueOf(input.get(5).substring(30));
        }
    }

    class Operation {

        private final char operator;
        private final String rhs;

        public Operation(String input) {
            //Operation: new = old * 19
            //new = old + 6
            //new = old * old
            operator = input.charAt(23);
            rhs = input.substring(25);
        }

        public Integer calcNewValue(Integer old) {
            Integer other;
            if ("old".equals(rhs)) {
                other = old;
            } else {
                other = Integer.parseInt(rhs);
            }

            if (operator == '+') {
                return old + other;
            } else if (operator == '*') {
                return old * other;
            }

            throw new IllegalArgumentException("operation unknown");
        }
    }
}
