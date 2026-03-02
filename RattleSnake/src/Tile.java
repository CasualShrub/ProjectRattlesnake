import java.awt.Color;
public class Tile {
    int x;
    int y;
    private Color color;

    Tile (int x, int y) {
        this.x = x;
        this.y = y;
    }

    Tile (int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
} 