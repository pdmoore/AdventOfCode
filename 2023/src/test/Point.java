package com.pdmoore.aoc;

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        Point other = (Point)obj;
        return x == other.x && y == other.y;
    }
}
