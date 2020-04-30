/**
 * Elijah Helmandollar
 * Lab #8
 * Description: A class that contains methods for commonly used formulas within the application.
 */
public class MathUtil {

    // Compute a length 1 vector pointing in the direction given by theta
    public static Vector2D unitVectorAtDegrees(double degrees) {

        double radians = degreesToRadians(degrees);

        return new Vector2D(Math.cos(radians), Math.sin(radians));

    }

    // Converts degrees to radians
    public static double degreesToRadians(double degrees) {

        return degrees * (Math.PI / 180);

    }

    // Returns true if the passed in number's signs are opposite
    public static boolean oppositeSigns (double point1, double point2) {

        return (point1 * point2) < 0;

    }

}
