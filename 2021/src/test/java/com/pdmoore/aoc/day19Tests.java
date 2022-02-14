package com.pdmoore.aoc;

import lombok.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class day19Tests {

    @Test
    void part1_example_3DRegion_ScannerCount() {
        List<String> input = PuzzleInput.asStringListFrom("data/day19_part1_example");

        ThreeDRegion sut = new ThreeDRegion(input);

        assertEquals(5, sut.scanners.size());
    }

    @Test
    void part1_example_beaconCount() {
        List<String> input = PuzzleInput.asStringListFrom("data/day19_part1_example");

        ThreeDRegion sut = new ThreeDRegion(input);
        sut.placeBeaconsOnOneMap();

        assertEquals(79, sut.beaconCount());
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day19");

        ThreeDRegion sut = new ThreeDRegion(input);
        sut.placeBeaconsOnOneMap();

        assertEquals(457, sut.beaconCount());
    }

    static class ThreeDRegion {
        public static final int NUM_OVERLAPPING_BEACONS = 12;

        List<Scanner> scanners = new ArrayList<>();
        private Set<Point3D> beaconPositions = new HashSet<>();

        public ThreeDRegion(List<String> input) {
            Scanner scanner = null;
            for (String line :
                    input) {

                if (line.startsWith("--- scanner ")) {
                    String scannerId = line.substring(12, line.length() - 4);
                    scanner = new Scanner(scannerId);
                } else if (!line.isEmpty()) {
                    String[] coords = line.split(",");
                    scanner.add(new Point3D(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
                } else {
                    scanners.add(scanner);
                }
            }
            scanners.add(scanner);
        }

        public int beaconCount() {
            return beaconPositions.size();
        }

        public void placeBeaconsOnOneMap() {
            placeScannerOnMap(scanners.get(0), scanners.get(0).position);

            List<Scanner> unplacedScanners = scanners.stream()
                    .skip(1)
                    .collect(Collectors.toList());

            while (!unplacedScanners.isEmpty()) {
                for (Scanner candidateScanner : scanners) {
                    candidateScanner.allOrientations()
                            .map(this::overlapsWithMap)
                            .flatMap(Optional::stream)
                            .findFirst()
                            .ifPresent(offset -> {
                        placeScannerOnMap(candidateScanner, offset);

                        unplacedScanners.remove(candidateScanner);
                    });
                }
            }
        }

        private Optional<Point3D> overlapsWithMap(Scanner scanner) {
            return beaconPositions.stream()
                    .flatMap(mapPoint -> scanner.getBeaconLocations().stream().map(scannerPoint -> mapPoint.subtract(scannerPoint)))
                    .collect(groupingBy(identity(), counting()))
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() >= NUM_OVERLAPPING_BEACONS)
                    .findFirst()
                    .map(Map.Entry::getKey);
        }

        private void placeScannerOnMap(Scanner scanner, Point3D offset) {
            scanner.setPosition(offset);
            beaconPositions.addAll(scanner.getBeaconLocations()
                    .stream()
                    .map(point -> point.add(offset))
                    .collect(Collectors.toList()));
        }
    }

    @Getter
    @EqualsAndHashCode
    static class Point3D {
        private int x;
        private int y;
        private int z;

        public Point3D(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point3D() {
            this(0, 0, 0);
        }

        public Point3D subtract(Point3D other) {
            return new Point3D(x - other.getX(), y - other.getY(), z - other.getZ());
        }

        public Point3D add(Point3D other) {
            return new Point3D(x + other.getX(), y + other.getY(), z + other.getZ());
        }

        public Point3D rotateAroundX() {
            var previousY = y;
            y = z;
            z = -previousY;
            return this;
        }

        public Point3D rotateAroundZ() {
            var previousX = x;
            x = -y;
            y = previousX;
            return this;
        }

        public Point3D reverseRotateAroundZ() {
            var previousX = x;
            x = y;
            y = -previousX;
            return this;
        }
    }

    @Data
    static class Scanner {
        private String id;
        private final List<Point3D> beaconLocations;
        private Point3D position;

        public Scanner(String id) {
            this.id = id;
            beaconLocations = new ArrayList<>();
            position = new Point3D();
        }

        public void add(Point3D p) {
            beaconLocations.add(p);
        }

        public List<Point3D> getBeaconLocations() {
            return beaconLocations;
        }

        public Stream<Scanner> allOrientations() {
            return IntStream.range(0, 6)
                    .boxed()
                    .flatMap(rollIndex -> Stream.concat(Stream.of(this.roll()), IntStream.range(0, 3).boxed().map(turnIndex -> rollIndex % 2 == 0 ? this.turn() : this.reverseTurn())));
        }

        public Scanner roll() {
            this.beaconLocations.forEach(Point3D::rotateAroundX);
            return this;
        }

        public Scanner turn() {
            this.beaconLocations.forEach(Point3D::rotateAroundZ);
            return this;
        }

        public Scanner reverseTurn() {
            this.beaconLocations.forEach(Point3D::reverseRotateAroundZ);
            return this;
        }
    }
}
