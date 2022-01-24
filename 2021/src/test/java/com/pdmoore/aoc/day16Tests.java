package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day16Tests {

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
        assertEquals(0, ((Operator)actual).lengthTypeId);
    }

    @Test
    void part1_decode_operator_LengthType1() {
        String input = "EE00D40C823060";

        Message message = new Message(input);
        Packet actual = message.outermostPacket;

        assertEquals(7, actual.version);
        assertEquals(3, actual.typeID);
        assertEquals(1, ((Operator)actual).lengthTypeId);

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

    private class Message {
        Packet outermostPacket;

        public Message(String hexString) {
            String binaryString = convertToPaddedBinaryString(hexString);
            outermostPacket = Packet.decode(binaryString);
        }

        private String convertToPaddedBinaryString(String hexString) {
            String binaryString = new BigInteger(hexString, 16).toString(2);

            int fourBits = binaryString.length() % 4;
            for (int i = 0; i < fourBits; i++) {
                binaryString = "0" + binaryString;
            }
            return binaryString;
        }
    }
}

class Packet {
    public int version;
    public int typeID;
    int packetLength;

    public int sumOfVersions() {
        return version;
    }

    public static Packet decode(String binaryString) {
        int version = Integer.parseInt(binaryString.substring(0, 3), 2);
        int typeID = Integer.parseInt(binaryString.substring(3, 6), 2);
        if (typeID == 4) {
            return Literal.decode(version, binaryString);
        } else {
            return Operator.decode(version, typeID, binaryString);
        }
    }
}

class Literal extends Packet {
    long value;

    public Literal(int version) {
        this.typeID = 4;
        this.version = version;
    }

    static Literal decode(int version, String binaryString) {
        Literal result = new Literal(version);

        StringBuilder builder = new StringBuilder();
        int i = 6;
        while (binaryString.charAt(i) == '1') {
            builder.append(binaryString.substring(i + 1, i + 5));
            i += 5;
        }
        builder.append(binaryString.substring(i + 1, i + 5));
        result.value = Long.parseLong(builder.toString(), 2);

        result.packetLength = 6 + i - 1;

        return result;
    }
}

class Operator extends Packet {
    List<Packet> packetList;
    public int lengthTypeId;

    public Operator() {
        this.packetList = new ArrayList<>();
    }

    @Override
    public int sumOfVersions() {
        int subPacketSum = packetList.stream()
                .map(p -> p.sumOfVersions())
                .collect(Collectors.summingInt(Integer::intValue));
        return subPacketSum + this.version;
    }

    static Operator decode(int version, int operatorID, String binaryString) {
        char lengthTypeID = binaryString.charAt(6);
        if (lengthTypeID == '0') {
            return packetsByTotalLengthInBits(version, operatorID, binaryString);
        } else {
            return packetsByNumberOfPackets(version, operatorID, binaryString);
        }
    }


    public static Operator packetsByTotalLengthInBits(int version, int typeID, String bin) {
        Operator operatorPacket = new Operator();
        operatorPacket.version = version;
        operatorPacket.typeID = typeID;
        operatorPacket.lengthTypeId = 0;
        List<Packet> subPacketlist = new ArrayList<>();

        int packetsTotalLength = Integer.parseInt(bin.substring(7, 7 + 15), 2);
        int parseIndex = 0;
        while (parseIndex < packetsTotalLength) {
            Packet p = Packet.decode(bin.substring(parseIndex + 7 + 15));
            parseIndex += p.packetLength;
            subPacketlist.add(p);
        }

        operatorPacket.packetList.addAll(subPacketlist);
        operatorPacket.packetLength = 7 + 15 + packetsTotalLength;

        return operatorPacket;
    }


    public static Operator packetsByNumberOfPackets(int version, int typeID, String binaryString) {
        Operator operatorPacket = new Operator();
        operatorPacket.version = version;
        operatorPacket.typeID = typeID;
        operatorPacket.lengthTypeId = 1;

        int packetCount = Integer.parseInt(binaryString.substring(7, 7 + 11), 2);
        int parseIndex = 0;
        for (int i = 0; i < packetCount; i++) {
            Packet packet = Packet.decode(binaryString.substring(parseIndex + 7 + 11));
            operatorPacket.packetList.add(packet);
            parseIndex += packet.packetLength;
        }

        operatorPacket.packetLength = 7 + 11 + parseIndex;

        return operatorPacket;
    }

}


