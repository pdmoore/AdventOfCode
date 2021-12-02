package com.pdmoore.aoc;

import java.util.ArrayList;
import java.util.List;

public class day14 {
    public static List<Integer> createRecipes(List<Integer> recipeScores, int stepCount) {

        int elf1 = 0;
        int elf2 = 1;

        List<Integer> newRecipeScores = new ArrayList<Integer>(recipeScores);
        for (int i = 0; i < stepCount; i++) {
            int newRecipeScore = newRecipeScores.get(elf1) + newRecipeScores.get(elf2);
            newRecipeScores = appendScores(newRecipeScores, newRecipeScore);

            elf1 = calcNextIndex(newRecipeScores, elf1);
            elf2 = calcNextIndex(newRecipeScores, elf2);
        }

        return newRecipeScores;
    }

    public static List<Integer> appendScores(List<Integer> recipeScores, int score) {

        List<Integer> newScores = new ArrayList<Integer>(recipeScores);

        if (score < 10) {
            newScores.add(score);
        } else {
            int tensPart = (score / 10) % 10;
            int onesPart = score % 10;

            newScores.add(tensPart);
            newScores.add(onesPart);
        }

        return newScores;
    }

    public static int calcNextIndex(List<Integer> recipeScores, int index) {
        int nextScore = recipeScores.get(index) + 1;

        nextScore += index;
        if (nextScore >= recipeScores.size()) {
            nextScore = nextScore % recipeScores.size();
        }

        return nextScore;
    }


    public static String answer(List<Integer> resultingScores, int skipThese) {

        StringBuilder sb = new StringBuilder();
        for (int i = skipThese; i < 10 + skipThese; i++) {
            sb.append(resultingScores.get(i));
        }
        return sb.toString();
    }

    public static int solution2(List<Integer> resultingScores, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resultingScores.size(); i++) {
            sb.append(resultingScores.get(i));
        }

        return sb.toString().indexOf(s);
    }
}
