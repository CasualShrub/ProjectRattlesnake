import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener{

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
    Random random;
    Tile snakeHead;
    Tile pellet;

    Timer gameLoop;

    Game(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
        setBackground(Color.black);

        snakeHead = new Tile(0,0);
        pellet = new Tile(0,0);
        random = new Random();
        
        spawnTileRandom(snakeHead);
        spawnTileRandom(pellet);

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void spawnTileRandom(Tile tile) {
        tile.x = random.nextInt(screenWidth/tileSize);
        tile.y = random.nextInt(screenHeight/tileSize);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}