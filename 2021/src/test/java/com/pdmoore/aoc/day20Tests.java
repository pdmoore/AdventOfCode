package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    // TODO - account for the infinite image size
    // boolean to decide whether pixels outside bounds are on or off
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
        //5983 too high
        assertEquals(99, actual);
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
        private List<Point> litPixels;
        int minValue;
        int maxValue;

        public ImageEnhancer(String imageEnhancementAlgorithm, List<String> image) {
            litPixels = new ArrayList();
            minValue = 10;
            this.imageEnhancementAlgorithm = imageEnhancementAlgorithm;
            trackLitPixels(image);
        }

        private void trackLitPixels(List<String> input) {
            int x = minValue;
            for (String line :
                    input) {
                for (int y = 0; y < line.length(); y++) {
                    if (line.charAt(y) == '#') {
                        litPixels.add(new Point(x, minValue + y));
                    }
                }
                x++;
            }
            maxValue = x;
        }

        public void enhance() {
//            printLitPixels("prior to enhance");
            List<Point> nextImage = new ArrayList<>();

            int newMinValue = maxValue;
            int newMaxValue = minValue;

            int expansion = 2;
            for (int x = minValue - expansion; x <= maxValue + expansion; x++) {
                for (int y = minValue - expansion; y <= maxValue + expansion; y++) {
                    String nextPixel = outputPixelFor(x, y);

                    if (nextPixel.equals("#")) {
                        if (x < minValue) newMinValue = x;
                        if (y < minValue) newMinValue = y;
                        if (x > maxValue) newMaxValue = x;
                        if (y > maxValue) newMaxValue = y;

                        nextImage.add(new Point(x, y));
                    }
                }
            }

            minValue = Math.min(minValue, newMinValue);
            maxValue = Math.max(maxValue, newMaxValue);
            litPixels = nextImage;
//            printLitPixels("after enhance");
        }

        private String outputPixelFor(int x, int y) {

            StringBuilder pixelsAround = new StringBuilder();
            pixelsAround.append(litPixels.contains(new Point(x - 1, y - 1)) ? "1" : "0");
            pixelsAround.append(litPixels.contains(new Point(x - 1, y)) ? "1" : "0");
            pixelsAround.append(litPixels.contains(new Point(x - 1, y + 1)) ? "1" : "0");

            pixelsAround.append(litPixels.contains(new Point(x, y - 1)) ? "1" : "0");
            pixelsAround.append(litPixels.contains(new Point(x, y)) ? "1" : "0");
            pixelsAround.append(litPixels.contains(new Point(x, y + 1)) ? "1" : "0");

            pixelsAround.append(litPixels.contains(new Point(x + 1, y - 1)) ? "1" : "0");
            pixelsAround.append(litPixels.contains(new Point(x + 1, y)) ? "1" : "0");
            pixelsAround.append(litPixels.contains(new Point(x + 1, y + 1)) ? "1" : "0");
            Integer lookup = Integer.parseInt(pixelsAround.toString(), 2);
            String replaceWith = String.valueOf(imageEnhancementAlgorithm.charAt(lookup));

            return replaceWith;
        }

        private void printLitPixels(String message) {
            System.out.println(message);
            for (int x = 5; x <= maxValue + 3; x++) {
                for (int y = 5; y <= maxValue + 3; y++) {
                    if (litPixels.contains(new Point(x, y))) System.out.print("#");
                    else System.out.print(".");
                }
                System.out.println("");
            }
            System.out.println("min/max - " + minValue + ", " + maxValue);
            System.out.println("");
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
