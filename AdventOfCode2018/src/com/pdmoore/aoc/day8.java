package com.pdmoore.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day8 {
    private static int sumOfMetadata;

    public static int solution1(String inputFile) {
        String input = PuzzleInput.asStringFrom(inputFile).trim();
        return sumMetadata(input);
    }

    public static int solution2(String inputFile) {
        String input = PuzzleInput.asStringFrom(inputFile).trim();
        return valueOfNode(input);
    }

    public static int sumMetadata(String input) {
        String[] tokenItems = input.split(" ");
        List<String> tokens = new ArrayList<>(Arrays.asList(tokenItems));

        sumOfMetadata = 0;
        parseInput(tokens);

        return sumOfMetadata;
    }

    private static void parseInput(List<String> tokens) {
        if (tokens.isEmpty()) return;

        int numChildNodes = parseAndRemoveToken(tokens);
        int numMetadata = parseAndRemoveToken(tokens);

        for (int i = 0; i < numChildNodes; i++) {
            parseInput(tokens);
        }
        processMetadataForNode(tokens, numMetadata);
    }

    private static void processMetadataForNode(List<String> tokens, int numMetadata) {
        for (int i = 0; i < numMetadata; i++) {
            int metadataValue = parseAndRemoveToken(tokens);
            sumOfMetadata += metadataValue;
        }
    }

    public static int valueOfNode(String input) {
        String[] tokenItems = input.split(" ");
        List<String> tokens = new ArrayList<>(Arrays.asList(tokenItems));

        Node root = buildTree(null, tokens);

        return root.value;
    }

    private static Node buildTree(Node parent, List<String> tokens) {
        if (tokens.isEmpty()) return null;

        int numChildNodes = parseAndRemoveToken(tokens);
        int numMetadata = parseAndRemoveToken(tokens);

        Node node = new Node();
        List<Node> children = new ArrayList<>();
        for (int i = 0; i < numChildNodes; i++) {
            children.add(buildTree(node, tokens));
        }
        node.children = children;

        List<String> metadata = new ArrayList<>();
        for (int i = 0; i < numMetadata; i++) {
            String metadataValue = tokens.get(0);
            tokens.remove(0);
            metadata.add(metadataValue);
        }
        node.metadata = metadata;
        node.computeValue();

        return node;
    }

    private static int parseAndRemoveToken(List<String> tokens) {
        int numChildNodes = Integer.parseInt(tokens.get(0));
        tokens.remove(0);
        return numChildNodes;
    }

    private static class Node {
        List<String> metadata;
        List<Node> children;
        int value;

        public void computeValue() {
            value = 0;

            if (children.isEmpty()) {
                for (int i = 0; i < metadata.size(); i++) {
                    value += Integer.parseInt(metadata.get(i));
                }
            } else {
                for (int i = 0; i < metadata.size(); i++) {
                    int childIndex = Integer.parseInt(metadata.get(i));
                    if (childIndex <= children.size()) {
                        value += children.get(childIndex - 1).value;
                    }
                }
            }
        }
    }
}
