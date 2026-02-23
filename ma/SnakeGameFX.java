package ma;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class SnakeGameFX extends Application {

    static final int width = 1000;
    static final int height = 600;
    static final int unit_s = 25;
    static final int Game_U = (width * height) / (unit_s * unit_s);
    static final int DELAY = 100;

    int[] x = new int[Game_U];
    int[] y = new int[Game_U];
    int bodyP = 6;
    int applesE = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;

    Random random = new Random();
    AnimationTimer timer;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            switch (code) {
                case LEFT:
                    if (direction != 'R') direction = 'L';
                    break;
                case RIGHT:
                    if (direction != 'L') direction = 'R';
                    break;
                case UP:
                    if (direction != 'D') direction = 'U';
                    break;
                case DOWN:
                    if (direction != 'U') direction = 'D';
                    break;
            }
        });

        stage.setScene(scene);
        stage.setTitle("Snake Game");
        stage.getIcons().add(new Image("m.png"));
        stage.show();

        startGame(gc);
    }

    public void startGame(GraphicsContext gc) {
        newApple();
        running = true;

        timer = new AnimationTimer() {
            long lastU = 0;

            @Override
            public void handle(long now) {
                if (now - lastU >= DELAY * 1_000_000) {
                    if (running) {
                        move();
                        checkApple();
                        checkCollisions();
                    }
                    draw(gc);
                    lastU = now;
                }
            }
        };
        timer.start();
    }
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        if (running) {

            gc.setFill(Color.RED);
            gc.fillOval(appleX, appleY, unit_s, unit_s);

            for (int i = 0; i < bodyP; i++) {
                if (i == 0) {
                    gc.setFill(Color.WHITE);
                } else {
                    gc.setFill(Color.GOLD);
                }
                gc.fillRect(x[i], y[i], unit_s, unit_s);
            }

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", 20));
            gc.fillText("Points: " + applesE, 10, 20);
        } else {
            gameOver(gc);
        }
    }
    public void newApple() {
        appleX = random.nextInt(width / unit_s) * unit_s;
        appleY = random.nextInt(height / unit_s) * unit_s;
    }

    public void move() {
        for (int i = bodyP; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] -= unit_s;
                break;
            case 'D':
                y[0] += unit_s;
                break;
            case 'L':
                x[0] -= unit_s;
                break;
            case 'R':
                x[0] += unit_s;
                break;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyP++;
            applesE++;
            newApple();
        }
    }

    public void checkCollisions() {

        for (int i = bodyP; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        if (x[0] < 0 || x[0] >= width || y[0] < 0 || y[0] >= height) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Arial", 40));
        gc.fillText("GAME OVER", width / 2.0 - 150, height / 2.0);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 20));
        gc.fillText("Total Points: " + applesE, width / 2.0 - 60, height / 2.0 + 40);
    }

    public static void main(String[] args) {
        launch(args);

    }
}
