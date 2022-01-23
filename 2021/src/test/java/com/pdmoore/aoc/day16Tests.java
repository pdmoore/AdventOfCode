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


    @Test
    void part1_decode_literal_via_Message() {
        String input = "D2FE28";

        Message message = new Message(input);

        Packet actual = message.outermostPacket;

        assertEquals(6, actual.version);
        assertEquals(4, actual.typeID);
        assertEquals(2021, ((Literal)actual).value);
    }

    @Test
    void part1_sumVersions_SingleLiteral() {
        String input = "D2FE28";

        Message message = new Message(input);
        Packet actual = message.outermostPacket;

        assertEquals(6, actual.sumOfVersions());
    }

    @Test
    void part1_decodeMultipleOperatorInOneMessage() {
        String input = "38006F45291200";

        Message message = new Message(input);
        Packet actual = message.outermostPacket;

        assertEquals(9, actual.sumOfVersions());
    }

    @Test
    void part1_decode_operator_LengthType0() {
        String input = "38006F45291200";

        Message message = new Message(input);
        Packet actual = message.outermostPacket;

        assertEquals(1, actual.version);
        assertEquals(6, actual.typeID);
        assertEquals(0, actual.lengthTypeId);
    }

    @Test
    void part1_decode_operator_LengthType1() {
        String input = "EE00D40C823060";

        Message message = new Message(input);
        Packet actual = message.outermostPacket;

        assertEquals(7, actual.version);
        assertEquals(3, actual.typeID);
        assertEquals(1, actual.lengthTypeId);

        assertEquals(14, actual.sumOfVersions());
    }

    @Test
    void part1_versionSums_examples() {
        assertEquals(16, new Message("8A004A801A8002F478").outermostPacket.sumOfVersions());
        assertEquals(23, new Message("C0015000016115A2E0802F182340").outermostPacket.sumOfVersions());
        assertEquals(31, new Message("A0016C880162017C3686B18A3D4780").outermostPacket.sumOfVersions());
    }


    @Test
    void part1_solution() {
        String input = PuzzleInput.asStringFrom("data/day16");

        Message actual = new Message(input);

        assertEquals(920, actual.outermostPacket.sumOfVersions());
    }

    public Packet PacketDecoder(String binaryString) {
        int version = Integer.parseInt(binaryString.substring(0, 3), 2);
        int typeID = Integer.parseInt(binaryString.substring(3, 6), 2);
        if (typeID == 4) {
            return LiteralDecoder(version, binaryString);
        } else {
            return OperatorDecoder(version, typeID, binaryString);
        }
    }

    class Packet {
        public int version;
        public int typeID;
        public int lengthTypeId;
        int len;

        public int sumOfVersions() {
            return version;
        }
    }

    Literal LiteralDecoder(int version, String bin) {
        Literal result = new Literal();
        result.version = version;

        StringBuilder builder = new StringBuilder();
        int i = 6;
        while (bin.charAt(i) == '1') {
            builder.append(bin.substring(i + 1, i + 5));
            i += 5;
        }
        builder.append(bin.substring(i + 1, i + 5));
        result.value = Long.parseLong(builder.toString(), 2);

        result.len = 6 + i - 1;

        return result;
    }

    class Literal extends Packet {
        long value;

        public Literal() {
            this.typeID = 4;
        }
    }

    Operator OperatorDecoder(int version, int typeID, String binaryString) {
        if (binaryString.charAt(6) == '0') {
            return fifteenBit(version, typeID, binaryString);
        } else {
            return elevenBit(version, typeID, binaryString);
        }
    }

    //total length
    public Operator fifteenBit(int version, int typeID, String bin) {
        Operator outp = new Operator();
        outp.version = version;
        outp.typeID = typeID;
        outp.lengthTypeId = 0;
        List<Packet> packetlist = new ArrayList<>();

        int packetslen = Integer.parseInt(bin.substring(7, 7 + 15), 2);
        int packetcursor = 0;
        while (packetcursor < packetslen) {
            Packet p = PacketDecoder(bin.substring(packetcursor + 7 + 15));
            packetcursor += p.len;
            packetlist.add(p);
        }

        outp.packets = packetlist.toArray(new Packet[0]);
        outp.len = 7 + 15 + packetslen;

        return outp;
    }

    //number of sub-packets
    public Operator elevenBit(int version, int typeID, String bin) {
        Operator outp = new Operator();
        outp.version = version;
        outp.typeID = typeID;
        outp.lengthTypeId = 1;

        int packetcount = Integer.parseInt(bin.substring(7, 7 + 11), 2);
        int packetcursor = 0;
        outp.packets = new Packet[packetcount];
        for (int i = 0; i < packetcount; i++) {
            outp.packets[i] = PacketDecoder(bin.substring(packetcursor + 7 + 11));
            packetcursor += outp.packets[i].len;
        }

        outp.len = 7 + 11 + packetcursor;

        return outp;
    }

    class Operator extends Packet {
//        int typeID;
        Packet[] packets;


        @Override
        public int sumOfVersions() {
            int sum = 0;
            for (Packet packet : packets) {
                sum += packet.sumOfVersions();
            }
            return sum + version;
        }
    }


    private class Message {
//        private int packetLimit;
        private String binaryString;
        public List<Packet> packets;
//        private int subPacketsLength;
        Packet outermostPacket;

        public Message(String input) {
            packets = new ArrayList<>();
//            packetLimit = Integer.MAX_VALUE;
            binaryString = new BigInteger(input, 16).toString(2);

            int fourBits = binaryString.length() % 4;
            for (int i = 0; i < fourBits; i++) {
                binaryString = "0" + binaryString;
            }

//            decodePackets();
            outermostPacket = PacketDecoder(binaryString);
        }

//        public Message(String binaryString, boolean b) {
//            packets = new ArrayList<>();
//            packetLimit = Integer.MAX_VALUE;
//
//            this.binaryString = binaryString;
//
//            decodePackets();
//        }

//        public Message(String binaryString, int numPackets) {
//            packets = new ArrayList<>();
//            this.packetLimit = numPackets;
//            this.binaryString = binaryString;
//
//            decodePackets();
//        }

        public int sumOfVersions() {
            return packets.stream()
                    .map(p -> p.version)
                    .collect(Collectors.summingInt(Integer::intValue));
        }

        private void decodePackets() {
//            int index = 0;
//
//            while (index < binaryString.length()) {
//
//                int version = Integer.parseInt(binaryString.substring(index, index + 3), 2);
//                int typeID = Integer.parseInt(binaryString.substring(index + 3, index + 6), 2);
//                index += 6;
//
//                if (typeID == 4) {
//                    //Literal value
//
//                    StringBuilder literalString = new StringBuilder();
//                    boolean leadCharacterIsOne;
//                    do {
//                        String group = binaryString.substring(index, index + 5);
//                        literalString.append(group, 1, 5);
//                        leadCharacterIsOne = group.charAt(0) == '1';
//                        index += 5;
//                    } while (leadCharacterIsOne);
//
//                    int literalValue = Integer.parseInt(literalString.toString(), 2);
//
//                    Packet p = new Packet(version, typeID, literalValue);
//                    packets.add(p);
//                } else {
//
//                    // Operator packet
//                    // contains hierarchy of sub-packets
//                    String lengthTypeId = binaryString.substring(index, index + 1);   // really want a single 0 or 1
//                    index += 1;
//
//                    //TODO bring down the Operator logic and then should be set up to already loop through
//                    // the next operands
//                    // TODO add the operand, maybe new ctor to specify type 0 or 1
//                    Packet p = new Packet(version, typeID, 99);
//                    p.lengthTypeId = Integer.parseInt(lengthTypeId);
//                    packets.add(p);
//
//                    if (lengthTypeId.equals("0")) {
//                        // next 15 bits represent the total length in bits of the sub-packets contained by this packet
//                        // sub-packets appear after the 15 bits
//                        String substring = binaryString.substring(index, index + 15);
//                        int length = Integer.parseInt(substring, 2);
//                        index += 15;
//
//                        String nextPackets = binaryString.substring(index, index + length);
//                        index += length;
//
//                        Message operands = new Message(nextPackets, true);
//                        packets.addAll(operands.packets);
//
//                    } else if (lengthTypeId.equals("1")) {
//                        // next 11 bits represent the number of sub-packets immediately contained in this packet
//                        // sub-packets appear after the 11 bits
//                        String substring = binaryString.substring(index, index + 11);
//                        int numPackets = Integer.parseInt(substring, 2);
//                        index += 11;
//
//                        //TODO - Crap - how to know how many bits to jump ahead???
//                        // well, if they are literal, it's 11 times the # of packets
//                        // but you know the real data will be packets inside packets....
//                        String remainderOfThisMessage = binaryString.substring(index);
//
//                        Message subPackets = new Message(remainderOfThisMessage, numPackets);
//                        packets.addAll(subPackets.packets);
//                        index += subPackets.subPacketsLength;
//                    } else {
//                        throw new UnsupportedOperationException("unknown length type ID " + lengthTypeId);
//                    }
//                }
//
//                // TODO check for any extra zeros at tend
//                // for now just bailing if there aren't enough bits for version & typeID & more
//                if (index >= binaryString.length() - 7) {
//                    index = binaryString.length();
//                }
//
//                if (packets.size() >= packetLimit) {
//                    subPacketsLength = index;
//                    return;
//                }
//            }
        }

    }

}
