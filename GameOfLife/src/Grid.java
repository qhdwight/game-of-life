import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {

    public static final Color GRID_COLOR = Color.DARK_GRAY, BG_COLOR = Color.LIGHT_GRAY;
    public static final int
            GRID_CELL_AMOUNT_X = 200, GRID_CELL_AMOUNT_Y = 200, GRID_PADDING = 1,
            TOTAL_CELL_SIZE = Cell.CELL_SIZE + GRID_PADDING,
            GRID_SIZE_X = GRID_CELL_AMOUNT_X * TOTAL_CELL_SIZE,
            GRID_SIZE_Y = GRID_CELL_AMOUNT_Y * TOTAL_CELL_SIZE;

    private Cell[][] cells;

    public Cell getCell(int x, int y) {
        // First, check if indices are not out of range, then return the value or null
        return x >= 0 && y >= 0 && x < GRID_CELL_AMOUNT_X && y < GRID_CELL_AMOUNT_Y ? cells[x][y] : null;
    }

    public Grid()
    {
        // Create new 2D array
        cells = new Cell[GRID_CELL_AMOUNT_X][GRID_CELL_AMOUNT_Y];

        // Initialize 2D array with cells
        for (int x = 0; x < GRID_CELL_AMOUNT_X; x++) {
            for (int y = 0; y < GRID_CELL_AMOUNT_Y; y++) {
                cells[x][y] = new Cell(this, false, x, y);
            }
        }

        // Init cells
        for (int x = 0; x < GRID_CELL_AMOUNT_X; x++) {
            for (int y = 0; y < GRID_CELL_AMOUNT_Y; y++) {
                cells[x][y].init();
            }
        }

        cells[100][100].setAlive(true);
        cells[101][100].setAlive(true);
        cells[101][102].setAlive(true);
        cells[103][101].setAlive(true);
        cells[104][100].setAlive(true);
        cells[105][100].setAlive(true);
        cells[106][100].setAlive(true);
    }

    public void update()
    {
        // Update all of the cells
        for (int x = 0; x < GRID_CELL_AMOUNT_X; x++) {
            for (int y = 0; y < GRID_CELL_AMOUNT_Y; y++) {
                cells[x][y].update();
            }
        }

        // Late update all of the cells
        for (int x = 0; x < GRID_CELL_AMOUNT_X; x++) {
            for (int y = 0; y < GRID_CELL_AMOUNT_Y; y++) {
                cells[x][y].lateUpdate();
            }
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(GRID_SIZE_X, GRID_SIZE_Y);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        // Get 2D graphics
        Graphics2D g2 = (Graphics2D)g;

        // Draw background
        g2.setColor(BG_COLOR);
        g2.fillRect(0, 0, GRID_SIZE_X, GRID_SIZE_Y);

        /* Draw grid and cells */
        for (int x = 0; x < GRID_CELL_AMOUNT_X; x++) {
            // Draw vertical grid lines
            g2.setColor(GRID_COLOR);
            g2.drawLine(x * TOTAL_CELL_SIZE, 0, x * TOTAL_CELL_SIZE, GRID_CELL_AMOUNT_Y * TOTAL_CELL_SIZE);
            for (int y = 0; y < GRID_CELL_AMOUNT_Y; y++) {
                // Draw horizontal grid lines
                g2.setColor(GRID_COLOR);
                g2.drawLine(0, y * TOTAL_CELL_SIZE, GRID_CELL_AMOUNT_X * TOTAL_CELL_SIZE, y * TOTAL_CELL_SIZE);

                // Draw individual cells
                cells[x][y].draw(g);
            }
        }
    }
}
