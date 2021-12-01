import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day10Tests {

    public List<Integer> createSimpleInput() {
        return new ArrayList<>(Arrays.asList(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4));
    }

    private List<Integer> createSecondInput() {
        return new ArrayList<>(Arrays.asList(28, 33, 18, 42, 31, 14, 46, 20, 48, 47,
                24, 23, 49, 45, 19, 38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3));
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
        List<Integer> input = PuzzleInput.asIntegerListFrom("./data/day10-part01");
        int result = solvePart1(input);

        assertEquals(2760, result);
    }

    @Test
    public void part2_simpleExample_recursiveSolution() {
        int result = solvePart2_recursionOutOfMemory(createSimpleInput());

        assertEquals(8, result);
    }

    @Test
    @Disabled("disabled - Double counts most of the possible paths")
    public void part2_simpleExample_AnotherRecursionSolution() {
        int result = solvePart2ByCountingPaths(createSimpleInput());

        assertEquals(8, result);
    }

    @Test
    public void part2_secondExample() {
        int result = solvePart2_recursionOutOfMemory(createSecondInput());

        assertEquals(19208, result);
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

    private int solvePart2_recursionOutOfMemory(List<Integer> input) {
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


    private int solvePart2ByCountingPaths(List<Integer> input) {
        Collections.sort(input);
        int finalJoltage = input.get(input.size() - 1);
        return recurseAndTrackPaths(input, finalJoltage);
    }

    private int recurseAndTrackPaths(List<Integer> input, int finalJoltage) {
        int count = 1;

        for (int i = 1; i < input.size(); i++) {
            List<Integer> copy = new ArrayList<>();
            copy.addAll(input);
            copy.remove(i);

            if (validChain(copy, finalJoltage)) {
                count += recurseAndTrackPaths(copy, finalJoltage);
            }
        }

        return count;
    }

    private boolean validChain(List<Integer> chain, int finalJoltage) {
        if (chain.get(chain.size() - 1) != finalJoltage) return false;

        int currentJoltage = chain.get(0);
        for (int i = 1; i < chain.size(); i++) {
            if (chain.get(i) - currentJoltage <= 3) {
                currentJoltage = chain.get(i);
            } else {
                return false;
            }
        }

        return true;
    }


    @Test
    public void part2_simpleExample_GroupPathsTogether() {
        long result = solvePart2WithoutRecursion(createSimpleInput());

        assertEquals(8, result);
    }

    @Test
    public void part2_secondExample_GroupPathsTogether() {
        long result = solvePart2WithoutRecursion(createSecondInput());

        assertEquals(19208, result);
    }

    @Test
    public void part2_solution() {
        List<Integer> input = PuzzleInput.asIntegerListFrom("./data/day10-part01");
        long result = solvePart2WithoutRecursion(input);
        long expected = 13816758796288L;
        assertEquals(expected, result);
    }

    private long solvePart2WithoutRecursion(List<Integer> input) {
        Collections.sort(input);

        int lastAdapterJoltage = input.get(input.size() - 1);
        final long[] sums = new long[lastAdapterJoltage + 1];
        sums[0] = 1;

        for (int i = 0; i < input.size(); i++) {
            int thisJoltage = input.get(i);
            final long x = thisJoltage >= 3 ? sums[thisJoltage - 3] : 0;
            final long y = thisJoltage >= 2 ? sums[thisJoltage - 2] : 0;
            final long z = thisJoltage >= 1 ? sums[thisJoltage - 1] : 0;
            sums[thisJoltage] = x + y + z;
        }

        return sums[sums.length - 1];
    }

    // One of the abandoned recursive approaches
    // Given the sorted input, creates a list of groups of adapters
    // Adapters within a group are within one joltage of each other
    private List<List<Integer>> buildGrouping(List<Integer> input) {
        List<List<Integer>> groups = new ArrayList<>();

        List<Integer> group = new ArrayList<>();
        int lastJoltage = 0;
        group.add(lastJoltage);

        for (int i = 0; i < input.size(); i++) {
            int thisJoltage = input.get(i);
            if (group.size() == 4 || thisJoltage - lastJoltage != 1) {
                groups.add(group);
                group = new ArrayList<>();
                group.add(thisJoltage);
            } else {
                group.add(thisJoltage);
            }

            lastJoltage = thisJoltage;
        }

        if (!group.isEmpty()) {
            groups.add(group);
        }

        return groups;
    }


}
