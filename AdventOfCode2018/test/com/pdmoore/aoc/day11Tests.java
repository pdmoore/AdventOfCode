package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day11Tests {

    public static final int GRID_SERIAL_NUMBER = 1133;

    @Test
    public void calculatePowerLevel() {
        int x = 3;
        int y = 5;
        int serialNumber = 8;

        int actual = day11.calculatePowerLevel(x, y, serialNumber);

        assertEquals(4, actual);

        assertEquals(-5, day11.calculatePowerLevel(122, 79, 57));
        assertEquals(0, day11.calculatePowerLevel(217, 196, 39));
        assertEquals(4, day11.calculatePowerLevel(101, 153, 71));
    }

    @Test
    public void hundredsDigit() {
        assertEquals(0, day11.hundredsDigitOf(4));
        assertEquals(0, day11.hundredsDigitOf(89));
        assertEquals(9, day11.hundredsDigitOf(949));
        assertEquals(3, day11.hundredsDigitOf(2345));
        assertEquals(6, day11.hundredsDigitOf(12645));
    }

    @Test
    public void calcSum() {
        int[][] input = new int[][] {
                { -2, -4, 4,  4,  4 },
                { -4,  4, 4,  4, -5 },
                {  4,  3, 3,  4, -4 },
                {  1,  1, 2,  4, -3 },
                { -1,  0, 2, -5, -2 }
        };

        int actual = day11.calcSum(input, 1, 1);
        assertEquals(29, actual);
    }


    @Test
    public void day11_solution1() {
        int[][] fuelCells = new int[301][301];
        day11.populateFuelCells(fuelCells, GRID_SERIAL_NUMBER);

        Point actual = day11.findLargestTotalPower(fuelCells);
        assertEquals(235, actual.getX());
        assertEquals(14, actual.getY());
    }

    @Test
    @Disabled("2021-12 test takes much too long to be part of unit test suite")
    public void day11_solution2() {
        // Solution is painfully slow....
        int[][] fuelCells = new int[301][301];
        day11.populateFuelCells(fuelCells, GRID_SERIAL_NUMBER);

        day11.Day11Result actual = day11.findLargestTotalPowerOfAllGrids(fuelCells);
        assertEquals(237, actual.upperLeft.getX());
        assertEquals(227, actual.upperLeft.getY());
        assertEquals(14, actual.size);
    }
}
