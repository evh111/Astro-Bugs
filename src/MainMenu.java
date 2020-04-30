import java.io.File;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.io.FileNotFoundException;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
/**
 * Elijah Helmandollar
 * Lab #8
 * Description: A class for constructing the main menu for user navigation.
 */
public class MainMenu extends Application {

    // Static 'Media' and 'MediaPlayer' to avoid garbage collection
    public static final Media music = new Media(new File("assets/music/music.mp3").toURI().toString());
    public static final MediaPlayer mediaPlayer = new MediaPlayer(music);

    // Sets the initial stage for all scenes to take place upon
    public void start (Stage stage) {

        // Set the initial stage
        constructMainMenu(stage);
        stage.setTitle("Astro-Bugs");
        stage.show();

        // Play music
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

    }

    // Constructs the main menu and allows the user to start the game, traverse the sub-menus, and quit the game.
    public void constructMainMenu (Stage stage) {

        // 'GridPane' as the parent container
        GridPane gridPane = new GridPane();

        // 'VBox' for alignment
        VBox vBox = new VBox();

        // Create the label(s)
        Label titleLabel = new Label("Astro-Bugs");

        // Create the menu buttons
        Button startBtn = new Button("Start");
        Button instructionsBtn = new Button("Instructions");
        Button creditBtn = new Button("Credits");
        Button quitBtn = new Button("Quit");

        // Place elements
        vBox.getChildren().add(titleLabel);
        vBox.getChildren().add(startBtn);
        vBox.getChildren().add(instructionsBtn);
        vBox.getChildren().add(creditBtn);
        vBox.getChildren().add(quitBtn);
        gridPane.add(vBox, 0, 0);

        // Handle events
        startBtn.setOnMouseClicked(e -> {

            try {

                GameStage.constructGameStage(stage);

            } catch (FileNotFoundException ex) {

                System.out.println("File not found.");

            }

        });

        instructionsBtn.setOnMouseClicked(e -> constructInstructions(stage));

        creditBtn.setOnMouseClicked(e -> constructCredits(stage));

        quitBtn.setOnMouseClicked(e -> stage.close());

        // Set properties
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: black");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        startBtn.setPrefSize(250, 60);
        instructionsBtn.setPrefSize(250, 60);
        creditBtn.setPrefSize(250, 60);
        quitBtn.setPrefSize(250, 60);

        // Create scene and set style
        Scene scene = new Scene(gridPane, 800, 800);
        scene.getStylesheets().add("styles.css");

        // Display the scene
        stage.setScene(scene);

    }

    // Constructs the 'Instructions' sub-menu
    public void constructInstructions (Stage stage) {

        // 'GridPane' as the parent container
        GridPane gridPane = new GridPane();

        // 'VBox' for alignment
        VBox vBox = new VBox();

        // 'Label' for the scene title
        Label instructionsTitle = new Label("Instructions");

        // 'Button' for escaping the menu
        Button backBtn = new Button("Back");

        // 'Text' for description and controls
        Text objectiveText = new Text("Objective: To eliminate all of the astro-bugs");
        Text upMovementText = new Text("W or UP arrow key for upwards movement");
        Text downMovementText = new Text("S or DOWN arrow key for downwards movement");
        Text leftMovementText = new Text("A or LEFT arrow key for counter-clockwise rotation");
        Text rightMovementText = new Text("D or RIGHT arrow key for clockwise rotation");
        Text spaceBarText = new Text("SPACEBAR key for shooting");

        // Place elements
        vBox.getChildren().add(instructionsTitle);
        vBox.getChildren().add(objectiveText);
        vBox.getChildren().add(upMovementText);
        vBox.getChildren().add(downMovementText);
        vBox.getChildren().add(leftMovementText);
        vBox.getChildren().add(rightMovementText);
        vBox.getChildren().add(spaceBarText);
        vBox.getChildren().add(backBtn);
        gridPane.add(vBox, 0, 0);

        // Handle button event
        backBtn.setOnMouseClicked(e -> constructMainMenu(stage));

        // Set properties
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: black");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        objectiveText.setFill(Color.web("#69FF00"));
        upMovementText.setFill(Color.web("#69FF00"));
        downMovementText.setFill(Color.web("#69FF00"));
        leftMovementText.setFill(Color.web("#69FF00"));
        rightMovementText.setFill(Color.web("#69FF00"));
        spaceBarText.setFill(Color.web("#69FF00"));
        objectiveText.setScaleX(2.3);
        objectiveText.setScaleY(2.3);
        upMovementText.setScaleX(1.7);
        upMovementText.setScaleY(1.7);
        downMovementText.setScaleX(1.7);
        downMovementText.setScaleY(1.7);
        leftMovementText.setScaleX(1.7);
        leftMovementText.setScaleY(1.7);
        rightMovementText.setScaleX(1.7);
        rightMovementText.setScaleY(1.7);
        spaceBarText.setScaleX(1.7);
        spaceBarText.setScaleY(1.7);
        backBtn.setPrefSize(100, 40);

        // Create scene and set style
        Scene scene = new Scene(gridPane, 800, 800);
        scene.getStylesheets().add("styles.css");

        // Display the scene
        stage.setScene(scene);

    }

    // Constructs the 'Credits' sub-menu
    public void constructCredits (Stage stage) {

        // 'GridPane' as the parent container
        GridPane gridPane = new GridPane();

        // 'VBox' for alignment
        VBox vBox = new VBox();

        // 'Label' for the scene title
        Label creditsTitle = new Label("Credits");

        // 'Button' for escaping the menu
        Button backBtn = new Button("Back");

        // 'Text' for description and controls
        Text programmerText = new Text("Programmer: Elijah Helmandollar");
        Text artAndMusicText = new Text("Art and Music: Elijah Helmandollar");
        Text specialThanksText = new Text("Special thanks to Dr. Andrey Puretskiy and Mardigon Toler");

        // Place elements
        vBox.getChildren().add(creditsTitle);
        vBox.getChildren().add(programmerText);
        vBox.getChildren().add(artAndMusicText);
        vBox.getChildren().add(specialThanksText);
        vBox.getChildren().add(backBtn);
        gridPane.add(vBox,0,0);

        // Handle button event
        backBtn.setOnMouseClicked(e -> constructMainMenu(stage));

        // Set properties
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: black");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        programmerText.setFill(Color.web("#69FF00"));
        artAndMusicText.setFill(Color.web("#69FF00"));
        specialThanksText.setFill(Color.web("#69FF00"));
        programmerText.setScaleX(1.8);
        programmerText.setScaleY(1.8);
        artAndMusicText.setScaleX(1.8);
        artAndMusicText.setScaleY(1.8);
        specialThanksText.setScaleX(1.8);
        specialThanksText.setScaleY(1.8);

        // Create scene and set style
        Scene scene = new Scene(gridPane, 800, 800);
        scene.getStylesheets().add("styles.css");

        // Display the scene
        stage.setScene(scene);

    }

    public static void main (String [] args) {
        launch(args);
    }

}
