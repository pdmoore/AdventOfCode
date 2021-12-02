package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.event.AdjustmentEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AdventOfCode2017Test {

    private List<List<Integer>> readFileAsListOfListOfIntegers(String fileName) throws IOException {
        List<List<Integer>> allLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            List<Integer> lineOfIntegers = new ArrayList<>();
            Scanner s = new Scanner(line);
            while (s.hasNextInt()) {
                lineOfIntegers.add(s.nextInt());
            }
            allLines.add(lineOfIntegers);
        }
        return allLines;
    }


    @Test
    public void NoDigitsMatch_Sum0() {
        String input = "1234";
        assertEquals(0, AdventOfCode2017.sum(input), "none of the digits match, sum is 0");
    }

    @Test
    public void CountDuplicates() {
        String input = "1122";
        assertEquals(3, AdventOfCode2017.sum(input), "none of the digits match, sum is 0");
    }

    @Test
    public void CountLastEqualsFirst() {
        String input = "1111";
        assertEquals(4, AdventOfCode2017.sum(input));
    }

    @Test
    public void CountOnlyWhenLastDigitMatchesFirst() {
        String input = "91212129";
        assertEquals(9, AdventOfCode2017.sum(input), "Only the last and first digit match, sum those");
    }

    @Test
    public void MyPuzzle() {
        String input = "5255443714755555317777152441826784321918285999594221531636242944998363716119294845838579943562543247239969555791772392681567883449837982119239536325341263524415397123824358467891963762948723327774545715851542429832119179139914471523515332247317441719184556891362179267368325486642376685657759623876854958721636574219871249645773738597751429959437466876166273755524873351452951411628479352522367714269718514838933283861425982562854845471512652555633922878128558926123935941858532446378815929573452775348599693982834699757734714187831337546474515678577158721751921562145591166634279699299418269158557557996583881642468274618196335267342897498486869925262896125146867124596587989531495891646681528259624674792728146526849711139146268799436334618974547539561587581268886449291817335232859391493839167111246376493191985145848531829344198536568987996894226585837348372958959535969651573516542581144462536574953764413723147957237298324458181291167587791714172674717898567269547766636143732438694473231473258452166457194797819423528139157452148236943283374193561963393846385622218535952591588353565319432285579711881559343544515461962846879685879431767963975654347569385354482226341261768547328749947163864645168428953445396361398873536434931823635522467754782422557998262858297563862492652464526366171218276176258582444923497181776129436396397333976215976731542182878979389362297155819461685361676414725597335759976285597713332688275241271664658286868697167515329811831234324698345159949135474463624749624626518247831448143876183133814263977611564339865466321244399177464822649611969896344874381978986453566979762911155931362394192663943526834148596342268321563885255765614418141828934971927998994739769141789185165461976425151855846739959338649499379657223196885539386154935586794548365861759354865453211721551776997576289811595654171672259129335243531518228282393326395241242185795828261319215164262237957743232558971289145639852148197184265766291885259847236646615935963759631145338159257538114359781854685695429348428884248972177278361353814766653996675994784195827214295462389532422825696456457332417366426619555";
        assertEquals(1049, AdventOfCode2017.sum(input));
    }

    @Test
    public void sum2_NoMatch() {
        String input = "1221";
        assertEquals(0, AdventOfCode2017.sum2(input));
    }

    @Test
    public void sum2_1212() {
        String input = "1212";
        assertEquals(6, AdventOfCode2017.sum2(input));
    }

    @Test
    public void sum2_123425() {
        String input = "123425";
        assertEquals(4, AdventOfCode2017.sum2(input));
    }

    @Test
    public void sum2_123123() {
        String input = "123123";
        assertEquals(12, AdventOfCode2017.sum2(input));
    }

    @Test
    public void sum2_12131415() {
        String input = "12131415";
        assertEquals(4, AdventOfCode2017.sum2(input));
    }

    @Test
    public void sum2_MyPuzzle() {
        String input = "5255443714755555317777152441826784321918285999594221531636242944998363716119294845838579943562543247239969555791772392681567883449837982119239536325341263524415397123824358467891963762948723327774545715851542429832119179139914471523515332247317441719184556891362179267368325486642376685657759623876854958721636574219871249645773738597751429959437466876166273755524873351452951411628479352522367714269718514838933283861425982562854845471512652555633922878128558926123935941858532446378815929573452775348599693982834699757734714187831337546474515678577158721751921562145591166634279699299418269158557557996583881642468274618196335267342897498486869925262896125146867124596587989531495891646681528259624674792728146526849711139146268799436334618974547539561587581268886449291817335232859391493839167111246376493191985145848531829344198536568987996894226585837348372958959535969651573516542581144462536574953764413723147957237298324458181291167587791714172674717898567269547766636143732438694473231473258452166457194797819423528139157452148236943283374193561963393846385622218535952591588353565319432285579711881559343544515461962846879685879431767963975654347569385354482226341261768547328749947163864645168428953445396361398873536434931823635522467754782422557998262858297563862492652464526366171218276176258582444923497181776129436396397333976215976731542182878979389362297155819461685361676414725597335759976285597713332688275241271664658286868697167515329811831234324698345159949135474463624749624626518247831448143876183133814263977611564339865466321244399177464822649611969896344874381978986453566979762911155931362394192663943526834148596342268321563885255765614418141828934971927998994739769141789185165461976425151855846739959338649499379657223196885539386154935586794548365861759354865453211721551776997576289811595654171672259129335243531518228282393326395241242185795828261319215164262237957743232558971289145639852148197184265766291885259847236646615935963759631145338159257538114359781854685695429348428884248972177278361353814766653996675994784195827214295462389532422825696456457332417366426619555";
        assertEquals(1508, AdventOfCode2017.sum2(input));
    }

    @Test
    public void day2_SingleLineChecksum() {
        int[] input = new int[] { 5, 1, 9, 5 };
        assertEquals(8, AdventOfCode2017.checksum(input), "checksum is difference of largest and smallest value");
    }

//    @Test
//    public void day2_DifferenceChecksum() {
//        int[][] input = new int[][] { {5, 1, 9, 5}, {7, 5, 3}, {2, 4, 6, 8} };
//        assertEquals(18, AdventOfCode2017.checksum(input), "checksum is sum of all single line checksums");
//    }

    @Test
    public void day2_checksum_puzzle() throws Exception {
        List<List<Integer>> allLines = readFileAsListOfListOfIntegers("data/day2.txt");
        assertEquals(16, allLines.size());
        assertEquals(53460, AdventOfCode2017.checksum(allLines), "checksum is sum of all single line checksums");
    }

    @Test
    public void day2_2_checksumDivision() {
        List<Integer> input = new ArrayList(Arrays.asList(5, 9, 2, 8));
        assertEquals(4, AdventOfCode2017.checksumLine2(input), "8 divides by 2 for 4");
        input = new ArrayList(Arrays.asList(9, 4, 7, 3));
        assertEquals(3, AdventOfCode2017.checksumLine2(input), "9 divides by 3");
        input = new ArrayList(Arrays.asList(3, 8, 6, 5));
        assertEquals(2, AdventOfCode2017.checksumLine2(input), "6 divides by 3");
    }

    @Test
    public void day2_2_checksum_puzzle() throws Exception {
        List<List<Integer>> allLines = readFileAsListOfListOfIntegers("data/day2.txt");
        assertEquals(16, allLines.size());
        assertEquals(282, AdventOfCode2017.checksum2(allLines), "checksum is sum of all single line checksums");
    }

    @Test
    public void day3_1_gridSizeContainingTarget() {
        boolean found = false;
        int gridSize = 0;
        int dimension = 0;
        for (int i = 1; found == false; i += 2) {
            if ((i * i) > 361527) found = true;
            gridSize = i*i;
            dimension = i;
        }

        assertEquals(603,dimension);
        assertEquals(363609, gridSize);
        assertEquals( 302, (dimension / 2) + 1, "central point location");
    }

    @Test
    public void day3_1_growGridFromSingleItemSeed() {
        int[][] seed = new int[][] { {1} };
        int[][] expected = new int[][] {
                { 5, 4, 3 },
                { 6, 1, 2 },
                { 7, 8, 9 }
        };
        assertArrayEquals(expected, AdventOfCode2017.day3_growGrid(seed));
    }

    @Test
    public void day3_1_nextLargerGrid() {
        int[][] startWith = new int[][] {
                {5, 4, 3},
                {6, 1, 2},
                {7, 8, 9}
        };
        int[][] expected = new int[][] {
                {17, 16, 15, 14, 13},
                {18,  5,  4,  3, 12},
                {19,  6,  1,  2, 11},
                {20,  7,  8,  9, 10},
                {21, 22, 23, 24, 25}
        };
        assertArrayEquals(expected, AdventOfCode2017.day3_growGrid(startWith));
    }

    @Test
    public void day3_1_growGridUntilItContainsTargetValue() {
        int[][] startWith = new int[][]{ { 1 } };
        int targetValue = 361527;
        assertEquals(603, AdventOfCode2017.day3_sizeOfGridContaining(targetValue, startWith));
    }

    @Test
    public void day3_1_ManhattanDistance_SimpleGrid() {
        int[][] startWith = new int[][]{ { 1 } };
        int targetValue = 22;
        assertEquals(3, AdventOfCode2017.day3_1_puzzle(targetValue, startWith));

        targetValue = 17;
        assertEquals(4, AdventOfCode2017.day3_1_puzzle(targetValue, startWith));
    }

    @Test
    public void day3_1_ManhattanDistance_puzzle() {
        int[][] startWith = new int[][]{ { 1 } };
        int targetValue = 361527;
        assertEquals(326, AdventOfCode2017.day3_1_puzzle(targetValue, startWith));
    }

    @Test
    public void day3_2_growGridFromSingleItemSeed() {
        int[][] seed = new int[][] { {1} };
        int[][] expected = new int[][] {
                {  5,  4,  2 },
                { 10,  1,  1 },
                { 11, 23, 25 }
        };
        assertArrayEquals(expected, AdventOfCode2017.day3_growGridBySum(seed));
    }

    @Test
    public void day3_2_puzzle() {
        int[][] seed = new int[][] { {1} };
        int[][] nextGrid = AdventOfCode2017.day3_growGridBySum(seed);
        int target = 361527;
        try {
            while (true) {
                nextGrid = AdventOfCode2017.day3_huntForTarget(target, nextGrid);
            }
        } catch (RuntimeException e) {
            assertEquals(363010, Integer.parseInt(e.getMessage()));
        }
    }

    @Test
    public void day4_1_Passphrase_valid() {
        String validPhrase = "aa bb cc dd ee";
        assertTrue(AdventOfCode2017.isPassphraseValid(validPhrase), "valid passphrase does not repeat any words");
    }

    @Test
    public void day4_1_Passphrase_invalid() {
        String invalidPhrase = "aa bb cc dd aa";
        assertFalse(AdventOfCode2017.isPassphraseValid(invalidPhrase), "invalid passphrase repeats the word 'aa'");
    }

    @Test
    public void day4_2_Passphrase_valid() {
        String validPhrase = "abcde fghij";
        assertTrue(AdventOfCode2017.isPassphraseValid_2(validPhrase), "now check if words are anagrams of each other");
        assertTrue(AdventOfCode2017.isPassphraseValid_2("a ab abc abd abf abj"), "now check if words are anagrams of each other");
        assertTrue(AdventOfCode2017.isPassphraseValid_2("iiii oiii ooii oooi oooo"), "now check if words are anagrams of each other");
    }

    @Test
    public void day4_2_Passphrase_invalid() {
        String invalidPhrase = "abcde xyz ecdab";
        assertFalse(AdventOfCode2017.isPassphraseValid_2(invalidPhrase), "invalid since 1st and 3rd word are anagrams of each other");
        assertFalse(AdventOfCode2017.isPassphraseValid_2("oiii ioii iioi iiio"), "many collisions here");
    }

    @Test
    public void day4_1_passphrase_puzzle() throws IOException {
        List<String> allLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/day4.txt"));

        String line;
        int validPassphraseCount = 0;
        while ((line = br.readLine()) != null) {
            if (AdventOfCode2017.isPassphraseValid(line)) validPassphraseCount++;
        }

        assertEquals(383, validPassphraseCount, "number of valid passphrase in input file");
    }

    @Test
    public void day4_2_passphrase_puzzle() throws IOException {
        List<String> allLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/day4.txt"));

        String line;
        int validPassphraseCount = 0;
        while ((line = br.readLine()) != null) {
            if (AdventOfCode2017.isPassphraseValid_2(line)) validPassphraseCount++;
        }

        assertEquals(265, validPassphraseCount, "number of valid passphrase in input file");
    }

    @Test
    public void day5_1_mae() {
        int[] maze = new int[] { 0, 3, 0, 1, -3 };
        int actual = AdventOfCode2017.mazeSteps(maze);
        assertEquals(5, actual);
    }

    @Test
    public void day5_1_puzzle() throws IOException {
        List<Integer> allLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/day5.txt"));

        String line;
        while ((line = br.readLine()) != null) {
            allLines.add(Integer.parseInt(line));
        }

        int[] maze   = new int[allLines.size()];
        int[] maze_2 = new int[allLines.size()];
        for (int i = 0; i < maze.length; i++) {
            maze[i] = allLines.get(i);
            maze_2[i] = maze[i];
        }
        assertEquals(378980, AdventOfCode2017.mazeSteps(maze));
        assertEquals(26889114, AdventOfCode2017.mazeSteps_2(maze_2));
    }

    @Test
    public void day5_2_maze() {
        int[] maze = new int[] { 0, 3, 0, 1, -3 };
        int actual = AdventOfCode2017.mazeSteps_2(maze);
        assertEquals(10, actual);
    }

    @Test
    public void day6_1_redistribute() {
        int[] blocks = new int[] { 0, 2, 7, 0 };
        int[] expected = new int[] { 2, 4, 1, 2 };
        assertArrayEquals(expected, AdventOfCode2017.redistribute(blocks));
    }

    @Test
    public void day6_1_redistributeAndRemember() {
        int[] block = new int[] { 0, 2, 7, 0 };
        int expected = 5;
        assertEquals(expected, AdventOfCode2017.redistributeUntilCycle(block).stepCount);
    }

    @Test
    public void day6_1_puzzle() {
        int[] block = new int[] { 5, 1, 10, 0, 1,	7, 13, 14, 3, 12, 8, 10, 7, 12, 0, 6 };
        assertEquals(5042, AdventOfCode2017.redistributeUntilCycle(block).stepCount);
        assertEquals(1086, AdventOfCode2017.redistributeUntilCycle(block).cycleCount);
    }

    @Test
    public void day6_1_redistributeAnDetectCycle() {
        int[] block = new int[] { 0, 2, 7, 0 };
        int expected = 4;
        assertEquals(expected, AdventOfCode2017.redistributeUntilCycle(block).cycleCount);
    }

    @Test
    public void day9_1_OneGroup() {
        String stream = "{}";
        assertEquals(1, AdventOfCode2017.processStream(stream));
        assertEquals(1, AdventOfCode2017.processStream("{<a>,<a>,<a>,<a>}"));
    }

    @Test
    public void day9_1_MultipleGroups() {
        assertEquals(6, AdventOfCode2017.processStream("{{{}}}"));
        assertEquals(5, AdventOfCode2017.processStream("{{},{}}"));
        assertEquals(16, AdventOfCode2017.processStream("{{{},{},{{}}}}"));
    }

    @Test
    public void day9_1_handleGarbage() {
        assertEquals(1, AdventOfCode2017.processStream("{<a>,<a>,<a>,<a>}"));
        assertEquals(9, AdventOfCode2017.processStream("{{<ab>},{<ab>},{<ab>},{<ab>}}"));
    }

    @Test public void day9_1_handleIgnore() {
        assertEquals(9, AdventOfCode2017.processStream("{{<!!>},{<!!>},{<!!>},{<!!>}}"));
        assertEquals(3, AdventOfCode2017.processStream("{{<a!>},{<a!>},{<a!>},{<ab>}}"));
    }

    @Test
    public void day9_1_puzzle() throws IOException {
        List<String> allLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/day9.txt"));

        String line;
        int actual = 0;
        while ((line = br.readLine()) != null) {
            actual = AdventOfCode2017.processStream(line);
        }
        assertEquals(7616, actual);
    }

    @Test
    public void day9_2_countGarbage() {
        assertEquals(17, AdventOfCode2017.processStream_2("<random characters>"));
        assertEquals(3, AdventOfCode2017.processStream_2("<<<<>"));
        assertEquals(10, AdventOfCode2017.processStream_2("<{o\"i!a,<{i<a>"));
    }

    @Test
    public void day9_2_puzzle() throws IOException {
        List<String> allLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/day9.txt"));

        String line;
        int actual = 0;
        while ((line = br.readLine()) != null) {
            actual = AdventOfCode2017.processStream_2(line);
        }
        assertEquals(3838, actual);
    }

    @Test
    public void day10_1_Position_0() {
        int[] input = new int[] { 0, 1, 2, 3, 4 };
        int[] expected = new int[] { 2, 1, 0, 3, 4 };
        assertArrayEquals(expected, AdventOfCode2017.day10_reverse(input,0,3));
    }

    @Test
    public void day10_1_PositionWraps() {
        int[] input = new int[] { 2, 1, 0, 3, 4 };
        int[] expected = new int[] { 4, 3, 0, 1, 2 };
        assertArrayEquals(expected, AdventOfCode2017.day10_reverse(input,3,4));
    }

    @Test
    public void day10_1_LengthOf_1() {
        int[] input = new int[] { 4, 3, 0, 1, 2 };
        int[] expected = new int[] { 4, 3, 0, 1, 2 };
        assertArrayEquals(expected, AdventOfCode2017.day10_reverse(input,1,1));
    }

    @Test
    public void day10_1_LengthOfEntireInput() {
        int[] input = new int[] { 4, 3, 0, 1, 2 };
        int[] expected = new int[] { 3, 4, 2, 1, 0 };
        assertArrayEquals(expected, AdventOfCode2017.day10_reverse(input, 1, 5));
    }

    @Test
    public void day10_knotHash() {
        int length = 5;
        int inputLengths[] = new int[] { 3, 4, 1, 5 };
        int expected[] = new int[] { 3, 4, 2, 1, 0 };
        assertArrayEquals(expected, AdventOfCode2017.day10_knotHash(length, inputLengths));
    }

    @Test
    public void day10_1_puzzle() {
        int length = 256;
        int inputLengths[] = new int[] { 70,66,255,2,48,0,54,48,80,141,244,254,160,108,1,41 };
        int[] actual = AdventOfCode2017.day10_knotHash(length, inputLengths);
        int product = actual[0] * actual[1];
        assertEquals(7888, product);
    }

    @Test
    public void day7_1_sampleData() throws IOException {
        List<String> allLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/day7_sample.txt"));

        String line;
        while ((line = br.readLine()) != null) {
            allLines.add(line);
        }

        String expected = "tknk";
        assertEquals(expected, AdventOfCode2017.day7_FindBottom(allLines));
    }

    @Test
    public void day7_programNameNotConsistentLength() {
        String line = "wjdkcjo (29) -> jdntuc, htaxf, edpqtnn";
        String expected = "wjdkcjo";
        assertEquals(expected, AdventOfCode2017.day7_extractProgramName(line));
    }

    @Test
    public void day7_programWeight() {
        String line = "wjdkcjo (29) -> jdntuc, htaxf, edpqtnn";
        int expected = 29;
        assertEquals(expected, AdventOfCode2017.day7_extractProgramWeight(line));
    }

    @Test
    public void day7_1_nodesBeingSupported() {
        String line = "fwft (72) -> ktlj, cntj, xhth";
        List<String> expected = new ArrayList<>();
        expected.add("ktlj");
        expected.add("cntj");
        expected.add("xhth");
        assertEquals(expected, AdventOfCode2017.day7_supportedNodes(line));
    }

    @Test
    public void day7_1_puzzle() throws IOException {
        List<String> allLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/day7.txt"));

        String line;
        while ((line = br.readLine()) != null) {
            allLines.add(line);
        }

        String expected = "azqje";
        assertEquals(expected, AdventOfCode2017.day7_FindBottom(allLines));
    }

    @Test
//    @Disabled
    public void day7_2_createTree() throws IOException {
        List<String> allLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/day7_sample.txt"));

        String line;
        while ((line = br.readLine()) != null) {
            allLines.add(line);
        }

        Collection<AdventOfCode2017.day7Node> nodes = AdventOfCode2017.day7_2_createTree(allLines);
        assertEquals(13, nodes.size());
        System.out.println(nodes.toString());
    }
}
