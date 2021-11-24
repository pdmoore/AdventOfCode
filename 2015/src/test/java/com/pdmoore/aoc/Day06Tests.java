package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day06Tests {

    // data structure
    // naive - create 1000,1000 array of boolean
    // or - store grid point and state of light in a map
    // or - use a packed bit array
    // parse a line - turn on
    // parse a line - turn off
    // parse a line - toggle
    // process a single line input
    // process >1 line of input
    // count number of lit lights
    // read file and process all input

    /*
    examples
- turn on 0,0 through 999,999
would turn on (or leave on) every light.
- toggle 0,0 through 999,0
would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.
- turn off 499,499 through 500,500
would turn off (or leave off) the middle four lights.
     */
    @Test
    public void processTurnOn() {
        Grid grid = new Grid();
        grid.processInstruction("turn on 499,499 through 500,500");
        Assertions.assertEquals(4, grid.litCount());
    }
    
    class Grid {

        boolean[][] lights;

        public Grid() {
            this.lights = new boolean[1000][1000];
        }

        public int litCount() {
            int litCount = 0;
            for (int x = 0; x <= lights.length - 1; x++) {
                for (int y = 0; y <= lights[0].length - 1; y++) {
                    if (lights[x][y]) litCount++;
                }
            }
            return litCount;
        }

        public void processInstruction(String instruction) {
            // assume turn on
            // grab first #,# pair
            // grab second #,# pair
            //loop/loop over the pairs and turn on data structure
            for (int x = 499; x <= 500; x++) {
                for (int y = 499; y <= 500; y++) {
                    lights[x][y] = true;
                }
            }
        }
    }
}
