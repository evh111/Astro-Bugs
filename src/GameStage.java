import java.io.File;
import java.util.Random;
import java.util.HashMap;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.ArrayList;
import javafx.util.Duration;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
import javafx.scene.media.MediaPlayer;
import javafx.animation.AnimationTimer;
/**
 * Elijah Helmandollar
 * Description: A class that constructs the game world and updates the current game state.
 */
public class GameStage {

    // 'Pane' as the parent container
    private static final Pane pane = new Pane();

    // 'HashMap' for keys
    private static final HashMap <KeyCode, Boolean> keys = new HashMap<>();

    // 'ArrayList' for managing enemies
    private static final ArrayList<EnemySprite> enemies = new ArrayList<>();

    // 'ArrayLists' for managing bullets
    private static final ArrayList<BulletSprite> playerBullets = new ArrayList<>();
    private static final ArrayList<BulletSprite> enemyBullets = new ArrayList<>();

    // 'ArrayLists' for removable sprites
    private static final ArrayList<EnemySprite> removableEnemies = new ArrayList<>();
    private static final ArrayList<BulletSprite> removableBullets = new ArrayList<>();

    // 'PlayerSprite' as the playable character
    private static final PlayerSprite playerSprite = new PlayerSprite(400 - 18, 400 - 16, 100.0);

    // 'Colliders' as the playing field borders
    private static final Collider topBorder = new Collider(0, 0, 800, 0, 0, 1);
    private static final Collider bottomBorder = new Collider(0, 800, 800, 0, 0, -1);
    private static final Collider leftBorder = new Collider(0, 0, 0, 800, 1, 0);
    private static final Collider rightBorder = new Collider(800, 0, 0, 800, -1, 0);

    // Used to enforce the player's rate of fire
    private static long timeSinceLastBullet = 0;

    // Indicates the player's current health
    private static final ImageView healthIndicator = new ImageView();

    // 'Labels' to indicate if the player has won or lost
    private static final Label winMessage = new Label("YOU WON!");
    private static final Label lossMessage = new Label("YOU LOSE!");

    // 'Button' for returning to the main menu
    private static final Button mainMenuButton = new Button("Menu");

    // Constructs the entire game scene and invokes the 'AnimationTimer'
    public static void constructGameStage (Stage stage) throws FileNotFoundException {

        // Create the 'Scene' and add the 'StackPane' to it
        Scene scene = new Scene(pane);

        // Get the background
        Image backgroundImage = new Image(new FileInputStream("assets/sprites/background.png"));
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Get the health indicator and set it's properties
        Image healthBarImage = new Image(new FileInputStream("assets/sprites/heart.png"));
        healthIndicator.setImage(healthBarImage);
        healthIndicator.setPreserveRatio(true);
        healthIndicator.setFitWidth(48);
        healthIndicator.setFitHeight(48);
        healthIndicator.layoutXProperty().bind(pane.widthProperty().subtract(healthIndicator.fitWidthProperty()).divide(2));
        healthIndicator.setY(30);

        // Place elements
        pane.getChildren().add(topBorder);
        pane.getChildren().add(bottomBorder);
        pane.getChildren().add(leftBorder);
        pane.getChildren().add(rightBorder);
        pane.getChildren().add(backgroundImageView);
        pane.getChildren().add(playerSprite.renderPlayer());
        spawnEnemies();
        pane.getChildren().add(healthIndicator);

        // Add win and loss messages and set their properties
        pane.getChildren().add(winMessage);
        winMessage.setVisible(false);
        winMessage.layoutXProperty().bind(pane.widthProperty().subtract(winMessage.widthProperty()).divide(2));
        winMessage.layoutYProperty().bind(pane.heightProperty().subtract(winMessage.heightProperty()).divide(2));

        pane.getChildren().add(lossMessage);
        lossMessage.setVisible(false);
        lossMessage.layoutXProperty().bind(pane.widthProperty().subtract(lossMessage.widthProperty()).divide(2));
        lossMessage.layoutYProperty().bind(pane.heightProperty().subtract(lossMessage.heightProperty()).divide(2));

        // Initialize key values
        keys.put(KeyCode.W, false);
        keys.put(KeyCode.S, false);
        keys.put(KeyCode.A, false);
        keys.put(KeyCode.D, false);
        keys.put(KeyCode.UP, false);
        keys.put(KeyCode.DOWN, false);
        keys.put(KeyCode.LEFT, false);
        keys.put(KeyCode.RIGHT, false);
        keys.put(KeyCode.SPACE, false);
        keys.put(KeyCode.ESCAPE, false);

        // Handle events
        scene.setOnKeyPressed(GameStage::setKeyDown);
        scene.setOnKeyReleased(GameStage::setKeyUp);

        // Move the enemies
        Timeline enemyMovementRate = new Timeline(new KeyFrame(Duration.millis(500), event -> setEnemyMove()));
        enemyMovementRate.setCycleCount(Timeline.INDEFINITE);
        enemyMovementRate.play();

        // Game Loop
        new AnimationTimer () {

            public void handle (long currentNanoTime) {

                // Update player's state
                playerSprite.update();
                controlPlayer();
                checkBoundaries(playerSprite);
                checkCollisions();

                // Update the enemies' state
                for (EnemySprite enemySprite : enemies) {

                    enemySprite.update();
                    checkBoundaries(enemySprite);

                }

                // Update the player's bullets state
                for (BulletSprite bulletSprite : playerBullets) {

                    bulletSprite.update();
                    checkBulletAgainstBoundaries(bulletSprite);

                }

                // Update the enemies' bullets state
                for (BulletSprite bulletSprite : enemyBullets) {

                    bulletSprite.update();
                    checkBulletAgainstBoundaries(bulletSprite);

                }

                // Check bullet's impact
                checkBulletImpact();

                // Check if any of the enemies are dead
                checkEnemyDeath();

                // Clean up the playing field
                cleanUpSprites();

                // Check if the player has won or lost
                checkWon();
                checkLost();

            }

        }.start();

        // Display the scene
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);

    }


    // Checks list of keys to flag as down
    public static void setKeyDown (KeyEvent e) {

        for (KeyCode key : keys.keySet()) {

            if (e.getCode() == key) {

                keys.replace(key, true);

            }

        }

    }

    // Checks list of keys to flag as up
    public static void setKeyUp (KeyEvent e) {

        for (KeyCode key : keys.keySet()) {

            if (e.getCode() == key) {

                keys.replace(key, false);

            }

        }

    }

    // Watches for key input and moves the player accordingly
    public static void controlPlayer() {

        for (KeyCode key : keys.keySet()) {

            if (key.equals(KeyCode.W) && keys.get(key) || key.equals(KeyCode.UP) && keys.get(key)) {

                playerSprite.moveForward();

            } else if (key.equals(KeyCode.S) && keys.get(key) || key.equals(KeyCode.DOWN) && keys.get(key)) {

                playerSprite.moveBackward();

            } else if (key.equals(KeyCode.A) && keys.get(key) || key.equals(KeyCode.LEFT) && keys.get(key)) {

                playerSprite.rotateLeft();

            } else if (key.equals(KeyCode.D) && keys.get(key) || key.equals(KeyCode.RIGHT) && keys.get(key)) {

                playerSprite.rotateRight();

            } else if (key.equals(KeyCode.SPACE) && keys.get(key) && getTime() - timeSinceLastBullet >= 700) {

                firePlayerBullet();
                timeSinceLastBullet = getTime();

            }

        }

    }

    // Returns the current time in milliseconds as to withhold the user from firing
    public static long getTime () {

        return System.currentTimeMillis();

    }

    // Sets each enemies' move
    public static void setEnemyMove () {

        for (EnemySprite enemySprite: enemies) {

            enemySprite.moveEnemy();

            // Generate a random index
            int randomIndex = new Random().nextInt(enemies.size());

            // Make the enemy at the fire a shot
            fireEnemyBullet(enemies.get(randomIndex));


        }

    }

    // Spawns a random amount of enemies (up to 10) in random locations
    public static void spawnEnemies () {

        // Generate a random number of sprites
        int rInt = (int) (Math.random() * 10) + 1;

        for (int i = 1; i <= rInt; i++) {

            // Generate a random x and y position for each sprite
            int randomX = (int) (Math.random() * 701) + 50;
            int randomY = (int) (Math.random() * 701) + 50;

            // Create the new enemy
            EnemySprite enemy = new EnemySprite(randomX, randomY, 100);

            // Render the enemy
            pane.getChildren().add(enemy.renderEnemy());

            // Check if sprites overlap
            checkEnemyOverlap(enemy);

            // Mange the enemy
            enemies.add(enemy);

        }

    }

    // Fires a bullet from the player's location
    public static void firePlayerBullet () {

        // Create the bullet
        BulletSprite bullet = new BulletSprite(playerSprite.getTranslateX() + 18, playerSprite.getTranslateY() + 16, playerSprite.rotation);

        // Manage the bullet
        playerBullets.add(bullet);

        // Render the bullet
        pane.getChildren().add(bullet.renderBullet());

        // Play the sound effect
        Media music = new Media(new File("assets/sounds/pew.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setVolume(0.03);
        mediaPlayer.setAutoPlay(true);

        // Set the bullet into motion based upon the player's direction
        bullet.moveForward(playerSprite.forward);

    }

    // Fires a bullet from the enemy's location
    public static void fireEnemyBullet (EnemySprite enemySprite) {

        // Create the bullet
        BulletSprite bullet = new BulletSprite(enemySprite.getTranslateX() + 18, enemySprite.getTranslateY() + 16, enemySprite.rotation);

        // Manage the bullet
        enemyBullets.add(bullet);

        // Render the bullet
        pane.getChildren().add(bullet.renderBullet());

        // Play the sound effect
        Media music = new Media(new File("assets/sounds/pew.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setVolume(0.03);
        mediaPlayer.setAutoPlay(true);

        // Set the bullet into motion based upon the enemy's direction
        bullet.moveForward(enemySprite.forward);

    }

    // If a newly created enemy overlaps with the player or other enemies, remove it
    public static void checkEnemyOverlap (EnemySprite newEnemy) {

        for (EnemySprite enemySprite : enemies) {

            if (newEnemy.getBoundsInParent().intersects(enemySprite.getBoundsInParent()) || newEnemy.getBoundsInParent().intersects(playerSprite.getBoundsInParent())) {

                // Make the game easier
                removableEnemies.add(newEnemy);

            }

        }

        enemies.removeAll(removableEnemies);

    }

    // Checks each bullets' intersection and deals damage accordingly
    public static void checkBulletImpact () {

        for (EnemySprite enemySprite : enemies) {

            for (BulletSprite bulletSprite : playerBullets) {

                if (bulletSprite.getBoundsInParent().intersects(enemySprite.getBoundsInParent())) {

                    enemySprite.health -= 50;

                    try {

                        Image damagedEnemy = new Image(new FileInputStream("assets/sprites/damaged-enemy.png"));
                        enemySprite.setImage(damagedEnemy);

                    } catch (FileNotFoundException ex) {

                        System.out.println("File not found");

                    }

                    removableBullets.add(bulletSprite);

                }

            }

        }

        for (BulletSprite bulletSprite : enemyBullets) {

            if (bulletSprite.getBoundsInParent().intersects(playerSprite.getBoundsInParent())) {

                playerSprite.health -= 25;

                // Shrink the health indicator
                healthIndicator.setFitWidth(healthIndicator.getFitWidth() - 12);

                removableBullets.add(bulletSprite);

                if (playerSprite.health == 25) {

                    try {

                        Image damagedPlayer = new Image(new FileInputStream("assets/sprites/damaged-ship.png"));
                        playerSprite.setImage(damagedPlayer);

                    } catch (FileNotFoundException ex) {

                        System.out.println("File not found");

                    }

                }

            }

        }

        // Remove all unnecessary sprites from their corresponding 'ArrayLists'
        enemies.removeAll(removableEnemies);
        playerBullets.removeAll(removableBullets);
        enemyBullets.removeAll(removableBullets);

    }

    // Sets the passed in bullet to 'removable' if it intersects with any of the borders
    public static void checkBulletAgainstBoundaries (BulletSprite bulletSprite) {

        if (bulletSprite.getBoundsInParent().intersects(topBorder.colliderField.getBoundsInParent())
            || bulletSprite.getBoundsInParent().intersects(bottomBorder.colliderField.getBoundsInParent())
            || bulletSprite.getBoundsInParent().intersects(leftBorder.colliderField.getBoundsInParent())
            || bulletSprite.getBoundsInParent().intersects(rightBorder.colliderField.getBoundsInParent())) {

            removableBullets.add(bulletSprite);

        }

    }

    // Bounces the player sprite off of the enemy sprite that it collided with
    public static void checkCollisions () {

        for (EnemySprite enemySprite : enemies) {

            if (enemySprite.getBoundsInParent().intersects(playerSprite.getBoundsInParent())) {

                playerSprite.handleCollision();
                enemySprite.handleCollision();

            }

        }

    }

    // Halts the player at each border's location
    public static void checkBoundaries (Sprite sprite) {

        if (topBorder.colliderField.intersects(sprite.getBoundsInParent())) {

            topBorder.halt(sprite);

        } else if (bottomBorder.colliderField.intersects(sprite.getBoundsInParent())) {

            bottomBorder.halt(sprite);

        } else if (leftBorder.colliderField.intersects(sprite.getBoundsInParent())) {

            leftBorder.halt(sprite);

        } else if (rightBorder.colliderField.intersects(sprite.getBoundsInParent())) {

            rightBorder.halt(sprite);

        }

    }

    // Allows the enemy to be removed if their health is equal to zero
    public static void checkEnemyDeath () {

        for (EnemySprite enemySprite : enemies) {

            if (enemySprite.health == 0) {

                removableEnemies.add(enemySprite);

            }

        }

        enemies.removeAll(removableEnemies);

    }

    // Un-renders all sprites within the list of 'removableSprites'
    public static void cleanUpSprites () {

        for (EnemySprite enemySprite : removableEnemies) {

            pane.getChildren().remove(enemySprite);

        }

        for (BulletSprite bulletSprite : removableBullets) {

            pane.getChildren().remove(bulletSprite);

        }

    }

    // Check if the player won
    public static void checkWon () {

        if (enemies.size() <= 0) {

            winMessage.setVisible(true);
            mainMenuButton.setVisible(true);

        }

    }

    // Check if the player lost
    public static void checkLost () {

        if (playerSprite.health <= 0) {

            pane.getChildren().remove(playerSprite);
            pane.getChildren().remove(healthIndicator);
            lossMessage.setVisible(true);
            mainMenuButton.setVisible(true);

        }

    }

}