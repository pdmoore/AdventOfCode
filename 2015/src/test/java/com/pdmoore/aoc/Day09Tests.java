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

        assertEquals(605, sut.shortestDistance());
    }

    @Test
    void part2_example() {
        Day09 sut = new Day09(exampleInput);

        assertEquals(982, sut.longestDistance());
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09");
        Day09 sut = new Day09(input);

        assertEquals(251, sut.shortestDistance());
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day09");
        Day09 sut = new Day09(input);

        assertEquals(898, sut.longestDistance());
    }

    private class Day09 {
        public List<String> cities;
        public Map<String, Integer> distanceMap;
        private int shortestDistance;
        private int longestDistance;

        public Day09(List<String> input) {
            cities = collectCityNamesFrom(input);
            distanceMap = createDistanceMapFrom(input);

            findShortAndLongDistancesThatVisitAllCities(cities);
        }

        private void findShortAndLongDistancesThatVisitAllCities(List cities) {
            shortestDistance = Integer.MAX_VALUE;
            longestDistance  = 0;
            List<String> unvisitedCities = new ArrayList(cities);
            for (String currentCity :
                    unvisitedCities) {
                recurseShortest(currentCity, 0, unvisitedCities);
            }
        }

        private void recurseShortest(String currentCity, int currentDistance, List unvisitedCities) {
            List<String> connectingCities = new ArrayList<>(unvisitedCities);
            connectingCities.remove(currentCity);

            if (connectingCities.isEmpty()) {
                if (currentDistance < shortestDistance) {
                    shortestDistance = currentDistance;
                    return;
                } else if (currentDistance > longestDistance) {
                    longestDistance = currentDistance;
                    return;
                }
            }

            for (String connection :
                    connectingCities) {
                recurseShortest(connection, currentDistance + distanceMap.get(createKey(currentCity, connection)), connectingCities);
            }
        }

        private Map<String, Integer> createDistanceMapFrom(List<String> input) {
            // input line is of form
            // cityName to CityName = mileage
            Map<String, Integer> map = new HashMap();
            for (String inputLine :
                    input) {

                String[] tokens = inputLine.split(" ");
                String cityA = tokens[0];
                String cityB = tokens[2];
                int distance = Integer.parseInt(tokens[4]);

                map.put(createKey(cityA, cityB), distance);
                map.put(createKey(cityB, cityA), distance);
            }

            return map;
        }

        private String createKey(String cityA, String cityB) {
            return cityA + "-" + cityB;
        }

        private List collectCityNamesFrom(List<String> input) {
            // input line is of form
            // cityName to CityName = mileage
            List uniqueCityNames = new ArrayList();
            for (String inputLine :
                    input) {
                String[] tokens = inputLine.split(" ");
                if (!uniqueCityNames.contains(tokens[0])) uniqueCityNames.add(tokens[0]);
                if (!uniqueCityNames.contains(tokens[2])) uniqueCityNames.add(tokens[2]);
            }

            return uniqueCityNames;
        }

        public int shortestDistance() {
            return shortestDistance;
        }

        public int longestDistance() {
            return longestDistance;
        }
    }
}
