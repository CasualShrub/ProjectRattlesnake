/*
As of this version, the code is taken almost entirely from Kenny Yip Coding's online tutorial https://www.youtube.com/watch?v=Y62MJny9LHg.
I intend to use this code as a foundation for building my own augmented version of the classic snake game.

@author Alex Bernard Francia
@version 1.0
*/

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int screenWidth = 600;
        int screenHeight = screenWidth;

        JFrame frame = new JFrame("RattleSnake");
        frame.setVisible(true);
        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Game game = new Game(screenWidth, screenHeight);
        frame.add(game);
        frame.pack();
        game.requestFocusInWindow();
    }
}
