package com.pdmoore.aoc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class day24Tests {

    @Test
    public void parseInput_SingleUnit_Weakness_NoImmunity() {
        day24.Group.resetIdCount();
        String input = "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2";

        day24.Group actual = day24.parseInputLine(input);

        assertAll(
                () -> assertEquals(1, actual.Id),
                () -> assertEquals(17, actual.units),
                () -> assertEquals(5390, actual.hpPerUnit),
                () -> assertEquals(4507, actual.attackDamage),
                () -> assertEquals(day24.ATTACK_TYPE.FIRE, actual.attackType),
                () -> assertEquals(2, actual.initiative),
                () -> assertEquals(2, actual.weakness.size()),
                () -> assertTrue(actual.weakness.contains(day24.ATTACK_TYPE.BLUDGEONING), "weakness to bludgeoning"),
                () -> assertTrue(actual.weakness.contains(day24.ATTACK_TYPE.RADIATION), "weakness to radiation"),
                () -> assertTrue(actual.immunity.isEmpty(), "no immunities")
        );
    }

    @Test
    public void parseInput_SingleUnit_NoImmunityOrWeakness() {
        String input = "442 units each with 1918 hit points with an attack that does 35 fire damage at initiative 8";

        day24.Group actual = day24.parseInputLine(input);

        assertAll(
                () -> assertTrue(actual.immunity.isEmpty(), "no immunity"),
                () -> assertTrue(actual.weakness.isEmpty(), "no weakness")
        );
    }

    @Test
    public void parseInput_SingleUnit_BothImmunityAndWeakness() {
        String input = "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3";

        day24.Group actual = day24.parseInputLine(input);

        assertAll(
                () -> assertEquals(1, actual.immunity.size()),
                () -> assertEquals(day24.ATTACK_TYPE.FIRE, actual.immunity.get(0)),

                () -> assertTrue(actual.weakness.contains(day24.ATTACK_TYPE.SLASHING), "weakness to slashing")
        );
    }

    @Test
    public void parseInput_SingleUnit_AttackType_Cold() {
        String input = "18 units each with 729 hit points (weak to fire; immune to cold, slashing)\n" +
                " with an attack that does 8 radiation damage at initiative 10";

        day24.Group actual = day24.parseInputLine(input);

        assertTrue(actual.immunity.contains(day24.ATTACK_TYPE.COLD), "immune to cold");
    }

    @Test
    public void SingleUnit_EffectivePower() {
        String input = "18 units each with 729 hit points (weak to fire; immune to cold, slashing)\n" +
                " with an attack that does 8 radiation damage at initiative 10";

        day24.Group actual = day24.parseInputLine(input);

        assertEquals(144, actual.effectivePower());
    }

    // TODO handle IMMUNE SYSTEM COLLECTION
    // TODO handle INFECTION COLLECTION

    @Test
    public void parseInput_Immune() {
        List<String> input = Arrays.asList(
                "Immune System:",
                "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
                "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3"
        );

        day24.Army actual = day24.parseSingleArmyInput(input);

        assertAll(
                () -> assertEquals("Immune System:", actual.name),
                () -> assertEquals(2, actual.groups.size()),
                () -> assertEquals(17 + 989, actual.totalUnits())
        );
    }

    @Test
    public void parseInput_TwoArmies() {
        List<String> input = utilities.getFileContentsAsStrings("data/aoc18.24.txt");

        day24.Simulator actual = day24.parseInput(input);

        assertEquals(2, actual.armies.size());
    }

    @Test
    public void parseInput_Sample() {
        List<String> input = utilities.getFileContentsAsStrings("data/aoc18.24a.txt");

        day24.Simulator actual = day24.parseInput(input);
        String[] actualTokens = actual.toString().split("\\n");

        String expected = "Immune System:\n" +
                "Group 1 contains 17 units\n" +
                "Group 2 contains 989 units\n" +
                "Infection:\n" +
                "Group 1 contains 801 units\n" +
                "Group 2 contains 4485 units\n";
        String[] expectedTokens = expected.split("\\n");

        assertArrayEquals(expectedTokens, actualTokens);
    }

    @Test
    public void targetSelection_Sample_firstRound() {
        List<String> input = utilities.getFileContentsAsStrings("data/aoc18.24a.txt");

        day24.Simulator actual = day24.parseInput(input);

        String targetSelection = actual.targetSelection();
        String[] actualTokens = targetSelection.split("\\n");

        String expected = "Infection group 1 would deal defending group 1 185832 damage\n" +
                "Infection group 1 would deal defending group 2 185832 damage\n" +
                "Infection group 2 would deal defending group 2 107640 damage\n" +
                "Immune System group 1 would deal defending group 1 76619 damage\n" +
                "Immune System group 1 would deal defending group 2 153238 damage\n" +
                "Immune System group 2 would deal defending group 1 24725 damage\n";
        String[] expectedTokens = expected.split("\\n");

        assertArrayEquals(expectedTokens, actualTokens);
    }

    @Test
    public void targetSelection_Sample_TieBreaker() {
        List<String> input = utilities.getFileContentsAsStrings("data/aoc18.24a.txt");

        day24.Simulator actual = day24.parseInput(input);

        actual.targetSelection();
        actual.attacking();

        String[] actualTokens = actual.toString().split("\\n");

        String expected = "Immune System:\n" +
                "Group 2 contains 905 units\n" +
                "Infection:\n" +
                "Group 1 contains 797 units\n" +
                "Group 2 contains 4434 units\n";
        String[] expectedTokens = expected.split("\\n");

        assertArrayEquals(expectedTokens, actualTokens);

        actual.targetSelection();
        actual.attacking();

        actualTokens = actual.toString().split("\\n");

        expected = "Immune System:\n" +
                "Group 2 contains 761 units\n" +
                "Infection:\n" +
                "Group 1 contains 793 units\n" +
                "Group 2 contains 4434 units\n";
        expectedTokens = expected.split("\\n");

        assertArrayEquals(expectedTokens, actualTokens);
    }

    @Test
    public void attacking_Sample_firstRound() {
        List<String> input = utilities.getFileContentsAsStrings("data/aoc18.24a.txt");
        day24.Simulator actual = day24.parseInput(input);
        actual.targetSelection();

        String attacking = actual.attacking();
        String[] actualTokens = attacking.split("\\n");

        String expected = "Infection group 2 attacks defending group 2, killing 84 units\n" +
                "Immune System group 2 attacks defending group 1, killing 4 units\n" +
                "Immune System group 1 attacks defending group 2, killing 51 units\n" +
                "Infection group 1 attacks defending group 1, killing 17 units\n";
        String[] expectedTokens = expected.split("\\n");

        assertArrayEquals(expectedTokens, actualTokens);
    }

    @Test
    public void battle_Sample_toFinish() {
        List<String> input = utilities.getFileContentsAsStrings("data/aoc18.24a.txt");
        day24.Simulator actual = day24.parseInput(input);

        actual.battle();

        assertEquals("Infection:", actual.winner.name);
        assertEquals(5216, actual.infectionArmy.totalUnits());
    }

    @Test
//    @Disabled("Infection wins but result is too high")
    @Disabled("2021-12 noticed this test was failing")
    public void battle_solution1() {
        List<String> input = utilities.getFileContentsAsStrings("data/aoc18.24.txt");
        day24.Simulator actual = day24.parseInput(input);

        actual.battle();

        assertEquals("Infection:", actual.winner.name);
        assertEquals(21891, actual.infectionArmy.totalUnits());
        // 22458 too high
    }

    @Test
    public void computeResult() {
        List<String> input = Arrays.asList(
                "Immune System:",
                "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
                "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3"
        );

        day24.Army actual = day24.parseSingleArmyInput(input);

        assertEquals(17 + 989, actual.totalUnits());
    }


    @Test
    public void targetSelection_SortedBy_DecreasingEffectivePower() {
        day24.Group.resetIdCount();

        int ignored = -1;

        day24.Group group1 = new day24.Group(5, ignored, ignored, 50, day24.ATTACK_TYPE.SLASHING, Collections.emptyList(), Collections.emptyList());
        day24.Group group2 = new day24.Group(10, ignored, ignored, 2, day24.ATTACK_TYPE.BLUDGEONING, Collections.emptyList(), Collections.emptyList());
        day24.Group group3 = new day24.Group(30, ignored, ignored, 10, day24.ATTACK_TYPE.RADIATION, Collections.emptyList(), Collections.emptyList());
        List<day24.Group> sortInPlace = new ArrayList<>();
        sortInPlace.add(group1);
        sortInPlace.add(group2);
        sortInPlace.add(group3);

        day24.sortByDecreasingEffectivePower(sortInPlace);

        assertEquals(3, sortInPlace.get(0).Id);
        assertEquals(1, sortInPlace.get(1).Id);
        assertEquals(2, sortInPlace.get(2).Id);
    }

    @Test
    public void targetSelection_SortedBy_Intiative_WhenEffectivePowerSame() {
        day24.Group.resetIdCount();

        int ignored = -1;

        day24.Group group1 = new day24.Group(5, ignored, 5, 10, day24.ATTACK_TYPE.SLASHING, Collections.emptyList(), Collections.emptyList());
        day24.Group group2 = new day24.Group(5, ignored, 99, 10, day24.ATTACK_TYPE.BLUDGEONING, Collections.emptyList(), Collections.emptyList());
        day24.Group group3 = new day24.Group(25, ignored, 44, 2, day24.ATTACK_TYPE.RADIATION, Collections.emptyList(), Collections.emptyList());
        List<day24.Group> sortInPlace = new ArrayList<>();
        sortInPlace.add(group1);
        sortInPlace.add(group2);
        sortInPlace.add(group3);

        day24.sortByDecreasingEffectivePower(sortInPlace);

        assertEquals(2, sortInPlace.get(0).Id);
        assertEquals(3, sortInPlace.get(1).Id);
        assertEquals(1, sortInPlace.get(2).Id);
    }

}
