package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day07 {

    private boolean _part2 = false;

    enum handTypes {fiveOfKind, fourOfKind, fullHouse, threeOfKind, twoPair, onePair, highCard}

    @Test
    void cardValue_for_all_cards_part1() {
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
    void cardValue_for_all_cards_part2() {
        _part2 = true;
        assertEquals(14, cardValue('A'));
        assertEquals(13, cardValue('K'));
        assertEquals(12, cardValue('Q'));
        assertEquals(1, cardValue('J'));
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
    void part2_example() {
        List<String> input = Arrays.asList("32T3K 765", "T55J5 684",
                "KK677 28", "KTJJT 220", "QQQJA 483");

        int actual = part2SolveFor(input);

        assertEquals(5905, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07");

        int actual = part1SolveFor(input);

        assertEquals(246163188, actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day07");

        int actual = part2SolveFor(input);

        assertEquals(245794069, actual);
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
    void determine_hand_joker() {
        _part2 = true;
        assertEquals(handTypes.fourOfKind, determineHand("T55J5"));
        assertEquals(handTypes.fourOfKind, determineHand("KTJJT"));
        assertEquals(handTypes.fourOfKind, determineHand("QQQJA"));
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
        return Integer.compare(hand2Type.ordinal(), hand1Type.ordinal());
    }

    private int compareCard(char c1, char c2) {
        // should only be called when they are different
        // TODO - tests for all these cases?
        if ('2' <= c1 && c1 <= '9' &&
                '2' <= c2 && c2 <= '9') {
            return Character.compare(c1, c2);
        }

        int cardNum1 = cardValue(c1);
        int cardNum2 = cardValue(c2);

        return Integer.compare(cardNum1, cardNum2);
    }

    private int cardValue(char card) {
        switch (card) {
            case 'T':
                return 10;
            case 'J':
                if (_part2) {
                    return 1;
                } else {
                    return 11;
                }
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
        handTypes handType = determineHand_part1(hand);
        if (!_part2) {
            return handType;
        }

        long jokerCount = hand.chars().filter(ch -> ch == 'J').count();
        if (jokerCount == 0) {
            return handType;
        }

        if (jokerCount == 1) {
            switch (handType) {
                case highCard -> {
                    return handTypes.onePair;
                }
                case onePair -> {
                    return handTypes.threeOfKind;
                }
                case twoPair -> { return handTypes.fullHouse; }
                case threeOfKind -> {
                    return handTypes.fourOfKind;
                }
                case fullHouse -> throw new IllegalArgumentException("5 got here - hand is [" + hand + "] joker count is " + jokerCount);
                case fourOfKind ->  { return handTypes.fiveOfKind; }
                case fiveOfKind -> throw new IllegalArgumentException("7 got here - hand is [" + hand + "] joker count is " + jokerCount);
            }
        }
        if (jokerCount == 2) {
            switch (handType) {
                case highCard -> throw new IllegalArgumentException("1- got here - hand is [" + hand + "] joker count is " + jokerCount);
                case onePair -> {
                    return handTypes.threeOfKind;
                }

                case twoPair -> {
                    return handTypes.fourOfKind;
                }
                case threeOfKind -> throw new IllegalArgumentException("4- got here - hand is [" + hand + "] joker count is " + jokerCount);
                case fullHouse ->  { return handTypes.fiveOfKind; }
                case fourOfKind -> throw new IllegalArgumentException("6- got here - hand is [" + hand + "] joker count is " + jokerCount);
                case fiveOfKind -> throw new IllegalArgumentException("7- got here - hand is [" + hand + "] joker count is " + jokerCount);
            }
        }

        if (jokerCount == 3) {
            switch (handType) {
                case highCard -> throw new IllegalArgumentException("1--- got here - hand is [" + hand + "] joker count is " + jokerCount);
                case onePair ->  throw new IllegalArgumentException("2--- got here - hand is [" + hand + "] joker count is " + jokerCount);
                case twoPair -> throw new IllegalArgumentException("3--- got here - hand is [" + hand + "] joker count is " + jokerCount);
                case threeOfKind -> {
                    return handTypes.fourOfKind;
                }
                case fullHouse -> { return handTypes.fiveOfKind; }
                case fourOfKind -> throw new IllegalArgumentException("6--- got here - hand is [" + hand + "] joker count is " + jokerCount);
                case fiveOfKind -> throw new IllegalArgumentException("7--- got here - hand is [" + hand + "] joker count is " + jokerCount);
            }
        }

        if (jokerCount == 4 || jokerCount == 5) {
            return handTypes.fiveOfKind;
        }

        throw new IllegalArgumentException(" got here - hand is [" + hand + "] joker count is " + jokerCount);
    }

    private static handTypes determineHand_part1(String hand) {

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

        List<String> rankedHands = new ArrayList<>();

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
    }

    private int part2SolveFor(List<String> input) {
        _part2 = true;
        return part1SolveFor(input);
    }
}
