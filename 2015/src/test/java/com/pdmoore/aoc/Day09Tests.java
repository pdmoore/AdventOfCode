package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Tests {

    List<String> exampleInput =
            Arrays.asList("London to Dublin = 464",
                    "London to Belfast = 518",
                    "Dublin to Belfast = 141");

    @Test
    void CityList_FromInput() {
        Day09 sut = new Day09(exampleInput);

        assertEquals(3, sut.cities.size());
        assertEquals("London", sut.cities.get(0));
        assertEquals("Dublin", sut.cities.get(1));
        assertEquals("Belfast", sut.cities.get(2));
    }

    @Test
    void DistanceMap_FromInput() {
        Day09 sut = new Day09(exampleInput);

        assertEquals(6, sut.distanceMap.size());
    }

    @Test
    void part1_example() {
        Day09 sut = new Day09(exampleInput);

        assertEquals(605, sut.shortestDistance);
    }

    @Test
    void part2_example() {
        Day09 sut = new Day09(exampleInput);

        assertEquals(982, sut.longestDistance);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09");
        Day09 sut = new Day09(input);

        assertEquals(251, sut.shortestDistance);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09");
        Day09 sut = new Day09(input);

        assertEquals(898, sut.longestDistance);
    }

    private class Day09 {
        public List<String> cities;
        public Map<String, Integer> distanceMap;
        public int shortestDistance;
        public int longestDistance;

        public Day09(List<String> input) {
            cities = new ArrayList<>();
            distanceMap = new HashMap<>();
            populateCityAndDistanceInfo(input, cities, distanceMap);

            findShortAndLongDistancesThatVisitAllCities(cities);
        }

        private void findShortAndLongDistancesThatVisitAllCities(List cities) {
            shortestDistance = Integer.MAX_VALUE;
            longestDistance  = 0;
            List<String> unvisitedCities = new ArrayList(cities);
            for (String currentCity :
                    unvisitedCities) {
                recurse(currentCity, 0, unvisitedCities);
            }
        }

        private void recurse(String currentCity, int currentDistance, List unvisitedCities) {
            List<String> connectingCities = new ArrayList<>(unvisitedCities);
            connectingCities.remove(currentCity);

            if (connectingCities.isEmpty()) {
                shortestDistance = Math.min(shortestDistance, currentDistance);
                longestDistance  = Math.max(longestDistance, currentDistance);
                return;
            }

            for (String connection :
                    connectingCities) {
                recurse(connection, currentDistance + distanceMap.get(createKey(currentCity, connection)), connectingCities);
            }
        }

        private String createKey(String cityA, String cityB) {
            return cityA + "-" + cityB;
        }

        private void populateCityAndDistanceInfo(List<String> input, List<String> cities, Map<String, Integer> distanceMap) {
            for (String inputLine :
                    input) {
                String[] tokens = inputLine.split(" ");

                String cityA = tokens[0];
                String cityB = tokens[2];

                if (!cities.contains(cityA)) cities.add(cityA);
                if (!cities.contains(cityB)) cities.add(cityB);

                int distance = Integer.parseInt(tokens[4]);

                distanceMap.put(createKey(cityA, cityB), distance);
                distanceMap.put(createKey(cityB, cityA), distance);
            }
        }
    }
}
