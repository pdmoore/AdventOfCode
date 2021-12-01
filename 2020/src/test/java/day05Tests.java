import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day05Tests {



    /*
    read file as list of strings
    for a string, figure out row and seat
    calculate seatId
    track the largest seatId

     */


    //    BFFFBBFRRR: row 70, column 7, seat ID 567.
//    FFFBBBFRRR: row 14, column 7, seat ID 119.
//    BBFFBBFRLL: row 102, column 4, seat ID 820.
    @Test
    public void findRow() {
        assertEquals(44, rowFromBoardingPass("FBFBBFFRLR"));
        assertEquals(70, rowFromBoardingPass("BFFFBBFRRR"));
        assertEquals(14, rowFromBoardingPass("FFFBBBFRRR"));
        assertEquals(102, rowFromBoardingPass("BBFFBBFRLL"));
    }

    @Test
    public void findColumn() {
        assertEquals(5, columnFromBoardingPass("FBFBBFFRLR"));
        assertEquals(7, columnFromBoardingPass("BFFFBBFRRR"));
        assertEquals(7, columnFromBoardingPass("FFFBBBFRRR"));
        assertEquals(4, columnFromBoardingPass("BBFFBBFRLL"));
    }

    @Test
    public void findSeatID() {
        assertEquals(357, seatIdFor("FBFBBFFRLR"));
        assertEquals(567, seatIdFor("BFFFBBFRRR"));
        assertEquals(119, seatIdFor("FFFBBBFRRR"));
        assertEquals(820, seatIdFor("BBFFBBFRLL"));
    }

    @Test
    public void part1_sample() {
        List<String> passes = new ArrayList<>();
        passes.add("FBFBBFFRLR");
        passes.add("BFFFBBFRRR");
        passes.add("FFFBBBFRRR");
        passes.add("BBFFBBFRLL");

        int actual = largestSeatIDOf(passes);
        assertEquals(820, actual);
    }

    @Test
    public void part1_solution() {
        List<String> passes = PuzzleInput.asListOfStringsFrom("./data/day05-part01");
        int actual = largestSeatIDOf(passes);
        assertEquals(890, actual);
    }

    @Test
    public void part2_solution() {
        List<String> passes = PuzzleInput.asListOfStringsFrom("./data/day05-part01");
        int result = leftoverSeats(passes);
        assertEquals(651, result);
    }

    private int leftoverSeats(List<String> passes) {
        List<Integer> remainingSeatIDs = new ArrayList<>();
        for (String boardingPass :
                passes) {
            remainingSeatIDs.add(seatIdFor(boardingPass));
        }

        Collections.sort(remainingSeatIDs);

        int previousSeatID = remainingSeatIDs.get(0);
        for (int i = 1; i < remainingSeatIDs.size(); i++) {
            int thisSeatID = remainingSeatIDs.get(i);
            if (thisSeatID - previousSeatID != 1) {
                return (previousSeatID + 1);
            }
            previousSeatID = thisSeatID;
        }

        return 0;
    }

    private int largestSeatIDOf(List<String> passes) {
        return passes.
                stream().
                map(b -> seatIdFor(b)).
                max(Integer::compare).
                get();
    }


    private int seatIdFor(String boardingPass) {
        return 8 * rowFromBoardingPass(boardingPass) + columnFromBoardingPass(boardingPass);
    }


    private int columnFromBoardingPass(String boardingPass) {
        String columnPortion = boardingPass.substring(7);
        String binaryRow = columnPortion.replace("R", "1").replace("L", "0");
        return Integer.parseInt(binaryRow, 2);
    }


    private int rowFromBoardingPass(String boardingPass) {
        String rowPortion = boardingPass.substring(0, 7);
        String binaryRow = rowPortion.replace("F", "0").replace("B", "1");
        return Integer.parseInt(binaryRow, 2);
    }
}
