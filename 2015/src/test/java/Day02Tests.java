import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day02Tests {



    /*
A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet of wrapping paper plus 6 square feet of slack, for a total of 58 square feet.
A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet of wrapping paper plus 1 square foot of slack, for a total of 43 square feet.

     - Read file as list of strings
     - Parse a line to find length, width, height
     - Given l,w,h calculate area
     - Given l,w,h, find smallest area of triplet
     - Sum area and smallest area of triplet
     - Sum the whole dang list of things
     */

    @Test
    public void CalculateAreaOfBox() {
        String inputLine = "2x3x4";
        Assertions.assertEquals(52, squareFootageOf(inputLine));
    }

    private int squareFootageOf(String inputLine) {


        return 0;
    }
}
