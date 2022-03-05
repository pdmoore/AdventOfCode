package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day20Tests {

    @Test
    void part1_example_from_file() {
        List<String> input = PuzzleInput.asStringListFrom("data/day20_example");
        String imageEnhancementAlgorithm = input.get(0);
        List<String> image = new ArrayList<>();
        for (int i = 2; i < input.size(); i++) {
            image.add(input.get(i));
        }

        ImageEnhancer sut = new ImageEnhancer(imageEnhancementAlgorithm, image);
        sut.enhance(2);

        int actual = sut.countOfLitPixels();
        assertEquals(35, actual);
    }

    @Test
    void part2_example_from_file() {
        List<String> input = PuzzleInput.asStringListFrom("data/day20_example");
        String imageEnhancementAlgorithm = input.get(0);
        List<String> image = new ArrayList<>();
        for (int i = 2; i < input.size(); i++) {
            image.add(input.get(i));
        }

        ImageEnhancer sut = new ImageEnhancer(imageEnhancementAlgorithm, image);
        sut.enhance(50);

        int actual = sut.countOfLitPixels();
        assertEquals(3351, actual);
    }

    @Test
    void part1_from_file() {
        List<String> input = PuzzleInput.asStringListFrom("data/day20");
        String imageEnhancementAlgorithm = input.get(0);
        List<String> image = new ArrayList<>();
        for (int i = 2; i < input.size(); i++) {
            image.add(input.get(i));
        }

        ImageEnhancer sut = new ImageEnhancer(imageEnhancementAlgorithm, image);
        sut.enhance(2);

        int actual = sut.countOfLitPixels();
        assertEquals(5301, actual);
    }

    @Test
    @Timeout(2)
    void part2_from_file() {
        List<String> input = PuzzleInput.asStringListFrom("data/day20");
        String imageEnhancementAlgorithm = input.get(0);
        List<String> image = new ArrayList<>();
        for (int i = 2; i < input.size(); i++) {
            image.add(input.get(i));
        }

        ImageEnhancer sut = new ImageEnhancer(imageEnhancementAlgorithm, image);
        sut.enhance(50);

        int actual = sut.countOfLitPixels();
        assertEquals(19492, actual);
    }

    @Test
    @Timeout(1)
    void part2_10Enhancements_ToImprovePerformance() {
        List<String> input = PuzzleInput.asStringListFrom("data/day20");
        String imageEnhancementAlgorithm = input.get(0);
        List<String> image = new ArrayList<>();
        for (int i = 2; i < input.size(); i++) {
            image.add(input.get(i));
        }

        ImageEnhancer sut = new ImageEnhancer(imageEnhancementAlgorithm, image);
        sut.enhance(10);

        int actual = sut.countOfLitPixels();
        assertEquals(7126, actual);
    }

    @Test
    void part1_litPixelCount() {
        String imageEnhancementAlgorithm = "ignored";

        List<String> image = Arrays.asList(
                "#..#.",
                "#....",
                "##..#",
                "..#..",
                "..###");

        ImageEnhancer sut = new ImageEnhancer(imageEnhancementAlgorithm, image);

        int actual = sut.countOfLitPixels();

        assertEquals(10, actual);
    }

    @Test
    void Integer_ParseBinry() {
        String input = "000100010";
        int actual = Integer.parseInt(input, 2);
        assertEquals(34, actual);

        input = "111111111";
        actual = Integer.parseInt(input, 2);
        assertEquals(511, actual);
    }

    private class ImageEnhancer {
        private final String imageEnhancementAlgorithm;
        private Collection<Point> litPixels;
        private int enhancementCount;

        public ImageEnhancer(String imageEnhancementAlgorithm, List<String> image) {
            litPixels = new HashSet<>();
            enhancementCount = 0;
            this.imageEnhancementAlgorithm = imageEnhancementAlgorithm;
            trackLitPixels(image);
        }

        private void trackLitPixels(List<String> input) {
            int x = 0;
            for (String line :
                    input) {
                for (int y = 0; y < line.length(); y++) {
                    if (line.charAt(y) == '#') {
                        litPixels.add(new Point(x, y));
                    }
                }
                x++;
            }
        }

        private Point findUpperLeftPoint(Collection<Point> image) {
            int x = image.stream().min(Comparator.comparingInt(p -> p.x)).get().x;
            int y = image.stream().min(Comparator.comparingInt(p -> p.y)).get().y;
            return new Point(x, y);
        }

        private Point findLowerRightPoint(Collection<Point> image) {
            int x = image.stream().max(Comparator.comparingInt(p -> p.x)).get().x;
            int y = image.stream().max(Comparator.comparingInt(p -> p.y)).get().y;
            return new Point(x, y);
        }

        public void enhance() {
            Collection<Point> nextImage = new HashSet<>(litPixels.size());

            Point upperLeft = findUpperLeftPoint(litPixels);
            Point lowerRight = findLowerRightPoint(litPixels);

            int expansion = 1;
            for (int x = upperLeft.x - expansion; x <= lowerRight.x + expansion; x++) {
                for (int y = upperLeft.y - expansion; y <= lowerRight.y + expansion; y++) {
                    Character nextPixel = outputPixelFor(x, y, upperLeft, lowerRight);
                    if (nextPixel.equals('#')) {
                        nextImage.add(new Point(x, y));
                    }
                }
            }

            litPixels = nextImage;
            enhancementCount++;
        }

        private Character outputPixelFor(int midX, int midY, Point upperLeft, Point lowerRight) {
            StringBuilder pixelsAround = new StringBuilder();

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {

                    int checkX = midX + x;
                    int checkY = midY + y;

                    if (imageEnhancementAlgorithm.charAt(0) == '#' && enhancementCount % 2 != 0) {
                        if (checkY >= upperLeft.y && checkY <= lowerRight.y &&
                                checkX >= upperLeft.x && checkX <= lowerRight.x) {
                            pixelsAround.append(litPixels.contains(new Point(midX + x, midY + y)) ? "1" : "0");
                        } else {
                            pixelsAround.append("1");
                        }
                    } else {
                        pixelsAround.append(litPixels.contains(new Point(midX + x, midY + y)) ? "1" : "0");
                    }
                }
            }

            Integer imageEnhacementIndex = Integer.parseInt(pixelsAround.toString(), 2);

            return imageEnhancementAlgorithm.charAt(imageEnhacementIndex);
        }

        public int countOfLitPixels() {
            return litPixels.size();
        }

        public void enhance(int enhancementCount) {
            for (int i = 0; i < enhancementCount; i++) {
                enhance();
            }
        }
    }

    class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
