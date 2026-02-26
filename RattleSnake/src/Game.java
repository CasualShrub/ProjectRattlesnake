import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel{

    private class Tile {
        int x;
        int y;

        Tile (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int screenWidth;
    int screenHeight;
    int tileSize = 20;

    Tile snakeHead = new Tile(7, 7);
    Tile pellet = new Tile(10, 10);

    Game(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
        setBackground(Color.black);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < screenWidth/tileSize; i++){
            g.drawLine(i*tileSize, 0, i*tileSize, screenHeight); //vertical line
            g.drawLine(0, i*tileSize, screenWidth, i*tileSize);
        }

        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        g.setColor(Color.red);
        g.fillRect(pellet.x * tileSize, pellet.y * tileSize, tileSize, tileSize);
    }
}