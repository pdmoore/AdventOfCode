import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class day02Tests {

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
    public void validTobogganPasswords() {
        Assertions.assertTrue(validateTobogganPassword("1-3 a: abcde"));
    }

    @Test
    public void invalidTobogganPasswords() {
        Assertions.assertFalse(validateTobogganPassword("1-3 b: cdefg"));
        Assertions.assertFalse(validateTobogganPassword("2-9 c: ccccccccc"));
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
            if (validateTobogganPassword(line)) {
                actual++;
            }
        }

        Assertions.assertEquals(428, actual);
    }

    private boolean validateTobogganPassword(String input) {
        String[] elements = input.split(" ");
        Character letter = elements[1].charAt(0);

        String[] bounds = elements[0].split("-");
        int requiredPosition1 = Integer.parseInt(bounds[0]);
        int requiredPosition2 = Integer.parseInt(bounds[1]);

        String password = elements[2];
        boolean firstMatch = letter.equals(password.charAt(requiredPosition1 - 1));
        boolean secondMatch = letter.equals(password.charAt(requiredPosition2 - 1));

        if (firstMatch) {
            return !secondMatch;
        } else {
            return secondMatch;
        }
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
