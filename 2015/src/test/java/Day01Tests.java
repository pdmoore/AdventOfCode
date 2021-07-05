package test.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day01Tests {

    @Test
    public void EqualNumberOfOpenAndClose() {
        Assertions.assertEquals(0, something("(())"));
        Assertions.assertEquals(0, something("()()"));
    }

    @Test
    public void MoreOpenThanClose() {
        Assertions.assertEquals(3, something("))((((("));
        Assertions.assertEquals(3, something("))((((("));
    }

    @Test
    public void MoreCloseThanOpen() {
        Assertions.assertEquals(-1, something("())"));
        Assertions.assertEquals(-1, something("))("));
        Assertions.assertEquals(-3, something(")))"));
        Assertions.assertEquals(-3, something(")())())"));
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
