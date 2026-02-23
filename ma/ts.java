package ma;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;


public class ts extends Application {
    @Override
    public void start(Stage primaryStage) {
        CheckBox o1 = new CheckBox("Opsioni 1");
        CheckBox o2 = new CheckBox("Opsioni 2");
        CheckBox o3 = new CheckBox("Opsioni 3");

        o1.setOnAction(event ->{
            if(o1.isSelected()){
                System.out.println("Opsioni 1 është zgjedhur");
            } else {
                System.out.println("Opsioni 1 është ç'zgjedhur");
            }
        });
        o2.setOnAction(event ->{
            if(o2.isSelected()){
                System.out.println("Opsioni 2 eshte zgjedhur");
            }else
                System.out.println("Opsioni 2 eshte c'zgjedhur");
        });
        o3.setOnAction(event ->{
            if(o3.isSelected()){
                System.out.println("Opsioni 3 eshte zgjedhur");
            }else
                System.out.println("Opsioni 3 eshte c'zgjedhur");
        });

        VBox ro = new VBox(10,o1,o2,o3);
        Scene sc = new Scene(ro,300,300);

        primaryStage.setTitle("Checkbox");
        primaryStage.setScene(sc);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}