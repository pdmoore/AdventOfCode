import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day11Tests {

    static final int VISIBLE_OCCUPIED_THRESHOLD_PART_1 = 4;
    static final int VISIBLE_OCCUPIED_THRESHOLD_PART_2 = 5;

    /*

L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL

If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
Otherwise, the seat's state does not change.

.  Floor never changes
L  Empty Seat
#  Occupied seat

- read in initial grid

- detect adjacent seats (including edge checks)
- transform grid from current state to next state
- detect when stasis achieved
- count the seats at the end
- print the seats

     */


    @Test
    public void part1_simpleExample() {
        List<String> input = createSampleInput();

        input = occupySeats(input, VISIBLE_OCCUPIED_THRESHOLD_PART_1);

        int result = occupiedSeatCount(input);

        assertEquals(37, result);
    }

    @Test
    public void part2_simpleExample() {
        List<String> input = createSampleInput();

        input = occupySeats(input, VISIBLE_OCCUPIED_THRESHOLD_PART_2);

        int result = occupiedSeatCount(input);

        assertEquals(26, result);
    }

    @Test
    public void part1_solution() {
        List<String> input = Utilities.fileToStringList("./data/day11-part01");

        input = occupySeats(input, VISIBLE_OCCUPIED_THRESHOLD_PART_1);

        int result = occupiedSeatCount(input);

        assertEquals(2281, result);
    }

    @Test
    public void part2_solution() {
        List<String> input = Utilities.fileToStringList("./data/day11-part01");

        input = occupySeats(input, VISIBLE_OCCUPIED_THRESHOLD_PART_2);

        int result = occupiedSeatCount(input);

        assertEquals(2085, result);
    }

//------------------------------------------------

    private int occupiedSeatCount(List<String> input) {
        int count = 0;

        //TODO convert to stream impl
        for (String row :
                input) {
            count += row.chars().filter(c -> c == '#').count();
        }


        return count;
    }

    public List<String> createSampleInput() {
        List<String> input = new ArrayList<>();
        input.add("L.LL.LL.LL");
        input.add("LLLLLLL.LL");
        input.add("L.L.L..L..");
        input.add("LLLL.LL.LL");
        input.add("L.LL.LL.LL");
        input.add("L.LLLLL.LL");
        input.add("..L.L.....");
        input.add("LLLLLLLLLL");
        input.add("L.LLLLLL.L");
        input.add("L.LLLLL.LL");
        return input;
    }


    private List<String> occupySeats(List<String> input, int visibleOccupiedThreshold) {

        List<String> nextIteration = new ArrayList<>();
        int rowLength = input.get(0).length();

        boolean madeAChange = false;
        do {
            madeAChange = false;
            for (int x = 0; x < input.size(); x++) {
                String newRow = "";
                for (int y = 0; y < rowLength; y++) {

                    // cheat until I pass a function into this method
                    int neighbors = 0;
                    if (visibleOccupiedThreshold == VISIBLE_OCCUPIED_THRESHOLD_PART_1) {
                        neighbors = adjacentOccupiedNeighborCount(input, x, y);
                    } else {
                        neighbors = visibleOccupiedNeighborCount(input, x, y);
                    }

                    char thisSeat = input.get(x).charAt(y);
                    if (thisSeat == '.') {
                        newRow += '.';
                    } else if (thisSeat == 'L') {
                        if (neighbors == 0) {

                            newRow += '#';
                            madeAChange = true;
                        } else{
                            newRow += 'L';
                        }
                    } else { // assumed occupied #
                        if (neighbors >= visibleOccupiedThreshold) {
                            newRow += 'L';
                            madeAChange = true;
                        } else {
                            newRow += '#';
                        }
                    }
                }
                nextIteration.add(newRow);
            }
//            System.out.println(nextIteration);
            input = nextIteration;
            nextIteration = new ArrayList<>();
        } while (madeAChange);

        return input;
    }

    private int adjacentOccupiedNeighborCount(List<String> input, int x, int y) {
        // 0 1 2
        // 3 X 4
        // 5 6 7
        int neighborOccupied = 0;
        if (x >= 1) {
            if (y >= 1) {
                if (input.get(x - 1).charAt(y - 1) == '#') neighborOccupied++;
            }
            if (input.get(x - 1).charAt(y) == '#') neighborOccupied++;
            if (y < input.get(x).length() - 1) {
                if (input.get(x - 1).charAt(y + 1) == '#') neighborOccupied++;
            }
        }
        if (y >= 1) {
            if (input.get(x).charAt(y - 1) == '#') neighborOccupied++;
        }
        if (y < input.get(x).length() - 1) {
            if (input.get(x).charAt(y + 1) == '#') neighborOccupied++;
        }
        if (x < input.size() - 1) {
            if (y >= 1) {
                if (input.get(x + 1).charAt(y - 1) == '#') neighborOccupied++;
            }
            if (input.get(x + 1).charAt(y) == '#') neighborOccupied++;
            if (y < input.get(x).length() - 1) {
                if (input.get(x + 1).charAt(y + 1) == '#') neighborOccupied++;
            }
        }

        return neighborOccupied;
    }


    private int visibleOccupiedNeighborCount(List<String> input, int x, int y) {

        //instead of immediate adjacent, follow the vector from x,y in all 8 directions, only counting those seats that are occupied
        int visibleOccupied = 0;

        visibleOccupied += occupiedInDirection(input, x, y, -1, -1);
        visibleOccupied += occupiedInDirection(input, x, y, 0, -1);
        visibleOccupied += occupiedInDirection(input, x, y, 1, -1);
        visibleOccupied += occupiedInDirection(input, x, y, -1, 0);
        visibleOccupied += occupiedInDirection(input, x, y, 1, 0);
        visibleOccupied += occupiedInDirection(input, x, y, -1, 1);
        visibleOccupied += occupiedInDirection(input, x, y, 0, 1);
        visibleOccupied += occupiedInDirection(input, x, y, 1, 1);

        return visibleOccupied;
    }

    private int occupiedInDirection(List<String> input, int x, int y, int deltaX, int deltaY) {
        // 0 1 2
        // 3 X 4
        // 5 6 7
        // check edge conditions first
        // check seat occupied
        // return zero for empty seat
        // ignore floor
        int rowLength = input.get(0).length();

        int currX = x + deltaX;
        int currY = y + deltaY;

        boolean keepChecking = (currX >= 0 && currX < input.size()) && (currY >= 0 && currY < rowLength);

        while (keepChecking) {
            if (input.get(currX).charAt(currY) == '#') return 1;
            if (input.get(currX).charAt(currY) == 'L') return 0;

            currX += deltaX;
            currY += deltaY;

            keepChecking = (currX >= 0 && currX < input.size()) && (currY >= 0 && currY < rowLength);
        }

        return 0;
    }


}
