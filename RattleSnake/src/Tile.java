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

    public void SetColor(Color color) {
        this.color = color;
    }

    public Color GetColor() {
        return this.color;
    }
} 