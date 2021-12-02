package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class day10Tests {

    @Test
    public void parseLine() {
        String input = "position=< 9,  1> velocity=< 0,  2>";

        day10.PointOfLight actual = day10.parseLine(input);

        Point expectedPosition = new Point(9, 1);
        assertEquals(expectedPosition, actual.position);

        Point expectedVelocity = new Point(0, 2);
        assertEquals(expectedVelocity, actual.velocity);
    }

    @Test
    public void parseLine_NegativeValue() {
        String negativeValueInput = "position=<10, -3> velocity=<-1,  1>";
        day10.PointOfLight actual = day10.parseLine(negativeValueInput);

        Point expectedPosition = new Point(10, -3);
        assertEquals(expectedPosition, actual.position);

        Point expectedVelocity = new Point(-1, 1);
        assertEquals(expectedVelocity, actual.velocity);
    }

    @Test
    @Disabled("Sample char height is 8 PoL, real solution is 10")
    public void solution1_sample_3Seconds() {
        String inputFile = "data/aoc18.10a.txt";

        day10.Board actual = day10.createBoard(inputFile);
        try {
            actual.animate();
        } catch (RuntimeException e) {
            assertEquals(3, actual.ticks);
        }
    }

    @Test
    public void solution1() {
        String inputFile = "data/aoc18.10.txt";

        day10.Board actual = day10.createBoard(inputFile);
        try {
            actual.animate();
            fail("solution 1 failed to find converging points");
        } catch(RuntimeException e) {
            String expectedMessage =
                    "  ##       ###  ######  #    #  #    #  #    #  #    #  ######\n" +
                    " #  #       #        #  ##   #  #    #  #    #  #   #   #     \n" +
                    "#    #      #        #  ##   #   #  #   #    #  #  #    #     \n" +
                    "#    #      #       #   # #  #   #  #   #    #  # #     #     \n" +
                    "#    #      #      #    # #  #    ##    ######  ##      ##### \n" +
                    "######      #     #     #  # #    ##    #    #  ##      #     \n" +
                    "#    #      #    #      #  # #   #  #   #    #  # #     #     \n" +
                    "#    #  #   #   #       #   ##   #  #   #    #  #  #    #     \n" +
                    "#    #  #   #   #       #   ##  #    #  #    #  #   #   #     \n" +
                    "#    #   ###    ######  #    #  #    #  #    #  #    #  ######\n" +
                    "seconds: 10905";

            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
