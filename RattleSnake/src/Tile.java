import java.awt.Color;
public class Tile {
    int x;
    int y;
    Color color;

    Tile (int x, int y) {
        this.x = x;
        this.y = y;
    }

    Tile (int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
} 