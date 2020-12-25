import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day23Tests {


        /*
            List<String> input = Utilities.fileToStringList("./data/day23-part01");

     */

    String createPart1Input() {
        return "135468729";
    }


    String createSampleInput() {
        return "389125467";
    }


    @Test
    public void part1_sample() {
        String actual = solvePart1(createSampleInput(), 100);

        String expected = "167384529";
        assertEquals(expected, actual);
    }

    private String solvePart1(String input, int numMoves) {

        int[] cups = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            cups[i] = Character.getNumericValue(input.charAt(i));
        }

        int currentCup = 0;
        while (numMoves-- > 0) {

            /*
1 - The crab picks up the three cups that are immediately clockwise of the current cup.
  They are removed from the circle; cup spacing is adjusted as necessary to maintain the circle.

2 - The crab selects a destination cup: the cup with a label equal to the current cup's label minus one. If this would select one of the cups that was just picked up, the crab will keep subtracting one until it finds a cup that wasn't just picked up.
If at any point in this process the value goes below the lowest value on any cup's label, it wraps around to the highest value on any cup's label instead.

3 - The crab places the cups it just picked up so that they are immediately clockwise of the destination cup.
They keep the same order as when they were picked up.

4 - The crab selects a new current cup: the cup which is immediately clockwise of the current cup.

cups: (3) 8  9  1  2  5  4  6  7
pick up: 8, 9, 1
destination: 2

-- move 2 --
cups:  3 (2) 8  9  1  5  4  6  7
pick up: 8, 9, 1
destination: 7
             */

            // need to check for wrapping past end
            int cup1 = cups[(currentCup + 1) % cups.length];
            int cup2 = cups[(currentCup + 2) % cups.length];
            int cup3 = cups[(currentCup + 3) % cups.length];

            // move cups to the left
            //curr + 4 <== curr + 1
            // 3 8 9 1 2 5 4 6 7
            // 3 2 5 4 6 7
            // need to handle wrapping!
            for (int i = currentCup + 4; i < cups.length; i++) {
                int index = (currentCup + i) % cups.length;
                cups[index] = cups[i];
            }

            int destinationCup = cups[currentCup] - 1;
            // 3 2 8 9 1 5 4 6 7
// PROBABLY mISSING THE SECOND CHECK ON STEP 2


// BAILED ON TUESDAY EVE
// didn't tackle Step 4, inserting the 3 cut items into the collapsed list






            currentCup++;
            if (currentCup >= cups.length) currentCup = 0;
        }


        //convert list to string of integers
        return convertIntsToString(cups);
    }

    private String convertIntsToString(int[] cups) {
        String result = "";
        for (int i = 0; i < cups.length; i++) {
            result += cups[i];
        }
        return result;
    }


}
