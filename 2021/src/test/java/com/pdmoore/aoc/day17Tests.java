package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class day17Tests {

    private int eventuallyWithin;

    @ParameterizedTest
    @ValueSource(strings = {"7,2", "6,3", "9,0", "6,9"})
    void part1_fallsWithinTarget_2ndExample(String startingVelocityPair) {
        String[] split = startingVelocityPair.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);

        TargetArea t = new TargetArea(20, 30, -10, -5);

        Probe p = new Probe(x, y, t);

        assertTrue(p.eventuallyHitsTargetArea());
    }

    @Test
    void part1_fallsOutsideTarget() {
        TargetArea t = new TargetArea(20, 30, -10, -5);

        Probe p = new Probe(17, -4, t);

        assertFalse(p.eventuallyHitsTargetArea());
    }

    @Test
    void part1_captureHighestPoint() {
        TargetArea t = new TargetArea(20, 30, -10, -5);

        Probe p = new Probe(6, 9, t);
        p.launchProbe();

        assertEquals(45, p.maximumY);
    }

    @Test
    void part1_example_HighestProbe() {
        TargetArea t = new TargetArea(20, 30, -10, -5);

        int actual = findHighestProbe(t);

        assertEquals(45, actual);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void part1_solution() {
        TargetArea t = new TargetArea(88, 125, -157, -103);

        int actual = findHighestProbe(t);

        assertEquals(12246, actual);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void part2_solution() {
        TargetArea t = new TargetArea(88, 125, -157, -103);

        findHighestProbe(t);

        assertEquals(3528, eventuallyWithin);
    }

    private int findHighestProbe(TargetArea t) {
        int highestHeight = Integer.MIN_VALUE;
        int lowx = 0;
        int highx = t.x2;
        int lowy = t.y1;
        int highy = Math.abs(t.y2) * 2;
        for (int x = lowx; x <= highx; x++) {
            for (int y = lowy; y <= highy; y++) {
                Probe p = new Probe(x, y, t);
                if (p.eventuallyHitsTargetArea()) {
                    eventuallyWithin++;
                    if (p.maximumY > highestHeight) highestHeight = p.maximumY;
                }
            }
        }

        return highestHeight;
    }


    private class TargetArea {
        private final int x1;
        private final int x2;
        private final int y1;
        private final int y2;

        public TargetArea(int x1, int x2, int y1, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        public boolean contains(int x, int y) {
            return ((x >= x1 && x <= x2) && (y >= y1 && y <= y2));
        }

        public boolean above(int x, int y) {
            return y >= this.y1;
        }
    }

    private class Probe {
        public int maximumY;
        private int currentX;
        private int currentY;
        private int velocityX;
        private int velocityY;
        private final TargetArea targetArea;

        public Probe(int x, int y, TargetArea t) {
            this.velocityX = x;
            this.velocityY = y;

            this.targetArea = t;

            this.currentX = 0;
            this.currentY = 0;
            this.maximumY = Integer.MIN_VALUE;
        }

        public boolean eventuallyHitsTargetArea() {
            do {
                currentX += velocityX;
                currentY += velocityY;

                if (targetArea.contains(currentX, currentY)) {
                    return true;
                }

                if (maximumY < currentY) maximumY = currentY;

                if (velocityX > 0) velocityX -= 1;
                else if (velocityX < 0) velocityX += 1;
                velocityY -= 1;
            } while (targetArea.above(currentX, currentY));

            return false;
        }

        public void launchProbe() {
            eventuallyHitsTargetArea();
        }
    }
}
