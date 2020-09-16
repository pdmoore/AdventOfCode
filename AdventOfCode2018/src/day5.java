public class day5 {

    public static int solution_1(String filename) {
        String input = utilities.fileAsString(filename);

        String result = reduceAlgorithmByReplaceFirst(input);
        return result.length();
    }

    public static int solution_2(String filename) {
        String input = utilities.fileAsString(filename);

        return findSmallestReduction(input);
    }

    public static String reduceAlgorithmByReplaceFirst(String input) {

        String result = input;
        boolean tryAgain = true;
        boolean reductionPerformed = false;

//        char c = 'a';
        while (tryAgain) {

//            String lowerUpper = String.format("%c%c", c, Character.toUpperCase(c));
//            String swap1 = result.replaceFirst(lowerUpper, "");
//
//            if (swap1.length() == result.length()) {
//                String upperLower = String.format("%c%c", Character.toUpperCase(c), c);
//                String swap2 = result.replaceFirst(upperLower, "");
//
//                if (swap2.length() == result.length()) {
//                    c++;
//                    if (c > 'z') tryAgain = false;
//                } else {
//                    result = swap2;
//                    c = 'a';
//                }
//            } else {
//                result = swap1;
//                c = 'a';
//            }

            int lengthBefore = result.length();
            for (char c = 'a'; c <= 'z'; c++) {
                String target1 = String.format("%c%c", c, Character.toUpperCase(c));
                String target2 = String.format("%c%c", Character.toUpperCase(c), c);
                result = result.replaceFirst(target1, "");
                result = result.replaceFirst(target2, "");
            }
            if (result.length() == lengthBefore) tryAgain = false;
        }

        return result;
    }

    public static String try2(String input) {
        String attempt = input;

        boolean keepAtIt = true;
        while (keepAtIt) {
            int reduceAt = indexOfFirstPair(attempt);
            if (reduceAt == -1) {
                keepAtIt = false;
            } else {
                String remove = attempt.substring(reduceAt, reduceAt + 2);
                attempt = attempt.replaceFirst(remove, "");
            }
        }


        return attempt;
    }

    private static int indexOfFirstPair(String attempt) {
        for (int i = 0; i < attempt.length() - 1; i++) {
            char c        = attempt.charAt(i);
            char cPlusOne = attempt.charAt(i + 1);
            if (Character.isLowerCase(c)) {
                if (Character.toUpperCase(c) == cPlusOne) return i;
            } else {
                if (Character.toLowerCase(c) == cPlusOne) return i;
            }
        }

        return -1;
    }

    public static int findSmallestReduction(String input) {
        String reduced = reduceAlgorithmByReplaceFirst(input);

        int smallest = Integer.MAX_VALUE;
        for (char c = 'a'; c <= 'z' ; c++) {
            String characterToRemove = String.format("%c", c);
            String nowTry = reduced.replaceAll(characterToRemove, "");
            nowTry = nowTry.replaceAll(characterToRemove.toUpperCase(), "");
            String afterSecondReduction = reduceAlgorithmByReplaceFirst(nowTry);
            if (afterSecondReduction.length() < smallest) {
                smallest = afterSecondReduction.length();
            }
        }

        return smallest;
    }

}
