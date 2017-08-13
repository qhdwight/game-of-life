import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Grid extends JComponent implements MouseListener {

    public static final Color GRID_COLOR = Color.DARK_GRAY, BG_COLOR = Color.LIGHT_GRAY;
    public static final int
            GRID_CELL_AMOUNT_X = 100, GRID_CELL_AMOUNT_Y = 100, GRID_PADDING = 1,
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

        addMouseListener(this);

        cells[50][50].setAlive(true);
        cells[51][50].setAlive(true);
        cells[51][52].setAlive(true);
        cells[53][51].setAlive(true);
        cells[54][50].setAlive(true);
        cells[55][50].setAlive(true);
        cells[56][50].setAlive(true);
    }

    public void clear() {

        for (int x = 0; x < GRID_CELL_AMOUNT_X; x++) {
            for (int y = 0; y < GRID_CELL_AMOUNT_Y; y++) {
                cells[x][y].setAlive(false);
            }
        }
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
        super.paintComponent(g);

        // Get 2D graphics
        Graphics2D g2 = (Graphics2D)g;

        // Draw background
        g2.setColor(BG_COLOR);
        g2.fillRect(0, 0, GRID_SIZE_X, GRID_SIZE_Y);

        /* Draw grid and cells */
        for (int x = 0; x < GRID_CELL_AMOUNT_X; x++) {
            // Draw vertical grid lines
            if (x != 0) {
                g2.setColor(GRID_COLOR);
                g2.drawLine(x * TOTAL_CELL_SIZE, 0, x * TOTAL_CELL_SIZE, GRID_CELL_AMOUNT_Y * TOTAL_CELL_SIZE);
            }
            for (int y = 0; y < GRID_CELL_AMOUNT_Y; y++) {
                // Draw horizontal grid lines
                if (y != 0) {
                    g2.setColor(GRID_COLOR);
                    g2.drawLine(0, y * TOTAL_CELL_SIZE, GRID_CELL_AMOUNT_X * TOTAL_CELL_SIZE, y * TOTAL_CELL_SIZE);
                }

                // Draw individual cells
                cells[x][y].draw(g);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        // Get raw x and y of mouse
        float x = e.getX(), y = e.getY();

        // Translate x and y to find cell in array
        int cellX = (int)(x / TOTAL_CELL_SIZE);
        int cellY = (int)(y / TOTAL_CELL_SIZE);

        // Get cell from grid
        Cell cell = cells[cellX][cellY];
        // Toggle alive state
        cell.setAlive(!cell.isAlive());
    }

    @Override public void mouseClicked(MouseEvent e) { }

    @Override public void mouseReleased(MouseEvent e) { }

    @Override public void mouseEntered(MouseEvent e) { }

    @Override public void mouseExited(MouseEvent e) { }
}
