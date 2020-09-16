import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class utilities {
    static List<String> getFileContentsAsStrings(String filename) {
        List<String> lines = new ArrayList<>();
        try {
            File f = new File(filename);
            Scanner scanner = new Scanner(f);

            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        } catch(Exception err){
            err.printStackTrace();
        }
        return lines;
    }

    static String fileAsString(String filename) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            return new String(encoded).trim();
        } catch (IOException e) {
            System.out.println("ERROR reading " + filename);
        }
        return null;
    }

    public static char[][] convertInputToMap(List<String> inputAsStrings) {
        int rowCount = inputAsStrings.size();
        int colCount = inputAsStrings.get(0).length();
        char[][] map = new char[rowCount][colCount];
        int i = 0;
        for (String line :
                inputAsStrings) {
            map[i] = line.toCharArray();
            i++;
        }
        return map;
    }
}
