import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day06Tests {

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

    private int part2Solution(List<String> input) {
        int sum = 0;

        List<String> group = new ArrayList<>();
        for (String s :
                input) {

            if (s.isEmpty()) {
                int result = commonAnswersOf(group);
                sum += result;
                group = new ArrayList<>();
            } else {
                group.add(s);
            }
        }

        if (!group.isEmpty()) {
            sum += commonAnswersOf(group);
        }

        return sum;
    }


    @Test
    public void part1_solution() {
        List<String> input = Utilities.fileToStringList("./data/day06-part01");

        int result = part1Solution(input);

        assertEquals(7128, result);
    }

    private int uniqueAnswersOf(List<String> input) {
        Set<Character> answers = uniqueCharactersIn(input);

        return answers.size();
    }

    private Set<Character> uniqueCharactersIn(List<String> input) {
        Set<Character> answers = new HashSet<>();
        for (String s :
                input) {
            for (char c :
                    s.toCharArray()) {
                answers.add(c);
            }
        }
        return answers;
    }


    private int uniqueAnswersOf(String input) {
        Set<Character> answers = new HashSet<>();

        for (char c :
                input.toCharArray()) {
            answers.add(c);
        }

        return answers.size();
    }


    @Test
    public void part2_onePersonThreeAnswers() {
        List<String> input = new ArrayList<>();
        input.add("abc");

        int result = commonAnswersOf(input);
        assertEquals(3, result);
    }

    @Test
    public void part2_threePeopleThreeAnswers() {
        List<String> input = new ArrayList<>();
        input.add("a");
        input.add("b");
        input.add("c");
        input.add("");

        int result = commonAnswersOf(input);
        assertEquals(0, result);
    }

    @Test
    public void part2_twoPeopleOneAnswer() {
        List<String> input = new ArrayList<>();
        input.add("ab");
        input.add("ac");
        input.add("");

        int result = commonAnswersOf(input);
        assertEquals(1, result);
    }

    @Test
    public void part2_fourPeopleOneAnswer() {
        List<String> input = new ArrayList<>();
        input.add("a");
        input.add("a");
        input.add("a");
        input.add("a");
        input.add("");

        int result = commonAnswersOf(input);
        assertEquals(1, result);
    }

    @Test
    public void part2_onePersonOneAnswer() {
        List<String> input = new ArrayList<>();
        input.add("b");

        int result = commonAnswersOf(input);
        assertEquals(1, result);
    }

    @Test
    public void part2_simpleExample() {
        List<String> input = new ArrayList<>();
        input.add("a");
        input.add("");
        input.add("ab");
        input.add("ac");

        int result = part2Solution(input);
        assertEquals(2, result);
    }

    @Test
    public void part2_fullExample() {
        List<String> input = new ArrayList<>();
        input.add("abc");
        input.add("");
        input.add("a");
        input.add("b");
        input.add("c");
        input.add("");
        input.add("ab");
        input.add("ac");
        input.add("");
        input.add("a");
        input.add("a");
        input.add("a");
        input.add("a");
        input.add("");
        input.add("b");

        int result = part2Solution(input);
        assertEquals(6, result);
    }

    @Test
    public void part2_solution() {
        List<String> input = Utilities.fileToStringList("./data/day06-part01");

        int result = part2Solution(input);

        // getting result of 3657, 3650 which is too high
        // eyeballing it all looks good

        assertEquals(-99, result);
    }


    private int commonAnswersOf(List<String> input) {
//        System.out.println(input);

        // when only one person answers, just collect their responses
        if (input.size() == 1) {
            int i = uniqueAnswersOf(input);
            return i;
        }

        Set<Character> answers = new TreeSet<Character>();

        String first = input.get(0);
        for (int i = 1; i < input.size(); i++) {

            if (!input.get(i).isEmpty()) {

                if (answers.isEmpty()) {
                    answers.addAll(uniqueCharactersIn(first));
                    answers.retainAll(uniqueCharactersIn(input.get(i)));
                    if (answers.isEmpty()) {
                        return 0;
                    }
                } else {
                    answers.retainAll(uniqueCharactersIn(input.get(i)));
                }

            }
        }

//        System.out.println("answer: " +  answers + "    " + answers.size());

        return answers.size();
    }

    private Collection<Character> uniqueCharactersIn(String s) {
        Set<Character> chars = new TreeSet<>();
        for (char c : s.toCharArray()) {
            chars.add(c);
        }

        return chars;
    }

    private Set<Character> part2AnswersFor(List<String> group) {
        String allChars = "";
        for (String s :
                group) {
            allChars = allChars.concat(s);
        }

        Set<Character> chars = new TreeSet<>();
        for (char c : allChars.toCharArray()) {
            chars.add(c);
        }

        return chars;
    }


}
