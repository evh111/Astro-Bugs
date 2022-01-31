import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
/**
 * Elijah Helmandollar
 * Description: A border to ensure that the player, enemies, and bullets cannot escape the playing field.
 */
public class Collider extends Line {

    // The direction that each collider represents
    public Vector2D pushDirection;

    // The border on the playing field
    public Rectangle colliderField;

    // Constructor
    public Collider (double x, double y, double width, double height, double vectorX, double vectorY) {

        this.colliderField = new Rectangle(x, y, width, height);
        this.pushDirection = new Vector2D(vectorX, vectorY);

    }

    // Halts the sprite at its current location
    public void halt (Sprite sprite) {

        if (MathUtil.oppositeSigns(sprite.velocity.x, this.pushDirection.x)) {

            sprite.velocity.x *= -0.5;

        }

        if (MathUtil.oppositeSigns(sprite.velocity.y, this.pushDirection.y)) {

            sprite.velocity.y *= -0.5;

        }
    }

}
