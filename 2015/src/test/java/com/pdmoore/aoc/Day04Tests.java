package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class Day04Tests {

    @Test
    public void ConfirmHashedAnswer_BeginsWithCorrect_Sequence_GivenSecret() throws Exception {
        String secretKey = "abcdef";
        String someNumber = "609043";

        String input = secretKey + someNumber;

        byte[] thedigest = getMD5Hash(input);

        String result = getHexStringOfHash(thedigest);
        String expectedHashStartsWith = "000001dbbfa";
        String actualHash = result.substring(0, expectedHashStartsWith.length()).toLowerCase(Locale.ROOT);
        Assertions.assertEquals(expectedHashStartsWith, actualHash);
    }

    @Test
    public void  ConfirmHashedAnswer_BeginsWithCorrect_Sequence_GivenDifferentSecret() throws Exception {
        String secretKey = "pqrstuv";
        String someNumber = "1048970";

        String input = secretKey + someNumber;

        byte[] thedigest = getMD5Hash(input);

        String result = getHexStringOfHash(thedigest);
        String expectedHashStartsWith = "000006136ef";
        String actualHash = result.substring(0, expectedHashStartsWith.length()).toLowerCase(Locale.ROOT);
        Assertions.assertEquals(expectedHashStartsWith, actualHash);
    }

    @Test
    public void part1_simpleExamples() {
        String secretKey = "abcdef";
        // This does work starting from 0, just takes a while
        int startingNumber = 600000;

        int actual = findNumberThatHashesFiveZeros(secretKey, startingNumber);

        Assertions.assertEquals(609043, actual);
    }

    @Test
    public void part1_solution() {
        String secretKey = "ckczppom";
        int startingNumber = 0;

        int actual = findNumberThatHashesFiveZeros(secretKey, startingNumber);

        Assertions.assertEquals(117946, actual);
    }


    private int findNumberThatHashesFiveZeros(String secretKey, int startingNumber) {
        int candidate = startingNumber;

        while (candidate <= Integer.MAX_VALUE) {
            String input = secretKey + Integer.toString(candidate);

            byte[] thedigest = new byte[0];
            try {
                thedigest = getMD5Hash(input);

                String result = getHexStringOfHash(thedigest);
                String actualStartsWith = result.substring(0, 5);
                if (actualStartsWith.equals("00000")) {
                    return candidate;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            candidate++;
        }

        return -1;
    }

    private byte[] getMD5Hash(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = input.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        return thedigest;
    }

    private String getHexStringOfHash(byte[] thedigest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : thedigest) {
            sb.append(String.format("%02X", b));
        }

        String result = sb.toString();
        return result;
    }


}
