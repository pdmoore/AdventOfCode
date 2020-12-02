import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class day02Tests {

    /*
//parse a line
//validate a password
    range letter:  password
VALID
1-3 a: abcde
2-9 c: ccccccccc

INVALID
1-3 b: cdefg

// then count of valid passwords

     */

    @Test
    public void parseLine() {
//        1-3 a: abcde
        //split on spaces
        String input = "1-3 a: abcde";
        String[] elements = input.split(" ");
        Assertions.assertEquals("1-3", elements[0]);
        Assertions.assertEquals("a:", elements[1]);
        Assertions.assertEquals("abcde", elements[2]);
    }


}
