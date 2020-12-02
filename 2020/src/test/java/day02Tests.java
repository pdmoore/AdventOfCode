import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    public void validPasswords() {
        Assertions.assertTrue(validatePassword("1-3 a: abcde"));
        Assertions.assertTrue(validatePassword("2-9 c: ccccccccc"));
    }

    @Test
    public void invalidPasswords() {
        Assertions.assertFalse(validatePassword("1-3 b: cdefg"));
    }

    @Test
    public void day02_part01() {
        // read file as list of strings
        List<String> passwordLines = Utilities.getFileContentsAsStrings("./data/day02-part01");

        // loop through strings and count the Valid==true
        int actual = 0;

        for (String line :
                passwordLines) {
            if (validatePassword(line)) {
                actual++;
            }
        }

        Assertions.assertEquals(396, actual);
    }

    private boolean validatePassword(String input) {
        String[] elements = input.split(" ");

        String letter = elements[1].substring(0,1);

        String removedLetter = elements[2].replaceAll(letter, "");

        int occurencesOfLetter = elements[2].length() - removedLetter.length();

        String counts = elements[0];
        String[] bounds = counts.split("-");
        int lowerBound = Integer.parseInt(bounds[0]);
        int upperBound = Integer.parseInt(bounds[1]);

        return lowerBound <= occurencesOfLetter && occurencesOfLetter <= upperBound;
    }


}
