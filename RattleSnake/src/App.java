import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int screenWidth = 600;
        int screenHeight = screenWidth;

        JFrame frame = new JFrame("RattleSnake");
        frame.setVisible(true);
        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
