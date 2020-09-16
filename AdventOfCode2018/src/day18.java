import java.util.*;

public class day18 {
    public static char[][] generateNextMap(char[][] input) {
        char[][] nextGeneration = new char[input.length][input[0].length];
        char itBecomes = 0;

        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                itBecomes = input[row][col];

                char acre = input[row][col];
                if (acre == '.') {
                    if (isAdjacentToTrees(input, row, col)) {
                        itBecomes = '|';
                    }
                } else if (acre == '|') {
                    if (isAdjacentToLumberyards(input, row, col)) {
                        itBecomes = '#';
                    } else {
//                        itBecomes = '.';
                    }
                } else if (acre == '#') {
                    boolean hasTreeAndLumber = isAdjacentToLumberyardAndTrees(input, row, col);
                    if (!hasTreeAndLumber)
                        itBecomes = '.';
                }

                nextGeneration[row][col] = itBecomes;
            }
        }

        return nextGeneration;
    }

    private static boolean isAdjacentToLumberyardAndTrees(char[][] input, int row, int col) {
        Collection<Character> neighbors = collectAdjacentAcres(input, row, col);

        boolean isAdjacentToOneLumberyard = (Collections.frequency(neighbors, '#') >= 1);
        boolean isAdjacentToOneTree = (Collections.frequency(neighbors, '|') >= 1);

        return isAdjacentToOneLumberyard && isAdjacentToOneTree;
    }

    private static boolean isAdjacentToLumberyards(char[][] input, int row, int col) {
        Collection<Character> neighbors = collectAdjacentAcres(input, row, col);

        return (Collections.frequency(neighbors, '#') >= 3);
    }

    private static boolean isAdjacentToTrees(char[][] input, int row, int col) {
        Collection<Character> neighbors = collectAdjacentAcres(input, row, col);

        return (Collections.frequency(neighbors, '|') >= 3);
    }

    private static Collection<Character> collectAdjacentAcres(char[][] input, int row, int col) {
        Collection<Character> neighbors = new ArrayList();
        for (int rowIndex = -1; rowIndex <= 1; rowIndex++) {
            for (int colIndex = -1; colIndex <= 1; colIndex++) {

                try {
                    neighbors.add(input[row + rowIndex][col + colIndex]);
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
        }

        neighbors.remove(input[row][col]);
        return neighbors;
    }

    public static int result(char[][] input) {
        Collection<Character> acres = new ArrayList();
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                acres.add(input[row][col]);
            }
        }

        int treeCount = Collections.frequency(acres, '|');
        int lumberyardCount = Collections.frequency(acres, '#');
        int result = treeCount * lumberyardCount;

        return result;
    }

    public static int solution1(String filename, int reps) {
        List<String> fileContentsAsStrings = utilities.getFileContentsAsStrings(filename);
        char[][] currentMap = utilities.convertInputToMap(fileContentsAsStrings);

        char[][] nextMap = new char[currentMap.length][currentMap[0].length];
        for (int i = 1; i <= reps; i++) {
            nextMap = generateNextMap(currentMap);

            for (int row = 0; row < nextMap.length; row++) {
                for (int col = 0; col < nextMap[0].length; col++) {
                    currentMap[row][col] = nextMap[row][col];
                }
            }
        }

        return result(nextMap);
    }

    public static int solution2(String filename, int reps) {
        List<String> fileContentsAsStrings = utilities.getFileContentsAsStrings(filename);
        char[][] currentMap = utilities.convertInputToMap(fileContentsAsStrings);

        System.out.println("step  - trees   lumber   result   ");

        char[][] nextMap = new char[currentMap.length][currentMap[0].length];
        for (int i = 1; i <= reps; i++) {
            nextMap = generateNextMap(currentMap);

            for (int row = 0; row < nextMap.length; row++) {
                for (int col = 0; col < nextMap[0].length; col++) {
                    currentMap[row][col] = nextMap[row][col];
                }
            }

            result(i, nextMap);

        }

        return result(nextMap);
    }

    private static void result(int rep, char[][] input) {
        Collection<Character> acres = new ArrayList();
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                acres.add(input[row][col]);
            }
        }

        int treeCount = Collections.frequency(acres, '|');
        int lumberyardCount = Collections.frequency(acres, '#');
        int result = treeCount * lumberyardCount;

        System.out.println(String.format("%5d - %5d   %5d   %5d ", rep, treeCount, lumberyardCount, result));
    }
}

