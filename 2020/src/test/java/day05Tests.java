import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    private int largestSeatIDOf(List<String> passes) {
        int result = 0;
        for (String boardingPass :
                passes) {
            result = Math.max(result, seatIdFor(boardingPass));
        }
        return result;
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
