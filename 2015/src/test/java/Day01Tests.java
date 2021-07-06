package test.java;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Tests {

// TODO - Utilities jar to be shared across all
    // javadoc for this one pointing out file is relative at "data\filename"
    static String fileAsString(String filename) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            return new String(encoded).trim();
        } catch (IOException e) {
            System.out.println("ERROR reading " + filename);
        }
        return null;
    }


    @Test
    public void EqualNumberOfOpenAndClose_EndsUpAtGroundFloor() {
        assertEquals(0, floorNumberAfterManyUpAndDownMoves("(())"));
        assertEquals(0, floorNumberAfterManyUpAndDownMoves("()()"));
    }

    @Test
    public void MoreOpenThanClose_EndsUpAboveGround() {
        assertEquals(3, floorNumberAfterManyUpAndDownMoves("))((((("));
        assertEquals(3, floorNumberAfterManyUpAndDownMoves("))((((("));
    }

    @Test
    public void MoreCloseThanOpen_EndsUpInBasement() {
        assertEquals(-1, floorNumberAfterManyUpAndDownMoves("())"));
        assertEquals(-1, floorNumberAfterManyUpAndDownMoves("))("));
        assertEquals(-3, floorNumberAfterManyUpAndDownMoves(")))"));
        assertEquals(-3, floorNumberAfterManyUpAndDownMoves(")())())"));
    }

    @Test
    public void Day1_Part1() {
        String input = fileAsString("data/day01-part01");
        assertEquals(232, floorNumberAfterManyUpAndDownMoves(input));
    }

    private int floorNumberAfterManyUpAndDownMoves(String input) {
        int goingUp = (int) input.chars().filter(ch -> ch == '(').count();
        int goingDown = (int) input.chars().filter(ch -> ch == ')').count();

        return goingUp - goingDown;
    }


}
