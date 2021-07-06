package test.java;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Tests {

// TODO - Utilities jar to be shared across all

    /**
     * Read contents of the supplied filename and returns as a single string.
     * Provide path to file for files not in same directory as source file.
     *
     * @param   filename  filename (with path) of file to convert to String
     * @return            contents of file as String
     */
     static String fileAsString(String filename) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            return new String(encoded).trim();
        } catch (IOException e) {
            System.out.println("ERROR reading " + filename);
        }
        return null;
    }

    private String getInputFromFile() {
        return fileAsString("data/day01-part01");
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
        String input = getInputFromFile();
        assertEquals(232, floorNumberAfterManyUpAndDownMoves(input));
    }

    @Test
    public void positionOfFirstCharacterToEnterBasement_ItIsVeryFirstCharacter() {
        assertEquals(1, indexOfFirstBasementVisit(")"));
        assertEquals(5, indexOfFirstBasementVisit("()())"));
    }

    @Test
    public void Day1_Part2() {
        String input = getInputFromFile();
        assertEquals(1783, indexOfFirstBasementVisit(input));
    }

    private int floorNumberAfterManyUpAndDownMoves(String input) {
        int goingUp = (int) input.chars().filter(ch -> ch == '(').count();
        int goingDown = (int) input.chars().filter(ch -> ch == ')').count();

        return goingUp - goingDown;
    }

    private int indexOfFirstBasementVisit(String input) {
        int currentFloor = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') currentFloor++;
            else currentFloor--;

            if (currentFloor == -1) return i + 1;
        }
        return 0;
    }

}
