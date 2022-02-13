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

    /*
    - Figure distance between beacons (not scanner) since that value is the same regardless of orientation or offset
    - find closest beacon-to-beacon distances, or closest two? want to watch out for same-distancce but different beacons
    - then use the distance lists to start matching up with other scanner lists
      - then need to translate matching beacon to scanner 0 (rotation and offset), adding to some master list of unique beacon locations
     */


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


    @Getter
//    @Builder(access = AccessLevel.PRIVATE)
    static class ThreeDRegion {
        List<Scanner> scanners = new ArrayList<>();

        @Builder.Default
        private Set<Point3D> map = new HashSet<>();

        public ThreeDRegion(Scanner s, Point3D p) {
;
        }

        public static ThreeDRegion fromInput(String input, int overlappingCount) {
//            return ThreeDRegion.builder()
//                    .scanners(Arrays.stream(input.split("\n\n")).map(Scanner::fromInput).collect(Collectors.toList())))
//                    .overlappingCount(overlappingCount)
//                    .build();
            return null;
        }

        public ThreeDRegion(List<String> input) {
            Scanner scanner = null;
            for (String line :
                    input) {

                if (line.startsWith("--- scanner ")) {
                    int scannerID = parseInt(line.substring(12, line.length() - 4));
                    scanner = Scanner.builder()
                            .id(line.substring(12, line.length() - 4))
                            .points(new ArrayList<>())
                            .position(Point3D.builder().x(0).y(0).z(0).build())
                            .build();

                } else if (!line.isEmpty()) {
                    String[] coords = line.split(",");
                    Point3D p = Point3D.builder().x(Integer.parseInt(coords[0])).y(Integer.parseInt(coords[1])).z(Integer.parseInt(coords[2])).build();
                    scanner.add(p);
                } else {
                    scanners.add(scanner);
                }
            }
            scanners.add(scanner);
        }

        public int beaconCount() {
            return map.size();
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
            return map.stream().flatMap(mapPoint -> scanner.getPoints().stream().map(scannerPoint -> subtractPoint(mapPoint, scannerPoint)))
                    .collect(groupingBy(identity(), counting()))
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() >= 12)
                    .findFirst()
                    .map(Map.Entry::getKey);
        }

        private void placeScannerOnMap(Scanner scanner, int xOffset, int yOffset, int zOffset) {
            scanner.setPosition(Point3D.builder().x(xOffset).y(yOffset).z(zOffset).build());
            map.addAll(scanner.getPoints()
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

        public Point3D roll() {
            var oldY = y;

            y = z;
            z = -oldY;
            return this;
        }

        public Point3D turn() {
            var oldX = x;
            x = -y;
            y = oldX;
            return this;
        }

        public Point3D reverseTurn() {
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
            this.points.forEach(Point3D::roll);
            return this;
        }

        public Scanner turn() {
            this.points.forEach(Point3D::turn);
            return this;
        }

        public Scanner reverseTurn() {
            this.points.forEach(Point3D::reverseTurn);
            return this;
        }
    }
}
