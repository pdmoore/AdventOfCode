package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class day19Tests {

    /*
    The beacons and scanners float motionless in the water; they're designed to maintain the same position for long periods of time.
    Each scanner is capable of detecting all beacons in a large cube centered on the scanner;
    beacons that are at most 1000 units away from the scanner in each of the three axes (x, y, and z) have their precise position determined
    relative to the scanner.
    However, scanners cannot detect other scanners.
    The submarine has automatically summarized the relative positions of beacons detected by each scanner (your puzzle input).

Unfortunately, while each scanner can report the positions of all detected beacons relative to itself, the scanners do not know their own position.
You'll need to determine the positions of the beacons and scanners yourself.

The scanners and beacons map a single contiguous 3d region.
This region can be reconstructed by finding pairs of scanners that have overlapping detection regions such that there are at
least 12 beacons that both scanners detect within the overlap.
By establishing 12 common beacons, you can precisely determine where the scanners are relative to each other,
allowing you to reconstruct the beacon map one scanner at a time.

Unfortunately, there's a second problem: the scanners also don't know their rotation or facing direction.
Due to magnetic alignment, each scanner is rotated some integer number of 90-degree turns around all of the x, y, and z axes.
That is, one scanner might call a direction positive x, while another scanner might call that direction negative y.
Or, two scanners might agree on which direction is positive x, but one scanner might be upside-down from the perspective of the
other scanner. In total, each scanner could be in any of 24 different orientations:
facing positive or negative x, y, or z, and considering any of four directions "up" from that facing.

Assemble the full map of beacons. How many beacons are there?
     */



    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day19_part1_example");

        ThreeDRegion sut = new ThreeDRegion(input);

        assertEquals(79, sut.beaconCount());
    }

    
    private class ThreeDRegion {
        public ThreeDRegion(List<String> input) {
            // Read in input line by line, assigning them to Scanner list and beacon coordinates relative to scanner

            List<Scanner> scanners = new ArrayList<>();
            Scanner scanner = null;
            for (String line :
                    input) {

                if (line.startsWith("--- scanner ")) {
                    int scannerID = Integer.parseInt(line.substring(12, line.length() - 4));
                    scanner = new Scanner(scannerID);

                } else if (!line.isEmpty()) {
                    String[] coords = line.split(",");
                    Point3D p = new Point3D(coords[0], coords[1], coords[2]);
                    scanner.add(p);
                } else {
                    scanners.add(scanner);
                }
            }
            
        }

        public int beaconCount() {
            return 0;
        }
    }

    private class Point3D {
        final int x;
        final int y;
        final int z;

        public Point3D(String x, String y, String z) {
            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
            this.z = Integer.parseInt(z);
        }
    }

    private class Scanner {
        private final int ID;
        private final ArrayList<Point3D> points;

        public Scanner(int scannerID) {
            this.ID = scannerID;
            points = new ArrayList<>();
        }

        public void add(Point3D p) {
            points.add(p);
        }
    }
}
