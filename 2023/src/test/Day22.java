package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day22 {

    // FALLING STUFF
    // brick span, brick supports
    // anything below brick
    @Test
    void example1_brick_creation() {
        List<String> allBricksInput = PuzzleInput.asStringListFrom("./data/day22_part1_example");
        List<String> justABC = allBricksInput.subList(0, 3);

        List<Brick> bricks = createBricksFrom(justABC);
        Brick brickB = bricks.get(1);
        assertEquals(0, brickB.start.x);
        assertEquals(0, brickB.start.y);
        assertEquals(2, brickB.end.x);
        assertEquals(0, brickB.end.y);
    }

    @Test
    void falling_brick() {
        List<String> allBricksInput = PuzzleInput.asStringListFrom("./data/day22_part1_example");
        List<String> justABC = allBricksInput.subList(0, 3);

        List<Brick> bricks = createBricksFrom(justABC);
        assertEquals(3, bricks.size());

        settleAllBricks(bricks);

        assertEquals(1, bricks.get(0).start.z, "A is on the ground");
        assertEquals(2, bricks.get(1).start.z, "B is atop A, does not settle");
        assertEquals(2, bricks.get(2).start.z, "C was falling, settles on A");
    }


    private void settleAllBricks(List<Brick> bricks) {
//TODO -more work or tests around this, maybe have an outer loop that adds all non z==1 bricks,
        // then process each trying to fall one. If it falls, add it again
        // if it doesn't fall then maybe it's done?
        // Also maybe sort into a z-ordered map so that bottom ones don't get re-evaluated?
        for (Brick maybeFalling: bricks) {
            // is it already on the ground?
            if (maybeFalling.start.z == 1 || maybeFalling.end.z == 1) {
                continue;
            }

            // check if it is atop any other brick
            if (maybeFalling.StillInAir(bricks)) {
                maybeFalling.fall();
            }
        }
    }


    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day22_part1_example");

        List<Brick> bricks = createBricksFrom(input);
        assertEquals(7, bricks.size());

        //maybe a map by Z position with a list of those bricks at that z?

        // parse each line, create a 3D object
        // from bottom up, fall all the bricks until no bricks fall
        // process each brick, is it the only support for a brick above it
        // return the list of those bricks or just the count of those

        // not sure if it's a char[][][] or int[][][]
        // int could be the key to a map?

//        int actual = countSafeToDisintegrate(map);
//
//        assertEquals(5, actual);
    }

    private List<Brick> createBricksFrom(List<String> input) {
        List<Brick> result = new ArrayList<>();
        for (String inputLine: input) {
            String[] split = inputLine.split("~");
            result.add(new Brick(split[0], split[1]));
        }
        return result;
    }

    class Brick {
        Point3D start;
        Point3D end;

        public Brick(String startString, String endString) {
            String[] startSplit = startString.split(",");
            start = new Point3D(Integer.parseInt(startSplit[0]), Integer.parseInt(startSplit[1]), Integer.parseInt(startSplit[2]));
            String[] endSplit = endString.split(",");
            end = new Point3D(Integer.parseInt(endSplit[0]), Integer.parseInt(endSplit[1]), Integer.parseInt(endSplit[2]));
        }


        public void fall() {
            this.start.z -= 1;
            this.end.z -= 1;
        }

        public boolean StillInAir(List<Brick> bricks) {
            for (int otherIndex = 0; otherIndex < bricks.size(); otherIndex++) {
                Brick other = bricks.get(otherIndex);
                if (this == other) {
                    continue;
                }

                if (this.isAtop(other)) {
                    return false;
                }
            }
            return true;
        }

        private boolean isAtop(Brick other) {
            if (this.start.z != other.start.z + 1) return false;

            // TODO - API seems to indicate x/x, y/y but test passes for x/y, x/y
            Line2D thisBrick  = new Line2D.Float(start.x, start.y, end.x, end.y);
            Line2D otherBrick = new Line2D.Float(other.start.x, other.start.y, other.end.x, other.end.y);

            return thisBrick.intersectsLine(otherBrick);
        }
    }

    class Point3D {

        private final int x;
        private final int y;
        private int z;

        public Point3D(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
