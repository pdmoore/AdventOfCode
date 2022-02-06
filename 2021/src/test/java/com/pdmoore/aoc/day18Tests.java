package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
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

        String actual = add(input);

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
    @Disabled
    void part1_addAndReduce() {
        List<String> input = Arrays.asList("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]", "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]");

        String actual = addAndReduce(input);

        String expected = "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]";
        assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void part1_SlightlyLargerExample() {
        /*
[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
[7,[5,[[3,8],[1,4]]]]
[[2,[2,2]],[8,[8,1]]]
[2,9]
[1,[[[9,3],9],[[9,0],[0,7]]]]
[[[5,[7,4]],7],1]
[[[[4,2],2],6],[8,7]]

final sum == [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]
         */
    }

    private String addAndReduce(List<String> input) {
        String lhs = input.get(0);
        String rhs = input.get(1);

        String added = add(lhs, rhs);

        return reduce(added);
    }

    private String add(List<String> input) {
        String lhs = input.get(0);
        String rhs = input.get(1);
        return add(lhs, rhs);
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

    // TODO - add number to number
    // TODO - reduce a number
    // TODO - reduce via explode
    // TODO - reduce via split
    // TODO - part1 example
    // TODO - part 1 solution


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
        String result = input;
        System.out.println("reduc: " + result.toString());

        while (true) {
            String beforeActions = result;

            result = attemptExplode(result);

            if (beforeActions.equals(result)) {
                result = attemptSplit(result);
            }

            if (beforeActions.equals(result)) {
                return result;
            }
        }
    }

    private String attemptExplode(String input) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        int openBrackCount = 0;
        int indexOfLeftRegularNumber = 0;

        // just copy and transform as I go
        while (i < input.length()) {
            if (input.charAt(i) == '[') openBrackCount++;
            else if (input.charAt(i) == ']') openBrackCount--;
            else if (Character.isDigit(input.charAt(i))) indexOfLeftRegularNumber = i;

            if (openBrackCount == 5) {

                int closeOfFifthPair = input.indexOf(']', i);

                String explodingPair = input.substring(i + 1, closeOfFifthPair);
                String[] pairValues = explodingPair.split(",");
                int leftElement = Integer.parseInt(pairValues[0]);
                int rightElement = Integer.parseInt(pairValues[1]);

                if (indexOfLeftRegularNumber > 0) {
                    String leftPortion = input.substring(0, indexOfLeftRegularNumber);

                    String remainder = "";
                    if (result.length() > indexOfLeftRegularNumber) {
                        remainder = result.substring(indexOfLeftRegularNumber + 1);
                    }

                    result = new StringBuilder();
                    result.append(leftPortion);

                    int leftRegularNumber = Integer.parseInt(String.valueOf(input.charAt(indexOfLeftRegularNumber)));
                    result.append(leftRegularNumber + leftElement);
//                    result.append(",");   // May be , may be [ -- need to check what it is in the input
                    result.append(remainder);
                }

                result.append("0");

                int j = closeOfFifthPair + 1;
                while (j < input.length()) {
                    if (Character.isDigit(input.charAt(j))) {
                        int rightRegularNumber = Integer.parseInt(String.valueOf(input.charAt(j)));
                        result.append(rightRegularNumber + rightElement);
                        result.append(input.substring(j + 1));
                        return result.toString();

                    } else {
                        result.append(input.charAt(j));
                    }
                    j++;
                }

                System.out.println("expld: " + result.toString());
                return result.toString();
            } else {
                result.append(input.charAt(i));
            }

            i++;
        }

        return result.toString();
    }

    private String attemptSplit(String input) {
        // if any regular number is 10 or greater, the leftmost such regular number splits
        // regular number is replaced with pair
        // left element is regular number divided by two, rounded down
        // right element is regular number divided by two, rounded up
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            // Might need to check for (i +1) < input.length when nothing needs split
            if (Character.isDigit(input.charAt(i)) &&
                    Character.isDigit(input.charAt(i + 1))) {

                int splitThis = Integer.parseInt(input.substring(i, i + 2));
                int newLeft = Math.floorDiv(splitThis, 2);
                int newRight = (int)Math.ceil((double)splitThis / 2);
                result.append("[");
                result.append(newLeft);
                result.append(",");
                result.append(newRight);
                result.append("]");

                result.append(input.substring(i + 2));

                System.out.println("split: " + result.toString());
                return result.toString();
            } else {
                result.append(input.charAt(i));
            }

            i++;
        }

        return result.toString();
    }
}
