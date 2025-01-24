import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Objects;

/**
 * The Main class represents the entry point of the JavaFX application.
 */
public class DuckHunt extends Application {
    private final double SCALE = 3;
    private final String TITLE = "HUBBM Duck Hunt";
    private final String FAVICON_PATH = "assets/favicon/1.png";
    private final String BACKGROUND_MUSIC_PATH = "src/assets/effects/Title.mp3";
    private final String BACKGROUND_SELECTION_PATH = "assets/background/";
    private final String CRROSHAIR_SELECTION_PATH = "assets/crosshair/";
    private final String FOREGROUND_SELECTION_PATH = "assets/foreground/";
    private static final String DUCK_BLACK_PATH = "assets/duck_black/";
    private static final String DUCK_BLUE_PATH = "assets/duck_blue/";
    private static final String DUCK_RED_PATH = "assets/duck_red/";
    Media music;
    MediaPlayer mediaPlayer;
    double VOLUME = 0.025;

    private ImageView gameBackground;
    private ImageView gameForeground;
    private ImageView gameCrosshair;
    private ImageView gameBackground2;
    private ImageView gameForeground2;
    private ImageView gameCrosshair2;
    private ImageView gameBackground3;
    private ImageView gameForeground3;
    private ImageView gameCrosshair3;
    private ImageView gameBackground4;
    private ImageView gameForeground4;
    private ImageView gameCrosshair4;
    private ImageView gameBackground5;
    private ImageView gameForeground5;
    private ImageView gameCrosshair5;
    private ImageView gameBackground6;
    private ImageView gameForeground6;
    private ImageView gameCrosshair6;
    private final double BIRD_WIDTH = 35 * SCALE;
    private final double BIRD_HEIGHT = 35 * SCALE;
    private final double SCREEN_WIDTH = 280 * SCALE;
    private final double SCREEN_HEIGHT = 260 * SCALE;
    private boolean[] moveRight = {true};
    private boolean[] moveRight2_1 = {false};
    private boolean[] moveRight2_2 = {true};
    private boolean[] moveLeft = {true};
    private boolean[] moveUp = {true};
    private boolean[] moveLeft2_1 = {true};
    private boolean[] moveUp2_1 = {true};
    private boolean[] moveLeft2_2 = {true};
    private boolean[] moveUp2_2 = {true};
    private boolean[] moveRight3_1 = {true};
    private boolean[] moveRight3_2 = {false};
    private boolean[] moveLeft3 = {true};
    private boolean[] moveUp3 = {true};
    private boolean[] moveRight4_1 = {true};
    private boolean[] moveRight4_2 = {false};
    private boolean[] moveLeft4_1 = {true};
    private boolean[] moveUp4_1 = {true};
    private boolean[] moveLeft4_2 = {true};
    private boolean[] moveUp4_2 = {true};

    private Image[] ducks;
    private ImageView duck;
    private Image[] ducks2;
    private ImageView duck2;
    private Image[] ducks3_1;
    private ImageView duck3_1;
    private Image[] ducks3_2;
    private ImageView duck3_2;
    private Image[] ducks4_1;
    private ImageView duck4_1;
    private Image[] ducks4_2;
    private ImageView duck4_2;
    private Image[] ducks5_1;
    private ImageView duck5_1;
    private Image[] ducks5_2;
    private ImageView duck5_2;
    private Image[] ducks5_3;
    private ImageView duck5_3;
    private Image[] ducks6_1;
    private ImageView duck6_1;
    private Image[] ducks6_2;
    private ImageView duck6_2;
    private Image[] ducks6_3;
    private ImageView duck6_3;
    private Image[] ducks6_4;
    private ImageView duck6_4;
    private boolean areBirdsShot = false;
    ImageView deadDuck;
    int ammoCount;
    int ammoCount2;
    int ammoCount3;
    int ammoCount4;
    int ammoCount5;
    int ammoCount6;
    int[] shotDuckCount1 = {0};
    int[] shotDuckCount2 = {0};
    int[] shotDuckCount3 = {0};
    int[] shotDuckCount4 = {0};
    int[] shotDuckCount5 = {0};
    int[] shotDuckCount6 = {0};
    ImageView[] duckObjects1;
    ImageView[] duckObjects2;
    ImageView[] duckObjects3;
    ImageView[] duckObjects4;
    ImageView[] duckObjects5;
    ImageView[] duckObjects6;

    private static final String[] BACKGROUND_OPTIONS = {
            "1.png",
            "2.png",
            "3.png",
            "4.png",
            "5.png",
            "6.png"
    };

    private static final String[] CROSSHAIR_OPTIONS = {
            "1.png",
            "2.png",
            "3.png",
            "4.png",
            "5.png",
            "6.png"
    };

    private int selectedBackgroundIndex = 0;
    private int selectedCrosshairIndex = 0;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main method, which launches the JavaFX application.
     *
     * @param primaryStage The command-line arguments.
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle(TITLE);
        primaryStage.requestFocus();
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(FAVICON_PATH)).toExternalForm()));

        //TITLESCREEN
        ImageView background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(FAVICON_PATH)).toExternalForm()));
        background.setFitWidth(280 * SCALE);
        background.setFitHeight(260 * SCALE);

        Text instructionsLabel = new Text("PRESS ENTER TO START");
        instructionsLabel.setTranslateX(0);
        instructionsLabel.setTranslateY(35 * SCALE);
        Font font = Font.font("Arial", 15 * SCALE);
        instructionsLabel.setFont(font);
        instructionsLabel.setFill(Color.ORANGE);

        Text exitLabel = new Text("PRESS ESC TO EXIT");
        exitLabel.setTranslateX(0);
        exitLabel.setTranslateY(50 * SCALE);
        exitLabel.setFont(font);
        exitLabel.setFill(Color.ORANGE);

        StackPane root = new StackPane();
        root.getChildren().addAll(background, exitLabel, instructionsLabel);

        Scene titleScene = new Scene(root, 280 * SCALE, 260 * SCALE);
        primaryStage.setScene(titleScene);
        primaryStage.show();

        music = new Media(new File(BACKGROUND_MUSIC_PATH).toURI().toString());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setVolume(VOLUME);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        Timeline blinkTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(instructionsLabel.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(instructionsLabel.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(0.0), new KeyValue(exitLabel.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(exitLabel.opacityProperty(), 1))
        );
        blinkTimeline.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline.setAutoReverse(true);
        blinkTimeline.play();

        /**

         The selection screen of the game where the background and crosshair options are displayed.
         */

        //SELECTIONSCREEN
        ImageView backgroundSelection = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[0])).toExternalForm()));
        backgroundSelection.setFitWidth(280 * SCALE);
        backgroundSelection.setFitHeight(260 * SCALE);

        ImageView crosshairSelection = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[0])).toExternalForm()));
        crosshairSelection.setFitWidth(crosshairSelection.getImage().getWidth() * SCALE);
        crosshairSelection.setFitHeight(crosshairSelection.getImage().getHeight() * SCALE);


        Text label1 = new Text("USE ARROW KEYS TO NAVIGATE");
        label1.setTranslateY(-120 * SCALE);
        label1.setFont(Font.font("Arial", 10 * SCALE));
        label1.setFill(Color.ORANGE);

        Text label2 = new Text("PRESS ENTER TO START");
        label2.setTranslateY(-108 * SCALE);
        label2.setFont(Font.font("Arial", 10 * SCALE));
        label2.setFill(Color.ORANGE);

        Text label3 = new Text("PRESS ESC TO EXIT");
        label3.setTranslateY(-95 * SCALE);
        label3.setFont(Font.font("Arial", 10 * SCALE));
        label3.setFill(Color.ORANGE);

        StackPane backgroundSelectionRoot = new StackPane();
        backgroundSelectionRoot.getChildren().addAll(backgroundSelection, crosshairSelection, label1, label2, label3);

        Scene backgroundSelectionScene = new Scene(backgroundSelectionRoot, 280 * SCALE, 260 * SCALE);

        /**

         Represents the first game screen where the gameplay takes place.
         */

        // GAMESCREEN 1
        gameBackground = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameBackground.setFitWidth(280 * SCALE);
        gameBackground.setFitHeight(260 * SCALE);

        gameForeground = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameForeground.setFitWidth(280 * SCALE);
        gameForeground.setFitHeight(260 * SCALE);

        gameCrosshair = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));
        gameCrosshair.setFitWidth(crosshairSelection.getImage().getWidth() * SCALE);
        gameCrosshair.setFitHeight(crosshairSelection.getImage().getHeight() * SCALE);

        Text level1 = new Text("LEVEL 1");
        level1.setTranslateX(0);
        level1.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        level1.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        level1.setFont(font);
        level1.setFill(Color.ORANGE);

        duck = new ImageView();
        duck.setFitWidth(BIRD_WIDTH);
        duck.setPreserveRatio(true);


        ducks = new Image[3];
        for (int i = 0; i < 3; i++) {
            ducks[i] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_BLACK_PATH + (i + 4) + ".png")).toExternalForm());
        }
        duck = new ImageView(ducks[0]);
        duck.setFitWidth(BIRD_WIDTH);
        duck.setPreserveRatio(true);

        ammoCount = 3;
        Text ammoLeft = new Text("Ammo Left: " + ammoCount);
        ammoLeft.setTranslateX(100 * SCALE);
        ammoLeft.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        ammoLeft.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        ammoLeft.setFill(Color.ORANGE);

        EventHandler<ActionEvent> ammoCountEventHandler = event -> {
            ammoCount--;
            ammoLeft.setText("Ammo Left: " + ammoCount);
        };

        EventHandler<ActionEvent> resetAmmoCount = event -> {
            ammoCount = 3;
            ammoLeft.setText("Ammo Left: " + ammoCount);
        };

        StackPane gameRoot = new StackPane();
        gameRoot.getChildren().addAll(gameBackground, duck, gameForeground, ammoLeft, level1);

        Scene gameScene = new Scene(gameRoot, 280 * SCALE, 260 * SCALE);
        animateBird(duck, ducks, moveRight);

        Text youWin = new Text("YOU WIN!");
        youWin.setTranslateX(0);
        youWin.setTranslateY(-20 * SCALE);
        youWin.setFont(font);
        youWin.setFill(Color.ORANGE);

        Text enter = new Text("Press ENTER to play next level");
        enter.setTranslateX(0);
        enter.setTranslateY(0 * SCALE);
        enter.setFont(font);
        enter.setFill(Color.ORANGE);

        Text gameOver = new Text("GAME OVER!");
        gameOver.setTranslateX(0);
        gameOver.setTranslateY(-20 * SCALE);
        gameOver.setFont(font);
        gameOver.setFill(Color.ORANGE);

        Text enterGameOver = new Text("Press ENTER to play again");
        enterGameOver.setTranslateX(0);
        enterGameOver.setTranslateY(0 * SCALE);
        enterGameOver.setFont(font);
        enterGameOver.setFill(Color.ORANGE);

        Text escGameOver = new Text("Press ESC to exit");
        escGameOver.setTranslateX(0);
        escGameOver.setTranslateY(20 * SCALE);
        escGameOver.setFont(font);
        escGameOver.setFill(Color.ORANGE);

        Timeline blinkTimeline1 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enterGameOver.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enterGameOver.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(0.0), new KeyValue(escGameOver.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(escGameOver.opacityProperty(), 1))
        );
        blinkTimeline1.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline1.setAutoReverse(true);
        blinkTimeline1.play();


        Timeline blinkTimeline2 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enter.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enter.opacityProperty(), 1))
        );
        blinkTimeline2.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline2.setAutoReverse(true);
        blinkTimeline2.play();

        /**

         Represents the second game screen where the gameplay takes place.
         */

        // GAMESCREEN 2
        gameBackground2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameBackground2.setFitWidth(280 * SCALE);
        gameBackground2.setFitHeight(260 * SCALE);

        gameForeground2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameForeground2.setFitWidth(280 * SCALE);
        gameForeground2.setFitHeight(260 * SCALE);

        gameCrosshair2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));
        gameCrosshair2.setFitWidth(crosshairSelection.getImage().getWidth() * SCALE);
        gameCrosshair2.setFitHeight(crosshairSelection.getImage().getHeight() * SCALE);

        Text level2 = new Text("LEVEL 2");
        level2.setTranslateX(0);
        level2.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        level2.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        level2.setFont(font);
        level2.setFill(Color.ORANGE);

        ducks2 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks2[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_BLUE_PATH + (j + 1) + ".png")).toExternalForm());
        }
        duck2 = new ImageView(ducks2[0]);
        duck2.setTranslateX(SCREEN_WIDTH / 2);
        duck2.setTranslateY(0);
        duck2.setScaleX(-1);
        duck2.setFitWidth(BIRD_WIDTH);
        duck2.setPreserveRatio(true);

        ammoCount2 = 3;
        Text ammoLeft2 = new Text("Ammo Left: " + ammoCount2);
        ammoLeft2.setTranslateX(100 * SCALE);
        ammoLeft2.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        ammoLeft2.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        ammoLeft2.setFill(Color.ORANGE);

        EventHandler<ActionEvent> ammoCountEventHandler2 = event -> {
            ammoCount2--;
            ammoLeft2.setText("Ammo Left: " + ammoCount2);
        };

        EventHandler<ActionEvent> resetAmmoCount2 = event -> {
            ammoCount2 = 3;
            ammoLeft2.setText("Ammo Left: " + ammoCount2);
        };

        Text youWin2 = new Text("YOU WIN!");
        youWin2.setTranslateX(0);
        youWin2.setTranslateY(-20 * SCALE);
        youWin2.setFont(font);
        youWin2.setFill(Color.ORANGE);

        Text enter2 = new Text("Press ENTER to play next level");
        enter2.setTranslateX(0);
        enter2.setTranslateY(0 * SCALE);
        enter2.setFont(font);
        enter2.setFill(Color.ORANGE);

        Text gameOver2 = new Text("GAME OVER!");
        gameOver2.setTranslateX(0);
        gameOver2.setTranslateY(-20 * SCALE);
        gameOver2.setFont(font);
        gameOver2.setFill(Color.ORANGE);

        Text enterGameOver2 = new Text("Press ENTER to play again");
        enterGameOver2.setTranslateX(0);
        enterGameOver2.setTranslateY(0 * SCALE);
        enterGameOver2.setFont(font);
        enterGameOver2.setFill(Color.ORANGE);

        Text escGameOver2 = new Text("Press ESC to exit");
        escGameOver2.setTranslateX(0);
        escGameOver2.setTranslateY(20 * SCALE);
        escGameOver2.setFont(font);
        escGameOver2.setFill(Color.ORANGE);

        Timeline blinkTimeline3 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enterGameOver2.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enterGameOver2.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(0.0), new KeyValue(escGameOver2.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(escGameOver2.opacityProperty(), 1))
        );
        blinkTimeline3.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline3.setAutoReverse(true);
        blinkTimeline3.play();


        Timeline blinkTimeline4 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enter2.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enter2.opacityProperty(), 1))
        );
        blinkTimeline4.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline4.setAutoReverse(true);
        blinkTimeline4.play();

        StackPane gameRoot2 = new StackPane();
        gameRoot2.getChildren().addAll(gameBackground2, duck2, gameForeground2, ammoLeft2, level2);

        Scene gameScene2 = new Scene(gameRoot2, 280 * SCALE, 260 * SCALE);

        /**

         Represents the third game screen where the gameplay takes place.
         */
        // GAMESCREEN 3
        gameBackground3 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameBackground3.setFitWidth(280 * SCALE);
        gameBackground3.setFitHeight(260 * SCALE);

        gameForeground3 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameForeground3.setFitWidth(280 * SCALE);
        gameForeground3.setFitHeight(260 * SCALE);

        gameCrosshair3 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));
        gameCrosshair3.setFitWidth(crosshairSelection.getImage().getWidth() * SCALE);
        gameCrosshair3.setFitHeight(crosshairSelection.getImage().getHeight() * SCALE);

        Text level3 = new Text("LEVEL 3");
        level3.setTranslateX(0);
        level3.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        level3.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        level3.setFont(font);
        level3.setFill(Color.ORANGE);

        ducks3_1 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks3_1[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_BLUE_PATH + (j + 4) + ".png")).toExternalForm());
        }
        duck3_1 = new ImageView(ducks3_1[0]);
        duck3_1.setTranslateX(10 * SCALE);
        duck3_1.setScaleX(-1);
        duck3_1.setFitWidth(BIRD_WIDTH);
        duck3_1.setPreserveRatio(true);

        ducks3_2 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks3_2[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_BLUE_PATH + (j + 4) + ".png")).toExternalForm());
        }
        duck3_2 = new ImageView(ducks3_2[0]);
        duck3_2.setTranslateX(-10 * SCALE);
        duck3_2.setFitWidth(BIRD_WIDTH);
        duck3_2.setPreserveRatio(true);

        ammoCount3 = 6;
        Text ammoLeft3 = new Text("Ammo Left: " + ammoCount3);
        ammoLeft3.setTranslateX(100 * SCALE);
        ammoLeft3.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        ammoLeft3.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        ammoLeft3.setFill(Color.ORANGE);

        EventHandler<ActionEvent> ammoCountEventHandler3 = event -> {
            ammoCount3--;
            ammoLeft3.setText("Ammo Left: " + ammoCount3);
        };

        EventHandler<ActionEvent> resetAmmoCount3 = event -> {
            ammoCount3 = 3;
            ammoLeft3.setText("Ammo Left: " + ammoCount3);
        };

        Text youWin3 = new Text("YOU WIN!");
        youWin3.setTranslateX(0);
        youWin3.setTranslateY(-20 * SCALE);
        youWin3.setFont(font);
        youWin3.setFill(Color.ORANGE);

        Text enter3 = new Text("Press ENTER to play next level");
        enter3.setTranslateX(0);
        enter3.setTranslateY(0 * SCALE);
        enter3.setFont(font);
        enter3.setFill(Color.ORANGE);

        Text gameOver3 = new Text("GAME OVER!");
        gameOver3.setTranslateX(0);
        gameOver3.setTranslateY(-20 * SCALE);
        gameOver3.setFont(font);
        gameOver3.setFill(Color.ORANGE);

        Text enterGameOver3 = new Text("Press ENTER to play again");
        enterGameOver3.setTranslateX(0);
        enterGameOver3.setTranslateY(0 * SCALE);
        enterGameOver3.setFont(font);
        enterGameOver3.setFill(Color.ORANGE);

        Text escGameOver3 = new Text("Press ESC to exit");
        escGameOver3.setTranslateX(0);
        escGameOver3.setTranslateY(20 * SCALE);
        escGameOver3.setFont(font);
        escGameOver3.setFill(Color.ORANGE);

        Timeline blinkTimeline5 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enterGameOver3.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enterGameOver3.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(0.0), new KeyValue(escGameOver3.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(escGameOver3.opacityProperty(), 1))
        );
        blinkTimeline5.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline5.setAutoReverse(true);
        blinkTimeline5.play();


        Timeline blinkTimeline6 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enter3.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enter3.opacityProperty(), 1))
        );
        blinkTimeline6.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline6.setAutoReverse(true);
        blinkTimeline6.play();

        StackPane gameRoot3 = new StackPane();
        gameRoot3.getChildren().addAll(gameBackground3, duck3_1, duck3_2, gameForeground3, ammoLeft3, level3);

        Scene gameScene3 = new Scene(gameRoot3, 280 * SCALE, 260 * SCALE);

        /**

         Represents the fourth game screen where the gameplay takes place.
         */
        // GAMESCREEN 4
        gameBackground4 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameBackground4.setFitWidth(280 * SCALE);
        gameBackground4.setFitHeight(260 * SCALE);

        gameForeground4 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameForeground4.setFitWidth(280 * SCALE);
        gameForeground4.setFitHeight(260 * SCALE);

        gameCrosshair4 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));
        gameCrosshair4.setFitWidth(crosshairSelection.getImage().getWidth() * SCALE);
        gameCrosshair4.setFitHeight(crosshairSelection.getImage().getHeight() * SCALE);

        Text level4 = new Text("LEVEL 4");
        level4.setTranslateX(0);
        level4.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        level4.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        level4.setFont(font);
        level4.setFill(Color.ORANGE);

        ducks4_1 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks4_1[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_RED_PATH + (j + 1) + ".png")).toExternalForm());
        }
        duck4_1 = new ImageView(ducks4_1[0]);
        duck4_1.setTranslateX(SCREEN_WIDTH / 2);
        duck4_1.setTranslateY(0);
        duck4_1.setScaleX(-1);
        duck4_1.setFitWidth(BIRD_WIDTH);
        duck4_1.setPreserveRatio(true);

        ducks4_2 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks4_2[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_RED_PATH + (j + 1) + ".png")).toExternalForm());
        }
        duck4_2 = new ImageView(ducks4_2[0]);
        duck4_2.setTranslateX(-SCREEN_WIDTH / 2);
        duck4_2.setTranslateY(0);
        duck4_2.setFitWidth(BIRD_WIDTH);
        duck4_2.setPreserveRatio(true);

        ammoCount4 = 6;
        Text ammoLeft4 = new Text("Ammo Left: " + ammoCount4);
        ammoLeft4.setTranslateX(100 * SCALE);
        ammoLeft4.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        ammoLeft4.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        ammoLeft4.setFill(Color.ORANGE);

        EventHandler<ActionEvent> ammoCountEventHandler4 = event -> {
            ammoCount4--;
            ammoLeft4.setText("Ammo Left: " + ammoCount4);
        };

        EventHandler<ActionEvent> resetAmmoCount4 = event -> {
            ammoCount4 = 6;
            ammoLeft4.setText("Ammo Left: " + ammoCount4);
        };

        Text youWin4 = new Text("YOU WIN!");
        youWin4.setTranslateX(0);
        youWin4.setTranslateY(-20 * SCALE);
        youWin4.setFont(font);
        youWin4.setFill(Color.ORANGE);

        Text enter4 = new Text("Press ENTER to play next level");
        enter4.setTranslateX(0);
        enter4.setTranslateY(0 * SCALE);
        enter4.setFont(font);
        enter4.setFill(Color.ORANGE);

        Text gameOver4 = new Text("GAME OVER!");
        gameOver4.setTranslateX(0);
        gameOver4.setTranslateY(-20 * SCALE);
        gameOver4.setFont(font);
        gameOver4.setFill(Color.ORANGE);

        Text enterGameOver4 = new Text("Press ENTER to play again");
        enterGameOver4.setTranslateX(0);
        enterGameOver4.setTranslateY(0 * SCALE);
        enterGameOver4.setFont(font);
        enterGameOver4.setFill(Color.ORANGE);

        Text escGameOver4 = new Text("Press ESC to exit");
        escGameOver4.setTranslateX(0);
        escGameOver4.setTranslateY(20 * SCALE);
        escGameOver4.setFont(font);
        escGameOver4.setFill(Color.ORANGE);

        Timeline blinkTimeline7 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enterGameOver4.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enterGameOver4.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(0.0), new KeyValue(escGameOver4.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(escGameOver4.opacityProperty(), 1))
        );
        blinkTimeline7.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline7.setAutoReverse(true);
        blinkTimeline7.play();


        Timeline blinkTimeline8 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enter4.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enter4.opacityProperty(), 1))
        );
        blinkTimeline8.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline8.setAutoReverse(true);
        blinkTimeline8.play();

        StackPane gameRoot4 = new StackPane();
        gameRoot4.getChildren().addAll(gameBackground4, duck4_1, duck4_2, gameForeground4, ammoLeft4, level4);

        Scene gameScene4 = new Scene(gameRoot4, 280 * SCALE, 260 * SCALE);

        /**

         Represents the fifth game screen where the gameplay takes place.
         */
        //GAMESCREEN 5

        gameBackground5 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameBackground5.setFitWidth(280 * SCALE);
        gameBackground5.setFitHeight(260 * SCALE);

        gameForeground5 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameForeground5.setFitWidth(280 * SCALE);
        gameForeground5.setFitHeight(260 * SCALE);

        gameCrosshair5 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));
        gameCrosshair5.setFitWidth(crosshairSelection.getImage().getWidth() * SCALE);
        gameCrosshair5.setFitHeight(crosshairSelection.getImage().getHeight() * SCALE);

        Text level5 = new Text("LEVEL 5");
        level5.setTranslateX(0);
        level5.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        level5.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        level5.setFont(font);
        level5.setFill(Color.ORANGE);

        ducks5_1 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks5_1[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_RED_PATH + (j + 4) + ".png")).toExternalForm());
        }
        duck5_1 = new ImageView(ducks5_1[0]);
        duck5_1.setTranslateY(-30 * SCALE);
        duck5_1.setTranslateX(-10 * SCALE);
        duck5_1.setFitWidth(BIRD_WIDTH);
        duck5_1.setPreserveRatio(true);
        duck3_1.setTranslateX(10 * SCALE);

        ducks5_2 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks5_2[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_RED_PATH + (j + 4) + ".png")).toExternalForm());
        }
        duck5_2 = new ImageView(ducks5_2[0]);
        duck5_2.setTranslateY(10 * SCALE);
        duck5_2.setTranslateX(10 * SCALE);
        duck5_2.setScaleX(-1);
        duck5_2.setFitWidth(BIRD_WIDTH);
        duck5_2.setPreserveRatio(true);

        ducks5_3 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks5_3[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_RED_PATH + (j + 1) + ".png")).toExternalForm());
        }
        duck5_3 = new ImageView(ducks5_3[0]);
        duck5_3.setTranslateX(SCREEN_WIDTH / 2);
        duck5_3.setTranslateY(0);
        duck5_3.setScaleX(-1);
        duck5_3.setFitWidth(BIRD_WIDTH);
        duck5_3.setPreserveRatio(true);

        ammoCount5 = 9;
        Text ammoLeft5 = new Text("Ammo Left: " + ammoCount5);
        ammoLeft5.setTranslateX(100 * SCALE);
        ammoLeft5.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        ammoLeft5.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        ammoLeft5.setFill(Color.ORANGE);

        EventHandler<ActionEvent> ammoCountEventHandler5 = event -> {
            ammoCount5--;
            ammoLeft5.setText("Ammo Left: " + ammoCount5);
        };

        EventHandler<ActionEvent> resetAmmoCount5 = event -> {
            ammoCount5 = 9;
            ammoLeft5.setText("Ammo Left: " + ammoCount5);
        };

        Text youWin5 = new Text("YOU WIN!");
        youWin5.setTranslateX(0);
        youWin5.setTranslateY(-20 * SCALE);
        youWin5.setFont(font);
        youWin5.setFill(Color.ORANGE);

        Text enter5 = new Text("Press ENTER to play next level");
        enter5.setTranslateX(0);
        enter5.setTranslateY(0 * SCALE);
        enter5.setFont(font);
        enter5.setFill(Color.ORANGE);

        Text gameOver5 = new Text("GAME OVER!");
        gameOver5.setTranslateX(0);
        gameOver5.setTranslateY(-20 * SCALE);
        gameOver5.setFont(font);
        gameOver5.setFill(Color.ORANGE);

        Text enterGameOver5 = new Text("Press ENTER to play again");
        enterGameOver5.setTranslateX(0);
        enterGameOver5.setTranslateY(0 * SCALE);
        enterGameOver5.setFont(font);
        enterGameOver5.setFill(Color.ORANGE);

        Text escGameOver5 = new Text("Press ESC to exit");
        escGameOver5.setTranslateX(0);
        escGameOver5.setTranslateY(20 * SCALE);
        escGameOver5.setFont(font);
        escGameOver5.setFill(Color.ORANGE);

        Timeline blinkTimeline9 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enterGameOver5.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enterGameOver5.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(0.0), new KeyValue(escGameOver5.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(escGameOver5.opacityProperty(), 1))
        );
        blinkTimeline9.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline9.setAutoReverse(true);
        blinkTimeline9.play();


        Timeline blinkTimeline10 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enter5.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enter5.opacityProperty(), 1))
        );
        blinkTimeline10.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline10.setAutoReverse(true);
        blinkTimeline10.play();

        StackPane gameRoot5 = new StackPane();
        gameRoot5.getChildren().addAll(gameBackground5, duck5_1, duck5_2, duck5_3, gameForeground5, ammoLeft5, level5);

        Scene gameScene5 = new Scene(gameRoot5, 280 * SCALE, 260 * SCALE);

        /**

         Represents the sixth game screen where the gameplay takes place.
         */

        //GAMESCREEN 6

        gameBackground6 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameBackground6.setFitWidth(280 * SCALE);
        gameBackground6.setFitHeight(260 * SCALE);

        gameForeground6 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
        gameForeground6.setFitWidth(280 * SCALE);
        gameForeground6.setFitHeight(260 * SCALE);

        gameCrosshair6 = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));
        gameCrosshair6.setFitWidth(crosshairSelection.getImage().getWidth() * SCALE);
        gameCrosshair6.setFitHeight(crosshairSelection.getImage().getHeight() * SCALE);

        Text level6 = new Text("LEVEL 6");
        level6.setTranslateX(0);
        level6.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        level6.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        level6.setFont(font);
        level6.setFill(Color.ORANGE);

        ducks6_1 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks6_1[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_BLACK_PATH + (j + 4) + ".png")).toExternalForm());
        }
        duck6_1 = new ImageView(ducks6_1[0]);
        duck6_1.setTranslateY(-30 * SCALE);
        duck6_1.setTranslateX(-10 * SCALE);
        duck6_1.setFitWidth(BIRD_WIDTH);
        duck6_1.setPreserveRatio(true);
        duck6_1.setTranslateX(10 * SCALE);

        ducks6_2 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks6_2[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_BLACK_PATH + (j + 4) + ".png")).toExternalForm());
        }
        duck6_2 = new ImageView(ducks6_2[0]);
        duck6_2.setTranslateY(10 * SCALE);
        duck6_2.setTranslateX(10 * SCALE);
        duck6_2.setScaleX(-1);
        duck6_2.setFitWidth(BIRD_WIDTH);
        duck6_2.setPreserveRatio(true);

        ducks6_3 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks6_3[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_BLACK_PATH + (j + 1) + ".png")).toExternalForm());
        }
        duck6_3 = new ImageView(ducks6_3[0]);
        duck6_3.setTranslateX(SCREEN_WIDTH / 2);
        duck6_3.setTranslateY(0);
        duck6_3.setScaleX(-1);
        duck6_3.setFitWidth(BIRD_WIDTH);
        duck6_3.setPreserveRatio(true);

        ducks6_4 = new Image[3];
        for (int j = 0; j < 3; j++) {
            ducks6_4[j] = new Image(Objects.requireNonNull(getClass().getResource(DUCK_BLACK_PATH + (j + 1) + ".png")).toExternalForm());
        }
        duck6_4 = new ImageView(ducks6_4[0]);
        duck6_4.setTranslateX(-SCREEN_WIDTH / 2);
        duck6_4.setTranslateY(0);
        duck6_4.setFitWidth(BIRD_WIDTH);
        duck6_4.setPreserveRatio(true);


        ammoCount6 = 12;
        Text ammoLeft6 = new Text("Ammo Left: " + ammoCount6);
        ammoLeft6.setTranslateX(100 * SCALE);
        ammoLeft6.setTranslateY(-SCREEN_HEIGHT / 2 + 5 * SCALE);
        ammoLeft6.setStyle("-fx-font-size: " + 10 * SCALE + "px;");
        ammoLeft6.setFill(Color.ORANGE);

        EventHandler<ActionEvent> ammoCountEventHandler6 = event -> {
            ammoCount6--;
            ammoLeft6.setText("Ammo Left: " + ammoCount6);
        };

        EventHandler<ActionEvent> resetAmmoCount6 = event -> {
            ammoCount6 = 12;
            ammoLeft6.setText("Ammo Left: " + ammoCount6);
        };

        Text youWin6 = new Text("You have completed the game!");
        youWin6.setTranslateX(0);
        youWin6.setTranslateY(-20 * SCALE);
        youWin6.setFont(font);
        youWin6.setFill(Color.ORANGE);

        Text enter6 = new Text("Press ENTER to play again");
        enter6.setTranslateX(0);
        enter6.setTranslateY(0 * SCALE);
        enter6.setFont(font);
        enter6.setFill(Color.ORANGE);

        Text gameOver6 = new Text("GAME OVER!");
        gameOver6.setTranslateX(0);
        gameOver6.setTranslateY(-20 * SCALE);
        gameOver6.setFont(font);
        gameOver6.setFill(Color.ORANGE);

        Text enterGameOver6 = new Text("Press ENTER to play again");
        enterGameOver6.setTranslateX(0);
        enterGameOver6.setTranslateY(0 * SCALE);
        enterGameOver6.setFont(font);
        enterGameOver6.setFill(Color.ORANGE);

        Text escGameOver6 = new Text("Press ESC to exit");
        escGameOver6.setTranslateX(0);
        escGameOver6.setTranslateY(20 * SCALE);
        escGameOver6.setFont(font);
        escGameOver6.setFill(Color.ORANGE);

        Timeline blinkTimeline11 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enterGameOver6.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enterGameOver6.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(0.0), new KeyValue(escGameOver6.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(escGameOver6.opacityProperty(), 1))
        );
        blinkTimeline11.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline11.setAutoReverse(true);
        blinkTimeline11.play();


        Timeline blinkTimeline12 = new Timeline(
                new KeyFrame(Duration.seconds(0.0), new KeyValue(enter6.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(enter6.opacityProperty(), 1))
        );
        blinkTimeline12.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline12.setAutoReverse(true);
        blinkTimeline12.play();

        StackPane gameRoot6 = new StackPane();
        gameRoot6.getChildren().addAll(gameBackground6, duck6_1, duck6_2, duck6_3, duck6_4, gameForeground6, ammoLeft6, level6);

        Scene gameScene6 = new Scene(gameRoot6, 280 * SCALE, 260 * SCALE);

        duckObjects1 = new ImageView[]{duck};
        duckObjects2 = new ImageView[]{duck2};
        duckObjects3 = new ImageView[]{duck3_1, duck3_2};
        duckObjects4 = new ImageView[]{duck4_1, duck4_2};
        duckObjects5 = new ImageView[]{duck5_1, duck5_2, duck5_3};
        duckObjects6 = new ImageView[]{duck6_1, duck6_2, duck6_3, duck6_4};

        // Set event handlers for mouse clicks
        gameScene.setOnMouseClicked(event -> handleMouseClick(ammoCountEventHandler, gameRoot, youWin, enter, gameOver,
                enterGameOver, escGameOver, duckObjects1, ammoCount, "assets/duck_black/7.png", "assets/duck_black/8.png", shotDuckCount1, 1, event));
        gameScene2.setOnMouseClicked(mouseEvent -> handleMouseClick(ammoCountEventHandler2, gameRoot2, youWin2, enter2,
                gameOver2, enterGameOver2, escGameOver2, duckObjects2, ammoCount2, "assets/duck_blue/7.png", "assets/duck_blue/8.png", shotDuckCount2, 1, mouseEvent));
        gameScene3.setOnMouseClicked(event -> handleMouseClick(ammoCountEventHandler3, gameRoot3, youWin3, enter3, gameOver3,
                enterGameOver3, escGameOver3, duckObjects3, ammoCount3, "assets/duck_blue/7.png", "assets/duck_blue/8.png", shotDuckCount3, 2, event));
        gameScene4.setOnMouseClicked(event -> handleMouseClick(ammoCountEventHandler4, gameRoot4, youWin4, enter4, gameOver4,
                enterGameOver4, escGameOver4, duckObjects4, ammoCount4, "assets/duck_red/7.png", "assets/duck_red/8.png", shotDuckCount4, 2, event));
        gameScene5.setOnMouseClicked(event -> handleMouseClick(ammoCountEventHandler5, gameRoot5, youWin5, enter5, gameOver5,
                enterGameOver5, escGameOver5, duckObjects5, ammoCount5, "assets/duck_red/7.png", "assets/duck_red/8.png", shotDuckCount5, 3, event));
        gameScene6.setOnMouseClicked(event -> handleMouseClick(ammoCountEventHandler6, gameRoot6, youWin6, enter6, gameOver6,
                enterGameOver6, escGameOver6, duckObjects6, ammoCount6, "assets/duck_black/7.png", "assets/duck_black/8.png", shotDuckCount6, 4, event));

        /**
         * Sets the event handler for key presses on the title scene.
         * If the Enter key is pressed, the scene is changed to the background selection scene.
         * If the Escape key is pressed, the application is closed.
         *
         * @param event KeyEvent representing the key press event
         */
        titleScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                primaryStage.setScene(backgroundSelectionScene);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });

        /**
         * Sets the event handler for key presses on the background selection scene.
         * Handles various key events to perform actions such as starting the game,
         * navigating options, and updating image views based on selected options.
         *
         * @param event KeyEvent representing the key press event
         */
        backgroundSelectionScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Start the game with selected configurations
                mediaPlayer.stop();
                music = new Media(new File("src/assets/effects/Intro.mp3").toURI().toString());
                mediaPlayer = new MediaPlayer(music);
                mediaPlayer.setCycleCount(1);
                mediaPlayer.setVolume(VOLUME);
                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaPlayer.stop();
                    primaryStage.setScene(gameScene);
                });
                mediaPlayer.play();

            } else if (event.getCode() == KeyCode.ESCAPE) {
                // Go back to the title screen
                primaryStage.setScene(titleScene);
                blinkTimeline.play();
                exitLabel.setVisible(true);
                instructionsLabel.setVisible(true);
            } else if (event.getCode() == KeyCode.LEFT) {
                // Select the previous background option
                selectedBackgroundIndex = (selectedBackgroundIndex - 1 + BACKGROUND_OPTIONS.length) % BACKGROUND_OPTIONS.length;
            } else if (event.getCode() == KeyCode.RIGHT) {
                // Select the next background option
                selectedBackgroundIndex = (selectedBackgroundIndex + 1) % BACKGROUND_OPTIONS.length;
            } else if (event.getCode() == KeyCode.UP) {
                // Select the previous crosshair option
                selectedCrosshairIndex = (selectedCrosshairIndex - 1 + CROSSHAIR_OPTIONS.length) % CROSSHAIR_OPTIONS.length;
            } else if (event.getCode() == KeyCode.DOWN) {
                // Select the next crosshair option
                selectedCrosshairIndex = (selectedCrosshairIndex + 1) % CROSSHAIR_OPTIONS.length;
            }
            // Update image views with selected options
            crosshairSelection.setImage(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));
            backgroundSelection.setImage(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameForeground.setImage(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameCrosshair.setImage(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));

            gameBackground2.setImage(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameForeground2.setImage(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameCrosshair2.setImage(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));

            gameBackground3.setImage(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameForeground3.setImage(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameCrosshair3.setImage(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));

            gameBackground4.setImage(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameForeground4.setImage(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameCrosshair4.setImage(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));

            gameBackground5.setImage(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameForeground5.setImage(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameCrosshair5.setImage(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));

            gameBackground6.setImage(new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameForeground6.setImage(new Image(Objects.requireNonNull(getClass().getResource(FOREGROUND_SELECTION_PATH + BACKGROUND_OPTIONS[selectedBackgroundIndex])).toExternalForm()));
            gameCrosshair6.setImage(new Image(Objects.requireNonNull(getClass().getResource(CRROSHAIR_SELECTION_PATH + CROSSHAIR_OPTIONS[selectedCrosshairIndex])).toExternalForm()));


            // Create a new WritableImage with transparent background
            WritableImage transparentImage = new WritableImage(
                    (int) gameCrosshair.getBoundsInLocal().getWidth(),
                    (int) gameCrosshair.getBoundsInLocal().getHeight()
            );

            // Get the pixel writer for the transparent image
            PixelWriter pixelWriter = transparentImage.getPixelWriter();

            // Set all pixels of the transparent image to transparent
            for (int x = 0; x < transparentImage.getWidth(); x++) {
                for (int y = 0; y < transparentImage.getHeight(); y++) {
                    pixelWriter.setColor(x, y, Color.TRANSPARENT);
                }
            }

            // Draw the gameCrosshair onto the transparent image
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            gameCrosshair.snapshot(params, transparentImage);

            // Calculate the hotspot position
            double hotspotX = transparentImage.getWidth() / 2.0;
            double hotspotY = transparentImage.getHeight() / 2.0;

            // Create the custom cursor with the transparent image and set the hotspot
            Cursor customCursor = new ImageCursor(transparentImage, hotspotX, hotspotY);

            gameScene.setCursor(customCursor);
            gameScene2.setCursor(customCursor);
            gameScene3.setCursor(customCursor);
            gameScene4.setCursor(customCursor);
            gameScene5.setCursor(customCursor);
            gameScene6.setCursor(customCursor);
        });

        /**
         * Sets the event handler for key presses on the game scene.
         * Handles various key events to perform actions such as advancing to the next level,
         * restarting the game, or returning to the title screen.
         *
         * @param event KeyEvent representing the key press event
         */
        gameScene.setOnKeyPressed(event -> {
            if (areBirdsShot && event.getCode() == KeyCode.ENTER) {
                // Start the next level
                mediaPlayer.stop();
                areBirdsShot = false;
                gameRoot.getChildren().removeAll(youWin, enter);
                gameRoot.getChildren().add(duck);
                resetAmmoCount.handle(new ActionEvent());
                gameRoot.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene2);
                animateBirdCross(duck2, ducks2, moveLeft, moveUp);

            } else if (!areBirdsShot && ammoCount == 0 && event.getCode() == KeyCode.ENTER) {
                // Restart the game after game over
                mediaPlayer.stop();
                gameRoot.getChildren().removeAll(gameOver, enterGameOver, escGameOver);
                resetAmmoCount.handle(new ActionEvent());
                duck.setTranslateX(0);
                gameRoot.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene);

            } else if (ammoCount == 0 && !areBirdsShot && event.getCode() == KeyCode.ESCAPE) {
                // Return to the title screen after game over
                mediaPlayer.stop();
                gameRoot.getChildren().removeAll(gameOver, enterGameOver, escGameOver);
                resetAmmoCount.handle(new ActionEvent());
                gameRoot.getChildren().remove(deadDuck);
                primaryStage.setScene(titleScene);
            }
        });

        /**
         * Sets the event handler for key presses on the second game scene.
         * Handles various key events to perform actions such as advancing to the next level,
         * restarting the game, or returning to the title screen.
         *
         * @param event KeyEvent representing the key press event
         */
        gameScene2.setOnKeyPressed(event -> {
            if (areBirdsShot && event.getCode() == KeyCode.ENTER) {
                // Start the next level
                mediaPlayer.stop();
                areBirdsShot = false;
                gameRoot2.getChildren().removeAll(youWin2, enter2);
                gameRoot2.getChildren().add(1, duck2);
                resetAmmoCount2.handle(new ActionEvent());
                gameRoot2.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene3);
                animateBird(duck3_1, ducks3_1, moveRight2_1);
                animateBird(duck3_2, ducks3_2, moveRight2_2);

            } else if (!areBirdsShot && ammoCount2 == 0 && event.getCode() == KeyCode.ENTER) {
                // Restart the game after game over
                mediaPlayer.stop();
                gameRoot2.getChildren().removeAll(gameOver2, enterGameOver2, escGameOver2);
                resetAmmoCount2.handle(new ActionEvent());
                gameRoot2.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene);

            } else if (ammoCount2 == 0 && !areBirdsShot && event.getCode() == KeyCode.ESCAPE) {
                // Return to the title screen after game over
                mediaPlayer.stop();
                gameRoot2.getChildren().removeAll(gameOver2, enterGameOver2, escGameOver2);
                resetAmmoCount2.handle(new ActionEvent());
                gameRoot2.getChildren().remove(deadDuck);
                primaryStage.setScene(titleScene);
            }
        });

        /**
         * Sets the event handler for key presses on the third game scene.
         * Handles various key events to perform actions such as advancing to the next level,
         * restarting the game, or returning to the title screen.
         *
         * @param event KeyEvent representing the key press event
         */
        gameScene3.setOnKeyPressed(event -> {
            if (areBirdsShot && event.getCode() == KeyCode.ENTER) {
                // Start the next level
                mediaPlayer.stop();
                areBirdsShot = false;
                gameRoot3.getChildren().removeAll(youWin3, enter3);
                for (ImageView duck : duckObjects3) {
                    gameRoot3.getChildren().add(1, duck);
                }
                gameRoot3.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene4);
                animateBirdCross(duck4_1, ducks4_1, moveLeft2_1, moveUp2_1);
                animateBirdCross(duck4_2, ducks4_2, moveLeft2_2, moveUp2_2);

            } else if (!areBirdsShot && ammoCount3 == 0 && event.getCode() == KeyCode.ENTER) {
                // Restart the game after game over
                mediaPlayer.stop();
                gameRoot3.getChildren().removeAll(gameOver3, enterGameOver3, escGameOver3);
                for (ImageView duck : duckObjects3) {
                    gameRoot3.getChildren().remove(duck);
                }
                for (ImageView duck : duckObjects3) {
                    gameRoot3.getChildren().add(1, duck);
                }
                resetAmmoCount3.handle(new ActionEvent());
                gameRoot3.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene);

            } else if (ammoCount3 == 0 && !areBirdsShot && event.getCode() == KeyCode.ESCAPE) {
                // Return to the title screen after game over
                mediaPlayer.stop();
                gameRoot3.getChildren().removeAll(gameOver3, enterGameOver3, escGameOver3);
                for (ImageView duck : duckObjects3) {
                    gameRoot3.getChildren().remove(duck);
                }
                for (ImageView duck : duckObjects3) {
                    gameRoot3.getChildren().add(1, duck);
                }
                resetAmmoCount3.handle(new ActionEvent());
                gameRoot3.getChildren().remove(deadDuck);
                primaryStage.setScene(titleScene);
            }
        });

        /**
         * Sets the event handler for key presses on the fourth game scene.
         * Handles various key events to perform actions such as advancing to the next level,
         * restarting the game, or returning to the title screen.
         *
         * @param event KeyEvent representing the key press event
         */
        gameScene4.setOnKeyPressed(event -> {
            if (areBirdsShot && event.getCode() == KeyCode.ENTER) {
                // Start the next level
                mediaPlayer.stop();
                areBirdsShot = false;
                gameRoot4.getChildren().removeAll(youWin4, enter4);
                for (ImageView duck : duckObjects4) {
                    gameRoot4.getChildren().add(1, duck);
                }
                resetAmmoCount4.handle(new ActionEvent());
                gameRoot4.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene5);
                animateBird(duck5_1, ducks5_1, moveRight3_1);
                animateBird(duck5_2, ducks5_2, moveRight3_2);
                animateBirdCross(duck5_3, ducks5_3, moveLeft3, moveUp3);

            } else if (!areBirdsShot && ammoCount4 == 0 && event.getCode() == KeyCode.ENTER) {
                // Restart the game after game over
                mediaPlayer.stop();
                gameRoot4.getChildren().removeAll(gameOver4, enterGameOver4, escGameOver4);
                for (ImageView duck : duckObjects4) {
                    gameRoot4.getChildren().remove(duck);
                }
                for (ImageView duck : duckObjects4) {
                    gameRoot4.getChildren().add(1, duck);
                }
                resetAmmoCount4.handle(new ActionEvent());
                gameRoot4.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene);

            } else if (ammoCount4 == 0 && !areBirdsShot && event.getCode() == KeyCode.ESCAPE) {
                // Return to the title screen after game over
                mediaPlayer.stop();
                gameRoot4.getChildren().removeAll(gameOver4, enterGameOver4, escGameOver4);
                for (ImageView duck : duckObjects4) {
                    gameRoot4.getChildren().remove(duck);
                }
                for (ImageView duck : duckObjects4) {
                    gameRoot4.getChildren().add(1, duck);
                }
                resetAmmoCount4.handle(new ActionEvent());
                gameRoot4.getChildren().remove(deadDuck);
                primaryStage.setScene(titleScene);
            }
        });

        /**
         * Sets the event handler for key presses on the fifth game scene.
         * Handles various key events to perform actions such as advancing to the next level,
         * restarting the game, or returning to the title screen.
         *
         * @param event KeyEvent representing the key press event
         */
        gameScene5.setOnKeyPressed(event -> {
            if (areBirdsShot && event.getCode() == KeyCode.ENTER) {
                // Start the next level
                mediaPlayer.stop();
                areBirdsShot = false;
                gameRoot5.getChildren().removeAll(youWin5, enter5);
                for (ImageView duck : duckObjects5) {
                    gameRoot5.getChildren().add(1, duck);
                }
                gameRoot5.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene6);
                animateBird(duck6_1, ducks6_1, moveRight4_1);
                animateBird(duck6_2, ducks6_2, moveRight4_2);
                animateBirdCross(duck6_3, ducks6_3, moveLeft4_1, moveUp4_1);
                animateBirdCross(duck6_4, ducks6_4, moveLeft4_2, moveUp4_2);

            } else if (!areBirdsShot && ammoCount5 == 0 && event.getCode() == KeyCode.ENTER) {
                // Restart the game after game over
                gameRoot5.getChildren().removeAll(gameOver5, enterGameOver5, escGameOver5);
                for (ImageView duck : duckObjects5) {
                    gameRoot5.getChildren().remove(duck);
                }
                for (ImageView duck : duckObjects5) {
                    gameRoot5.getChildren().add(1, duck);
                }
                resetAmmoCount5.handle(new ActionEvent());
                gameRoot5.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene);

            } else if (ammoCount5 == 0 && !areBirdsShot && event.getCode() == KeyCode.ESCAPE) {
                // Return to the title screen after game over
                gameRoot5.getChildren().removeAll(gameOver5, enterGameOver5, escGameOver5);
                for (ImageView duck : duckObjects5) {
                    gameRoot5.getChildren().remove(duck);
                }
                for (ImageView duck : duckObjects5) {
                    gameRoot5.getChildren().add(1, duck);
                }
                resetAmmoCount5.handle(new ActionEvent());
                gameRoot5.getChildren().remove(deadDuck);
                primaryStage.setScene(titleScene);
            }
        });

        gameScene6.setOnKeyPressed(event -> {
            if (areBirdsShot && event.getCode() == KeyCode.ENTER) {
                // Start the game from the beginning
                mediaPlayer.stop();
                areBirdsShot = false;
                gameRoot6.getChildren().removeAll(youWin6, enter6, escGameOver6);
                for (ImageView duck : duckObjects6) {
                    gameRoot6.getChildren().add(1, duck);
                }
                gameRoot6.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene);

            } else if (areBirdsShot && event.getCode() == KeyCode.ESCAPE) {
                // Return to the title screen
                areBirdsShot = false;
                gameRoot6.getChildren().removeAll(youWin6, enter6, escGameOver6);
                for (ImageView duck : duckObjects6) {
                    gameRoot6.getChildren().add(1, duck);
                }
                resetAmmoCount6.handle(new ActionEvent());
                gameRoot6.getChildren().remove(deadDuck);
                primaryStage.setScene(titleScene);


            } else if (!areBirdsShot && ammoCount6 == 0 && event.getCode() == KeyCode.ENTER) {
                // Restart the game after game over
                gameRoot6.getChildren().removeAll(gameOver6, enterGameOver6, escGameOver6);
                for (ImageView duck : duckObjects6) {
                    gameRoot6.getChildren().remove(duck);
                }
                for (ImageView duck : duckObjects6) {
                    gameRoot6.getChildren().add(1, duck);
                }
                resetAmmoCount6.handle(new ActionEvent());
                gameRoot6.getChildren().remove(deadDuck);
                primaryStage.setScene(gameScene);

            } else if (ammoCount6 == 0 && !areBirdsShot && event.getCode() == KeyCode.ESCAPE) {
                // Return to the title screen after game over
                gameRoot6.getChildren().removeAll(gameOver6, enterGameOver6, escGameOver6);
                for (ImageView duck : duckObjects6) {
                    gameRoot6.getChildren().remove(duck);
                }
                for (ImageView duck : duckObjects6) {
                    gameRoot6.getChildren().add(1, duck);
                }
                resetAmmoCount6.handle(new ActionEvent());
                gameRoot6.getChildren().remove(deadDuck);
                primaryStage.setScene(titleScene);
            }
        });

        gameRoot.requestFocus();

    }

    /**
     * Handles the mouse click event for shooting ducks.
     *
     * @param ammoCountEventHandler EventHandler for decrementing ammo count
     * @param gameRoot              StackPane representing the game root
     * @param youWin                Text for "You Win" message
     * @param enter                 Text for enter key instructions
     * @param gameOver              Text for "Game Over" message
     * @param enterGameOver         Text for enter key instructions in game over state
     * @param escGameOver           Text for escape key instructions in game over state
     * @param ducks                 Array of ImageView objects representing the ducks
     * @param ammoCount             Current ammo count
     * @param path                  Path to the image for shot duck
     * @param path2                 Path to the image for falling duck animation
     * @param shotDuckCount         Array to store the count of shot ducks
     * @param totalDuck             Total number of ducks in the level
     * @param event                 MouseEvent object representing the mouse click event
     */
    private void handleMouseClick(EventHandler<ActionEvent> ammoCountEventHandler, StackPane gameRoot, Text youWin, Text enter,
                                  Text gameOver, Text enterGameOver, Text escGameOver, ImageView[] ducks, int ammoCount, String path, String path2,
                                  int[] shotDuckCount, int totalDuck, MouseEvent event) {
        if (ammoCount > 0 && !areBirdsShot) {
            music = new Media(new File("src/assets/effects/GunShot.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(music);
            mediaPlayer.setCycleCount(1);
            mediaPlayer.setVolume(VOLUME);
            mediaPlayer.play();
            for (ImageView duck : ducks) {
                if (!areBirdsShot && duck.getBoundsInParent().contains(event.getX(), event.getY())) {
                    shotDuckCount[0]++;
                    gameRoot.getChildren().remove(duck);

                    deadDuck = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm()));

                    if (!moveRight[0]) {
                        deadDuck.setScaleX(-1);
                    }
                    deadDuck.setFitWidth(BIRD_WIDTH);
                    deadDuck.setFitHeight(BIRD_HEIGHT);

                    deadDuck.setTranslateX(duck.getTranslateX());
                    deadDuck.setTranslateY(duck.getTranslateY());

                    animateFalling(path2, gameRoot);
                    gameRoot.getChildren().add(1, deadDuck);
                    music = new Media(new File("src/assets/effects/DuckFalls.mp3").toURI().toString());
                    mediaPlayer = new MediaPlayer(music);
                    mediaPlayer.setVolume(VOLUME);
                    mediaPlayer.setCycleCount(1);
                    mediaPlayer.play();


                } else if (ammoCount == 1) {
                    if (!gameRoot.getChildren().contains(gameOver)) {
                        gameRoot.getChildren().addAll(gameOver, enterGameOver, escGameOver);
                        music = new Media(new File("src/assets/effects/GameOver.mp3").toURI().toString());
                        mediaPlayer = new MediaPlayer(music);
                        mediaPlayer.setVolume(VOLUME);
                        mediaPlayer.setCycleCount(1);
                        mediaPlayer.play();
                    }
                }
            }
            if (shotDuckCount[0] != totalDuck) {
                ammoCountEventHandler.handle(new ActionEvent());
                ammoCount--;
            }

            if (shotDuckCount[0] == totalDuck) {
                areBirdsShot = true;
                gameRoot.getChildren().addAll(youWin, enter);
                shotDuckCount[0] = 0;
                if (totalDuck == 4) {
                    gameRoot.getChildren().add(escGameOver);
                    music = new Media(new File("src/assets/effects/GameCompleted.mp3").toURI().toString());
                    mediaPlayer = new MediaPlayer(music);
                    mediaPlayer.setVolume(VOLUME);
                    mediaPlayer.setCycleCount(1);
                    mediaPlayer.play();
                } else {
                    music = new Media(new File("src/assets/effects/LevelCompleted.mp3").toURI().toString());
                    mediaPlayer = new MediaPlayer(music);
                    mediaPlayer.setVolume(VOLUME);
                    mediaPlayer.setCycleCount(1);
                    mediaPlayer.play();
                }
            }
        }
    }

    /**
     * Animates the bird by moving it horizontally and cycling through different frames of the bird image.
     *
     * @param duck      ImageView representing the bird
     * @param ducks     Array of Image objects representing the frames of the bird animation
     * @param moveRight Boolean flag indicating the direction of bird movement (true for moving right, false for moving left)
     */
    private void animateBird(ImageView duck, Image[] ducks, boolean[] moveRight) {
        final int[] frameIndex = {0};

        KeyFrame birdKeyFrame = new KeyFrame(Duration.seconds(0.13), event -> {
            double birdX = duck.getTranslateX();
            double birdWidth = duck.getBoundsInParent().getWidth();

            if (birdX + birdWidth <= -180 * SCALE / 2 || birdX + birdWidth >= 320 * SCALE / 2) {
                moveRight[0] = !moveRight[0];
                duck.setScaleX(moveRight[0] ? 1 : -1);
            }
            if (moveRight[0]) {
                duck.setTranslateX(birdX + 6 * SCALE);
            } else {
                duck.setTranslateX(birdX - 6 * SCALE);
            }

            duck.setImage(ducks[frameIndex[0]]);
            frameIndex[0] = (frameIndex[0] + 1) % 3;
        });

        Timeline birdAnimation = new Timeline(birdKeyFrame);
        birdAnimation.setCycleCount(Animation.INDEFINITE);
        birdAnimation.play();

    }

    /**
     * Animates the falling of a dead duck by updating its image and translating it downwards.
     *
     * @param path     Path to the image file for the falling duck
     * @param gameRoot StackPane representing the game scene root node
     */
    private void animateFalling(String path, StackPane gameRoot) {

        ImageView fallingImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm()));

        KeyFrame birdKeyFramede = new KeyFrame(Duration.seconds(0.3), event -> {
            deadDuck.setImage(fallingImage.getImage());
        });

        Timeline birdAnimation1 = new Timeline(birdKeyFramede);
        birdAnimation1.setCycleCount(1);
        birdAnimation1.play();

        KeyFrame birdKeyFrame = new KeyFrame(Duration.seconds(0.3), event -> {
            deadDuck.setTranslateY(deadDuck.getTranslateY() + 7 * SCALE); // Kuşun Y konumunu aşağı doğru güncelleyin
        });

        Timeline birdAnimation = new Timeline(birdKeyFrame);
        birdAnimation.setCycleCount(Animation.INDEFINITE);
        birdAnimation.play();

    }

    /**
     * Animates the movement of a cross-flying bird by updating its position, orientation, and image.
     *
     * @param duck2    ImageView representing the cross-flying bird
     * @param ducks2   Array of Images representing the frames of the bird's animation
     * @param moveLeft Array containing a single boolean indicating the bird's horizontal movement direction
     * @param moveUp   Array containing a single boolean indicating the bird's vertical movement direction
     */
    private void animateBirdCross(ImageView duck2, Image[] ducks2, boolean[] moveLeft, boolean[] moveUp) {
        final int[] frameIndex = {0};

        KeyFrame birdKeyFrame = new KeyFrame(Duration.seconds(0.13), event -> {
            double birdX = duck2.getTranslateX();
            double birdY = duck2.getTranslateY();
            double birdWidth = duck2.getBoundsInParent().getWidth();
            double birdHeight = duck2.getBoundsInParent().getHeight();

            if (birdY + birdHeight <= -180 * SCALE / 2) {
                moveUp[0] = false;
                duck2.setScaleY(-1);
            } else if (birdX + birdWidth <= -180 * SCALE / 2) {
                moveLeft[0] = false;
                duck2.setScaleX(1);
            } else if (birdY + birdHeight >= 320 * SCALE / 2) {
                moveUp[0] = true;
                duck2.setScaleY(1);
            } else if (birdX + birdWidth >= 320 * SCALE / 2) {
                moveLeft[0] = true;
                duck2.setScaleX(-1);
            }
            if (moveLeft[0] && moveUp[0]) {
                duck2.setTranslateX(birdX - 6 * SCALE);
                duck2.setTranslateY(birdY - 6 * SCALE);
            } else if (moveLeft[0] && !moveUp[0]) {
                duck2.setTranslateX(birdX - 6 * SCALE);
                duck2.setTranslateY(birdY + 6 * SCALE);
            } else if (!moveLeft[0] && moveUp[0]) {
                duck2.setTranslateX(birdX + 6 * SCALE);
                duck2.setTranslateY(birdY - 6 * SCALE);
            } else if (!moveLeft[0] && !moveUp[0]) {
                duck2.setTranslateX(birdX + 6 * SCALE);
                duck2.setTranslateY(birdY + 6 * SCALE);
            }

            duck2.setImage(ducks2[frameIndex[0]]);
            frameIndex[0] = (frameIndex[0] + 1) % 3;
        });

        Timeline birdAnimation = new Timeline(birdKeyFrame);
        birdAnimation.setCycleCount(Animation.INDEFINITE);
        birdAnimation.play();
    }
}