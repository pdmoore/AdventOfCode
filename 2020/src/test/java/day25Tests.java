import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day25Tests {

    public static final int SUBJECT_NUMBER = 7;

    public List<String> createPart1Input() {
        List<String> input = new ArrayList<>();
        input.add("8252394");
        input.add("6269621");
        return input;
    }

    public List<String> createSampleInput() {
        List<String> input = new ArrayList<>();
        input.add("5764801");   //card
        input.add("17807724");  //door
        return input;
    }

    @Test
    public void findLoopSize() {
        assertEquals(8, discoverLoopSize(5764801));

        assertEquals(11, discoverLoopSize(17807724));
    }

    @Test
    public void part_sample() {
        int result = findEcnryptionKey(5764801, 17807724);
        assertEquals(14897079, result);
    }

    @Test
    public void part1_solution() {
        int result = findEcnryptionKey(8252394, 6269621);
        assertEquals(181800, result);

    }

    private int findEcnryptionKey(int cardPublicKey, int doorPublicKey) {
        int cardLoopSize = discoverLoopSize(cardPublicKey);
        return calculateEncryptionKey(doorPublicKey, cardLoopSize);

//        int doorLoopSize = discoverLoopSize(doorPublicKey);
//        int encryptionKey_2 = calculateEncryptionKey(cardPublicKey, doorLoopSize);
    }

    private int calculateEncryptionKey(int subjectNumber, int loopCount) {
        long target = 1;
        while (loopCount > 0) {
            loopCount--;

            target *= subjectNumber;
            target %= 20201227;
        }

        return (int)target;
    }

    private int discoverLoopSize(int publicKey) {
        int target = 1;
        int loopCount = 0;
        while (target != publicKey) {
            loopCount++;

            target *= SUBJECT_NUMBER;
            target %= 20201227;
        }

        return loopCount;
    }
}
