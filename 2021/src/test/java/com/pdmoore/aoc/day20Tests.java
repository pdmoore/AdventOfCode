package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        //TODO - left off here - read the description again and take a shot at this
        // - Looping stuff to handle both unlit and lit pixels
        // - converting the 9 character string to a digit
        // - looking that digit up in the image enhancement algo
        // - storing a new litPixel if the lookup is '#'
        public void enhance() {
            List<Point> nextImage = new ArrayList<>();
            //loop through lit pixels, checking neighbors are in litPixels or not
            // loop through the grid boundaries - square grid, so minValue, maxValue
            // need to update minValue and MaxVlue as I go
            // creating a new list along the way


                // If the following coord is in litPixels, append a "#, otherwise append a "."
                // x-1, y-1
                // x, y-1
                // x+1, y
                // x-1, y
                // x, y
                // x+1, y
                // x-1, y+1
                // x, y+1
                // x+1, y+1

                // convert that #.#. string to a number
                // get the value at that number index in the imageEnhancementAlgorithm
                // if that value is a "#" then create a new point at x,y


            litPixels = nextImage;
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
