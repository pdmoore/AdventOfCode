package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

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

        Set<String> moduleIDs = sut.getModules().keySet();
        assertEquals(5, moduleIDs.size());
    }

    private class ModuleConfiguration {
        Map<String, Module> _modules = new HashMap<>();

        public ModuleConfiguration(List<String> input) {
            for (String inputLine: input) {
                String[] split = inputLine.split(" -> ");
                if (split[0].startsWith("broadcaster")) {
                    Module m = new Module("broadcaster", ModuleType.broadcaster, split[1]);
                    _modules.put("broadcaster", m);
                } else if (split[0].charAt(0) == '%') {
                    String ID = split[0].substring(1).trim();
                    Module m = new Module(ID, ModuleType.flipflop, split[1]);
                    _modules.put(ID, m);
                } else if (split[0].charAt(0) == '&') {
                    String ID = split[0].substring(1).trim();
                    Module m = new Module(ID, ModuleType.conjunction, split[1]);
                    _modules.put(ID, m);
                } else {
                    throw new IllegalArgumentException("unknown module "  + inputLine);
                }
            }
        }

        public List<String> getBroadcasterModules() {
            return _modules.get("broadcaster")._destinationModules;
        }

        public Map<String, Module> getModules() {
            return _modules;
        }
    }

    enum ModuleType { broadcaster, flipflop, conjunction };


    class Module {

        private final String ID;
        private final ModuleType type;
        private final List<String> _destinationModules;

        public Module(String id, ModuleType type, String s) {
            this.ID = id;
            this.type = type;

            _destinationModules = new ArrayList<>();
            String[] modules = s.split(", ");
            for (String module: modules) {
                _destinationModules.add(module.trim());
            }
        }
    }
}
