import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class day15Tests {


    // read file and set up map, elves, and goblins
    // day 13 used a char[][] structure
    // battle logic
    //  - target selection when more than one
    //  - attack
    // movement logic

    // end of game detection
    // scoring at the end

    // character array # . E G
    // list of goblins
    // list of elves

    // counter of rounds


    @Test
    public void createMap_oneOfEachCharacter() {
        char[][] map = new char[][]{
                {'#', '#', '#', '#', '#'},
                {'#', '.', 'E', 'G', '#'},
                {'#', 'G', '.', 'E', '#'},
                {'#', 'E', '.', '.', '#'},
                {'#', '#', '#', '#', '#'},
        };

        Day15 day15 = new Day15(map);

        assertEquals(3, day15.elfCount());
        assertEquals(2, day15.goblinCount());
    }

    @Test
    public void Attack_LeftRight() {
        char[][] map = new char[][]{
                {'#', '#', '#', '#', '#'},
                {'#', '.', 'E', 'G', '#'},
                {'#', '#', '#', '#', '#'},
        };

        Day15 day15 = new Day15(map);
        day15.combat();

        int rounds = 67;
        int hitpoints_remaining = 2;
        int expected = rounds * hitpoints_remaining;
        assertEquals(expected, day15.result());
    }

    @Test
    public void Simple_Movement_LeftRight_OneCell() {
        char[][] map = new char[][]{
                {'#', '#', '#', '#', '#', '#', '#'},
                {'#', 'E', '.', '.', '.', 'G', '#'},
                {'#', '#', '#', '#', '#', '#', '#'},
        };

        Day15 day15 = new Day15(map);
        day15.combat();

        int rounds = 68;
        int hitpoints_remaining = 2;
        int expected = rounds * hitpoints_remaining;
        assertEquals(expected, day15.result());
    }


    @Test
    public void Attack_PickWeakestFoe() {
        char[][] map = new char[][]{
                {'#', '#', '#', '#', '#'},
                {'#', 'G', 'E', '.', '#'},
                {'#', 'E', 'G', 'E', '#'},
                {'#', '.', 'E', '.', '#'},
                {'#', '#', '#', '#', '#'},
        };

        Day15 day15 = new Day15(map);
        day15.combat();

        assertEquals(20298, day15.result());
    }

    @Test
    @Disabled
    public void exampleWithMovement() {
        char[][] map = new char[][]{
                {'#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', 'G', '.', '.', 'G', '.', '.', 'G', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', 'G', '.', '.', 'E', '.', '.', 'G', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', 'G', '.', '.', 'G', '.', '.', 'G', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#'}
        };

        Day15 day15 = new Day15(map);
        day15.combat();

        assertEquals(-99, day15.result());
    }

    @Test
    @Disabled
    public void Movement_Up() {
//        char[][] map = new char[][]{
//                {'.', 'G' },
//                {'E', '.' },
//        };
//
//        Day15Unit elf = new Day15Unit(1, 0, 'E');
//
//        Point actual = Day15Move.calculateMove(elf, map);
//
//        assertEquals(0, actual.y);
//        assertEquals(0, actual.x);
    }

    @Test
    @Disabled
    public void FullExample() {
        char[][] map = new char[][]{
                {'#', '#', '#', '#', '#', '#', '#'},
                {'#', '.', 'G', '.', '.', '.', '#'},
                {'#', '.', '.', '.', 'E', 'G', '#'},
                {'#', '.', '#', '.', '#', 'G', '#'},
                {'#', '.', '.', 'G', '#', 'E', '#'},
                {'#', '.', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#', '#', '#'},
        };

        Day15 day15 = new Day15(map);
        day15.combat();

        assertEquals(27730, day15.result());
    }

    private class Day15 {
        char[][] map;
        List<Day15Unit> units;
        private int goblinCount;
        private int elfCount;
        private int rounds;

        public Day15(char[][] input) {
            map = new char[input.length][input[0].length];
            units = new ArrayList<>();

            for (int row = 0; row < input.length; row++) {
                for (int col = 0; col < input[0].length; col++) {
                    map[row][col] = input[row][col];

                    if (map[row][col] == 'E') {
                        addAnElf(row, col);
                    } else if (map[row][col] == 'G') {
                        AddGoblin(row, col);
                    }
                }
            }

            rounds = 0;
        }

        private void AddGoblin(int row, int col) {
            Day15Unit goblin = new Day15Unit(row, col, 'G');
            units.add(goblin);
            goblinCount++;
        }

        private void addAnElf(int row, int col) {
            Day15Unit elf = new Day15Unit(row, col, 'E');
            units.add(elf);
            elfCount++;
        }

        public int elfCount() {
            return elfCount;
        }

        public int goblinCount() {
            return goblinCount;
        }

        public void combat() {
            while (keepFighting()) {
                rounds++;

                for (int row = 0; row < map.length; row++) {
                    for (int col = 0; col < map[0].length; col++) {
                        char whoIs = map[row][col];
                        if (whoIs == 'E' || whoIs == 'G') {

                            Day15Unit unit = getUnitAt(row, col);
                            if (unit.turnCompleted < rounds) {

                                Day15Unit foe = getWeakestAdjacentFoe(unit);
                                if (foe == null) {
                                    // execute move - dijkstra's?
                                    Point moveTo = calculateMove(unit, map);
                                    if (moveTo != null) {
                                        move(unit, moveTo);
                                    }
                                }

                                foe = getWeakestAdjacentFoe(unit);
                                if (foe != null) {
                                    attackOn(foe);
                                    if (!keepFighting()) return;
                                }

                                unit.turnCompleted = rounds;
                            }
                        }
                    }
                }
            }
        }

        private void move(Day15Unit unit, Point moveTo) {
            map[unit.row][unit.col] = '.';

            unit.row = moveTo.x;
            unit.col = moveTo.y;

            map[unit.row][unit.col] = unit.type;
        }

        private Day15Unit getWeakestAdjacentFoe(Day15Unit unit) {

            // Puzzle input provides a border on all sides, no need to check for out-of-bounds exceptions

            List<Day15Unit> foes = new ArrayList<>();

            Day15Unit unitAbove = getUnitAt(unit.row - 1, unit.col);
            if (unitAbove != null && unitAbove.isFoeOf(unit)) foes.add(unitAbove);

            Day15Unit unitLeft = getUnitAt(unit.row, unit.col - 1);
            if (unitLeft != null && unitLeft.isFoeOf(unit)) foes.add(unitLeft);

            Day15Unit unitRight = getUnitAt(unit.row, unit.col + 1);
            if (unitRight != null && unitRight.isFoeOf(unit)) foes.add(unitRight);

            Day15Unit unitBelow = getUnitAt(unit.row + 1, unit.col);
            if (unitBelow != null && unitBelow.isFoeOf(unit)) foes.add(unitBelow);

            return weakestFoe(foes);
        }

        private Day15Unit weakestFoe(List<Day15Unit> foes) {
            Day15Unit weakestFoe = null;
            for (Day15Unit foe :
                    foes) {
                if (weakestFoe == null) {
                    weakestFoe = foe;
                } else if (foe.hp < weakestFoe.hp) {
                    weakestFoe = foe;
                }
            }

            return weakestFoe;
        }

        private Day15Unit getUnitAt(int row, int col) {
            for (Day15Unit unit :
                    units) {
                if (unit.isLocation(row, col)) return unit;
            }

            return null;
        }

        private void attackOn(Day15Unit unit) {
            unit.takeDamage();
            if (unit.hp <= 0) {
                kill(unit);
            }
        }

        private void kill(Day15Unit unit) {
            if (unit.isGoblin()) {
                goblinCount--;
            } else {
                elfCount--;
            }

            map[unit.row][unit.col] = '.';

            units.remove(unit);
        }

        private boolean keepFighting() {
            return (elfCount > 0) && (goblinCount > 0);
        }

        public Point calculateMove(Day15Unit unit, char[][] map) {
            Point moveTo = null;

            if (unit.type == 'E') {


                moveTo = new Point(unit.row, unit.col + 1);

                // check up

                // check right

                // check left

                // check down



            } else if (unit.type == 'G') {
                moveTo = new Point(unit.row, unit.col - 1);
            }


            return moveTo;
        }

        public int result() {
            int hpSum = 0;
            for (Day15Unit unit :
                    units) {
                hpSum += unit.hp;
            }
            return rounds * hpSum;
        }

    }


    public class Day15Unit {
        public char type;
        public int row;
        public int col;
        public int hp;
        public int turnCompleted;

        public Day15Unit(int row, int col, char type) {
            this.type = type;
            this.row = row;
            this.col = col;
            this.hp = 200;
            this.turnCompleted = 0;
        }

        public void takeDamage() {
            hp -= 3;
        }

        public boolean isElf() {
            return type == 'E';
        }

        public boolean isGoblin() {
            return type == 'G';
        }

        public boolean isLocation(int row, int col) {
            return this.row == row && this.col == col;
        }

        public boolean isFoeOf(Day15Unit unit) {
            if (this.isElf()) return unit.isGoblin();

            if (this.isGoblin()) return unit.isElf();

            return false;
        }
    }
}
