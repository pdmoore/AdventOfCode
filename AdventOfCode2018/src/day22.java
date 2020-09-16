import java.awt.*;
import java.math.BigInteger;

public class day22 {

    public static final char ROCKY = '.';
    public static final char WET = '=';
    public static final char NARROW = '|';

    public static final BigInteger bi3 = new BigInteger(String.valueOf(3));
    public static final BigInteger biMultY = new BigInteger(String.valueOf(16807));
    public static final BigInteger biMultX = new BigInteger(String.valueOf(48271));

    public static final BigInteger elMod = new BigInteger(String.valueOf(20183));


    public static int calculateRiskLevel(char[][] caveSystem, Point target) {
        int riskLevel = 0;
        for (int y = 0; y <= target.y; y++) {
            for (int x = 0; x <= target.x; x++) {
                switch (caveSystem[y][x]) {
                    case WET:
                        riskLevel += 1;
                        break;
                    case NARROW:
                        riskLevel += 2;
                        break;
                }
            }
        }

        return riskLevel;
    }

    public static char[][] solution1_caveSystem(Region[][] regions, int depth, Point target) {
        BigInteger biDepth = new BigInteger(String.valueOf(depth));

        for (int x = 0; x <= target.x; x++) {
            for (int y = 0; y <= target.y; y++) {

                BigInteger geologicIndex = null;
                if ((x == 0 && y == 0) ||
                        (x == target.x && y == target.y)) {
                    geologicIndex = new BigInteger(String.valueOf(0));
                } else if (y == 0) {
                    BigInteger biX = new BigInteger(String.valueOf(x));
                    geologicIndex = biMultY.multiply(biX);
                } else if (x == 0) {
                    BigInteger biX = new BigInteger(String.valueOf(y));
                    geologicIndex = biMultX.multiply(biX);
                } else {
                    geologicIndex = regions[x - 1][y].erosionLevel.multiply(regions[x][y - 1].erosionLevel);
                }

                Region region = new Region(geologicIndex, biDepth);

                regions[x][y] = region;
            }
        }

        return caveSystemOf(regions);
    }

    private static char[][] caveSystemOf(Region[][] regions) {
        char[][] caveSystem = new char[regions[0].length][regions.length];

        for (int x = 0; x < regions.length; x++) {
            for (int y = 0; y < regions[0].length; y++) {
                caveSystem[y][x] = regions[x][y].type;
            }
        }

        return caveSystem;
    }

    public static int solution1(Region[][] regions, int depth, Point target) {
        char[][] caveSystem = solution1_caveSystem(regions, depth, target);
        return calculateRiskLevel(caveSystem, target);
    }

    public static class Region {
        BigInteger erosionLevel;
        BigInteger geologicIndex;
        char type;

        public Region(BigInteger geologicIndex, BigInteger biDepth) {
            this.geologicIndex = geologicIndex;
            this.erosionLevel = geologicIndex.add(biDepth).mod(elMod);
            assignType();
        }

        public void assignType() {
            int mod = erosionLevel.mod(bi3).intValue();
            switch (mod) {
                case 0:
                    type = ROCKY;
                    break;
                case 1:
                    type = WET;
                    break;
                case 2:
                    type = NARROW;
                    break;
            }
        }
    }
}
