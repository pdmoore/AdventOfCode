package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    // Given a very long Hex string
    // Convert it to a very, very long binary string
    // Pass the binary string to new Class
    // new class walks through string
    // starting at beginning
    // strip off a packet
    // add packet to list
    // bump index to end of current packet
    // repeat until end of string


// Switching over to Message as container of Packets, not directly instantiating packets
//    @Test
//    void part1_decode_literal() {
//        String input = "D2FE28";
//
//        Packet actual = new Packet(input);
//
//        assertEquals(6, actual.version);
//        assertEquals(4, actual.typeID);
//        assertEquals(2021, actual.literalValue);
//    }

    @Test
    void part1_decode_literal_via_Message() {
        String input = "D2FE28";

        Message message = new Message(input);

        Packet actual = message.packets.get(0);

        assertEquals(6, actual.version);
        assertEquals(4, actual.typeID);
        assertEquals(2021, actual.literalValue);
    }

    @Test
    void part1_sumVersions_SingleLiteral() {
        String input = "D2FE28";

        Message actual = new Message(input);

        assertEquals(6, actual.sumOfVersions());
    }

    @Test
    void part1_decodeMultipleOperatorInOneMessage() {
        String input = "38006F45291200";

        Message actual = new Message(input);

        assertEquals(3, actual.packets.size());
        assertEquals(9, actual.sumOfVersions());
    }


    //TODO - parse some of the operator examples
    @Test
    @Disabled
    void part1_decode_operator_LengthType0() {
        String input = "38006F45291200";

        Packet actual = new Packet(input);

        assertEquals(1, actual.version);
        assertEquals(6, actual.typeID);
        assertEquals(0, actual.lengthTypeId);
        assertEquals(27, actual.subPacketLength);

        //TODO - expect a List of 2 sub-packets
    }

    @Test
    @Disabled
    void part1_versionSums_examples() {
        assertEquals(16, new Message("8A004A801A8002F478").sumOfVersions());
        assertEquals(23, new Message("C0015000016115A2E0802F182340").sumOfVersions());
        assertEquals(31, new Message("A0016C880162017C3686B18A3D4780").sumOfVersions());
    }

    private class Packet {
        public int version;
        public int typeID;
        public int literalValue;
        public int lengthTypeId;
        public int subPacketLength;

        public Packet(String input) {

/*
            String binaryString = new BigInteger(input, 16).toString(2);

            // Didn't see this explicitly called out, but was part of the second example
            // pre-pend zeros until there are an evenly % 4 number of bits
            int fourBits = binaryString.length() % 4;
            for (int i = 0; i < fourBits; i++) {
                binaryString = "0" + binaryString;
            }

            version = Integer.parseInt(binaryString.substring(0, 3), 2);
            typeID = Integer.parseInt(binaryString.substring(3, 6), 2);


            //0011 1000 0000 0000 0110 1111 0100 0101 0010 1001 0001 0010 0000 0000
            //  11 1000 0000 0000 0110 1111 0100 0101 0010 1001 0001 0010 0000 0000

            if (typeID == 4) {
                //Literal value
                int pos = 6;

                StringBuilder literalString = new StringBuilder();
                boolean leadCharacterIsOne;
                do {
                    String group = binaryString.substring(pos,  pos + 5);
                    literalString.append(group.substring(1, 5));
                    leadCharacterIsOne = group.charAt(0) == '1';
                    pos += 5;
                } while (leadCharacterIsOne);

                literalValue = Integer.parseInt(literalString.toString(), 2);
            } else {
                // Operator packet
                // contains hierarchy of sub-packets
                //TODO - replace hard-coded offsets with index,
                // increment index after each piece (not all at once at bottom)
                String lengthTypeId = binaryString.substring(6, 7);   // really want a single 0 or 1

                if (lengthTypeId.equals("0")) {
                    // next 15 bits represent the total length in bits of the sub-packets contained by this packet
                    // sub-packets appear after the 15 bits
                    String substring = binaryString.substring(7, 22);
                    int length = Integer.parseInt(substring, 2);

                    String nextPackets = binaryString.substring(22, 22 + length);
                    // assuming it's always 2 packets - create a method that takes this as input and returns 2 packets
                    // except it could be another operator packet
                    // 110 100 01010
                    // 010 100 10001 00100

                } else if (lengthTypeId.equals("1")) {
                    // next 11 bits represent the number of sub-packets immediately contained in this packet
                    // sub-packets appear after the 11 bits
                    throw new UnsupportedOperationException("Haven't handled the length type ID = 1 case");
                } else {
                    throw new UnsupportedOperationException("unknown length type ID " + lengthTypeId);
                }

            }

 */
        }

        public Packet(int version, int typeID, int literalValue) {
            this.version = version;
            this.typeID = typeID;
            this.literalValue = literalValue;
        }
    }

    private class Message {
        private String binaryString;
        public List<Packet> packets;

        public Message(String input) {
            packets = new ArrayList<>();
            binaryString = new BigInteger(input, 16).toString(2);

            int fourBits = binaryString.length() % 4;
            for (int i = 0; i < fourBits; i++) {
                binaryString = "0" + binaryString;
            }

            decodePackets();
        }

        public Message(String binaryString, boolean b) {
            packets = new ArrayList<>();

            this.binaryString = binaryString;

            decodePackets();
        }

        public int sumOfVersions() {
            return packets.stream()
                    .map(p -> p.version)
                    .collect(Collectors.summingInt(Integer::intValue));
        }

        private void decodePackets() {
            int index = 0;

            while (index < binaryString.length()) {

                int version = Integer.parseInt(binaryString.substring(index, index + 3), 2);
                int typeID = Integer.parseInt(binaryString.substring(index + 3, index + 6), 2);
                index += 6;

                if (typeID == 4) {
                    //Literal value

                    StringBuilder literalString = new StringBuilder();
                    boolean leadCharacterIsOne;
                    do {
                        String group = binaryString.substring(index, index + 5);
                        literalString.append(group, 1, 5);
                        leadCharacterIsOne = group.charAt(0) == '1';
                        index += 5;
                    } while (leadCharacterIsOne);

                    int literalValue = Integer.parseInt(literalString.toString(), 2);

                    Packet p = new Packet(version, typeID, literalValue);
                    packets.add(p);
                } else {

                    //TODO bring down the Operator logic and then should be set up to already loop through
                    // the next operands
                    // TODO add the operand, maybe new ctor to specify type 0 or 1
                    Packet p = new Packet(version, typeID, 99);
                    packets.add(p);

                    // Operator packet
                    // contains hierarchy of sub-packets
                    String lengthTypeId = binaryString.substring(index, index + 1);   // really want a single 0 or 1
                    index += 1;

                    if (lengthTypeId.equals("0")) {
                        // next 15 bits represent the total length in bits of the sub-packets contained by this packet
                        // sub-packets appear after the 15 bits
                        String substring = binaryString.substring(index, index + 15);
                        int length = Integer.parseInt(substring, 2);
                        index += 15;

                        String nextPackets = binaryString.substring(index, index + length);
                        index += length;

                        Message operands = new Message(nextPackets, true);
                        packets.addAll(operands.packets);

                    } else if (lengthTypeId.equals("1")) {
                        // next 11 bits represent the number of sub-packets immediately contained in this packet
                        // sub-packets appear after the 11 bits
                        throw new UnsupportedOperationException("Haven't handled the length type ID = 1 case");
                    } else {
                        throw new UnsupportedOperationException("unknown length type ID " + lengthTypeId);
                    }
                }

                // TODO check for any extra zeros at tend
                // for now just bailing if there aren't enough bits for version & typeID & more
                if (index >= binaryString.length() - 7) {
                    index = binaryString.length();
                }
            }
        }

    }
}
