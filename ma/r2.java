package ma;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class r2 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent ro = FXMLLoader.load((getClass().getResource("c.fxml")));
        primaryStage.setTitle("Ma");
        primaryStage.setScene(new Scene(ro));
        primaryStage.show();

    }
    @FXML
    private Circle ci;
    private double x;
    private double y;

    public void up (ActionEvent e){
       ci.setCenterY(y=y-5);
     }
     public void right (ActionEvent e){
        ci.setCenterX(x+=5);
     }
    public void down (ActionEvent e){
        ci.setCenterY(y+=5);
    }
     public void left (ActionEvent e){
        ci.setCenterX(y-=5);
    }
    public static void main(String[] args) {
        launch(args);
    }
}


