package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day20Tests {

    @Test
    @Disabled
    void part1_example() {
        String imageEnhancementAlgorithm = "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##\n" +
                "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###\n" +
                ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#.\n" +
                ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#.....\n" +
                ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#..\n" +
                "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.....\n" +
                "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#";

        List<String> image = Arrays.asList(
                "#..#.\n",
                "#....\n",
                "##..#\n",
                "..#..\n",
                "..###" );

        ImageEnhancer sut = new ImageEnhancer(imageEnhancementAlgorithm, image);

        sut.enhance();
        sut.enhance();
        int actual = sut.countOfLitPixels();

        Assertions.assertEquals(35, actual);
    }

    @Test
    void part1_litPixelCount() {
        String imageEnhancementAlgorithm = "ignored";

        List<String> image = Arrays.asList(
                "#..#.",
                "#....",
                "##..#",
                "..#..",
                "..###" );

        ImageEnhancer sut = new ImageEnhancer(imageEnhancementAlgorithm, image);

        int actual = sut.countOfLitPixels();

        Assertions.assertEquals(10, actual);
    }

    private class ImageEnhancer {
        private final String imageEnhancementAlgorithm;
        private List<Point> litPixels;

        public ImageEnhancer(String imageEnhancementAlgorithm, List<String> image) {
            litPixels = new ArrayList();
            this.imageEnhancementAlgorithm = imageEnhancementAlgorithm;
            trackLitPixels(image);
        }

        private void trackLitPixels(List<String> input) {
            int x = 0;
            for (String line :
                    input) {
                for (int y = 0; y < line.length(); y++) {
                    if (line.charAt(y) == '#') {
                        Point p = new Point(x, y);
                        litPixels.add(p);
                    }
                }
            }

        }

        public void enhance() {

        }

        public int countOfLitPixels() {
            return litPixels.size();
        }
    }

    class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
