package ma;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class KapObjektin extends Application {

    private Rectangle player;
    private Circle object;
    private int score = 0;
    private Label scoreLabel;

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        pane.setPrefSize(600, 400);

        player = new Rectangle(50, 20, Color.BLUE);
        player.setLayoutX(275);
        player.setLayoutY(370);

        object = new Circle(10, Color.RED);
        resetObject();

        scoreLabel = new Label("Pikët: 0");
        scoreLabel.setLayoutX(10);
        scoreLabel.setLayoutY(10);

        pane.getChildren().addAll(player, object, scoreLabel);

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Kap Objektin");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()) {
                case LEFT:
                    if (player.getLayoutX() > 0)
                        player.setLayoutX(player.getLayoutX() - 10);
                    break;
                case RIGHT:
                    if (player.getLayoutX() < pane.getWidth() - player.getWidth())
                        player.setLayoutX(player.getLayoutX() + 10);
                    break;
                default:
                    break;
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), ev -> {
            object.setLayoutY(object.getLayoutY() + 5);
            if (object.getBoundsInParent().intersects(player.getBoundsInParent())) {
                score++;
                scoreLabel.setText("Pikët: " + score);
                resetObject();
            }
            if (object.getLayoutY() > pane.getHeight()) {
                resetObject();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void resetObject() {
        Random rand = new Random();
        object.setLayoutX(rand.nextInt(580) + 10);
        object.setLayoutY(0);
  }
    public static void main(String[] args) {
        launch(args);
    }
}
