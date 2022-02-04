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

        String actual = reduce(resultOfAddition);

        String expected = "[7,[6,[5,[7,0]]]]";
        assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void singleExplode_RegularNumberLeft_AndRight() {
        String resultOfAddition = "[[6,[5,[4,[3,2]]]],1]";

        String actual = reduce(resultOfAddition);

        // TODO - FAILING
        // Current logic is handles either LEFT or RIGHT case
        // Maybe RIGHT logic needs to know if Left logic happened and not add the magic 2
        // on line 212

        String expected = "[[6,[5,[7,0]]],3]";
        assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void part1_example_addition_and_reduction() {
        List<String> input = Arrays.asList("[[[[4,3],4],4],[7,[[8,4],9]]]", "[1,1]");

        String actual = addAndReduce(input);

        String expected = "[[[[0,7],4],[7,8],[6,0]]],[8,1]]";
        assertEquals(expected, actual);
    }

    private String addAndReduce(List<String> input) {
        // Add first to second, AND Reduce that result,  then third, etc to end of input

        return "not implemented yet";
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

        while (true) {
            String beforeActions = result;

            // check entire string for explode action
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
        // if any pair is nested inside four pairs, the leftmost such pair explodes

        // the pairs left value is added to the first regular number to the left of the exploding pair (if any)
        // the pairs right value is added to the first regular number to the right of the exploding pair (if any)
        // then the entire exploding pair is replaced with regular number 0

        // running count of left brackets - when it hits 4, then action
        String exploded = input;
        int leftBracketCount = 0;
        int i = 0;
        int regularNumberToLeft = -1;
        while (i < input.length()) {
            if (input.charAt(i) == '[') leftBracketCount++;
            else if (input.charAt(i) == ']') leftBracketCount--;
            else if (Character.isDigit(input.charAt(i))) regularNumberToLeft = i;

            if (leftBracketCount == 5) {
                StringBuilder result = new StringBuilder();
                //TODO - Here's where I left off. I've detected a pair that needs exploded.
                // read through below and implement
                //ACTION!
                // find values inside this pair [leftElement, rightElement]
                // check if regularNumberToLeft is > -1 and add leftElement to it and replace in position
                // look for a regular number to the right of this pair
                // if there is one, add rightElement to it and replace in position
                // return the resulting string
                //[7,[6,[5,[7,0]]]]
                //         i
                int closingBracketIndex = input.indexOf(']', i);
                String thisPair = input.substring(i + 1, closingBracketIndex);
                String[] pairValues = thisPair.split(",");

                boolean hasRegularNumberToLeft = regularNumberToLeft != -1;
                int digitToRightIndex = closingBracketIndex + 1;
                while (digitToRightIndex < input.length() &&
                        !Character.isDigit(input.charAt(digitToRightIndex))) {
                    digitToRightIndex++;
                }
                boolean hasRegularNumberToRight = digitToRightIndex < input.length();

                if (!hasRegularNumberToLeft) {
                    result.append(input.substring(0, i));
                } else {
                    result.append(input.substring(0, regularNumberToLeft));

                    // MAY NOT WORK IF regNumLeft index points to something > 10
                    int valueToLeft = Integer.parseInt("" + input.charAt(regularNumberToLeft));
                    int leftElement = Integer.parseInt(pairValues[0]);
                    int newLeftValue = valueToLeft + leftElement;
                    result.append(newLeftValue);
                    result.append(",");
                }

                result.append("0"); // replaces the exloded pair - DO I NEED , or ]???

                // HANDLE RIGHT SIDE STUFF
                if (hasRegularNumberToRight) {
                    // MAY NOT WORK IF digitToRight is > 10
                    int valueToRight = Integer.parseInt("" + input.charAt(digitToRightIndex));
                    int rightElement = Integer.parseInt(pairValues[1]);
                    int newRightValue = rightElement + valueToRight;
                    result.append(",");
                    result.append(newRightValue);
                    result.append(input.substring(closingBracketIndex + 1 + 2));
                } else {
                    result.append(input.substring(closingBracketIndex + 1));
                }


                return result.toString();
            }

            i++;
        }

        return input;
    }

    private String attemptSplit(String input) {
        // if any regular number is 10 or greater, the leftmost such regular number splits
        // regular number is replaced with pair
        // left element is regular number divided by two, rounded down
        // right element is regular number divided by two, rounded up
        return input;
    }
}
