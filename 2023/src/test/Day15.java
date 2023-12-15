package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15 {

    @Test
    void example_current_value_HASH() {
        String input = "HASH";

        int actual = currentValueOf(input);

        assertEquals(52, actual);
    }

    private int currentValueOf(String input) {
        int currentValue = 0;

        for (char c : input.toCharArray()) {
            int ascii = (int)c;
            currentValue += ascii;
            currentValue *= 17;
            currentValue %= 256;
                    /*
        Determine the ASCII code for the current character of the string.
Increase the current value by the ASCII code you just determined.
Set the current value to itself multiplied by 17.
Set the current value to the remainder of dividing itself by 256.
         */




        }


        return currentValue;
    }
}
