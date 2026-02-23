package ma;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class m1 extends Application {
    @Override
    public void start(Stage primaryStage){
        Group ro = new Group();
        Stage st = new Stage();
        Scene sc = new Scene(ro,400,400);
        sc.setFill(Color.BLACK);
        st.setTitle("Marjo");
        st.setScene(sc);
        st.setResizable(false);
        st.setFullScreen(true);
        st.setFullScreenExitHint("You can't escape unless you press Esc");
        st.show();

        Image io = new Image("Screenshot 2025-05-29 213145.png");
        st.getIcons().add(io);

    }
    public static void main(String[] args) {
        launch(args);
    }
}
