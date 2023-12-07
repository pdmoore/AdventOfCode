package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day07 {

    enum handTypes {fiveOfKind, fourOfKind, fullHouse, threeOfKind, twoPair, onePair, highCard};

    @Test
    void cardValue_for_all_cards() {
        assertEquals(14, cardValue('A'));
        assertEquals(13, cardValue('K'));
        assertEquals(12, cardValue('Q'));
        assertEquals(11, cardValue('J'));
        assertEquals(10, cardValue('T'));
        assertEquals(9, cardValue('9'));
        assertEquals(5, cardValue('5'));
        assertEquals(2, cardValue('2'));
    }

    @Test
    void part1_example() {
        List<String> input = Arrays.asList("32T3K 765", "T55J5 684",
                "KK677 28", "KTJJT 220", "QQQJA 483");

        int actual = part1SolveFor(input);

        assertEquals(6440, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07");

        int actual = part1SolveFor(input);

        assertEquals(246163188, actual);
    }



    @Test
    void compare_four_of_kind() {
        String hand1 = "KKKK2";
        String hand2 = "5KKKK";
        assertTrue(areHandsEqual(hand1, hand2));
    }

    @Test
    void determine_hand_5ofakind() {
        String hand1 = "KKKKK";
        handTypes actual = determineHand(hand1);
        assertEquals(handTypes.fiveOfKind, actual);
    }

    @Test
    void determine_hand_4ofakind() {
        assertEquals(handTypes.fourOfKind, determineHand("KKKK2"));
        assertEquals(handTypes.fourOfKind, determineHand("2KKKK"));
    }

    @Test
    void determine_hand_fullHouse() {
        assertEquals(handTypes.fullHouse, determineHand("22KKK"));
        assertEquals(handTypes.fullHouse, determineHand("222KK"));
        assertEquals(handTypes.fullHouse, determineHand("3Q3QQ"));
    }

    @Test
    void determine_hand_3ofakind() {
        assertEquals(handTypes.threeOfKind, determineHand("K3335"));
        assertEquals(handTypes.threeOfKind, determineHand("KK3K5"));
        assertEquals(handTypes.threeOfKind, determineHand("2KK3K"));
        assertEquals(handTypes.threeOfKind, determineHand("KKKT2"));
    }

    @Test
    void determine_hand_2pair() {
        assertEquals(handTypes.twoPair, determineHand("K4422"));
        assertEquals(handTypes.twoPair, determineHand("22344"));
        assertEquals(handTypes.twoPair, determineHand("23244"));
        assertEquals(handTypes.twoPair, determineHand("32244"));
        assertEquals(handTypes.twoPair, determineHand("42K24"));
        assertEquals(handTypes.twoPair, determineHand("KK677"));
    }

    @Test
    void determine_hand_1pair() {
        assertEquals(handTypes.onePair, determineHand("44689"));
        assertEquals(handTypes.onePair, determineHand("44289"));
        assertEquals(handTypes.onePair, determineHand("84289"));
        assertEquals(handTypes.onePair, determineHand("94289"));
        assertEquals(handTypes.onePair, determineHand("94982"));
    }

    @Test
    void determine_hand_highCard() {
        assertEquals(handTypes.highCard, determineHand("K2856"));
    }

    @Test
    void compare_different_hands() {
        String highCard = "2468K";
        String onePair = "KQT99";
        String twoPair = "QQT99";
        String threeKind = "QQTQ9";
        String fullHouse = "QQTQT";
        String fourKind = "QQTQQ";
        String fiveKind = "QQQQQ";

        assertEquals(1, compareHands(onePair, highCard));
        assertEquals(1, compareHands(twoPair, onePair));
        assertEquals(1, compareHands(threeKind, twoPair));
        assertEquals(1, compareHands(fullHouse, threeKind));
        assertEquals(1, compareHands(fourKind, fullHouse));
        assertEquals(1, compareHands(fiveKind, fourKind));
    }

    @Test
    void compareTo_same_hand_twoPair() {
        // KK677 and KTJJT are both two pair.
        // Their first cards both have the same label,
        // but the second card of KK677 is stronger (K vs T), so KTJJT gets rank 2 and KK677 gets rank 3.
        String hand1 = "KK677";
        String hand2 = "KTJJT";

        assertEquals(1, compareHands(hand1, hand2));
        assertEquals(-1, compareHands(hand2, hand1));
    }

    private int compareHands(String hand1, String hand2) {

        handTypes hand1Type = determineHand(hand1);
        handTypes hand2Type = determineHand(hand2);

        if (hand1Type == hand2Type) {
            for (int i = 0; i < 5; i++) {
                if (hand1.charAt(i) != hand2.charAt(i)) {
                    return compareCard(hand1.charAt(i), hand2.charAt(i));
                }
            }

            throw new IllegalArgumentException("Hands are equal, type, couldn't find different card " + hand1 + "-" + hand2);
        }

        // hand2 comes first since enum is highest to lowest
        return new Integer(hand2Type.ordinal()).compareTo(hand1Type.ordinal());
    }

    private int compareCard(char c1, char c2) {
        // should only be called when they are different
        // TODO - tests for all these cases?
        if ('2' <= c1 && c1 <= '9' &&
                '2' <= c2 && c2 <= '9') {
            return new Character(c1).compareTo(c2);
        }

        int cardNum1 = cardValue(c1);
        int cardNum2 = cardValue(c2);

        return new Integer(cardNum1).compareTo(cardNum2);
    }

    private int cardValue(char card) {
        switch (card) {
            case 'T':
                return 10;
            case 'J':
                return 11;
            case 'Q':
                return 12;
            case 'K':
                return 13;
            case 'A':
                return 14;
        }

        if ('2' <= card && card <= '9')
            return Integer.parseInt(String.valueOf(card));

        throw new IllegalArgumentException("how'd this card get here? " + card);
    }


    private boolean areHandsEqual(String hand1, String hand2) {
        handTypes hand1Type = determineHand(hand1);
        handTypes hand2Type = determineHand(hand2);
        return hand1Type == hand2Type;
    }

    private handTypes determineHand(String hand) {
        if (hand.charAt(0) == hand.charAt(1) &&
                hand.charAt(1) == hand.charAt(2) &&
                hand.charAt(2) == hand.charAt(3) &&
                hand.charAt(3) == hand.charAt(4)) {
            return handTypes.fiveOfKind;
        }

        char[] charArray = hand.toCharArray();
        Arrays.sort(charArray);

        if (charArray[0] == charArray[1] &&
                charArray[1] == charArray[2] &&
                charArray[2] == charArray[3]) {
            return handTypes.fourOfKind;
        }

        if (charArray[1] == charArray[2] &&
                charArray[2] == charArray[3] &&
                charArray[3] == charArray[4]) {
            return handTypes.fourOfKind;
        }

        if (charArray[0] == charArray[1] &&
                charArray[2] == charArray[3] &&
                charArray[3] == charArray[4]) {
            return handTypes.fullHouse;
        }

        if (charArray[0] == charArray[1] &&
                charArray[1] == charArray[2] &&
                charArray[3] == charArray[4]) {
            return handTypes.fullHouse;
        }

        if (charArray[0] == charArray[1] &&
                charArray[1] == charArray[2]) {
            return handTypes.threeOfKind;
        }

        if (charArray[1] == charArray[2] &&
                charArray[2] == charArray[3]) {
            return handTypes.threeOfKind;
        }

        if (charArray[2] == charArray[3] &&
                charArray[3] == charArray[4]) {
            return handTypes.threeOfKind;
        }

        if (charArray[0] == charArray[1] && charArray[3] == charArray[4]) {
            return handTypes.twoPair;
        }

        if (charArray[0] == charArray[1] && charArray[2] == charArray[3]) {
            return handTypes.twoPair;
        }

        if (charArray[1] == charArray[2] && charArray[3] == charArray[4]) {
            return handTypes.twoPair;
        }

        if (charArray[0] == charArray[1] ||
                charArray[1] == charArray[2] ||
                charArray[2] == charArray[3] ||
                charArray[3] == charArray[4]) {
            return handTypes.onePair;
        }

        return handTypes.highCard;
    }

    private int part1SolveFor(List<String> input) {
        Map<String, Integer> inputToBid = new HashMap<>();

        for (String inputLine :
                input) {
            String[] pair = inputLine.split(" ");
            String hand = pair[0];

            int bid = Integer.parseInt(pair[1]);
            inputToBid.put(hand, bid);
        }


        //"32T3K 765",
        // "T55J5 684",
        //"KK677 28",
        // "KTJJT 220",
        // "QQQJA 483"


        // hands to rank

        List<String> rankedHands = new ArrayList();

        for (String hand :
                inputToBid.keySet()) {
            boolean added = false;
            if (rankedHands.isEmpty()) {
                rankedHands.add(hand);
            } else {


                for (int j = 0; j < rankedHands.size(); j++) {
                    if (compareHands(hand, rankedHands.get(j)) < 0) {
                        rankedHands.add(j, hand);
                        added = true;
                        break;
                    }
                }

                if (!added) {
                    rankedHands.add(hand);
                }

            }
        }


        // sum hand/rank/bid
        int sum = 0;
        for (int i = 0; i < rankedHands.size(); i++) {
            sum += (i + 1) * inputToBid.get(rankedHands.get(i));
        }

        return sum;
//        int result = 765 * 1 + 220 * 2 + 28 * 3 + 684 * 4 + 483 * 5;
//        return result;
    }
}
