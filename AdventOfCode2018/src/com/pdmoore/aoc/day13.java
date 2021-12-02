package com.pdmoore.aoc;

import java.util.ArrayList;
import java.util.List;

public class day13 {

    public static boolean removeCollidingCarts = false;
    private static int tick = 0;

    public static class Cart {
        public Direction orientation;
        public int x;
        public int y;
        public int currentTick = 0;
        public int intersections = 0;

        public Cart(int y, int x, Direction orientation) {
            this.x = x;
            this.y = y;
            this.orientation = orientation;
        }
    }


    enum Direction {
        UP, DOWN, LEFT, RIGHT,
    };

    enum IntersectionOption {
        LEFT, STRAIGHT, RIGHT,
    };

    public static List<Cart> extractCartsFromMap(char[][] map) {
        List<Cart> carts = new ArrayList<>();

        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {

                if (isCart(map[x][y])) {
                    carts.add(createCart(map, x, y));
                    removeCartCharacter(map, x, y);
                }
            }
        }
        return carts;
    }

    private static void removeCartCharacter(char[][] map, int x, int y) {
        char cartCharacter = map[x][y];
        switch (cartCharacter) {
            case '>':
            case '<':
                map[x][y] = '-';
                break;
            case '^':
            case 'v':
                map[x][y] = '|';
                break;
        }
    }

    private static Cart createCart(char[][] map, int x, int y) {
        char cartCharacter = map[x][y];
        Direction orientation = null;
        switch (cartCharacter) {
            case '>':
                orientation = Direction.RIGHT;
                break;
            case '<':
                orientation = Direction.LEFT;
                break;
            case '^':
                orientation = Direction.UP;
                break;
            case 'v':
                orientation = Direction.DOWN;
                break;
        }
        Cart cart = new Cart(x, y, orientation);
        return cart;
    }

    private static boolean isCart(char c) {
        String cartChars = "><^v";
        return cartChars.indexOf(c, 0) != -1;
    }

    public static void tick(char[][] map, List<Cart> carts) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {

                Cart cart = isThereACartAt(x, y, carts);
                if (cart != null) {
                    cartTick(map, cart);

                    areThereTwoCartsAt(cart.x, cart.y, carts);
                }
            }
        }

        tick++;
    }

    private static Cart isThereACartAt(int x, int y, List<Cart> carts) {
        for (Cart cart :
                carts) {
            if (cart.x == x && cart.y == y && cart.currentTick < tick) {
                return cart;
            }
        }
        return null;
    }

    private static void areThereTwoCartsAt(int x, int y, List<Cart> carts) {
        int cartDetectedAt = 0;
        Cart firstCart = null;
        for (Cart cart :
                carts) {
            if (cart.x == x && cart.y == y) {
                cartDetectedAt++;
                if (firstCart == null) {
                    firstCart = cart;
                }

                if (cartDetectedAt == 2) {
                    if (removeCollidingCarts) {
                        carts.remove(firstCart);
                        carts.remove(cart);
                        if (carts.size() == 1) {
                            Cart lastCart = carts.get(0);
                            throw new RuntimeException("last - x: " + lastCart.x + "  y: " + lastCart.y + "dir: " + lastCart.orientation);
                        }
                        return;
                    } else {
                        throw new RuntimeException("boom - x: " + x + "  y: " + y);
                    }
                }
            }
        }
    }


    private static void cartTick(char[][] map, Cart cart) {
        if (cart.currentTick > tick) return;

        cart.currentTick++;

        if (cart.orientation == Direction.RIGHT) {
            cart.x++;
        } else if (cart.orientation == Direction.LEFT) {
            cart.x--;
        } else if (cart.orientation == Direction.DOWN) {
            cart.y++;
        } else if (cart.orientation == Direction.UP) {
            cart.y--;
        }

        char newLocation = map[cart.y][cart.x];

        // check if this new cell is a / or \ and change orientation (depends on current orientation!)
        if (newLocation == '\\') {
            backSlashAction(cart);
        } else if (newLocation == '/') {
            forwardSlashAction(cart);
        } else if (newLocation == '+') {
            intersectionAction(cart);
        }
    }

    private static void backSlashAction(Cart cart) {
        if (cart.orientation == Direction.RIGHT) {
            cart.orientation = Direction.DOWN;
        } else if (cart.orientation == Direction.LEFT) {
            cart.orientation = Direction.UP;
        } else if (cart.orientation == Direction.UP) {
            cart.orientation = Direction.LEFT;
        } else if (cart.orientation == Direction.DOWN) {
            cart.orientation = Direction.RIGHT;
        }
    }

    private static void forwardSlashAction(Cart cart) {
        if (cart.orientation == Direction.RIGHT) {
            cart.orientation = Direction.UP;
        } else if (cart.orientation == Direction.LEFT) {
            cart.orientation = Direction.DOWN;
        } else if (cart.orientation == Direction.UP) {
            cart.orientation = Direction.RIGHT;
        } else if (cart.orientation == Direction.DOWN) {
            cart.orientation = Direction.LEFT;
        }
    }

    public static void intersectionAction(Cart cart) {

        IntersectionOption option = IntersectionOption.values()[cart.intersections % 3];
        if (cart.orientation == Direction.RIGHT) {
            if (option == IntersectionOption.LEFT) cart.orientation = Direction.UP;
            else if (option == IntersectionOption.RIGHT) cart.orientation = Direction.DOWN;
        } else if (cart.orientation == Direction.LEFT) {
            if (option == IntersectionOption.LEFT) cart.orientation = Direction.DOWN;
            else if (option == IntersectionOption.RIGHT) cart.orientation = Direction.UP;
        } else if (cart.orientation == Direction.UP) {
            if (option == IntersectionOption.LEFT) cart.orientation = Direction.LEFT;
            else if (option == IntersectionOption.RIGHT) cart.orientation = Direction.RIGHT;
        } else if (cart.orientation == Direction.DOWN) {
            if (option == IntersectionOption.LEFT) cart.orientation = Direction.RIGHT;
            else if (option == IntersectionOption.RIGHT) cart.orientation = Direction.LEFT;
        }

        cart.intersections++;
    }
}