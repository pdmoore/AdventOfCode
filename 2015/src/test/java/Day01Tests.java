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


    //TODO - Stream instead of for loop

    @Test
    public void EqualNumberOfOpenAndClose_EndsUpAtGroundFloor() {
        assertEquals(0, something("(())"));
        assertEquals(0, something("()()"));
    }

    @Test
    public void MoreOpenThanClose_EndsUpAboveGround() {
        assertEquals(3, something("))((((("));
        assertEquals(3, something("))((((("));
    }

    @Test
    public void MoreCloseThanOpen_EndsUpInBasement() {
        assertEquals(-1, something("())"));
        assertEquals(-1, something("))("));
        assertEquals(-3, something(")))"));
        assertEquals(-3, something(")())())"));
    }

    @Test
    public void Day1_Part1() {
        String input = fileAsString("data/day01-part01");
        assertEquals(232, something(input));
    }

    private int something(String input) {
        // it's also just the difference of count of ( and )
        int floor = 0;
        for (char c : input.toCharArray()) {
            if ('(' == c) floor++;
            else floor--;
        }
        return floor;
    }


}
