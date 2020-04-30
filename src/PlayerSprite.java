import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
/**
 * Elijah Helmandollar
 * Lab #8
 * Description: A class containing all of the data and methods necessary for rendering and updating the player sprite.
 */
public class PlayerSprite extends Sprite {

    // Theta
    public double rotation = 0;

    // A 'Vector2D' that points in the ship's direction
    public Vector2D forward = new Vector2D(rotation, -1);

    // The amount to change the velocity's magnitude
    public final double accelerationAmount = 0.1;

    // For tracking the sprites health
    public double health;

    // Constructor
    public PlayerSprite (double positionX, double positionY, double health) {

        super(positionX, positionY);

        this.health = health;

        setTranslateX(positionX);
        setTranslateY(positionY);

    }

    // Renders the player's image and sets width and height
    public ImageView renderPlayer () {

        try {

            Image playerImage = new Image(new FileInputStream("assets/sprites/ship.png"));

            this.setFitWidth(40);
            this.setFitHeight(42);

            this.setImage(playerImage);

        } catch (FileNotFoundException ex) {

            System.out.println("IO Errors.");

        }

        return this;

    }

    // Accelerates the sprite forward
    public void moveForward () {

        velocity.x += forward.x * accelerationAmount;
        velocity.y += forward.y * accelerationAmount;

    }

    // Accelerates the sprite backward
    public void moveBackward () {

        velocity.x += -1 * forward.x * accelerationAmount;
        velocity.y += -1 * forward.y * accelerationAmount;

    }

    // Rotates the sprite counter-clockwise
    public void rotateLeft () {

        rotation = this.getRotate();
        rotation -= 10;
        setRotate(rotation);
        forward = MathUtil.unitVectorAtDegrees(rotation - 90);

    }

    // Rotates the sprite clockwise
    public void rotateRight () {

        rotation = this.getRotate();
        rotation += 10;
        setRotate(rotation);
        forward = MathUtil.unitVectorAtDegrees(rotation - 90);

    }

    // Bounces the player off of enemy sprites
    public void handleCollision () {

        velocity.x *= -1.0;
        velocity.y *= -1.0;

    }

    // Updates the sprites location
    public void update () {

        setTranslateX(getTranslateX() + velocity.x);
        setTranslateY(getTranslateY() + velocity.y);

    }

}
