package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day18Tests {

    @Test
    void part1_magnitude_examples() {
        assertEquals(29, magnitudeOf("[9,1]"));
        assertEquals(129, magnitudeOf("[[9,1],[1,9]]"));
        assertEquals(143, magnitudeOf("[[1,2],[[3,4],5]]"));
        assertEquals(1384, magnitudeOf("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"));
        assertEquals(445, magnitudeOf("[[[[1,1],[2,2]],[3,3]],[4,4]]"));
        assertEquals(791, magnitudeOf("[[[[3,0],[5,3]],[4,4]],[5,5]]"));
        assertEquals(1137, magnitudeOf("[[[[5,0],[7,4]],[5,5]],[6,6]]"));
        assertEquals(3488, magnitudeOf("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"));
        assertEquals(4140, magnitudeOf("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]"));
    }

    @Test
    void breakApart() {
        String[] expected = {"[1,2]", "[[3,4],5]"};
        String[] actual = breakApart("[[1,2],[[3,4],5]]");
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);

        String[] expected2 = {"[3,4]", "5"};
        actual = breakApart("[[3,4],5]");
        assertEquals(expected2[0], actual[0]);
        assertEquals(expected2[1], actual[1]);
    }

    @Test
    void singleExplode_noRegularNumberLeft_OnlyRight() {
        String resultOfAddition = "[[[[[9,8],1],2],3],4]";

        String actual = reduce(resultOfAddition);

        String expected = "[[[[0,9],2],3],4]";
        assertEquals(expected, actual);
    }

    @Test
    void singleExplode_RegularNumberLeft_NotRight() {
        String resultOfAddition = "[7,[6,[5,[4,[3,2]]]]]";

        String actual = attemptExplode(resultOfAddition);

        String expected = "[7,[6,[5,[7,0]]]]";
        assertEquals(expected, actual);
    }

    @Test
    void singleExplode_RegularNumberLeft_AndRight() {
        String resultOfAddition = "[[6,[5,[4,[3,2]]]],1]";

        String actual = attemptExplode(resultOfAddition);

        String expected = "[[6,[5,[7,0]]],3]";
        assertEquals(expected, actual);
    }

    @Test
    void singleExplode_DoNotExplodePairOnTheRight() {
        String resultOfAddition = "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]";

        String actual = attemptExplode(resultOfAddition);

        String expected = "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]";
        assertEquals(expected, actual);
    }

    @Test
    void singleExplode_ExplodePairOnFarRight() {
        String resultOfAddition = "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]";

        String actual = attemptExplode(resultOfAddition);

        String expected = "[[3,[2,[8,0]]],[9,[5,[7,0]]]]";
        assertEquals(expected, actual);
    }

    @Test
    void explode_regularNumberLargerThan10() {
        String input = "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]";

        String actual = attemptExplode(input);

        String expected = "[[[[0,7],4],[15,[0,13]]],[1,1]]";
        assertEquals(expected, actual);
    }

    @Test
    void part1_split() {
        String input = "[[[[0,7],4],[15,[0,13]]],[1,1]]";

        String actual = attemptSplit(input);

        String expected = "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]";
        assertEquals(expected, actual);
    }

    @Test
    void part1_add_justTwo() {
        List<String> input = Arrays.asList("[1,2]", "[[3,4],5]");

        String actual = add(input.get(0), input.get(1));

        String expected = "[[1,2],[[3,4],5]]";
        assertEquals(expected, actual);
    }

    @Test
    void part1_example_addition_and_reduction() {
        List<String> input = Arrays.asList("[[[[4,3],4],4],[7,[[8,4],9]]]", "[1,1]");

        String actual = addAndReduce(input);

        String expected = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]";
        assertEquals(expected, actual);
    }

    @Test
    void part1_addAndReduce() {
        List<String> input = Arrays.asList("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]", "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]");

        String actual = addAndReduce(input);

        String expected = "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]";
        assertEquals(expected, actual);
    }

    @Test
    void part1_explode_LeftRegularAndLeftElement_greaterThan10() {
        String input = "[[[[12,12],[6,14]],[[15,0],[17,[8,1]]]],[2,9]]";

        String actual = attemptExplode(input);

        String expected = "[[[[12,12],[6,14]],[[15,0],[25,0]]],[3,9]]";
        assertEquals(expected, actual);
    }

    @Test
    void part1_addition_fails_LeftRegularAbove9() {
        List<String> input = Arrays.asList("[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]", "[2,9]");

        String actual = addAndReduce(input);

        String expected = "[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]";
        assertEquals(expected, actual);
    }

    @Test
    void part1_SlightlyLargerExample() {
        List<String> input = PuzzleInput.asStringListFrom("data/day18_part1_largerExample");

        String actual = addAndReduce(input);

        String expected = "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]";
        assertEquals(expected, actual);
    }

    @Test
    void part1_homeworkExample() {
        List<String> input = PuzzleInput.asStringListFrom("data/day18_part1_homeworkExample");

        String actual = addAndReduce(input);

        String expected = "[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]";
        assertEquals(expected, actual);
        assertEquals(4140, magnitudeOf(actual));
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day18");

        String actual = addAndReduce(input);

        assertEquals(4176, magnitudeOf(actual));
    }

    @Test
    void part2_magnitudeCheck() {
        String lhs = "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]";
        String rhs = "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]";

        int actual = largestMagnitudeOf(Arrays.asList(lhs, rhs));

        String expected = "[[[[7,8],[6,6]],[[6,0],[7,7]]],[[[7,8],[8,8]],[[7,9],[0,6]]]]";

        assertEquals(3993, magnitudeOf(expected));
        assertEquals(3993, actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("data/day18_part1_homeworkExample");

        int actual = largestMagnitudeOf(input);

        assertEquals(3993, actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("data/day18");

        int actual = largestMagnitudeOf(input);

        assertEquals(4633, actual);
    }

    private String addAndReduce(String lhs, String rhs) {
        List<String> addends = Arrays.asList(lhs, rhs);
        return addAndReduce(addends);
    }

    private String addAndReduce(List<String> input) {
        String lhs = input.get(0);
        int addWith = 1;
        String result = "";
        while (addWith < input.size()) {
            String rhs = input.get(addWith);
            result = add(lhs, rhs);
            lhs = reduce(result);
            addWith++;
        }

        return lhs;
    }

    private String add(String lhs, String rhs) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(lhs);
        sb.append(",");
        sb.append(rhs);
        sb.append("]");

        return sb.toString();
    }

    private String[] breakApart(String input) {
        String[] result = new String[2];

        int index = 1;
        int bracketBalanceCount = 0;
        while (true) {
            char c = input.charAt(index);
            if (bracketBalanceCount == 0 && c == ',') {
                result[0] = input.substring(1, index);
                result[1] = input.substring(index + 1, input.length() - 1);
                return result;
            }

            if (c == '[') {
                bracketBalanceCount++;
            } else if (c == ']') {
                bracketBalanceCount--;
            }

            index++;
        }
    }

    private int magnitudeOf(String input) {
        if (input.length() == 1) {
            return Integer.parseInt(input);
        }

        String[] split = breakApart(input);
        return (3 * magnitudeOf(split[0])) + (2 * magnitudeOf(split[1]));
    }

    private String reduce(String input) {
        String anyReductionApplied = input;

        while (true) {
            String previousPass = anyReductionApplied;

            anyReductionApplied = attemptExplode(anyReductionApplied);

            if (previousPass.equals(anyReductionApplied)) {
                anyReductionApplied = attemptSplit(anyReductionApplied);
            }

            if (previousPass.equals(anyReductionApplied)) {
                return anyReductionApplied;
            }
        }
    }

    private String attemptExplode(String input) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        int openBrackCount = 0;
        int indexEndOfLeftRegularNumber = 0;

        // just copy and transform as we go
        while (i < input.length()) {
            if (input.charAt(i) == '[') openBrackCount++;
            else if (input.charAt(i) == ']') openBrackCount--;
            else if (Character.isDigit(input.charAt(i))) indexEndOfLeftRegularNumber = i;

            if (openBrackCount < 5) {
                result.append(input.charAt(i));
            } else {

                int closeOfFifthBracket = input.indexOf(']', i);

                String explodingPair = input.substring(i + 1, closeOfFifthBracket);
                String[] pairValues = explodingPair.split(",");
                int leftPairValue = Integer.parseInt(pairValues[0]);
                int rightPairValue = Integer.parseInt(pairValues[1]);

                if (indexEndOfLeftRegularNumber > 0) {

                    int indexBeginOfLeftRegularNumber = indexEndOfLeftRegularNumber;
                    while (Character.isDigit(input.charAt(--indexBeginOfLeftRegularNumber))) {
                    }
                    indexBeginOfLeftRegularNumber++;

                    String remainder = "";
                    if (result.length() > indexEndOfLeftRegularNumber) {
                        remainder = result.substring(indexEndOfLeftRegularNumber + 1);
                    }

                    String leftPortion = input.substring(0, indexBeginOfLeftRegularNumber);
                    result = new StringBuilder();
                    result.append(leftPortion);

                    String leftRegularNumberString = input.substring(indexBeginOfLeftRegularNumber, indexEndOfLeftRegularNumber + 1);
                    int leftRegularNumber = Integer.parseInt(leftRegularNumberString);
                    result.append(leftRegularNumber + leftPairValue);
                    result.append(remainder);
                }

                result.append("0");

                int j = closeOfFifthBracket + 1;
                while (j < input.length()) {
                    if (Character.isDigit(input.charAt(j))) {

                        int k = j + 1;
                        while (Character.isDigit(input.charAt(k))) {
                            k++;
                        }

                        String rightRegularNumberString = input.substring(j, k);
                        int rightRegularNumber = Integer.parseInt(rightRegularNumberString);

                        result.append(rightRegularNumber + rightPairValue);
                        result.append(input.substring(j + rightRegularNumberString.length()));
                        return result.toString();
                    } else {
                        result.append(input.charAt(j));
                    }
                    j++;
                }

                // pair exploded
                return result.toString();
            }
            i++;
        }

        // nothing exploded
        return input;
    }

    private String attemptSplit(String input) {
        // if any regular number is 10 or greater, the leftmost such regular number splits
        // regular number is replaced with pair
        // left element is regular number divided by two, rounded down
        // right element is regular number divided by two, rounded up
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            if (Character.isDigit(input.charAt(i)) &&
                    Character.isDigit(input.charAt(i + 1))) {

                int splitThis = Integer.parseInt(input.substring(i, i + 2));
                int newLeft = Math.floorDiv(splitThis, 2);
                int newRight = (int) Math.ceil((double) splitThis / 2);
                result.append("[");
                result.append(newLeft);
                result.append(",");
                result.append(newRight);
                result.append("]");

                result.append(input.substring(i + 2));

                // something split
                return result.toString();
            } else {
                result.append(input.charAt(i));
            }

            i++;
        }

        // nothing split
        return input;
    }

    private int largestMagnitudeOf(List<String> input) {
        int result = Integer.MIN_VALUE;

        // for each input line
        // add to each other input line and reduce
        // get magnitude and compare to result

        int i = 0;
        while (i <= input.size() - 1) {
            int j = 0;

            while (j <= input.size() - 1) {
                if (i != j) {
                    String lhs = input.get(i);
                    String rhs = input.get(j);

                    String reduced = addAndReduce(lhs, rhs);
                    int magnitude = magnitudeOf(reduced);
                    result = Math.max(result, magnitude);
                }
                j++;
            }
            i++;
        }

        return result;
    }
}
