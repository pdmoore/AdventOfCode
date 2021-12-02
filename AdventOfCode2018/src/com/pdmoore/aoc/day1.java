package com.pdmoore.aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

public class day1 {
    static int parseLine(String number) {
        return Integer.parseInt(number);
    }

    public static void solution_1() {
        String puzzleInputFile = "data/aoc18.1.txt";


//        try (Stream<String> stream = Files.lines(Paths.get(puzzleInputFile))) {
//
//            stream.forEach(System.out::println).sum()   ;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try{
            File f = new File(puzzleInputFile);
            Scanner scanner = new Scanner(f);
            int sum = 0;
            while (scanner.hasNext()){
                sum += scanner.nextInt();
            }
            System.out.println("Sum:"+sum);
        }catch(Exception err){
            err.printStackTrace();
        }
    }

    public static int solution_2(String input) {
        Collection<Integer> frequencies = new HashSet<>();
//        List<Integer> frequencies = new ArrayList<>();
        int sum = 0;
        while (true) {
            Scanner scanner = new Scanner(input);
            while (scanner.hasNext()) {
                sum += scanner.nextInt();
                if (frequencies.contains(sum)) return sum;
                frequencies.add(sum);
            }
        }
    }

    public static int solution_2_from_file() throws FileNotFoundException {
        String puzzleInputFile = "data/aoc18.1.txt";

        Collection<Integer> frequencies = new HashSet<>();
        int sum = 0;
        while (true) {
            File f = new File(puzzleInputFile);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                sum += scanner.nextInt();
                if (frequencies.contains(sum)) return sum;
                frequencies.add(sum);
            }
        }
    }
}
