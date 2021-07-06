package test.java;

import com.google.common.base.Charsets;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Tests {

    //TODO - figure out why toString is deprecated here but not mentioned in docs
    private String getInputFromFile() {
        String filename = "data/day01-part01";
        try {
            return com.google.common.io.Files.toString(new File(filename), Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't open input file: " + filename);
        }
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
