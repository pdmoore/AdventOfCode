package test.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Tests {

    @Test
    public void EqualNumberOfOpenAndClose() {
        assertEquals(0, something("(())"));
        assertEquals(0, something("()()"));
    }

    @Test
    public void MoreOpenThanClose() {
        assertEquals(3, something("))((((("));
        assertEquals(3, something("))((((("));
    }

    @Test
    public void MoreCloseThanOpen() {
        assertEquals(-1, something("())"));
        assertEquals(-1, something("))("));
        assertEquals(-3, something(")))"));
        assertEquals(-3, something(")())())"));
    }


    private int something(String input) {
        int floor = 0;
        for (char c: input.toCharArray() ){
            if ('(' == c) floor++;
            else floor--;
        }
        return floor;
    }


}
