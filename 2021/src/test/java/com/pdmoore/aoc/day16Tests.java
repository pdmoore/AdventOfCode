package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day16Tests {

    // string - single packet, contains other packets

    // might be extra 0's at the end, ignore

    // standard header
    // 3 bits - version
    // 3 bits - packet TYPE ID
    // TYPE ID = 4, literal value
    //              pad with leading zeros until length is four bit multiple
    //              broken into four bit group, preceded by 1 or 0 (to terminate)
    //              so five bits total
    /*
    D2FE28
110100101111111000101000
VVVTTTAAAAABBBBBCCCCC
     */


    @Test
    void part1_decode_literal() {
        String input = "D2FE28";

        Packet actual = new Packet(input);

        assertEquals(6, actual.version);
        assertEquals(4, actual.typeID);
        assertEquals(2021, actual.literalValue);
    }

    private class Packet {
        public int version;
        public int typeID;
        public int literalValue;

        public Packet(String input) {
            String binaryString = new BigInteger(input, 16).toString(2);
            version = Integer.parseInt(binaryString.substring(0, 3), 2);
            typeID = Integer.parseInt(binaryString.substring(3, 6), 2);

            if (typeID == 4) {
//                110 100 10111 11110 00101 000
//                VVV TTT AAAAA BBBBB CCCCC

                StringBuilder literalString = new StringBuilder();
                // start grabbing 5 characters at a time from position
                String aaa = binaryString.substring(6,  11);
                literalString.append(aaa.substring(1, 5));
                String bbb = binaryString.substring(11, 16);
                literalString.append(bbb.substring(1, 5));
                String ccc = binaryString.substring(16, 21);
                literalString.append(ccc.substring(1, 5));

                literalValue = Integer.parseInt(literalString.toString(), 2);

            }


        }
    }
}