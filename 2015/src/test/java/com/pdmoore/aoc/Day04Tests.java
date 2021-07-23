package com.pdmoore.aoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class Day04Tests {

    private MessageDigest _md;

    @BeforeEach
    public void initializeHasher() {
        try {
            _md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could not create Message Digest");
            e.printStackTrace();
        }
    }

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
    public void ConfirmHashedAnswer_BeginsWithCorrect_Sequence_GivenDifferentSecret() throws Exception {
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

        int startingNumber = 0;
        int actual = findNumberThatHashesToPassedPrefix(secretKey, startingNumber, "00000");

        Assertions.assertEquals(609043, actual);
    }

    @Test
    @Timeout(1)
    public void part1_solution() {
        String secretKey = "ckczppom";
        int startingNumber = 0;

        int actual = findNumberThatHashesToPassedPrefix(secretKey, startingNumber, "00000");

        Assertions.assertEquals(117946, actual);
    }

    @Test
    @Timeout(2)
    public void part2_solution() {
        String secretKey = "ckczppom";
        int startingNumber = 0;

        int actual = findNumberThatHashesToPassedPrefix(secretKey, startingNumber, "000000");

        Assertions.assertEquals(3938038, actual);
    }

    private int findNumberThatHashesToPassedPrefix(String secretKey, int startingNumber, String targetPrefix) {
        int candidate = startingNumber;

        while (true) {
            String input = secretKey + candidate;

            try {
                byte[] hashedResult = getMD5Hash(input);

                if (hashedResult[0] == 0 && hashedResult[1] == 0) {

                    // TODO - should perform this check at start and then do either method in here
                    if (targetPrefix.length() == 6) {
                        if (hashedResult[2] == 0) {
                            return candidate;
                        }
                    } else {
                        int halfOfByte = hashedResult[2] >> 4;
                        if (halfOfByte == 0) {
                            return candidate;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            candidate++;
        }
    }

    private byte[] getMD5Hash(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = input.getBytes("UTF-8");
        _md.update(bytesOfMessage);
        return _md.digest();
    }

    // TODO - this is only used by tests, not the problem
    private String getHexStringOfHash(byte[] thedigest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : thedigest) {
            sb.append(String.format("%02X", b));
        }

        return sb.toString();
    }
}
