import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utilities {

    private Utilities() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    // File contains one integer per line
    static List<Integer> fileToIntegerList(String pathAndFileName) {
        List<Integer> integers = new ArrayList<>();
        try {
            File f = new File(pathAndFileName);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                integers.add(scanner.nextInt());
            }
            scanner.close();
        } catch (Exception err) {
            System.out.println("File not found in this directory " + System.getProperty("user.dir"));
            throw new RuntimeException("error while processing " + pathAndFileName);
        }
        return integers;
    }

    static List<String> fileToStringList(String pathAndFileName) {
        List<String> lines = new ArrayList<>();
        try {
            File f = new File(pathAndFileName);
            Scanner scanner = new Scanner(f);

            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception err){
            System.out.println("File not found in this directory " + System.getProperty("user.dir"));
            throw new RuntimeException("error while processing " + pathAndFileName);
        }
        return lines;
    }


    // AoC17 has
    // List<List<Integer>> readFileAsListOfListOfIntegers(String fileName)
    // AoC18 has
    // static String fileAsString(String filename)
    // public static char[][] convertInputToMap(List<String> inputAsStrings)
    // AoC19 has
    // public static String fileAsString(String filenameWithPath)
    // public static String convertIntArrayToCommaSeparatedString(int[] ints)
    // public static int[] convertCommaSeparatedStringToIntArray(String input)
}
