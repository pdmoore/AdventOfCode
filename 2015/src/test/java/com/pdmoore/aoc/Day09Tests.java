package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
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

    private class Day09 {
        public List<String> cities;
        public Map<String, Integer> distanceMap;
        private int shortestDistance;

        public Day09(List<String> input) {
            cities = collectCityNamesFrom(input);
            distanceMap = createDistanceMapFrom(input);

            findShortestDistance(cities);
        }

        private void findShortestDistance(List cities) {
            shortestDistance = Integer.MAX_VALUE;
            List<String> unvisitedCities = new ArrayList(cities);
            // TODO not tracking order of visit or which cities comprise path - should I?

            for (String currentCity :
                    unvisitedCities) {
                recurse(currentCity, 0, unvisitedCities);
            }
        }

        private void recurse(String currentCity, int currentDistance, List unvisitedCities) {
            List<String> connectingCities = new ArrayList<>(unvisitedCities);
            connectingCities.remove(currentCity);

            if (connectingCities.isEmpty()) {
                if (currentDistance < shortestDistance) {
                    shortestDistance = currentDistance;
                    return;
                }
            }

            for (String connection :
                    connectingCities) {
                currentDistance += distanceMap.get(currentCity + "-" + connection);
                recurse(connection, currentDistance, connectingCities);
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

                map.put(cityA + "-" + cityB, distance);
                map.put(cityB + "-" + cityA, distance);
            }

            return map;
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
    }
}
