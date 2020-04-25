import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utilities {
    static List<Integer> fileToIntegerList(String puzzleInputFile) {
        List<Integer> masses = new ArrayList<>();
        try{
            File f = new File(puzzleInputFile);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()){
                int i = scanner.nextInt();
                masses.add(i);
            }
        }catch(Exception err){
            err.printStackTrace();
            throw new RuntimeException("error while processing " + puzzleInputFile);
        }
        return masses;
    }
}
