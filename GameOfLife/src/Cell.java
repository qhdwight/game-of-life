import java.awt.*;

public class Cell {

    public static final int CELL_SIZE = 8;

    private static final Color aliveColor = Color.YELLOW, deadColor = Color.LIGHT_GRAY;

    private final int X, Y;

    private Grid grid;
    private Cell[][] neighbors;

    private boolean alive, aliveNext;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Cell(Grid grid, boolean alive, int X, int Y) {
        this.alive = alive;
        this.grid = grid;
        this.X = X;
        this.Y = Y;

        neighbors = new Cell[3][3];
    }

    public void init()
    {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                // Make sure we don't add ourselves
                if (x != 1 || y != 1) {
                    // Get the cell from the grid
                    Cell neighbor = grid.getCell(X + x - 1, Y + y - 1);
                    // Add neighbor to our array
                    neighbors[x][y] = neighbor;
                }
            }
        }
    }

    public void update()
    {
        // Get amount of cells alive that are neighbors
        int aliveCount = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {

                Cell neighbor = neighbors[x][y];

                // Make sure neighbor isn't null
                if (neighbor != null) {
                    if (neighbor.isAlive())
                        aliveCount++;
                }
            }
        }
        // Calculate whether or not we are alive
        if (alive) {
            aliveNext = aliveCount > 1 && aliveCount < 4;
        } else {
            aliveNext = aliveCount == 3;
        }
    }

    public void lateUpdate()
    {
        alive = aliveNext;
    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D)g;
        // Determine what color the cell should be
        g2.setColor(alive ? aliveColor : deadColor);
        // Draw cell in grid
        g2.fillRect(X * Grid.TOTAL_CELL_SIZE + Grid.GRID_PADDING, Y * Grid.TOTAL_CELL_SIZE + Grid.GRID_PADDING, CELL_SIZE, CELL_SIZE);
    }
}
