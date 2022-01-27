package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class day17Tests {

    @Test
    void part1_fallsWithinTarget() {
        int x = 7;
        int y = 2;

        TargetArea t = new TargetArea(20, 30, -10, -5);

        Probe p = new Probe(x, y, t);

        assertTrue(p.eventuallyHitsTargetArea());
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
        }

        public boolean eventuallyHitsTargetArea() {
            while (!targetArea.contains(currentX, currentY)) {
                if (velocityX > 0) velocityX -= 1;
                else if (velocityX <0) velocityX += 1;
                velocityY -= 1;

                currentX += velocityX;
                currentY += velocityY;
            }


            return true;
        }
    }
}
