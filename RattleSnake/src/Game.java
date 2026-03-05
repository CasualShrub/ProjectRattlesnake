/*
As of this version, the code is taken almost entirely from Kenny Yip Coding's online tutorial https://www.youtube.com/watch?v=Y62MJny9LHg.
I intend to use this code as a foundation for building my own augmented version of the classic snake game.

@author Alex Bernard Francia
@version 1.0
*/

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    int screenWidth;
    int screenHeight;
    final int tileSize = 20;

    int gameLoopDelay = 100;
    final int gameLoopSpeedIncrement = 2; //linear difficulty progression for now
    final int gameLoopMaxSpeed = 35;

    //spawning algorithm
    final int safeZoneDistanceFromHead = 3;
    final int safeZoneSize = 5;

    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    ArrayList<Tile> pelletArray;

    Timer gameLoop;

    // Game session data
    int velocityX;
    int velocityY;
    Direction currentDirection;
    int pelletSpawnMultiplier = 1;
    int[] pelletSpawnThresholds = {8, 20, 40};
    
    boolean gameOver;

    // Debug toggles
    // TODO: Debug Cheat Menu
    boolean canPhase = true;
    boolean isLoggingEnabled = true;

    Game(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        RandomHelper.InitializeRandom();

        snakeHead = new Tile(15,15, ColorHelper.DefaultSnakeColor);
        snakeBody = new ArrayList<Tile>();
        pelletArray = new ArrayList<Tile>();
        spawnNewPellet();

        velocityX = 0;
        velocityY = 1;
        currentDirection = Direction.getDirection(velocityX, velocityY);

        gameLoop = new Timer(gameLoopDelay, this);
        gameLoop.start();
    }

    public void spawnNewPellet(){
        Tile pellet = new Tile(0,0, ColorHelper.GetRandomPelletColor());
        repositionTileRandom(pellet);
        pelletArray.add(pellet);
    }

    public void repositionTileRandom(Tile tile) {
        tile.x = RandomHelper.GetRandom().nextInt(screenWidth/tileSize);
        tile.y = RandomHelper.GetRandom().nextInt(screenHeight/tileSize);
    }

    public void repositionAllPellets(){
        for(int i = 0; i < pelletArray.size(); i++) {
            int safeZoneOriginX = 0;
            int safeZoneOriginY = 0;
            Tile currentPellet = pelletArray.get(i);

            switch (currentDirection){
                case DOWN:
                    safeZoneOriginX = snakeHead.x;
                    safeZoneOriginY = snakeHead.y + safeZoneDistanceFromHead;
                    break;
                case LEFT:
                    safeZoneOriginX = snakeHead.x - safeZoneDistanceFromHead;
                    safeZoneOriginY = snakeHead.y;
                    break;
                case RIGHT:
                    safeZoneOriginX = snakeHead.x + safeZoneDistanceFromHead;
                    safeZoneOriginY = snakeHead.y;
                    break;
                case UP:
                    safeZoneOriginX = snakeHead.x;
                    safeZoneOriginY = snakeHead.y - safeZoneDistanceFromHead;
                    break;
            }

            int safeBoundMinX = safeZoneOriginX - safeZoneSize;
            int safeBoundMaxX = safeZoneOriginX + safeZoneSize;
            int safeBoundMinY = safeZoneOriginY - safeZoneSize;
            int safeBoundMaxY = safeZoneOriginY + safeZoneSize;

            int x;
            int y;
            
            while (true) {
                x = RandomHelper.GetRandom().nextInt(screenWidth/tileSize);
                y = RandomHelper.GetRandom().nextInt(screenHeight/tileSize);

                if ((x < safeBoundMinX || x >= safeBoundMaxX) || (y < safeBoundMinY || y >= safeBoundMaxY)) {
                    break;
                }

            }

            currentPellet.setPosition(x, y);
        }
    }

    private void incrementGameDifficulty(){
        incrementSnakeSpeed();
        increasePelletSpawnRate();
    }

    private void incrementSnakeSpeed(){
        if (gameLoopDelay <= gameLoopMaxSpeed) {
            return;
        }
        gameLoopDelay -= gameLoopSpeedIncrement;
        gameLoop.stop();
        gameLoop = new Timer(gameLoopDelay, this);
        gameLoop.start();
    }

    private void increasePelletSpawnRate(){
        for (int i = 0; i < pelletSpawnThresholds.length; i++) {
            if (snakeBody.size() >= pelletSpawnThresholds[i]){
                pelletSpawnMultiplier = (i+1) * 2;
                return;
            }
        }

        pelletSpawnMultiplier = 1;
    }

    // enforce dont spawn in front of snake?
    // in front of defined to be: 5x5 space with origin +3 from direction of snake

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Gridlines
        for (int i = 0; i < screenWidth/tileSize; i++){
            g.drawLine(i*tileSize, 0, i*tileSize, screenHeight);
            g.drawLine(0, i*tileSize, screenWidth, i*tileSize);
        }

        // Snake
        g.setColor(snakeHead.getColor());
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        for (int i = 0; i < snakeBody.size(); i++) {
            Tile section = snakeBody.get(i);
            g.setColor(section.getColor());
            g.fill3DRect(section.x * tileSize, section.y * tileSize, tileSize, tileSize, true);
        }

        // Pellets
        for (int i = 0 ; i < pelletArray.size(); i++) {
            Tile pellet = pelletArray.get(i);
            g.setColor(pellet.getColor());
            g.fillRect(pellet.x * tileSize, pellet.y * tileSize, tileSize, tileSize);
        }

        // Game Text
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(ColorHelper.GameOverTextColor);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else {
            g.setColor(ColorHelper.ScoreTextColor);
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void slither() {
        for (int i = 0; i < pelletArray.size(); i++){
            Tile pellet = pelletArray.get(i);
            if (collision(snakeHead, pellet)) {

                if (pellet.getColor() == snakeHead.getColor()){
                    gameOver = true;
                    return;
                }

                snakeBody.add(0, new Tile(pellet.x, pellet.y, snakeHead.getColor()));
                snakeHead.setColor(pellet.getColor());

                pellet.setColor(ColorHelper.GetRandomPelletColor());
                repositionTileRandom(pellet);

                for (int j = 0; j < pelletSpawnMultiplier; j++){
                    spawnNewPellet();
                }

                if (isLoggingEnabled) {
                    System.out.println("Added " + pelletSpawnMultiplier + " pellet to the pool.");
                    System.out.println("Total pellet pool is now size " + pelletArray.size());
                }

                repositionAllPellets();

                incrementGameDifficulty();
            }
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

        if (canPhase){
            // Reposition snake on opposite screen border
            if (snakeHead.x*tileSize < 0) {
                snakeHead.x = screenWidth/tileSize;
            }
            else if (snakeHead.x*tileSize >= screenWidth) {
                snakeHead.x = 0;
            }
            else if (snakeHead.y*tileSize < 0) {
                snakeHead.y = screenHeight/tileSize;
            }
            else if (snakeHead.y*tileSize >= screenHeight){
                snakeHead.y = 0;
            }
        }
        else {
            // Die upon hitting screen border
            if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > screenWidth ||
                snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > screenHeight) {
                gameOver = true;
            }
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
            case KeyEvent.VK_SPACE:
                if (gameLoop.isRunning()){
                    gameLoop.stop();
                }
                else {
                    gameLoop.start();
                }

        }

        currentDirection = Direction.getDirection(velocityX, velocityY);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}