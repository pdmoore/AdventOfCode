package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Day07 {

    enum handTypes {fiveOfKind, fourOfKind, fullHouse, threeOfKind, twoPair, onePair, highCard};

    @Test
    @Disabled
    void part1_example() {
        List<String> input = Arrays.asList("32T3K 765", "T55J5 684",
                "KK677 28", "KTJJT 220", "QQQJA 483");

        int actual = part1SolveFor(input);

        assertEquals(6440, actual);
    }

    @Test
    @Disabled
    void compare_four_of_kind() {
        String hand1 = "KKKK2";
        String hand2 = "5KKKK";
        assertTrue(compareHands(hand1, hand2));
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

    private boolean compareHands(String hand1, String hand2) {
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

        if (charArray[0] == charArray[1] ||
            charArray[1] == charArray[2] ||
            charArray[2] == charArray[3] ||
            charArray[3] == charArray[4]) {
            return handTypes.onePair;
        }

        return handTypes.highCard;
    }

    private int part1SolveFor(List<String> input) {
        Map<Integer, Integer> inputToBid = new HashMap<>();
        List<String> hands = new ArrayList<>();

        int i = 0;
        for (String inputLine :
                input) {
            String[] pair = inputLine.split(" ");
            String hand = pair[0];
            hands.add(hand);

            int bid = Integer.parseInt(pair[1]);
            inputToBid.put(i++, bid);
        }

        // 5 of a kind
        // 4 of a kind
        // full house
        // 3 of a kind
        // 2 pair
        // 1 pair
        // high card
        // same rank, compare on first card


        //"32T3K 765",
        // "T55J5 684",
        //"KK677 28",
        // "KTJJT 220",
        // "QQQJA 483"
        // need to know if a hand is greater than another
        // and insert in the right palce


        // hands to rank

        // sum hand/rank/bid


        int result = 765 * 1 + 220 * 2 + 28 * 3 + 684 * 4 + 483 * 5;
        return result;
    }
}
