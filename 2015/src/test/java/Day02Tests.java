import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Tests {

    private List<String> fileAsStringArray(String filename) {
        try {
            return Files.readLines(new File(filename), Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't open input file: " + filename);
        }
    }

    @Test
    public void CalculateRequiredWrappingPaper() {
        assertEquals(58, requiredWrappingPaperFor("2x3x4"));
        assertEquals(43, requiredWrappingPaperFor("1x1x10"));
    }

    @Test
    public void day02_part01() {
        List<String> inputLines = fileAsStringArray("data/day02-part01");
        assertEquals(1586300, solvePart01(inputLines));
    }

    private int solvePart01(List<String> inputLines) {
        int totalForAll = 0;
        for (String input :
                inputLines) {
            totalForAll += requiredWrappingPaperFor(input);
        }
        return totalForAll;
    }

    private int requiredWrappingPaperFor(String inputLine) {
        String[] measurements = inputLine.split("x");

        int length = Integer.parseInt(measurements[0]);
        int width = Integer.parseInt(measurements[1]);
        int height = Integer.parseInt(measurements[2]);

        int area1 = length * width;
        int area2 = length * height;
        int area3 = width * height;
        int totalArea = 2 * (area1 + area2 + area3);

        int smallestArea = Math.min(Math.min(area1, area2), area3);

        return totalArea + smallestArea;
    }

}
