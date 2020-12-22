import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day22Tests {


    List<String> createSampleInput() {
        List<String> input = new ArrayList<>();
        input.add("Player 1:");
        input.add("9");
        input.add("2");
        input.add("6");
        input.add("3");
        input.add("1");
        input.add("");
        input.add("Player 2:");
        input.add("5");
        input.add("8");
        input.add("4");
        input.add("7");
        input.add("10");
        return input;
    }

    @Test
    public void part1_sampleInput() {

        int actual = solvePart1(createSampleInput());

        assertEquals(306, actual);
    }

    @Test
    public void part1_solution() {

        int actual = solvePart1(Utilities.fileToStringList("./data/day22-part01"));

        assertEquals(33098, actual);
    }

//---------------------

    private int solvePart1(List<String> input) {
        //from input, generate two lists of hands
        List<Integer> player1Hand = populateHand(input, "Player 1");
        List<Integer> player2Hand = populateHand(input, "Player 2");

        //loop and play hands against each other until one is depleted
        while (gameActive(player1Hand, player2Hand)) {
            play(player1Hand, player2Hand);
        }

        if (player1Hand.isEmpty()) return calculateScoreOf(player2Hand);
        return calculateScoreOf(player1Hand);
    }

    private int calculateScoreOf(List<Integer> hand) {
        int mulitplier = 1;
        int score = 0;

        ListIterator li = hand.listIterator(hand.size());
        while (li.hasPrevious()) {
            int cardValue = (int) li.previous();
            score += mulitplier++ * cardValue;
        }

        return score;
    }

    private void play(List<Integer> player1Hand, List<Integer> player2Hand) {
        int topCard1 = player1Hand.get(0);
        int topCard2 = player2Hand.get(0);

        if (topCard1 > topCard2) {
            player1Hand.remove(0);
            player1Hand.add(topCard1);

            player2Hand.remove(0);
            player1Hand.add(topCard2);
        } else {
            player2Hand.remove(0);
            player2Hand.add(topCard2);

            player1Hand.remove(0);
            player2Hand.add(topCard1);
        }
    }

    private boolean gameActive(List<Integer> player1Hand, List<Integer> player2Hand) {
        return !player1Hand.isEmpty() && !player2Hand.isEmpty();
    }

    private List<Integer> populateHand(List<String> input, String playerID) {
        int index = 0;
        while (!input.get(index++).contains(playerID)) {
        }

        List<Integer> hand = new ArrayList<>();
        while (index < input.size()) {
            String nextLine = input.get(index++).trim();
            if (nextLine.isEmpty()) break;

            hand.add(Integer.valueOf(nextLine));
        }

        return hand;
    }
}
