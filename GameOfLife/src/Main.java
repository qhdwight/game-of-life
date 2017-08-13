import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game of Life");

        GamePanel gamePanel = new GamePanel();

        frame.add(gamePanel);

        frame.pack();

        frame.setVisible(true);
    }
}
