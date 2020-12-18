import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day12Tests {
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
    public void turnsLeft() {
        Location l = new Location(0, 0, Location.Direction.EAST);
        l = l.turn('L', 90);
        assertEquals(Location.Direction.NORTH, l.facing);
        l = l.turn('L', 90);
        assertEquals(Location.Direction.WEST, l.facing);
        l = l.turn('L', 90);
        assertEquals(Location.Direction.SOUTH, l.facing);
        l = l.turn('L', 90);
        assertEquals(Location.Direction.EAST, l.facing);
    }

    @Test
    public void turnsRight() {
        Location l = new Location(0, 0, Location.Direction.EAST);
        l = l.turn('R', 90);
        assertEquals(Location.Direction.SOUTH, l.facing);
        l = l.turn('R', 90);
        assertEquals(Location.Direction.WEST, l.facing);
        l = l.turn('R', 90);
        assertEquals(Location.Direction.NORTH, l.facing);
        l = l.turn('R', 90);
        assertEquals(Location.Direction.EAST, l.facing);
    }

    @Test
    public void part1_example() {
        List<String> input = createSampleInput();
        Location startAt = new Location(0, 0);

        Location endAt = processInstructionsPart1(startAt, input);

        int result = computeDistance(endAt);
        assertEquals(17+8, result);
    }

    @Test
    public void part1_solution() {
        List<String> input = Utilities.fileToStringList("./data/day12-part01");

        Location startAt = new Location(0, 0);

        Location endAt = processInstructionsPart1(startAt, input);

        int result = computeDistance(endAt);
        assertEquals(1177, result);
    }

    @Test
    public void part2_example() {
        List<String> input = createSampleInput();
        Location waypoint = new Location(10, 1);
        Location ship = new Location(0, 0);

        // waypoint is lost at the end of this process
        Location endAt = processInstructionsPart2(input, ship, waypoint);

        assertEquals(214, endAt.x);
        assertEquals(-72, endAt.y);

        int result = computeDistance(endAt);
        assertEquals(214+72, result);
    }


//-----------------------

    private Location processInstructionsPart1(Location startAt, List<String> input) {
        Location currentAt = new Location(startAt);

        for (String instruction :
                input) {

            Character direction = instruction.charAt(0);
            int amount = Integer.parseInt(instruction.substring(1));

            switch (direction) {
                case 'F': currentAt = currentAt.forward(amount); break;
                case 'N': currentAt = currentAt.north(amount);   break;
                case 'S': currentAt = currentAt.south(amount);   break;
                case 'E': currentAt = currentAt.east(amount);    break;
                case 'W': currentAt = currentAt.west(amount);    break;
                case 'R':
                case 'L': currentAt = currentAt.turn(direction, amount); break;
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
                case EAST: return east(amount);
                case WEST: return west(amount);
                case NORTH: return north(amount);
                case SOUTH: return south(amount);
            }

            return null;
        }

        public Location north(int amount) {
            return new Location(x, y + amount, facing);
        }

        public Location south(int amount) {
            return new Location(x, y - amount, facing);
        }

        public Location east(int amount) {
            return new Location(x + amount, y, facing);
        }

        public Location west(int amount) {
            return new Location(x - amount, y, facing);
        }

        public Location turn(Character direction, int amount) {
            int rotate = amount / 90;
            if (direction == 'L') {
                rotate = 0 - rotate;
            }

            int newIndex = Math.abs(((facing.ordinal() + rotate) + 4) % 4);
            Direction newFace = Direction.values()[newIndex];
            return new Location(x, y, newFace);
        }

        // used in part 2, might not be part of core location?
        // need to consider direction
        public Location forward(int amount, Location waypoint) {
            int magnitudeX = waypoint.x * amount;
            int magnitudeY = waypoint.y * amount;

            int newX = this.x + magnitudeX;
            int newY = this.y + magnitudeY;

            Location newShip = new Location(newX, newY, facing);
            return newShip;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "x=" + x +
                    ", y=" + y +
                    ", facing=" + facing +
                    '}';
        }

        public Location adjust(Character direction, int amount) {
            if (amount == 180) {
                return new Location(0 - x, 0 - y);
            }

            if (direction == 'R') {
                if (amount == 90) {
                    return new Location(y, 0 - x);
                }
            }

            return null;
        }

        enum Direction {
            EAST, SOUTH, WEST, NORTH
        }
    }


    private Location processInstructionsPart2(List<String> input, Location shipStarting, Location waypointStarting) {
        Location ship = new Location(shipStarting);
        Location waypoint = new Location(waypointStarting);

        for (String instruction :
                input) {
//System.out.println("process: " + instruction);
            Character direction = instruction.charAt(0);
            int amount = Integer.parseInt(instruction.substring(1));

            switch (direction) {
                case 'F': ship = ship.forward(amount, waypoint); break;
                case 'N': waypoint = waypoint.north(amount);   break;
                case 'S': waypoint = waypoint.south(amount);   break;
                case 'E': waypoint = waypoint.east(amount);    break;
                case 'W': waypoint = waypoint.west(amount);    break;
                case 'R':
                case 'L': ship = ship.turn(direction, amount);
                          waypoint = waypoint.adjust(direction, amount); break;
                default: System.out.println("NOT HANDLED: " + instruction);
            }

//            System.out.println("ship " + ship);
//            System.out.println("wayp " + waypoint);
        }

        return ship;
    }

}
