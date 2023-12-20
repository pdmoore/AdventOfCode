package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20 {

    // read input and create data structures
    // simulate pressing button and processing pulses
    // loop 1000 times
    // running count of low and high pulses across 1000 button presses
    // (don't look for cycles, assume that's part 2)

    @Test
    void module_configuration_example1() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day20_part1_example1");

        ModuleConfiguration sut = new ModuleConfiguration(input);

        List<String> broadcasterModules = Arrays.asList("a", "b", "c");
        assertEquals(broadcasterModules, sut.getBroadcasterModules());
    }

    private class ModuleConfiguration {
        List<String> _broadcasterModules = new ArrayList<>();

        public ModuleConfiguration(List<String> input) {
            for (String inputLine: input) {
                String[] split = inputLine.split(" -> ");
                if (split[0].startsWith("broadcaster")) {
                    String[] modules = split[1].split(", ");
                    for (String module: modules) {
                        _broadcasterModules.add(module.trim());
                    }
                } else if (split[0].charAt(0) == '%') {
                    // handle FLipFLop
                } else if (split[0].charAt(0) == '&') {
                    // handle Conjunction
                } else {
                    throw new IllegalArgumentException("unknown module "  + inputLine);
                }
                /*
                broadcaster -> a, b, c
%a -> b
%b -> c
%c -> inv
&inv -> a
                 */
            }
        }

        public List<String> getBroadcasterModules() {
            return _broadcasterModules;
        }
    }
}
