import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class day2Tests {

    @Test
    public void neither_2or3(){
        String input = "abcdef";
        assertEquals(0, findForTwos(input));
        assertEquals(0, findForThrees(input));
    }

    @Test
    public void only2() {
        String input = "abcdee";
        assertEquals(1, findForTwos(input));
    }

    @Test
    public void only3() {
        String input = "aaaeee";
        assertEquals(2, findForThrees(input));
    }

    private int findForThrees(String input) {
        Map<Character, Integer> characterCounts = new HashMap<>();
        for (char c: input.toCharArray()) {
            if (!characterCounts.containsKey(c)) {
                characterCounts.put(c, 1);
            } else {
                Integer i = characterCounts.get(c);
                i++;
                characterCounts.put(c, i);
            }
        }

        int charactersWithThree = 0;
        for (Character c: characterCounts.keySet()) {
            if (characterCounts.get(c) == 3) charactersWithThree++;
        }

        return charactersWithThree;
    }

    private int findForTwos(String input) {
        Map<Character, Integer> characterCounts = new HashMap<>();
        for (char c: input.toCharArray()) {
            if (!characterCounts.containsKey(c)) {
                characterCounts.put(c, 1);
            } else {
                Integer i = characterCounts.get(c);
                i++;
                characterCounts.put(c, i);
            }
        }

        int charactersWithTwo = 0;
        for (Character c: characterCounts.keySet()) {
            if (characterCounts.get(c) == 2) charactersWithTwo++;
        }

        return charactersWithTwo;
    }

    @Test
    public void solution1() {
        assertEquals(6888, day2.solution_1());
    }

    @Test
    public void d2_2_DifferByOne_False_SameString() {
        String example = "icxjubroqtunzeyzpomfksahgw";
        assertFalse(day2.differsByOnlyOne(example, example));
    }

    @Test
    public void d2_2_DifferByOne_False_DiffersByMoreThanOne() {
        String example_1 = "icxjubroqtunzeyzpomfksahgw";
        String example_2 = "completelydifferentstrings";
        assertFalse(day2.differsByOnlyOne(example_2, example_2));
    }

    @Test
    public void d2_2_DifferByOne_True_DifferentAtFront() {
        String example_1 = "icxjubroqtunzeyzpomfksahgw";
        String example_2 = "acxjubroqtunzeyzpomfksahgw";
        assertTrue(day2.differsByOnlyOne(example_1, example_2));
    }

    @Test
    public void d2_2_DifferByOne_True_DifferentAtEnd() {
        String example_1 = "icxjubroqtunzeyzpomfksahgw";
        String example_2 = "icxjubroqtunzeyzpomfksahga";
        assertTrue(day2.differsByOnlyOne(example_1, example_2));
    }

    @Test
    public void d2_2_DifferByOne_False_TwoDifferent() {
        String example_1 = "icxjubroqtunzeyzpomfksahgw";
        String example_2 = "acxjubroqtunzeyzpomfksahga";
        assertFalse(day2.differsByOnlyOne(example_1, example_2));
    }


    @Test
    public void d2_2_displayDistinctCharacters() {
        String match_1 = "fghij";
        String match_2 = "fguij";
        assertEquals("fgij", day2.findUniqueCharacters(match_1, match_2));
    }

    @Test
    public void d2_2_solution() {
        assertEquals("icxjvbrobtunlelzpdmfkahgs", day2.solution_2());
    }
}
