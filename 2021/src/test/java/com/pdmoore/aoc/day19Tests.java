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
        List<Scanner> scanners = new ArrayList<>();

        @Builder.Default
        private Set<Point3D> beaconPositions = new HashSet<>();

        public ThreeDRegion(List<String> input) {
            Scanner scanner = null;
            for (String line :
                    input) {

                if (line.startsWith("--- scanner ")) {
//                    String scannerIdSubstring = line.substring(12, line.length() - 4);
                    scanner = Scanner.builder()
                            .id(line)
                            .points(new ArrayList<>())
                            .position(Point3D.builder().x(0).y(0).z(0).build())
                            .build();
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
            var placedScanners = new LinkedList<Scanner>();
            var startingScanner = scanners.get(0);
            placeScannerOnMap(startingScanner, 0, 0, 0);

            placedScanners.add(startingScanner);

            while (placedScanners.size() < scanners.size()) {
                for (Scanner candidateScanner : scanners) {
                    if (placedScanners.contains(candidateScanner)) {
                        continue;
                    }
                    candidateScanner.allOrientations()
                            .map(this::overlapsWithMap)
                            .flatMap(Optional::stream)
                            .findFirst()
                            .ifPresent(offset -> {
                        placeScannerOnMap(candidateScanner, offset.getX(), offset.getY(), offset.getZ());

                        placedScanners.add(candidateScanner);
                    });
                }
            }
        }

        private Optional<Point3D> overlapsWithMap(Scanner scanner) {
            return beaconPositions.stream().flatMap(mapPoint -> scanner.getPoints().stream().map(scannerPoint -> subtractPoint(mapPoint, scannerPoint)))
                    .collect(groupingBy(identity(), counting()))
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() >= 12)
                    .findFirst()
                    .map(Map.Entry::getKey);
        }

        private void placeScannerOnMap(Scanner scanner, int xOffset, int yOffset, int zOffset) {
            scanner.setPosition(Point3D.builder().x(xOffset).y(yOffset).z(zOffset).build());
            beaconPositions.addAll(scanner.getPoints()
                    .stream()
                    .map(point -> Point3D.builder().x(point.getX() + xOffset).y(point.getY() + yOffset).z(point.getZ() + zOffset)
                            .build()).collect(Collectors.toList()));
        }

        private Point3D subtractPoint(Point3D a, Point3D b) {
            return Point3D.builder().x(a.getX() - b.getX()).y(a.getY() - b.getY()).z(a.getZ() - b.getZ()).build();
        }
    }

    @Getter
    @Builder
    @ToString
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
    @Builder
    static class Scanner {
        private String id;
        private final List<Point3D> points;
        private Point3D position;

//        public Scanner() {
//            points = new ArrayList<>();
//        }

        public static Scanner fromInput(String input) {
            var lines = input.split("\n");

            return Scanner.builder()
                    .id(lines[0])
                    .points(Arrays.stream(lines).skip(1).map(Point3D::fromInput).collect(Collectors.toList()))
                    .position(Point3D.builder().x(0).y(0).z(0).build())
                    .build();
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
