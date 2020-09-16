import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class day10 {

    public static PointOfLight parseLine(String input) {
        int positionStart = input.indexOf("<");
        int positionEnd = input.indexOf(">");

        int velocityStart = input.indexOf("<", positionEnd);
        int velocityEnd = input.indexOf(">", velocityStart);

        String position = input.substring(positionStart + 1, positionEnd);
        String velocity = input.substring(velocityStart + 1, velocityEnd);

        String positionTokens[] = position.split(",");
        String velocityTokens[] = velocity.split(",");

        PointOfLight pointOfLight = new PointOfLight();

        pointOfLight.position = new Point(Integer.parseInt(positionTokens[0].trim()), Integer.parseInt(positionTokens[1].trim()));
        pointOfLight.velocity = new Point(Integer.parseInt(velocityTokens[0].trim()), Integer.parseInt(velocityTokens[1].trim()));

        return pointOfLight;
    }

    public static Board createBoard(String filename) {
        List<String> fileContentsAsStrings = utilities.getFileContentsAsStrings(filename);

        List<PointOfLight> pointsOfLight = createPoints(fileContentsAsStrings);

        Board board = new Board(pointsOfLight);
        return board;
    }

    private static BoardSize determineBoardSize(List<PointOfLight> pointsOfLight) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (PointOfLight pointOfLight :
                pointsOfLight) {

            int thisX = pointOfLight.position.x;
            int thisY = pointOfLight.position.y;

            if (thisX < minX) minX = thisX;
            if (thisX > maxX) maxX = thisX;

            if (thisY < minY) minY = thisY;
            if (thisY > maxY) maxY = thisY;
        }

        BoardSize boardSize = new BoardSize(minX, maxX, minY, maxY);

        return boardSize;
    }

    private static List<PointOfLight> createPoints(List<String> fileContentsAsStrings) {
        List<PointOfLight> points = new ArrayList<>();
        for (String inputLine :
                fileContentsAsStrings) {
            PointOfLight pointOfLight = parseLine(inputLine);
            points.add(pointOfLight);
        }
        return points;
    }

    public static class PointOfLight {
        public Point position;
        public Point velocity;

        public void tick() {
            position.x += velocity.x;
            position.y += velocity.y;
        }
    }

    public static class Board {

        public static final int CHARACTER_HEIGHT = 9;
        private final List<PointOfLight> pointsOfLight;
        public int ticks;

        //TODO only needed to print out the final message
        public char[][] grid;

        public Board(List<PointOfLight> pointsOfLight) {
            this.pointsOfLight = pointsOfLight;
        }

        private void populateGrid(List<PointOfLight> pointsOfLight, BoardSize boardSize) {

            for (PointOfLight pointOfLight :
                    pointsOfLight) {
                int adjustedCol = pointOfLight.position.x - boardSize.minX;
                int adjustedRow = pointOfLight.position.y - boardSize.minY;

                grid[adjustedRow][adjustedCol] = '#';
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    if (grid[row][col] == 0) {
                        sb.append(' ');
                    } else {
                        sb.append(grid[row][col]);
                    }
                }
                sb.append(System.lineSeparator());
            }
            sb.append("seconds: ");
            sb.append(ticks);
            return sb.toString();
        }

        public void tick() {

            for (PointOfLight pointOfLight :
                    pointsOfLight) {
                pointOfLight.tick();
            }

            checkRowsHaveConverged();
        }

        private void checkRowsHaveConverged() {
            int minRow = Integer.MAX_VALUE;
            int maxRow = Integer.MIN_VALUE;

            for (PointOfLight pointOfLight :
                    pointsOfLight) {
                if (pointOfLight.position.y < minRow) minRow = pointOfLight.position.y;
                if (pointOfLight.position.y > maxRow) maxRow = pointOfLight.position.y;

                if (Math.abs(maxRow - minRow) > CHARACTER_HEIGHT) return;
            }


            BoardSize finalBoardSize = determineBoardSize(pointsOfLight);

            int maxColumn = finalBoardSize.maxX - finalBoardSize.minX + 1;
            maxRow = finalBoardSize.maxY - finalBoardSize.minY + 1;

            grid = new char[maxRow][maxColumn];

            populateGrid(pointsOfLight, finalBoardSize);

            String message = this.toString();

            throw new RuntimeException(message);
        }

        public void animate() {
            ticks = 1;
            while (ticks < 50000) {
                tick();
                ticks++;
            }
        }
    }

    public static class BoardSize {

        private final int minX;
        private final int maxX;
        private final int minY;
        private final int maxY;

        public BoardSize(int minX, int maxX, int minY, int maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }
    }
}
