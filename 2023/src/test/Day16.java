package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16 {

    @Test
    void part1_example() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day16_part1_example");
        int actual = countEnergizedTiles(input);
        assertEquals(46, actual);
    }

    @Test
    void part1_solution() {
        char[][] input = PuzzleInput.as2dCharArray("./data/day16");
        int actual = countEnergizedTiles(input);

        // 216 way too small
        assertEquals(7199, actual);
    }

    private int countEnergizedTiles(char[][] map) {
        Set<Point> energizedTiles = new HashSet<>();

        Beam startingBeam = new Beam(0, -1, Beam.directions.right);
        List<Beam> activeBeams = new ArrayList<>();
        activeBeams.add(startingBeam);

        Map<Point, Set<Beam.directions>> visited = new HashMap<>();

        while (!activeBeams.isEmpty()) {

            // remove active beam
            Beam activeBeam = activeBeams.remove(0);
//            System.out.println("processing " + activeBeam.toString() + " -- " + map[activeBeam.currentlocation.x][activeBeam.currentlocation.y]);
            // visit
            if (!visited.containsKey(activeBeam.currentlocation)) {
                Set<Beam.directions> d = new HashSet<>();
                d.add(activeBeam.heading);
                visited.put(activeBeam.currentlocation, d);
            } else {
                visited.get(activeBeam.currentlocation).add((activeBeam.heading));
            }

            // TODO answerr is much too low
            // Tracing the visited beams it seems like x is off by 1 and then splits up/down too early
            // maybe track the exact locations



            // process a beam
            energizedTiles.add(activeBeam.currentlocation);
            List<Beam> next = activeBeam.move(map);
            // add any splits as a result
            for (Beam b :
                    next) {

                if (!visited.containsKey(b.currentlocation)) {
                    activeBeams.add(b);
                } else {
                    Set<Beam.directions> directions = visited.get(b.currentlocation);
                    if (!directions.contains(b.heading)) {
                        activeBeams.add(b);
                    } else {
                        System.out.println(" just rejected " + b);
                    }
                }
            }
        }

        energizedTiles.remove(startingBeam.currentlocation);
        return energizedTiles.size();
    }

    static class Beam {
        @Override
        public int hashCode() {
            int hash = this.currentlocation.hashCode();
            hash = hash + heading.ordinal();
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            Beam other = (Beam)obj;
            return this.currentlocation == other.currentlocation &&
                    this.heading == other.heading;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("x: " + currentlocation.x);
            sb.append("  y: " + currentlocation.y);
            sb.append("  " + heading);
            return sb.toString();
        }

        enum directions {up, right, down, left}

        directions heading;
        Point currentlocation;

        public Beam(Point nextLocation, directions direction) {
            this.currentlocation = nextLocation;
            this.heading = direction;
        }

        public Beam(int x, int y, directions direction) {
            this(new Point(x, y), direction);
        }

        public List<Beam> move(char[][] map) {
            List<Beam> result = new ArrayList<>();

            Point nextLocation = new Point(-1, -1);
                switch (heading) {
                    case up -> {
                        nextLocation.x = this.currentlocation.x - 1;
                        nextLocation.y = this.currentlocation.y;
                    }
                    case down -> {
                        nextLocation.x = this.currentlocation.x + 1;
                        nextLocation.y = this.currentlocation.y;
                        if (nextLocation.x >= 9) {
                            int breakpoint = 0;
                        }
                    }
                    case left -> {
                        nextLocation.x = this.currentlocation.x;
                        nextLocation.y = this.currentlocation.y - 1;
                    }
                    case right -> {
                        nextLocation.x = this.currentlocation.x;
                        nextLocation.y = this.currentlocation.y + 1;
                        if (nextLocation.y >= 9) {
                            int breakpoint2 = 0;
                        }
                    }
                }


            if (nextLocation.y < 0 || nextLocation.x < 0) {
                return Collections.EMPTY_LIST;
            }

            if (nextLocation.x >= map.length || nextLocation.y >= map[0].length) {
                return Collections.EMPTY_LIST;
            }

            if (map[nextLocation.x][nextLocation.y] == '.') {
                result.add(new Beam(nextLocation, heading));
            } else if (map[nextLocation.x][nextLocation.y] == '|') {
                if (heading == directions.right || heading == directions.left) {
                    Beam upBeam = new Beam(nextLocation, directions.up);
                    Beam downBeam = new Beam(nextLocation, directions.down);
                    result.add(upBeam);
                    result.add(downBeam);
                } else {
                    result.add(new Beam(nextLocation, heading));
                }
            } else if (map[nextLocation.x][nextLocation.y] == '-') {
                if (heading == directions.up || heading == directions.down) {
                    Beam leftBeam = new Beam(nextLocation, directions.left);
                    Beam rightBeam = new Beam(nextLocation, directions.right);
                    result.add(leftBeam);
                    result.add(rightBeam);
                } else {
                    result.add(new Beam(nextLocation, heading));
                }
            } else if (map[nextLocation.x][nextLocation.y] == '/') {
                if (heading == directions.right) {
                    result.add(new Beam(nextLocation, directions.up));
                } else if (heading == directions.down) {
                    result.add(new Beam(nextLocation, directions.left));
                } else if (heading == directions.up) {
                    result.add(new Beam(nextLocation, directions.right));
                } else if (heading == directions.left) {
                    result.add(new Beam(nextLocation, directions.down));
                } else {
                    throw new IllegalArgumentException("unhandled heading at / " + heading);
                }

            } else if (map[nextLocation.x][nextLocation.y] == '\\') {
                if (heading == directions.right) {
                    result.add(new Beam(nextLocation, directions.down));
                } else if (heading == directions.up) {
                    result.add(new Beam(nextLocation, directions.left));
                } else if (heading == directions.left) {
                    result.add(new Beam(nextLocation, directions.up));
                } else if (heading == directions.down) {
                    result.add(new Beam(nextLocation, directions.right));
                } else {
                        throw new IllegalArgumentException("unhandled heading at \\ " + heading);
                }
            } else {
                throw new IllegalArgumentException("unhandled char " + map[nextLocation.x][nextLocation.y]);
            }

            return result;
        }
    }

}

