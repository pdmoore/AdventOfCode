import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day06Tests {


    /*


abc

a
b
c

ab
ac

a
a
a
a

b

things separated by a line are grouped together
unique those things
sum the unique counts

     */

    @Test
    public void onePersonThreeAnswers() {

        String input = "abc";
        int result = uniqueAnswersOf(input);
        assertEquals(3, result);
    }

    @Test
    public void threePeopleThreeAnswers() {
        List<String> input = new ArrayList<>();
        input.add("a");
        input.add("b");
        input.add("c");

        int result = uniqueAnswersOf(input);
        assertEquals(3, result);
    }

    @Test
    public void twoPeopleOverlappingAnswers() {
        List<String> input = new ArrayList<>();
        input.add("ab");
        input.add("ac");

        int result = uniqueAnswersOf(input);
        assertEquals(3, result);
    }

    @Test
    public void fourPeopleOverlappingAnswers() {
        List<String> input = new ArrayList<>();
        input.add("a");
        input.add("a");
        input.add("a");
        input.add("a");

        int result = uniqueAnswersOf(input);
        assertEquals(1, result);
    }

    @Test
    public void part1_simpleExample() {
        List<String> input = new ArrayList<>();
        input.add("a");
        input.add("");
        input.add("ac");

        int result = part1Solution(input);
        assertEquals(3, result);
    }

    private int part1Solution(List<String> input) {
        int sum = 0;

        List<String> group = new ArrayList<>();
        for (String s :
                input) {

            if (s.isEmpty()) {
                int result = uniqueAnswersOf(group);
                sum += result;
                System.out.println(result);
                group = new ArrayList<>();
            } else {
                group.add(s);
            }
        }

        if (!group.isEmpty()) {
            sum += uniqueAnswersOf(group);
        }

        return sum;
    }


    @Test
    public void part1_solution() {
        List<String> input = Utilities.fileToStringList("./data/day06-part01");

        int result = part1Solution(input);

        // 7148 not right
        assertEquals(0, result);
    }


    private int uniqueAnswersOf(List<String> input) {
        Set<Character> answers = new HashSet<>();
        for (String s :
                input) {
            for (char c :
                    s.toCharArray()) {
                answers.add(c);
            }
        }

        return answers.size();
    }


    private int uniqueAnswersOf(String input) {
        Set<Character> answers = new HashSet<>();

        for (char c :
                input.toCharArray()) {
            answers.add(c);
        }

        return answers.size();
    }
}
