package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04 {

    @Test
    void part1_score_single_scratch_card() {
        String input = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";

        int actual = scoreScratchCard(input);

        assertEquals(8, actual);
    }

    @Test
    void card_number_from() {
        String input = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53";

        int actual = cardNumberFrom(input);

        assertEquals(1, actual);
    }

    private int cardNumberFrom(String input) {
        String[] firstSplit = input.split(":");
        String secondSplit = firstSplit[0].substring(5);
        return Integer.parseInt(secondSplit.trim());
    }

    @Test
    void part1_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04_part1_example");

        int actual = solvePart1(input);

        assertEquals(13, actual);
    }

    @Test
    void part2_example() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04_part1_example");

        int actual = solvePart2(input);

        assertEquals(30, actual);
    }

    @Test
    void part1_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04");

        int actual = solvePart1(input);

        assertEquals(18619, actual);
    }

    @Test
    void part2_solution() {
        List<String> input = PuzzleInput.asStringListFrom("./data/day04");

        int actual = solvePart2(input);

        assertEquals(8063216, actual);
    }

// ---------------------------------------

    static class ScratchCard {
        int cardNumber;
        int numMatches;
        int countOfThisCard;

        public ScratchCard(int cardNumber, Integer matches) {
            this.cardNumber = cardNumber;
            this.numMatches = matches;
            this.countOfThisCard = 1;
        }
    }

    private int solvePart2(List<String> input) {
        Map<Integer, Integer> cardNumberToNumMatches = findMatchesForCards(input);

        ArrayList<String> cardsLeftToProcess = new ArrayList<>();
        cardsLeftToProcess.addAll(input);

        Map<Integer, ScratchCard> cardsByCount = new HashMap<>();
        for (int i = 1; i <= cardsLeftToProcess.size(); i++) {
            ScratchCard next = new ScratchCard(i, cardNumberToNumMatches.get(i));
            cardsByCount.put(i, next);
        }

        while (!cardsLeftToProcess.isEmpty()) {
            String nextCard = cardsLeftToProcess.remove(0);
            int cardNumber = cardNumberFrom(nextCard);
            int numMatches = cardNumberToNumMatches.get(cardNumber);

            ScratchCard currentCard = cardsByCount.get(cardNumber);
            for (int i = 0; i < currentCard.countOfThisCard; i++) {
                for (int j = 0; j < numMatches; j++) {
                    ScratchCard scratchCard = cardsByCount.get(cardNumber + j + 1);
                    scratchCard.countOfThisCard += 1;
                }

            }
        }

        return cardsByCount.values().stream().mapToInt(r -> r.countOfThisCard).sum();
    }

    private Map<Integer, Integer> findMatchesForCards(List<String> input) {
        Map<Integer, Integer> result = new HashMap<>();

        int i = 1;
        for (String inputLine :
                input) {
            int numberMatches = matchCountForCard(inputLine);
            result.put(i++, numberMatches);
        }

        return result;
    }

    private int matchCountForCard(String inputLine) {
        String[] firstSplit = inputLine.split(":");
        String[] secondSplit = firstSplit[1].split("\\|");

        List<String> winningNumbers = Arrays.asList(secondSplit[0].split(" "));
        List<String> numbersYouHave = Arrays.asList(secondSplit[1].split(" "));

        int count = 0;
        for (String numberYouHave :
                numbersYouHave) {
            if (!numberYouHave.isEmpty() && winningNumbers.contains(numberYouHave)) {
                count++;
            }
        }
        return count;
    }

    private int solvePart1(List<String> input) {
        int sum = 0;
        for (String inputLine :
                input) {
            sum += scoreScratchCard(inputLine);
        }
        return sum;
    }

    private int scoreScratchCard(String input) {
        String[] firstSplit = input.split(":");
        String[] secondSplit = firstSplit[1].split("\\|");

        List<String> winningNumbers = Arrays.asList(secondSplit[0].split(" "));
        List<String> numbersYouHave = Arrays.asList(secondSplit[1].split(" "));

        int count = 0;
        for (String numberYouHave :
                numbersYouHave) {
            if (!numberYouHave.isEmpty() && winningNumbers.contains(numberYouHave)) {
                count++;
            }
        }

       // split on colon, only retain second half
        // split second half on '|'
        // [0] is the winning numbers - convert to list
        // [1] is numbers you have - for each instance in winning numbers, score is 2^(num matches - 1)

        return (int) Math.pow(2, count - 1);
    }
}
