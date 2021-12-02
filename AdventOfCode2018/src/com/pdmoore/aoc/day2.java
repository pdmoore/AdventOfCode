package com.pdmoore.aoc;

import java.io.File;
import java.util.*;

public class day2 {

    public static int solution_1() {
        String puzzleInputFile = "data/aoc18.2.txt";
//        String puzzleInputFile = "data/aoc18.2a.txt";

        try{
            File f = new File(puzzleInputFile);
            Scanner scanner = new Scanner(f);

            int charactersWithTwo   = 0;
            int charactersWithThree = 0;

            Map<Character, Integer> characterCounts = new HashMap<>();
            while (scanner.hasNext()){

                String thisline = scanner.next();
                for (char c: thisline.toCharArray()) {
                    if (!characterCounts.containsKey(c)) {
                        characterCounts.put(c, 1);
                    } else {
                        Integer i = characterCounts.get(c);
                        i++;
                        characterCounts.put(c, i);
                    }
                }

                if (anyPairOfCharacters(characterCounts)) charactersWithTwo++;
                if (anyTripleCharacters(characterCounts)) charactersWithThree++;

                characterCounts = new HashMap<>();
            }

            return charactersWithTwo * charactersWithThree;
        } catch(Exception err){
            err.printStackTrace();
        }
        return -1;
    }

    private static boolean anyTripleCharacters(Map<Character, Integer> characterCounts) {
        for (Character c: characterCounts.keySet()) {
            if (characterCounts.get(c) == 3) return true;
        }

        return false;
    }

    private static boolean anyPairOfCharacters(Map<Character, Integer> characterCounts) {
        for (Character c: characterCounts.keySet()) {
            if (characterCounts.get(c) == 2) return true;
        }

        return false;
    }


    public static String solution_2() {
        String puzzleInputFile = "data/aoc18.2.txt";

        // Extract as utility method - file of input to list of strings
        List<String> boxIDs = new ArrayList<>();
        try {
            File f = new File(puzzleInputFile);
            Scanner scanner = new Scanner(f);

            while (scanner.hasNext()) {
                boxIDs.add(scanner.next());
            }
        } catch(Exception err){
            err.printStackTrace();
        }

        for (int i = 0; i < boxIDs.size(); i++) {
            String leftSide = boxIDs.get(i);
            for (int j = i + 1; j < boxIDs.size(); j++) {
                String rightSide = boxIDs.get(j);
                if (differsByOnlyOne(leftSide, rightSide)) {
                    return findUniqueCharacters(leftSide, rightSide);
                }
            }
        }

        return "nothing found";
    }


    public static boolean differsByOnlyOne(String first, String second) {
        // not checking string length
        boolean singleDifferenceFound = false;
        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) != second.charAt(i)) {
                if (singleDifferenceFound) {
                    return false;
                } else {
                    singleDifferenceFound = true;
                }
            }
        }

        return singleDifferenceFound;
    }

    public static String findUniqueCharacters(String match_1, String match_2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < match_1.length(); i++) {
            if (match_1.charAt(i) == match_2.charAt(i)) {
                sb.append(match_1.charAt(i));
            }
        }
        return sb.toString();
    }

}
