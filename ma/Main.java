package ma;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        Group ro = new Group();
        Scene sc = new Scene(ro,600,600,Color.BLACK);
        Stage st = new Stage();

        Text tx = new Text();
        tx.setText("Hello Marjo");
        tx.setX(50);
        tx.setY(50);
        tx.setFont(Font.font("Verdana",25));

        Line li = new Line();
        li.setStartX(200);
        li.setStartY(200);
        li.setEndX(500);
        li.setEndY(200);
        li.setStrokeWidth(2);
        li.setStroke(Color.RED);
        li.setOpacity(0.5);
        li.setRotate(90);

        Rectangle ra = new Rectangle();
        ra.setX(100);
        ra.setY(100);
        ra.setWidth(100);
        ra.setHeight(100);
        ra.setStroke(Color.WHITESMOKE);
        ra.setFill(Color.RED);

        Polygon po = new Polygon();
        po.getPoints().setAll(500.0,500.0,400.0,300.0,500.0,300.0);
        po.setFill(Color.GOLD);

        Circle c = new Circle();
        c.setCenterX(250);
        c.setCenterY(250);
        c.setRadius(50);
        c.setFill(Color.WHITE);

        Image ia = new Image("Screenshot 2025-05-29 213145.png");
        ImageView img = new ImageView(ia);
        img.setX(150);
        img.setY(150);

        ro.getChildren().add(tx);
        ro.getChildren().add(li);
        ro.getChildren().add(ra);
        ro.getChildren().add(po);
        ro.getChildren().add(c);
        st.setScene(sc);
        st.show();

    }
    public static void main(String[] args) {
         launch(args);
    }
}