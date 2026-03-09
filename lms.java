package ma;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class lms extends Application {

    private String[] words = {"apple", "banana", "computer", "practice", "language",
            "keyboard", "university", "knowledge", "success", "improvement"};
    private Random random = new Random();
    private String currentWord;
    private int correct = 0, wrong = 0;
    private Label wordLabel, resultLabel, statsLabel;
    private TextField inputField;

    @Override
    public void start(Stage stage) {
        wordLabel = new Label("Click 'New Word' to start");
        wordLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        inputField = new TextField();
        inputField.setPromptText("Type the word here");

        Button checkButton = new Button("Check");
        Button newWordButton = new Button("New Word");

        String buttonStyle = "-fx-background-color: purple; " +
                "-fx-text-fill: white; " +
                "-fx-border-radius: 4px; " +
                "-fx-background-radius: 4px; " +
                "-fx-padding: 8px 16px; " +
                "-fx-font-size: 14px;";

        checkButton.setStyle(buttonStyle);
        newWordButton.setStyle(buttonStyle);

        resultLabel = new Label();
        statsLabel = new Label("Correct: 0 | Wrong: 0");

        checkButton.setOnAction(e -> checkWord(inputField.getText()));
        newWordButton.setOnAction(e -> newWord());

        inputField.setOnAction(e -> checkWord(inputField.getText()));

        VBox root = new VBox(15, wordLabel, inputField, checkButton, newWordButton, resultLabel, statsLabel);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20px;");

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("English Typing Practice");
        stage.setScene(scene);
        stage.show();
    }

    private void newWord() {
        currentWord = words[random.nextInt(words.length)];
        wordLabel.setText("Listen and type the word!");
        inputField.clear();
        resultLabel.setText("");
        speakWord(currentWord);
    }

    private void checkWord(String input) {
        if (currentWord == null) {
            resultLabel.setText("⚠️ Please click 'New Word' first!");
            return;
        }
        if (input == null || input.trim().isEmpty()) {
            resultLabel.setText("⚠️ Please type a word before checking!");
            return;
        }
        if (input.trim().equalsIgnoreCase(currentWord)) {
            resultLabel.setText("✅ Correct!");
            correct++;
        } else {
            resultLabel.setText("❌ Wrong! The word was: " + currentWord);
            wrong++;
        }
        statsLabel.setText("Correct: " + correct + " | Wrong: " + wrong);
    }

    private void speakWord(String word) {
        new Thread(() -> {
            try {

                String script = "Add-Type -AssemblyName System.Speech; " +
                        "$synth = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                        "$synth.Speak('" + word + "');";

                ProcessBuilder pb = new ProcessBuilder(
                        "powershell", "-Command", script
                );
                pb.redirectErrorStream(true);
                Process process = pb.start();
                process.waitFor();

            } catch (Exception e) {
                System.err.println("TTS Error: " + e.getMessage());
            }
        }).start();
    }

    public static void main(String[] args) {
        launch();
    }
}