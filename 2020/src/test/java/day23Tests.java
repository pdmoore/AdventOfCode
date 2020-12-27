import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class day23Tests {

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
        return "NOT IMPLEMENTED";
    }
}
