import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {

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
    ArrayList<Tile> snakeBody;
    Tile pellet;

    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver;

    Game(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(0,0);
        snakeBody = new ArrayList<Tile>();
        pellet = new Tile(0,0);
        random = new Random();
        
        spawnTileRandom(snakeHead);
        spawnTileRandom(pellet);

        velocityX = 0;
        velocityY = 1;

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

        for (int i = 0; i < snakeBody.size(); i++) {
            Tile section = snakeBody.get(i);
            g.fillRect(section.x * tileSize, section.y * tileSize, tileSize, tileSize);
        }

        g.setColor(Color.red);
        g.fillRect(pellet.x * tileSize, pellet.y * tileSize, tileSize, tileSize);

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else {
            g.setColor(Color.green);
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void slither() {
        if (collision(snakeHead, pellet)) {
            snakeBody.add(new Tile(pellet.x, pellet.y));
            spawnTileRandom(pellet);
        }

        for (int i = snakeBody.size()- 1; i >= 0; i--){
            Tile section = snakeBody.get(i);
            if (i == 0) {
                section.x = snakeHead.x;
                section.y = snakeHead.y;
            }
            else {
                Tile prevSection = snakeBody.get(i - 1);
                section.x = prevSection.x;
                section.y = prevSection.y;
            }
        }
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Game over
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile section = snakeBody.get(i);

            if (collision(snakeHead, section)) {
                gameOver = true;
            }
        }

        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > screenWidth ||
            snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > screenHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        slither();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (velocityX == 0) {
                    break;
                }
                velocityX = 0;
                velocityY = -1;
                break;
            case KeyEvent.VK_DOWN:
                if (velocityX == 0) {
                    break;
                }
                velocityX = 0;
                velocityY = 1;
                break;
            case KeyEvent.VK_LEFT:
                if (velocityY == 0) {
                    break;
                }
                velocityX = -1;
                velocityY = 0;
                break;
            case KeyEvent.VK_RIGHT:
                if (velocityY == 0) {
                    break;
                }
                velocityX = 1;
                velocityY = 0;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}