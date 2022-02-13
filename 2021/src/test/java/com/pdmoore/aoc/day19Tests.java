package com.pdmoore.aoc;

import lombok.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.Collections.copy;
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
                    Point3D p = Point3D.builder()
                            .x(Integer.parseInt(coords[0]))
                            .y(Integer.parseInt(coords[1]))
                            .z(Integer.parseInt(coords[2])).build();
                    scanner.add(p);
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
                    .flatMap(mapPoint -> scanner.getPoints().stream().map(scannerPoint -> mapPoint.subtractPoint(scannerPoint)))
                    .collect(groupingBy(identity(), counting()))
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() >= NUM_OVERLAPPING_BEACONS)
                    .findFirst()
                    .map(Map.Entry::getKey);
        }

        private void placeScannerOnMap(Scanner scanner, Point3D offset) {
            scanner.setPosition(offset);
            beaconPositions.addAll(scanner.getPoints()
                    .stream()
                    .map(point -> point.addPoint(offset))
                    .collect(Collectors.toList()));
        }
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    static class Point3D {
        private int x;
        private int y;
        private int z;

        public static Point3D fromInput(String input) {
            var inputs = input.split(",");
            return Point3D.builder()
                    .x(parseInt(inputs[0]))
                    .y(parseInt(inputs[1]))
                    .z(parseInt(inputs[2]))
                    .build();
        }
//        public Point3D(String x, String y, String z) {
//            this.x = parseInt(x);
//            this.y = parseInt(y);
//            this.z = parseInt(z);
//        }

        public Point3D subtractPoint(Point3D other) {
            return Point3D.builder()
                    .x(x - other.getX())
                    .y(y - other.getY())
                    .z(z - other.getZ()).build();
        }

        public Point3D addPoint(Point3D other) {
            return Point3D.builder()
                    .x(x + other.getX())
                    .y(y + other.getY())
                    .z(z + other.getZ()).build();
        }

        public Point3D rotateAroundX() {
            var oldY = y;

            y = z;
            z = -oldY;
            return this;
        }

        public Point3D rotateAroundZ() {
            var oldX = x;
            x = -y;
            y = oldX;
            return this;
        }

        public Point3D reverseRotateAroundZ() {
            var oldX = x;
            x = y;
            y = -oldX;
            return this;
        }
    }

    @Data
    static class Scanner {
        private String id;
        private final List<Point3D> points;
        private Point3D position;

        public Scanner(String id) {
            this.id = id;
            points = new ArrayList<>();
            position = Point3D.builder().x(0).y(0).z(0).build();
        }

        public void add(Point3D p) {
            points.add(p);
        }

        public List<Point3D> getPoints() {
            return points;
        }

        public Stream<Scanner> allOrientations() {
            return IntStream.range(0, 6)
                    .boxed()
                    .flatMap(rollIndex -> Stream.concat(Stream.of(this.roll()), IntStream.range(0, 3).boxed().map(turnIndex -> rollIndex % 2 == 0 ? this.turn() : this.reverseTurn())));
        }

        public Scanner roll() {
            this.points.forEach(Point3D::rotateAroundX);
            return this;
        }

        public Scanner turn() {
            this.points.forEach(Point3D::rotateAroundZ);
            return this;
        }

        public Scanner reverseTurn() {
            this.points.forEach(Point3D::reverseRotateAroundZ);
            return this;
        }
    }
}
