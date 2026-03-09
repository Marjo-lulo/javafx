package ma;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class eb1 extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = WIDTH;
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = WIDTH / ROWS;

    private static final int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3;

    GraphicsContext gc;
    private List<Point> snakeBody = new ArrayList<>();
    private Point snakeHead;
    private Image foodImage;
    private int foodX, foodY;
    private boolean gameOver = false;
    private int currentDirection = RIGHT;
    private int score = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snake Game");
        primaryStage.getIcons().add(new Image("ebi.png"));

        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5 - i, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if ((code == KeyCode.RIGHT || code == KeyCode.D) && currentDirection != LEFT) {
                currentDirection = RIGHT;
            } else if ((code == KeyCode.LEFT || code == KeyCode.A) && currentDirection != RIGHT) {
                currentDirection = LEFT;
            } else if ((code == KeyCode.UP || code == KeyCode.W) && currentDirection != DOWN) {
                currentDirection = UP;
            } else if ((code == KeyCode.DOWN || code == KeyCode.S) && currentDirection != UP) {
                currentDirection = DOWN;
            }
        });
        newApple();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void run(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Arial", 70));
            gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
            return;
        }
        drawBackground();
        drawFood();
        checkApple();
        moveSnake();
        drawSnake();
        drawScore();
    }
    private void drawBackground() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gc.setFill((i + j) % 2 == 0 ? Color.web("AAD751") : Color.web("A2D149"));
                gc.fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }
    private Random random = new Random();
    private void newApple() {
        while (true) {
            int x = random.nextInt(COLUMNS);
            int y = random.nextInt(ROWS);
            Point applePoint = new Point(x, y);

            boolean onSnake = false;
            for (Point p : snakeBody) {
                if (p.equals(applePoint)) {
                    onSnake = true;
                    break;
                }
            }
            if (!onSnake) {
                foodX = x;
                foodY = y;
                break;
            }
        }
    }

    private boolean checkApple() {
        Point head = snakeBody.get(0);
        if (head.x == foodX && head.y == foodY) {

            Point tail = snakeBody.get(snakeBody.size() - 1);
            snakeBody.add(new Point(tail.x, tail.y));

            newApple();
            score += 5;
            return true;
        }
        return false;
    }
    private void drawFood() {
        gc.setFill(Color.RED);
        gc.fillOval(foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }
    private void drawSnake() {
        gc.setFill(Color.BLACK);
        for (int i = 0; i < snakeBody.size(); i++) {
            Point p = snakeBody.get(i);
            gc.fillRoundRect(p.x * SQUARE_SIZE, p.y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, 20, 20);
        }
    }
    private void moveSnake() {
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
        switch (currentDirection) {
            case RIGHT -> snakeHead.x++;
            case LEFT -> snakeHead.x--;
            case UP -> snakeHead.y--;
            case DOWN -> snakeHead.y++;
        }
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x >= COLUMNS || snakeHead.y >= ROWS) {
            gameOver = true;
            return;
        }
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.equals(snakeBody.get(i))) {
                gameOver = true;
                return;
            }
        }
        if (snakeHead.x == foodX && snakeHead.y == foodY) {
            snakeBody.add(new Point(-1, -1));
            newApple();
            score += 5;
        }
    }
    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

