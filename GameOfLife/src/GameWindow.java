import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public final long UPDATE_TIME = 100L;

    private Grid grid;

    private Thread updateThread;

    public GameWindow()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Game of Life");
        setLayout(new FlowLayout());

        // Create and add grid to frame
        grid = new Grid();
        add(grid);

        // Pack components
        pack();

        // Make sure frame is visible
        setVisible(true);

        // Create a recursive thread which updates
        // according to the constant UPDATE_TIME
        updateThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(UPDATE_TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                update();
                run();
            }
        };
        // Start the thread
        updateThread.run();
    }

    // Called by update thread, handles
    // updating the cells states
    private void update()
    {
        // Update the grid which has the cells
        grid.update();

        // Repaint frame and grid
        repaint();
    }

    @Override
    public void paint(Graphics g)
    {
        // Get the 2D graphics
        Graphics2D g2 = (Graphics2D)g;
        // Paint the grid
        grid.repaint();
    }
}
