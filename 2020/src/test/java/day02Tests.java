import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class day02Tests {

    /*
1-3 a: abcde is valid: position 1 contains a and position 3 does not.
1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
     */

    @Test
    public void validSledPasswords() {
        Assertions.assertTrue(validateSledPassword("1-3 a: abcde"));
        Assertions.assertTrue(validateSledPassword("2-9 c: ccccccccc"));
    }

    @Test
    public void invalidSledPasswords() {
        Assertions.assertFalse(validateSledPassword("1-3 b: cdefg"));
    }

    @Test
    public void validTobaggonPasswords() {
        Assertions.assertTrue(validateTobaggonPassword("1-3 a: abcde"));
    }

    @Test
    public void invalidTobaggonPasswords() {
        Assertions.assertFalse(validateTobaggonPassword("1-3 b: cdefg"));
        Assertions.assertFalse(validateTobaggonPassword("2-9 c: ccccccccc"));
    }

    private boolean validateTobaggonPassword(String input) {
        String[] elements = input.split(" ");
        Character letter = elements[1].charAt(0);

        String positions = elements[0];
        String[] bounds = positions.split("-");
        int requiredPosition1 = Integer.parseInt(bounds[0]);
        int requiredPosition2 = Integer.parseInt(bounds[1]);

        char firstChar = elements[2].charAt(requiredPosition1 - 1);
        boolean firstMatch = letter.equals(firstChar);
        char secondChar = elements[2].charAt(requiredPosition2 - 1);
        boolean secondMatch = letter.equals(secondChar);

        if (firstMatch) {
            return !secondMatch;
        } else {
            return secondMatch;
        }
    }


    @Test
    public void day02_part01() {
        List<String> passwordLines = Utilities.getFileContentsAsStrings("./data/day02-part01");

        int actual = 0;

        for (String line :
                passwordLines) {
            if (validateSledPassword(line)) {
                actual++;
            }
        }

        Assertions.assertEquals(396, actual);
    }

    @Test
    public void day02_part02() {
        List<String> passwordLines = Utilities.getFileContentsAsStrings("./data/day02-part01");

        int actual = 0;

        for (String line :
                passwordLines) {
            if (validateTobaggonPassword(line)) {
                actual++;
            }
        }

        Assertions.assertEquals(-99, actual);
    }

    private boolean validateSledPassword(String input) {
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
