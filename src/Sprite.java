import javafx.scene.image.ImageView;
/**
 * Elijah Helmandollar
 * Lab #8
 * Description: Abstract class for all of the sprite classes.
 */
public abstract class Sprite extends ImageView {

    // Attributes
    double positionX, positionY;

    // A 'Vector2D' representing the sprite's current speed on each axis
    public final Vector2D velocity = new Vector2D(0,0);

    // Constructor
    Sprite (double positionX, double positionY) {

        this.positionX = positionX;
        this.positionY = positionY;

    }

}
