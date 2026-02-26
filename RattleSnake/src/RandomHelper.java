import java.util.Random;

public class RandomHelper {

    static Random rand;

    private RandomHelper() {}

    public static void InitializeRandom(){
        rand = new Random();
    }

    public static Random GetRandom(){
        return rand;
    }
    
}
