package test.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Day01Tests {

    /*

((( and (()(()( both result in floor 3.
))((((( also results in floor 3.
()) and ))( both result in floor -1 (the first basement level).
))) and )())()) both result in floor -3.

     */

    @Test
    public void EqualNumberOfOpenAndClose() {
        Assertions.assertEquals(0, something("(())"));
        Assertions.assertEquals(0, something("()()"));
    }

    @Test
    public void MoreOpenThanClose() {
        Assertions.assertEquals(3, something("))((((("));
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