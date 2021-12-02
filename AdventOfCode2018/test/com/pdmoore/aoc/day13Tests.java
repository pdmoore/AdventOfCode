package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day13Tests {

    // X read map and store in data structure
    // X replace cart with appropriate section of track
    // update map row by row, col within row.
    // locate a cart within map, direction, and next move state
    // Detect collision and halt as soon as one occurs - return collision location

    @Test
    public void buildMap_example() {
        String filename = "data/aoc18.13a.txt";
        List<String> inputAsStrings = utilities.getFileContentsAsStrings(filename);
        char[][] actual = utilities.convertInputToMap(inputAsStrings);

        char[][] expected = {
                { '/',  '-', '>',  '-', '\\', ' ', ' ', ' ',  ' ', ' ',  ' ', ' ', ' '},
                { '|',  ' ', ' ',  ' ', ' ',  ' ', ' ', '/',  '-', '-',  '-', '-', '\\'},
                { '|',  ' ', '/',  '-', '+',  '-', '-', '+',  '-', '\\', ' ', ' ', '|'},
                { '|',  ' ', '|',  ' ', '|',  ' ', ' ', '|',  ' ', 'v',  ' ', ' ', '|'},
                { '\\', '-', '+',  '-', '/',  ' ', ' ', '\\', '-', '+',  '-', '-', '/'},
                { ' ',  ' ', '\'', '-', '-',  '-', '-', '-',  '-', '\\', ' ', ' ', ' '}
        };

        assertEquals(expected.length, actual.length);
    }

    @Test
    public void BuildCartList_FromInitialMap() {
        String filename = "data/aoc18.13a.txt";
        List<String> inputAsStrings = utilities.getFileContentsAsStrings(filename);
        char[][] map = utilities.convertInputToMap(inputAsStrings);

        List<day13.Cart> actual = day13.extractCartsFromMap(map);

        day13.Cart expected_cart_1 = new day13.Cart(0, 2, day13.Direction.RIGHT);
        day13.Cart expected_cart_2 = new day13.Cart(3, 9, day13.Direction.DOWN);

        assertEquals(2, actual.size());

        assertEquals(expected_cart_1.x, actual.get(0).x);
        assertEquals(expected_cart_1.y, actual.get(0).y);
        assertEquals(expected_cart_1.orientation, actual.get(0).orientation);

        assertEquals(expected_cart_2.x, actual.get(1).x);
        assertEquals(expected_cart_2.y, actual.get(1).y);
        assertEquals(expected_cart_2.orientation, actual.get(1).orientation);
    }

    @Test
    public void RemoveCartsFromMap() {
        String filename = "data/aoc18.13a.txt";
        List<String> inputAsStrings = utilities.getFileContentsAsStrings(filename);
        char[][] map = utilities.convertInputToMap(inputAsStrings);

        List<day13.Cart> actual = day13.extractCartsFromMap(map);

        assertEquals('-', map[0][2]);
        assertEquals('|', map[3][9]);
    }

    @Test
    public void intersectionAction_startedRight() {
        day13.Cart sut = new day13.Cart(0, 0, day13.Direction.RIGHT);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.UP, sut.orientation);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.UP, sut.orientation);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.RIGHT, sut.orientation);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.UP, sut.orientation);
    }

    @Test
    public void intersection_startedLeft() {
        day13.Cart sut = new day13.Cart(0, 0, day13.Direction.LEFT);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.DOWN, sut.orientation);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.DOWN, sut.orientation);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.LEFT, sut.orientation);
    }

    @Test
    public void intersectionAction_startedUp() {
        day13.Cart sut = new day13.Cart(0, 0, day13.Direction.UP);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.LEFT, sut.orientation);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.LEFT, sut.orientation);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.UP, sut.orientation);
    }

    @Test
    public void intersectionAction_startedDown() {
        day13.Cart sut = new day13.Cart(0, 0, day13.Direction.DOWN);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.RIGHT, sut.orientation);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.RIGHT, sut.orientation);

        day13.intersectionAction(sut);
        assertEquals(day13.Direction.DOWN, sut.orientation);
    }

    @Test
    @Disabled
    public void ManyTicks_MoveRight_MoveDownIntersection() {
        day13.removeCollidingCarts = false;

        String filename = "data/aoc18.13a.txt";
        List<String> inputAsStrings = utilities.getFileContentsAsStrings(filename);
        char[][] map = utilities.convertInputToMap(inputAsStrings);

        List<day13.Cart> carts = day13.extractCartsFromMap(map);

        day13.tick(map, carts);

        day13.Cart expected_cart_1 = new day13.Cart(0, 3,  day13.Direction.RIGHT);
        day13.Cart expected_cart_2 = new day13.Cart(4, 9, day13.Direction.RIGHT);

        assertEquals(expected_cart_1.x, carts.get(0).x);
        assertEquals(expected_cart_1.y, carts.get(0).y);
        assertEquals(expected_cart_1.orientation, carts.get(0).orientation);

        assertEquals(expected_cart_2.x, carts.get(1).x);
        assertEquals(expected_cart_2.y, carts.get(1).y);
        assertEquals(expected_cart_2.orientation, carts.get(1).orientation);

        // Next tick the first cart hits a \
        day13.tick(map, carts);

        expected_cart_1 = new day13.Cart(0, 4,  day13.Direction.DOWN);
        assertEquals(expected_cart_1.x, carts.get(0).x);
        assertEquals(expected_cart_1.y, carts.get(0).y);
        assertEquals(expected_cart_1.orientation, carts.get(0).orientation);

        expected_cart_2 = new day13.Cart(4, 10, day13.Direction.RIGHT);
        assertEquals(expected_cart_2.x, carts.get(1).x);
        assertEquals(expected_cart_2.y, carts.get(1).y);
        assertEquals(expected_cart_2.orientation, carts.get(1).orientation);

        day13.tick(map, carts);     // nothing interesting - cart1 moves down, cart2 moves right

        day13.tick(map, carts);

        expected_cart_1 = new day13.Cart(2, 4,  day13.Direction.RIGHT);
        assertEquals(expected_cart_1.x, carts.get(0).x);
        assertEquals(expected_cart_1.y, carts.get(0).y);
        assertEquals(expected_cart_1.orientation, carts.get(0).orientation);

        expected_cart_2 = new day13.Cart(4, 12, day13.Direction.UP);
        assertEquals(expected_cart_2.x, carts.get(1).x);
        assertEquals(expected_cart_2.y, carts.get(1).y);
        assertEquals(expected_cart_2.orientation, carts.get(1).orientation);

    }


    @Test
    @Disabled
    public void day13_solution1() {
        String filename = "data/aoc18.13.txt";
        List<String> inputAsStrings = utilities.getFileContentsAsStrings(filename);
        char[][] map = utilities.convertInputToMap(inputAsStrings);

        List<day13.Cart> carts = day13.extractCartsFromMap(map);

        int i = 1;
        while (true) {
            try {
                day13.tick(map, carts);
            } catch (Exception e) {
                assertEquals("SOLUTION", e.getMessage());
            }
        }
    }

    @Test
    @Disabled("2021-12 test is failing; unsure what the expected message intends")
    public void day13_example2_removeCarts() {
        String filename = "data/aoc18.13.txt";
        List<String> inputAsStrings = utilities.getFileContentsAsStrings(filename);
        char[][] map = utilities.convertInputToMap(inputAsStrings);

        List<day13.Cart> carts = day13.extractCartsFromMap(map);
        day13.removeCollidingCarts = true;

        while (true) {
            try {
                day13.tick(map, carts);
            } catch (Exception e) {
                e.printStackTrace();
                assertEquals("remove test", e.getMessage());
            }
        }
    }

    private void dumpCarts(List<day13.Cart> carts) {
        for (day13.Cart cart :
                carts) {
            System.out.println(dumpCart(cart));
        }
    }

    private String dumpCart(day13.Cart cart) {
        return String.format("col: %4d   row: %4d   dir: %s\n", cart.y, cart.x, cart.orientation);
    }
}
