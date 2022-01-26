import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
/**
 * Elijah Helmandollar
 * Description: A class containing all of the data and methods necessary for rendering and updating the enemy sprite(s).
 */
public class EnemySprite extends Sprite {

    // The current rotation of the sprite
    public double rotation = 0;

    // A 'Vector2D' that points in the ship's direction
    public Vector2D forward = new Vector2D(rotation, -1);

    // The amount to change the velocity's magnitude
    public final double accelerationAmount = 0.5;

    // For tracking the sprites health
    public double health;

    // Constructor
    public EnemySprite (double positionX, double positionY, double health) {

        super(positionX, positionY);

        this.health = health;

        setTranslateX(positionX);
        setTranslateY(positionY);


    }

    // Renders the player's image and sets width and height
    public ImageView renderEnemy () {

        try {

            Image playerImage = new Image(new FileInputStream("assets/sprites/enemy.png"));

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
        rotation -= 45;
        setRotate(rotation);
        forward = MathUtil.unitVectorAtDegrees(rotation - 90);

    }

    // Rotates the sprite clockwise
    public void rotateRight () {

        rotation = this.getRotate();
        rotation += 45;
        setRotate(rotation);
        forward = MathUtil.unitVectorAtDegrees(rotation - 90);

    }

    // Bounces the enemy sprite off of the player sprite
    public void handleCollision () {

        velocity.x *= -1.0;
        velocity.y *= -1.0;

    }

    // Updates the sprite's location
    public void update () {

        setTranslateX(getTranslateX() + velocity.x);
        setTranslateY(getTranslateY() + velocity.y);

    }

    // Generates a random move for an enemy sprite to be executed at a set time interval during the game
    public void moveEnemy () {

        int min = 1;
        int max = 4;
        int range = max - min + 1;

        for (int i = 1; i < 10; i++) {

            int rInt = (int) (Math.random() * range) + min;

            if (rInt == 1) {

                this.moveForward();

            } else if (rInt == 2) {

                this.moveBackward();

            } else if (rInt == 3) {

                this.rotateLeft();

            } else if (rInt == 4) {

                this.rotateRight();

            }

        }

    }

}
