import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener, Runnable {

    public final long UPDATE_TIME = 100L;

    private Grid grid;
    private Thread updateThread;
    private boolean isPaused;

    public GamePanel()
    {
        isPaused = true;

        FlowLayout layout = new FlowLayout();
        setLayout(layout);

        grid = new Grid();
        add(grid);

        // Add buttons
        createButton("Start");
        createButton("Pause");
        createButton("Step" );
        createButton("Clear");
        createButton("Exit" );

        // Create update thread
        updateThread = new Thread(this);
        updateThread.start();
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

    private JButton createButton(String name) {

        // Create button
        JButton button = new JButton(name);
        // Style button
        button.setForeground(Color.BLACK);
        button.setBackground(Color.LIGHT_GRAY);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        // Set action command
        button.setActionCommand(name);
        // Link button and this panel
        button.addActionListener(this);
        // Add button to this panel
        add(button);
        return button;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        // Get the 2D graphics
        Graphics2D g2 = (Graphics2D)g;
        // Paint the grid
        grid.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Start": {
                setPaused(false);
                break;
            }
            case "Pause": {
                setPaused(true);
                break;
            }
            case "Step": {
                update();
                break;
            }
            case "Clear": {
                grid.clear();
                break;
            }
            case "Exit": {
                System.exit(0);
                break;
            }
        }
    }

    private void setPaused(boolean isPaused) {

        this.isPaused = isPaused;

        if (!isPaused && !updateThread.isAlive()) {
            updateThread = new Thread(this);
            updateThread.start();
        }
    }

    @Override
    public void run() {
        if (!isPaused) {
            try {
                Thread.sleep(UPDATE_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            update();
            run();
        }
    }
}
