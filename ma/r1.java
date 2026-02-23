package ma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class r1 extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent ro = FXMLLoader.load(getClass().getResource("m1.fxml"));
        primaryStage.setTitle("Ma");
        primaryStage.setScene(new Scene(ro));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
