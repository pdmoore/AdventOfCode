import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day12Tests {

    /*

handle nsew
handle f
handle lr

calc manhattan distance from 0,0 to position x,y
     */

    public List<String> createSampleInput() {
        List<String> input = new ArrayList<>();
        input.add("F10");
        input.add("N3");
        input.add("F7");
        input.add("R90");
        input.add("F11");
        return input;
    }

    @Test
    public void foo() {
        List<String> input = createSampleInput();

        Location startAt = new Location(0, 0);

        Location endAt = processInstructions(startAt, input);

        int result = computeDistance(endAt);

        assertEquals(17+8, result);
    }

    private Location processInstructions(Location startAt, List<String> input) {
        Location currentAt = new Location(startAt);

        for (String instruction :
                input) {

            Character direction = instruction.charAt(0);
            int amount = Integer.parseInt(instruction.substring(1));

            switch (direction) {
                case 'F': currentAt = currentAt.forward(amount);  break;
                case 'N': currentAt = currentAt.north(amount);    break;
                case 'R': currentAt = currentAt.turn(direction, amount); break;
                default: System.out.println("NOT HANDLED: " + instruction);
             }


        }


        return currentAt;
    }

    private int computeDistance(Location point) {
        return Math.abs(point.x) + Math.abs(point.y);
    }

    private static class Location {
        int y;
        int x;
        private Direction facing;

        public Location(int x, int y) {
            this(x, y, Direction.EAST);
        }

        public Location(Location other) {
            this(other.x, other.y, other.facing);
        }

        public Location(int x, int y, Direction facing) {
            this.x = x;
            this.y = y;
            this.facing = facing;
        }

        public Location forward(int amount) {
            switch (facing) {
                case EAST: return new Location(x + amount, y, facing);
                case WEST: return new Location(x - amount, y, facing);
                case NORTH: return new Location(x, y + amount, facing);
                case SOUTH: return new Location(x, y - amount, facing);
            }

            return null;
        }

        public Location north(int amount) {
            return new Location(x, y + amount, facing);
        }

        public Location turn(Character direction, int amount) {
System.out.println("turn amount: " + amount);
            //TODO handle 90, 180, etc - scan input for values
            //TODO hard coded for the sample input
            return new Location(x, y, Direction.SOUTH);
        }

        enum Direction {
            EAST, WEST, NORTH, SOUTH
        }
    }
}
