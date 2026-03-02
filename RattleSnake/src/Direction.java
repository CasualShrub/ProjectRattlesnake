import java.security.InvalidParameterException;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction getDirection(int velocityX, int velocityY) throws InvalidParameterException {

        if (velocityX > 0 && velocityY == 0) {
            return RIGHT;
        }
        else if (velocityX < 0 && velocityY == 0) {
            return LEFT;
        }
        else if (velocityX == 0 && velocityY > 0) {
            return DOWN;
        }
        else if (velocityX == 0 && velocityY < 0) {
            return UP;
        }
        else {
            throw new InvalidParameterException("Invalid direction velocities given.");
        }
    }

}
