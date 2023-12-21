package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20 {
    public static final String BROADCASTER = "broadcaster";

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

    @Test
    void example1_pulses() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day20_part1_example1");

        ModuleConfiguration sut = new ModuleConfiguration(input);
        sut.pushButton();

        // not sure what to test, capture the string output?
    }

    private class ModuleConfiguration {
        private final List<Pulse> _unhandledPulses;
        Map<String, Module> _modules = new HashMap<>();
        StringBuilder _sb = new StringBuilder();

        public ModuleConfiguration(List<String> input) {
            _unhandledPulses = new ArrayList<>();

            for (String inputLine : input) {
                String[] split = inputLine.split(" -> ");
                if (split[0].startsWith(BROADCASTER)) {
                    Module m = new Module(BROADCASTER, ModuleType.broadcaster, split[1]);
                    _modules.put(BROADCASTER, m);
                } else if (split[0].charAt(0) == '%') {
                    String ID = split[0].substring(1).trim();
                    Module m = new Module(ID, ModuleType.flipflop, split[1]);
                    _modules.put(ID, m);
                } else if (split[0].charAt(0) == '&') {
                    String ID = split[0].substring(1).trim();
                    Module m = new Module(ID, ModuleType.conjunction, split[1]);
                    _modules.put(ID, m);
                } else {
                    throw new IllegalArgumentException("unknown module " + inputLine);
                }
            }
        }

        public List<String> getBroadcasterModules() {
            return _modules.get(BROADCASTER)._destinationModules;
        }

        public Map<String, Module> getModules() {
            return _modules;
        }

        public void pushButton() {
            Module broadcaster = _modules.get(BROADCASTER);

            Pulse button = new Pulse(broadcaster, PulseType.low);
            _unhandledPulses.add(button);

            while (!_unhandledPulses.isEmpty()) {
                Pulse p = _unhandledPulses.remove(0);
                for (String moduleID: p.module._destinationModules) {
                    Module destinationModule = _modules.get(moduleID);
                    PulseType newPulse = destinationModule.pulse(p.pulseType);
                    if (newPulse != null) _unhandledPulses.add(new Pulse(destinationModule, newPulse));
                    dumpPulse(p, destinationModule);
                }

            }
        }

        private void dumpPulse(Pulse p, Module m) {
            System.out.println(p.module.ID + " -"+p.pulseType+"-> "+ m.ID);
        }
    }

    enum ModuleType {broadcaster, flipflop, conjunction};

    enum PulseType {low, high}

    enum FlipFlopState {off, on};


    class Pulse {
        final Module module;
        final PulseType pulseType;

        public Pulse(Module m, PulseType pt) {
            this.module = m;
            this.pulseType = pt;
        }
    }


    class Module {

        private final String ID;
        private final ModuleType type;
        private final List<String> _destinationModules;
        private FlipFlopState state;


        public Module(String id, ModuleType type, String s) {
            this.ID = id;
            this.type = type;
            this.state = FlipFlopState.off;

            _destinationModules = new ArrayList<>();
            String[] modules = s.split(", ");
            for (String module : modules) {
                _destinationModules.add(module.trim());
            }
        }

        public PulseType pulse(PulseType pulseType) {
            // module type
            // pulse type
            // possibly empty
            if (type == ModuleType.flipflop) {
                if (pulseType == PulseType.high) {
                    return null;
                }
                if (state == FlipFlopState.off) {
                    state = FlipFlopState.on;
                    return PulseType.high;
                } else {
                    state = FlipFlopState.off;
                    return PulseType.low;
                }
            }

            // TODO bailed at this point
            // Conjunction modules need to track who the pulse comes from
            // and then do the new PulseType based on the most recent pulses from each input
            // Will be able to pass in the input module ID on the pulse and then build that
            // dynamically within the Conjunction module

            return null;
        }
    }
}
