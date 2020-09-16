import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day3Tests {

    @Test
    public void solution_1_testData() {
        List<String> claims = Arrays.asList(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2");

        assertEquals(4, day3.solution_1(claims));
    }

    @Test
    public void solution_1_testFile() {
        assertEquals(104126, day3.solution_1("data/aoc18.3.txt"));
    }

    @Test
    public void parseClaim() {
        String claim = "#1 @ 1,3: 4x4";

        Claim expected = new Claim("#1", 1, 3, 4, 4);

        Claim actual = day3.parseClaim(claim);
        assertEquals(expected.claimId, actual.claimId);
        assertEquals(expected.upperLeftX, actual.upperLeftX);
        assertEquals(expected.upperLeftY, actual.upperLeftY);
        assertEquals(expected.height, actual.height);
        assertEquals(expected.width, actual.width);
    }

    @Test
    public void countOverlapArea() {
        String[][] squares = new String[][] {
                {null, null, "2", "2", "2", "2", "2", null},
                {null, "1", "1", "X", "X", "2", "2", null},
                {null, "1", "1", "X", "X", "2", "2", null},
                {null, "1", "1", "1", "1", "3", "3", null},
                {null, "1", "1", "1", "1", "3", "3", null},
        };

        assertEquals(4, day3.findOverlap(squares));
    }
}
