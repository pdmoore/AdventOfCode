package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class day17Tests {

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
    @Disabled
    void part1_example_HighestProbe() {
        TargetArea t = new TargetArea(20, 30, -10, -5);

        int actual = findHighestProbe(t);

        assertEquals(45, actual);
    }

    private int findHighestProbe(TargetArea t) {

        //x from 0 to t.x2
        //y from t.y1 to abs(t.y2)


        return 0;
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
    }

    private class Probe {
        private final int initialX;
        private final int initialY;
        public int maximumY;
        private int currentX;
        private int currentY;
        private int velocityX;
        private int velocityY;
        private final TargetArea targetArea;

        public Probe(int x, int y, TargetArea t) {
            this.initialX = x;
            this.initialY = y;

            this.velocityX = x;
            this.velocityY = y;

            this.targetArea = t;

            this.currentX = 0;
            this.currentY = 0;
            this.maximumY = Integer.MIN_VALUE;
        }

        public boolean eventuallyHitsTargetArea() {
            int count = 0;
            currentX += velocityX;
            currentY += velocityY;

            while (!targetArea.contains(currentX, currentY) && (count <=1000)) {
                if (maximumY < currentY) maximumY = currentY;

                if (velocityX > 0) velocityX -= 1;
                else if (velocityX <0) velocityX += 1;
                velocityY -= 1;

                currentX += velocityX;
                currentY += velocityY;
                count += 1;
//                System.out.println(currentX + ", " + currentY);
            }

            return targetArea.contains(currentX, currentY);
        }

        public void launchProbe() {
            eventuallyHitsTargetArea();
        }
    }
}
