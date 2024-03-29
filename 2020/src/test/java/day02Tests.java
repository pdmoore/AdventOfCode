import com.pdmoore.aoc.PuzzleInput;
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
        List<String> passwordLines = PuzzleInput.asListOfStringsFrom("./data/day02-part01");

        int actual = (int) passwordLines
                .stream()
                .filter(b -> validateSledPassword(b))
                .count();

        Assertions.assertEquals(396, actual);
    }

    @Test
    public void day02_part02() {
        List<String> passwordLines = PuzzleInput.asListOfStringsFrom("./data/day02-part01");
        
        int actual = (int) passwordLines
                .stream()
                .filter(b -> validateTobogganPassword(b))
                .count();

        Assertions.assertEquals(428, actual);
    }

    private boolean validateTobogganPassword(String input) {
        String[] elements = input.split(" ");
        Character letter = elements[1].charAt(0);
        String password = elements[2];

        String[] bounds = elements[0].split("-");
        int requiredPosition1 = Integer.parseInt(bounds[0]);
        int requiredPosition2 = Integer.parseInt(bounds[1]);

        boolean firstMatch = letter.equals(password.charAt(requiredPosition1 - 1));
        boolean secondMatch = letter.equals(password.charAt(requiredPosition2 - 1));

        return firstMatch ^ secondMatch;
    }


    private boolean validateSledPassword(String input) {
        String[] elements = input.split(" ");
        Character letter = elements[1].charAt(0);
        String password = elements[2];

        String[] bounds = elements[0].split("-");
        int lowerBound = Integer.parseInt(bounds[0]);
        int upperBound = Integer.parseInt(bounds[1]);

        long countOfLetter = password.chars()
                .filter(c -> c == letter)
                .count();

        return lowerBound <= countOfLetter && countOfLetter <= upperBound;
    }
}
