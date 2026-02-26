import java.awt.Color;
import java.util.Random;

public class ColorHelper {

    public static Color SnakeColor = Color.green;
    private static Color pelletColors[] = {Color.red, Color.blue, Color.yellow};
    public static Color GameOverTextColor = Color.red;
    public static Color ScoreTextColor = Color.green;

    // Never need to instantiate this. I intend for this to be the equivalent of public static class in C#
    private ColorHelper() {}

    public static Color GetRandomPelletColor() {
        Random random = new Random();
        int i = random.nextInt(pelletColors.length);
        return pelletColors[i];
    }
}