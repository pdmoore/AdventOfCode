package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class day20Tests {

    @Test
    void part1_example() {
        String imageEnhancementAlgorithm = "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##\n" +
                "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###\n" +
                ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#.\n" +
                ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#.....\n" +
                ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#..\n" +
                "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.....\n" +
                "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#";

        String image = "#..#.\n" +
                "#....\n" +
                "##..#\n" +
                "..#..\n" +
                "..###";

        ImageEnhancer sut = new ImageEnhancer(imageEnhancementAlgorithm, image);

        sut.enhance();
        sut.enhance();
        int actual = sut.countOfLitPixels();

        Assertions.assertEquals(35, actual);


    }

    private class ImageEnhancer {
        public ImageEnhancer(String imageEnhancementAlgorithm, String image) {
        }

        public void enhance() {

        }

        public int countOfLitPixels() {
            return 0;
        }
    }
}
