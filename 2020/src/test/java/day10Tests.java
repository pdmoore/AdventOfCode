import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day10Tests {

    public List<Integer> createSimpleInput() {
        List<Integer> input = new ArrayList<>();
        input.add(16);
        input.add(10);
        input.add(15);
        input.add(5);
        input.add(1);
        input.add(11);
        input.add(7);
        input.add(19);
        input.add(6);
        input.add(12);
        input.add(4);
        return input;
    }

    private List<Integer> createSecondInput() {
        List<Integer> input = new ArrayList<>();
        input.add(28);
        input.add(33);
        input.add(18);
        input.add(42);
        input.add(31);
        input.add(14);
        input.add(46);
        input.add(20);
        input.add(48);
        input.add(47);
        input.add(24);
        input.add(23);
        input.add(49);
        input.add(45);
        input.add(19);
        input.add(38);
        input.add(39);
        input.add(11);
        input.add(1);
        input.add(32);
        input.add(25);
        input.add(35);
        input.add(8);
        input.add(17);
        input.add(7);
        input.add(9);
        input.add(4);
        input.add(2);
        input.add(34);
        input.add(10);
        input.add(3);
        return input;
    }

    @Test
    public void part1_simpleExample() {
        int result = solvePart1(createSimpleInput());

        assertEquals(7 * 5, result);
    }


    @Test
    public void part1_secondExample() {
        int result = solvePart1(createSecondInput());

        assertEquals(22 * 10, result);
    }

    @Test
    public void part1_solution() {
        List<Integer> input = Utilities.fileToIntegerList("./data/day10-part01");
        int result = solvePart1(input);

        assertEquals(2760, result);
    }

    @Test
    public void part2_simpleExample() {
        int result = solvePart2(createSimpleInput());

        assertEquals(8, result);
    }

    @Test
    public void part2_secondExample() {
        int result = solvePart2(createSecondInput());

        assertEquals(19208, result);
    }

    @Test
    public void part2_solution() {
        List<Integer> input = Utilities.fileToIntegerList("./data/day10-part01");
        int result = solvePart2(input);

        assertEquals(-99, result);
    }



//----------------------------------------------

    private int solvePart1(List<Integer> input) {
        Collections.sort(input);

        int jolt1 = 0;
        int jolt3 = 0;
        int currentJoltage = 0;

        for (int i = 0; i < input.size(); i++) {
            int thisJoltage = input.get(i);
            if (thisJoltage - currentJoltage == 1) {
                jolt1++;
            } else if (thisJoltage - currentJoltage == 3) {
                jolt3++;
            }


            currentJoltage = thisJoltage;
        }

        jolt3++;  // the device has a final 3 step

        return jolt1 * jolt3;
    }


    class Node {
        int joltage;
        List<Node> children;

        public Node(int j) {
            joltage = j;
            children = new ArrayList<>();
        }

        public void addChild(Node child) {
            children.add(child);
        }
    }

    private int solvePart2(List<Integer> input) {
        Collections.sort(input);

        Node root = new Node(0);
        root.joltage = 0;

        recurse(input, 0, root);

        return numLeavesThatMatch(root, input.get(input.size() - 1));
    }

    private void recurse(List<Integer> input, int i, Node node) {
        if (i >= input.size()) return;

        Integer nextInput = input.get(i);
        if (nextInput - node.joltage <= 3) {
            Node child = new Node(input.get(i));
            child.joltage = input.get(i);
            node.addChild(child);
            recurse(input, i + 1, child);
        }
        if (i < input.size() - 1) {
            nextInput = input.get(i + 1);
            if (nextInput - node.joltage <= 3) {
                Node child = new Node(input.get(i));
                child.joltage = input.get(i + 1);
                node.addChild(child);
                recurse(input, i + 2, child);
            }
        }
        if (i < input.size() - 2) {
            nextInput = input.get(i + 2);
            if (nextInput - node.joltage <= 3) {
                Node child = new Node(input.get(i + 2));
                child.joltage = input.get(i + 2);
                node.addChild(child);
                recurse(input, i + 3, child);
            }
        }
    }

    //TODO - not sure - do I need to recursively do this?
    private int numLeavesThatMatch(Node root, Integer terminator) {
        int leafCount = 0;

        leafCount += childrenMatching(root, terminator);

        return leafCount;
    }

    private int childrenMatching(Node node, Integer terminator) {
        if (node.joltage == terminator) {
            return 1;
        }

        int count = 0;
        for (Node child :
                node.children) {
            count += childrenMatching(child, terminator);
        }

        return count;
    }

}
