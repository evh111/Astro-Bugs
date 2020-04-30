import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
/**
 * Elijah Helmandollar
 * Lab #8
 * Description: A class containing all of the data and methods necessary for rendering and updating the bullet sprite.
 */
public class BulletSprite extends Sprite {

    // Constructor
    public BulletSprite (double positionX, double positionY, double rotation) {

        super(positionX, positionY);

        setRotate(rotation);

        setTranslateX(positionX);
        setTranslateY(positionY);

    }

    // Renders the bullet's image and sets width and height
    public ImageView renderBullet () {

        try {

            Image playerImage = new Image(new FileInputStream("assets/sprites/bullet.png"));

            this.setFitWidth(4);
            this.setFitHeight(11);

            this.setImage(playerImage);

        } catch (FileNotFoundException ex) {

            System.out.println("IO Errors.");

        }

        return this;

    }

    // Accelerates the sprite forward
    public void moveForward (Vector2D spriteForward) {

        velocity.x += spriteForward.x * 10.0;
        velocity.y += spriteForward.y * 10.0;

    }

    // Updates the sprite's location
    public void update () {

        setTranslateX(getTranslateX() + velocity.x);
        setTranslateY(getTranslateY() + velocity.y);

    }

}
