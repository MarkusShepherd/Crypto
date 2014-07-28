package info.riemannhypothesis.gol;

import java.util.List;

public class State {

    public final int width, height;
    public final boolean[][] cells;
    public final boolean torus;

    public State(int width, int height, boolean torus) {
        this(width, height, torus, null);
    }

    public State(int width, int height, boolean torus, List<Point> points) {
        this.width = width;
        this.height = height;
        this.torus = torus;
        this.cells = new boolean[this.width][this.height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[x][y] = false;
            }
        }

        if (points != null && points.size() > 0) {
            for (Point point : points) {
                if (!point.correct(width, height)) {
                    if (torus) {
                        point.torus(width, height);
                    } else {
                        continue;
                    }
                }
                cells[point.x][point.y] = true;
            }
        }
    }

    public State nextGeneration() {
        State nextGeneration = new State(width, height, torus);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int livingNeighbours = livingNeighbours(x, y);
                if ((cells[x][y] && (livingNeighbours == 2 || livingNeighbours == 3))
                        || (!cells[x][y] && livingNeighbours == 2)) {
                    nextGeneration.cells[x][y] = true;
                }
            }
        }
        return nextGeneration;
    }

    public int livingNeighbours(int x, int y) {
        int livingNeighbours = 0;
        final Point[] offsets = new Point[] { new Point(1, 0), new Point(1, 1),
                new Point(0, 1), new Point(-1, 1), new Point(-1, 0),
                new Point(-1, -1), new Point(0, -1), new Point(1, -1) };
        for (Point offset : offsets) {
            Point nb = new Point(x + offset.x, y + offset.y);
            if (!nb.correct(width, height)) {
                if (torus) {
                    nb.torus(width, height);
                } else {
                    continue;
                }
            }
            if (cells[nb.x][nb.y]) {
                livingNeighbours++;
            }
        }
        return livingNeighbours;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder((int) (height * width * 1.2));
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                builder.append(cells[x][y] ? '*' : ' ');
            }
            builder.append('\n');
        }
        return builder.toString();
    }

}
