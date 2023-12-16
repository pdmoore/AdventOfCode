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

        assertEquals(7199, actual);
    }

    private int countEnergizedTiles(char[][] map) {
        Set<Point> energizedTiles = new HashSet<>();

        Beam startingBeam = new Beam(0, -1, Beam.directions.right);
        List<Beam> activeBeams = new ArrayList<>();
        activeBeams.add(startingBeam);

        Map<Point, Set<Beam.directions>> visited = new HashMap<>();

        while (!activeBeams.isEmpty()) {

            Beam activeBeam = activeBeams.remove(0);
            if (!visited.containsKey(activeBeam.currentLocation)) {
                Set<Beam.directions> d = new HashSet<>();
                d.add(activeBeam.heading);
                visited.put(activeBeam.currentLocation, d);
            } else {
                visited.get(activeBeam.currentLocation).add((activeBeam.heading));
            }

            energizedTiles.add(activeBeam.currentLocation);
            List<Beam> next = activeBeam.move(map);
            for (Beam b :
                    next) {

                if (!visited.containsKey(b.currentLocation)) {
                    activeBeams.add(b);
                } else {
                    Set<Beam.directions> directions = visited.get(b.currentLocation);
                    if (!directions.contains(b.heading)) {
                        activeBeams.add(b);
                    }
                }
            }
        }

        energizedTiles.remove(startingBeam.currentLocation);
        return energizedTiles.size();
    }

    static class Beam {
        @Override
        public int hashCode() {
            int hash = this.currentLocation.hashCode();
            hash = hash + heading.ordinal();
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Beam)) return false;
            Beam other = (Beam)obj;
            return this.currentLocation == other.currentLocation &&
                    this.heading == other.heading;
        }

        @Override
        public String toString() {
            return "x: " + currentLocation.x +
                    "  y: " + currentLocation.y +
                    "  " + heading;
        }

        enum directions {up, right, down, left}

        directions heading;
        Point currentLocation;

        public Beam(Point nextLocation, directions direction) {
            this.currentLocation = nextLocation;
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
                        nextLocation.x = this.currentLocation.x - 1;
                        nextLocation.y = this.currentLocation.y;
                    }
                    case down -> {
                        nextLocation.x = this.currentLocation.x + 1;
                        nextLocation.y = this.currentLocation.y;
                    }
                    case left -> {
                        nextLocation.x = this.currentLocation.x;
                        nextLocation.y = this.currentLocation.y - 1;
                    }
                    case right -> {
                        nextLocation.x = this.currentLocation.x;
                        nextLocation.y = this.currentLocation.y + 1;
                    }
                }


            if (nextLocation.y < 0 || nextLocation.x < 0) {
                return List.of();
            }

            if (nextLocation.x >= map.length || nextLocation.y >= map[0].length) {
                return List.of();
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

