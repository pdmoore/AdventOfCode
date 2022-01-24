package com.pdmoore.aoc;

import org.junit.jupiter.api.Test;

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
        assertEquals(2021, ((Literal) actual).value);
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
        assertEquals(0, ((Operator) actual).lengthTypeId);
    }

    @Test
    void part1_decode_operator_LengthType1() {
        String input = "EE00D40C823060";

        Message message = new Message(input);
        Packet actual = message.outermostPacket;

        assertEquals(7, actual.version);
        assertEquals(3, actual.typeID);
        assertEquals(1, ((Operator) actual).lengthTypeId);

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

    @Test
    void part2_sum() {
        String input = "C200B40A82";

        Message message = new Message(input);
        int actual = message.outermostPacket.value();

        assertEquals(3, actual);
    }

    @Test
    void part2_product() {
        String input = "04005AC33890";

        Message m = new Message(input);
        int actual = m.outermostPacket.value();

        assertEquals(54, actual);
    }

    @Test
    void part2_minimum() {
        String input = "880086C3E88112";

        Message m = new Message(input);
        int actual = m.outermostPacket.value();

        assertEquals(7, actual);
    }

    @Test
    void part2_maximum() {
        String input = "CE00C43D881120";

        Message m = new Message(input);
        int actual = m.outermostPacket.value();

        assertEquals(9, actual);
    }

    @Test
    void part2_greaterThan() {
        String input = "F600BC2D8F";

        Message m = new Message(input);
        int actual = m.outermostPacket.value();

        assertEquals(0, actual);
    }

    @Test
    void part2_lessThan() {
        String input = "D8005AC2A8F0";

        Message m = new Message(input);
        int actual = m.outermostPacket.value();

        assertEquals(1, actual);
    }

    @Test
    void part2_equalTo_NotEqual() {
        String input = "9C005AC2F8F0";

        Message m = new Message(input);
        int actual = m.outermostPacket.value();

        assertEquals(0, actual);
    }

    @Test
    void part2_equalTo_Equal() {
        String input = "9C0141080250320F1802104A08";

        Message m = new Message(input);
        int actual = m.outermostPacket.value();

        assertEquals(1, actual);
    }
}

class Message {
    Packet outermostPacket;

    public Message(String hexValues) {
        String binaryString = convertToPaddedBinaryString(hexValues);
        outermostPacket = Packet.decode(binaryString);
    }

    private String convertToPaddedBinaryString(String hexString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexString.length(); i++)
        {
            sb.append(hexToBinaryDigits(hexString.charAt(i)));
        }
        return sb.toString();
    }

    private static String hexToBinaryDigits(char hexChar) {
        return switch (hexChar) {
            case '0' -> "0000";
            case '1' -> "0001";
            case '2' -> "0010";
            case '3' -> "0011";
            case '4' -> "0100";
            case '5' -> "0101";
            case '6' -> "0110";
            case '7' -> "0111";
            case '8' -> "1000";
            case '9' -> "1001";
            case 'A' -> "1010";
            case 'B' -> "1011";
            case 'C' -> "1100";
            case 'D' -> "1101";
            case 'E' -> "1110";
            case 'F' -> "1111";
            default -> throw new IllegalArgumentException("Bad Hex Character: " + hexChar);
        };
    }
}

class Packet {
    int version;
    int typeID;
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

    public int value() {
        return 0;
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

    public int value() {
        //TODO casting long to int - convert all value to return long instead
        return (int) value;
    }
}

class Operator extends Packet {
    List<Packet> packetList;
    public int lengthTypeId;

    public Operator(int version, int operatorID, char lengthTypeID) {
        this.version = version;
        this.typeID = operatorID;
        this.lengthTypeId = Character.getNumericValue(lengthTypeID);

        this.packetList = new ArrayList<>();
    }

    @Override
    public int sumOfVersions() {
        int packetListSum = packetList.stream()
                .map(p -> p.sumOfVersions())
                .collect(Collectors.summingInt(Integer::intValue));
        return packetListSum + this.version;
    }

    @Override
    public int value() {

        int value = switch (typeID) {
            case 0 -> sumOfOperands();
            case 1 -> productOfOperands();
            case 2 -> minimumOfOperands();
            case 3 -> maximumOfOperands();
            case 5 -> greaterThan();
            case 6 -> lessThan();
            case 7 -> equalTo();
            default -> throw new UnsupportedOperationException("unknown typeID: " + typeID);
        };

        return value;
    }

    private int sumOfOperands() {
        return packetList.stream()
                .map(p -> p.value())
                .collect(Collectors.summingInt(Integer::intValue));
    }

    private int productOfOperands() {
        return packetList.stream()
                .map(p -> p.value())
                .reduce(1, (a, b) -> a * b);
    }

    private int minimumOfOperands() {
        return packetList.stream()
                .map(p -> p.value())
                .min(Integer::compare).get();
    }

    private int maximumOfOperands() {
        return packetList.stream()
                .map(p -> p.value())
                .max(Integer::compare).get();
    }

    private int greaterThan() {
        if (packetList.get(0).value() > packetList.get(1).value()) {
            return 1;
        }
        return 0;
    }

    private int lessThan() {
        if (packetList.get(0).value() < packetList.get(1).value()) {
            return 1;
        }
        return 0;
    }

    private int equalTo() {
        if (packetList.get(0).value() == packetList.get(1).value()) {
            return 1;
        }
        return 0;
    }

    static Operator decode(int version, int operatorID, String binaryString) {
        char lengthTypeID = binaryString.charAt(6);
        Operator operatorPacket = new Operator(version, operatorID, lengthTypeID);

        if (operatorPacket.lengthTypeId == 0) {
            return packetsByTotalLengthInBits(operatorPacket, binaryString);
        } else {
            return packetsByNumberOfPackets(operatorPacket, binaryString);
        }
    }

    public static Operator packetsByTotalLengthInBits(Operator operatorPacket, String binaryString) {
        List<Packet> subPacketlist = new ArrayList<>();

        int packetsTotalLength = Integer.parseInt(binaryString.substring(7, 7 + 15), 2);
        int parseIndex = 0;
        while (parseIndex < packetsTotalLength) {
            Packet p = Packet.decode(binaryString.substring(parseIndex + 7 + 15));
            parseIndex += p.packetLength;
            subPacketlist.add(p);
        }

        operatorPacket.packetList.addAll(subPacketlist);
        operatorPacket.packetLength = 7 + 15 + packetsTotalLength;

        return operatorPacket;
    }

    public static Operator packetsByNumberOfPackets(Operator operatorPacket, String binaryString) {
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